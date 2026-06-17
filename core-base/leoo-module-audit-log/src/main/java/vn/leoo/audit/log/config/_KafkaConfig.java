package vn.leoo.audit.log.config;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.FailedDeserializationInfo;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
@ConditionalOnProperty(
        name = "audit.mode",
        havingValue = "kafka"
)
@Component
public class _KafkaConfig {

    private static final String AUTO_COMMIT_INTERVAL_MS_CONFIG = "15000";
    private static final String AUTO_OFFSET_RESET_CONFIG = "earliest";
    private static final String P60000 = "60000";
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.properties.session.timeout.ms}")
    private String SESSION_TIMEOUT_MS_CONFIG;

    @Value("${spring.kafka.consumer.properties.heartbeat.interval.ms}")
    private String HEARTBEAT_INTERVAL_MS_CONFIG;

    @Value("${spring.kafka.consumer.properties.max.poll.interval.ms}")
    private String MAX_POLL_INTERVAL_MS_CONFIG;
    @Value("${spring.kafka.consumer.properties.max.poll.records}")
    private String MAX_POLL_RECORDS_CONFIG;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.listener.ack-mode}")
    private String ackMode;

    @Bean("jsonSerializerProducerFactoryAppAuditLog")
    public ProducerFactory<String, Object> jsonSerializerProducerFactoryAppAuditLog() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        JsonSerializer<Object> jsonSerializer = new JsonSerializer<>();
        jsonSerializer.setAddTypeInfo(false);
        configProps.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, P60000);
        configProps.put(ProducerConfig.METADATA_MAX_IDLE_CONFIG, P60000);
        configProps.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, P60000);
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), jsonSerializer);
    }

    @Bean("jsonSerializerTemplateAppAuditLog")
    public KafkaTemplate<String, Object> jsonSerializerTemplateAppAuditLog() {
        return new KafkaTemplate<>(jsonSerializerProducerFactoryAppAuditLog());
    }

    /* Điều tra hình sự đăng ký hồ sơ */
    @Bean
    public ConsumerFactory<String, Object> objectConsumerFactoryAppAuditLog() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        JsonDeserializer<Object> deserializer = new JsonDeserializer<>(Object.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);

        ErrorHandlingDeserializer<Object> errorHandlingDeserializer = new ErrorHandlingDeserializer<>(deserializer);
        errorHandlingDeserializer.setFailedDeserializationFunction(failedDeserializationInfo -> {
            this.handleErrorDeserializer(failedDeserializationInfo);
            return null;
        });

        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        //ConsumerConfig.
        // props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, SESSION_TIMEOUT_MS_CONFIG);
        if (ObjectUtils.isNotEmpty(SESSION_TIMEOUT_MS_CONFIG)) {
            props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, SESSION_TIMEOUT_MS_CONFIG);
        }

        if (ObjectUtils.isNotEmpty(HEARTBEAT_INTERVAL_MS_CONFIG)) {
            props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, HEARTBEAT_INTERVAL_MS_CONFIG);
        }
        if (ObjectUtils.isNotEmpty(MAX_POLL_INTERVAL_MS_CONFIG)) {
            props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, MAX_POLL_INTERVAL_MS_CONFIG);
        }
        if (ObjectUtils.isNotEmpty(MAX_POLL_RECORDS_CONFIG)) {
            props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, MAX_POLL_RECORDS_CONFIG);
        }
        if (ObjectUtils.isNotEmpty(AUTO_OFFSET_RESET_CONFIG)) {
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        }

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), errorHandlingDeserializer);
    }

    @Bean("objectListenerContainerFactoryAppAuditLog")
    public ConcurrentKafkaListenerContainerFactory<String, Object> objectListenerContainerFactoryAppAuditLog() {
        ConcurrentKafkaListenerContainerFactory<String, Object>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(objectConsumerFactoryAppAuditLog());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }

    /**
     * <h2>Phản hồi cho trường hợp định dạng bản tin lỗi không thể chạy vào hàm consumer</h2>
     * <p>
     * khiến cho kafka gửi lại bản tin liên tục, gây tràn bộ nhớ server</span>
     *
     * @param failedDeserializationInfo
     */
    public void handleErrorDeserializer(FailedDeserializationInfo failedDeserializationInfo) {
        try {
            ConsumerRecord<String, String> consumerRecord = new ConsumerRecord<>(
                    failedDeserializationInfo.getTopic(),
                    1, 123L, "", new String(failedDeserializationInfo.getData()));
            for (Header header : failedDeserializationInfo.getHeaders()) {
                consumerRecord.headers().add(header);
            }
            // String messageType = getMessageType(failedDeserializationInfo.getTopic(), failedDeserializationInfo.getHeaders());
            // consumeDeserializerErrorHandler(consumerRecord, failedDeserializationInfo.getException(), messageType);
        } catch (Exception ignore) {
        }
    }
}