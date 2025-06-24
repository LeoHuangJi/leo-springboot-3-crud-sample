/*
 * package vn.leoo.document.config;
 * 
 * import java.io.File; import java.io.IOException;
 * 
 * import javax.annotation.PreDestroy;
 * 
 * import org.apache.http.Header; import org.apache.http.HttpHeaders; import
 * org.apache.http.HttpHost; import org.apache.http.auth.AuthScope; import
 * org.apache.http.auth.UsernamePasswordCredentials; import
 * org.apache.http.impl.client.BasicCredentialsProvider; import
 * org.apache.http.impl.nio.client.HttpAsyncClientBuilder; import
 * org.apache.http.impl.nio.reactor.IOReactorConfig; import
 * org.elasticsearch.client.RestClient; import
 * org.elasticsearch.client.RestClientBuilder; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.core.env.Environment;
 * 
 * import co.elastic.clients.transport.TransportUtils;
 * 
 * import javax.net.ssl.SSLContext;
 * 
 * 
 * import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
 * import org.elasticsearch.client.RestHighLevelClient;
 * 
 * @Configuration public class ESRestClient { private final Environment env;
 * private String hostName; private int DEFAULT_ES_PORT = 9200; private String
 * DEFAULT_SCHEME_NAME = "http";
 * 
 * public ESRestClient(Environment env) { this.env = env; }
 * 
 * private RestHighLevelClient client;
 * 
 * @Bean public RestHighLevelClient getElasticSearchClient() throws IOException
 * {
 * 
 * String hostName = env.getProperty("elastic.ip"); String shemeName =
 * env.getProperty("elastic.default-scheme-name"); String userName =
 * env.getProperty("elastic.user"); String password =
 * env.getProperty("elastic.pass"); String certPath =
 * env.getProperty("elastic.cert.path");
 * 
 * int port = DEFAULT_ES_PORT;
 * 
 * try { port = Integer.parseInt(env.getProperty("elastic.port")); } catch
 * (Exception e) { }
 * 
 * BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
 * credsProv.setCredentials(AuthScope.ANY, new
 * UsernamePasswordCredentials(userName, password)); File certFile = new
 * File(certPath);
 * 
 * final SSLContext sslContext =
 * TransportUtils.sslContextFromHttpCaCrt(certFile);
 * 
 * RestClientBuilder builder = RestClient.builder(new HttpHost(hostName, port,
 * shemeName)); // RestClientBuilder builder = RestClient.builder(new
 * HttpHost("127.0.0.1", // port, "http")); // Create the low-level client
 * builder.setHttpClientConfigCallback(new HttpClientConfigCallback() {
 * 
 * @Override public HttpAsyncClientBuilder
 * customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) { return
 * httpClientBuilder.setDefaultCredentialsProvider(credsProv).setSSLContext(
 * sslContext)
 * 
 * .setDefaultIOReactorConfig(IOReactorConfig.custom().setIoThreadCount(1).build
 * ()); } }); builder.setDefaultHeaders(new Header[] { new
 * org.apache.http.message.BasicHeader(HttpHeaders.ACCEPT,
 * "application/vnd.elasticsearch+json;compatible-with=7"), new
 * org.apache.http.message.BasicHeader(HttpHeaders.CONTENT_TYPE,
 * "application/vnd.elasticsearch+json;compatible-with=7") });
 * builder.setRequestConfigCallback( requestConfigBuilder ->
 * requestConfigBuilder.setConnectTimeout(10000).setSocketTimeout(60000)
 * 
 * time of inactivity to wait for packets[data] to receive.
 * 
 * .setConnectionRequestTimeout(0)); // time to fetch a connection from the
 * connection pool 0 // for infinite. client = new RestHighLevelClient(builder);
 * return client; // And create the API client
 * 
 * }
 * 
 * @PreDestroy public void clientClose() { try { this.client.close(); } catch
 * (IOException e) {
 * 
 * } } }
 */