package vn.leoo.common.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import vn.leoo.common.component.SpringContext;
import vn.leoo.common.constants.TimeType;

public class RedisManager {
	private Environment env;
	private RedisTemplate<String, Object> template;
	private StringRedisTemplate strTemplate;
	private RedisAtomicLong uniqueNbr;
	private boolean stringExists = false;
	private boolean objectExists = false;

	@SuppressWarnings("unchecked")
	private RedisManager() {
		template = (RedisTemplate<String, Object>) SpringContext.getBean("redisTemplate");
		strTemplate = (StringRedisTemplate) SpringContext.getBean("strRedisTemplate");
		env = SpringContext.getBean(Environment.class);
	}

	static class SingletonHelper {
		static RedisManager INSTANCE = new RedisManager();
	}

	public static RedisManager getInstance() {
		RedisManager redisM = SingletonHelper.INSTANCE;
		if (redisM == null) {
			synchronized (RedisManager.class) {
				redisM = new RedisManager();
				SingletonHelper.INSTANCE = redisM;
			}
		}
		return redisM;
	}

	private void setTimeout(RedisTemplate<String, Object> template, String key, Integer timeout) {
		if (timeout != null) {
			String time_unit = env.getProperty("redis.time_unit");
			if (time_unit.equals(TimeType.SECONDS.getValue()))
				template.expire(key, timeout.longValue(), TimeUnit.SECONDS);
			else if (time_unit.equals(TimeType.MINUTES.getValue()))
				template.expire(key, timeout.longValue(), TimeUnit.MINUTES);
			else if (time_unit.equals(TimeType.HOURS.getValue()))
				template.expire(key, timeout.longValue(), TimeUnit.HOURS);
			else if (time_unit.equals(TimeType.DAYS.getValue()))
				template.expire(key, timeout.longValue(), TimeUnit.DAYS);
		}
	}

	private void setTimeout(StringRedisTemplate strTemplate, String key, Integer timeout) {
		if (timeout != null) {
			String time_unit = env.getProperty("redis.time_unit");
			if (time_unit.equals(TimeType.SECONDS.getValue()))
				strTemplate.expire(key, timeout.longValue(), TimeUnit.SECONDS);
			else if (time_unit.equals(TimeType.MINUTES.getValue()))
				strTemplate.expire(key, timeout.longValue(), TimeUnit.MINUTES);
			else if (time_unit.equals(TimeType.HOURS.getValue()))
				strTemplate.expire(key, timeout.longValue(), TimeUnit.HOURS);
			else if (time_unit.equals(TimeType.DAYS.getValue()))
				strTemplate.expire(key, timeout.longValue(), TimeUnit.DAYS);
		}
	}

	public void setString(String key, String value, Boolean expire) {
		strTemplate.opsForValue().set(key, value);
		if (expire.booleanValue()) {
			Integer timeout = Integer.parseInt(env.getProperty("redis.expire"));
			setTimeout(strTemplate, key, timeout);
		}
	}

	public String getString(String key) {
		String val = (String) strTemplate.opsForValue().get(key);

		return val;
	}

	public void setObject(String key, Object obj, Boolean expire) {
		template.opsForValue().set(key, obj);
		if (expire.booleanValue()) {
			Integer timeout = Integer.parseInt(env.getProperty("redis.expire"));
			setTimeout(template, key, timeout);
		}
	}

	public Object getObject(String key) {
		return template.opsForValue().get(key);
	}

	public void setHash(String key, Map<Object, Object> map, Integer timeout) {
		template.opsForHash().putAll(key, map);
		setTimeout(template, key, timeout);
	}

	public Map<Object, Object> getHash(String key) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = template.opsForHash().entries(key);

		return map;
	}

	public boolean exists(String key) {
		boolean status = false;
		Object obj = this.getObject(key);
		if (obj != null) {
			status = true;
			objectExists = true;
		} else {
			objectExists = false;
			String val = this.getString(key);
			if (val != null) {
				status = true;
				stringExists = true;
			} else {
				stringExists = false;
			}
		}
		return status;
	}

	public void clear(String key) {
		if (objectExists) {
			template.delete(key);
		} else {
			if (stringExists) {
				strTemplate.delete(key);
			}
		}
	}

	public void beginAtomicLong() {
		RedisConnectionFactory connectionFactory = (RedisConnectionFactory) SpringContext.getBean("connectionFactory");
		uniqueNbr = new RedisAtomicLong("RedisUniqueNbr", connectionFactory, 0);
	}

	public Long nextAtomicLong() {
		Long l = Long.valueOf(uniqueNbr.incrementAndGet());

		return l;
	}
}
