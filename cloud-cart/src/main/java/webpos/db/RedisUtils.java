package webpos.db;

import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置key-value
     * @param key 键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置带生存时间的key-value
     * @param key 键
     * @param value 值
     * @param timeout 生存时间
     * @param unit 时间单位
     */
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * 设置指定数据的生存时间。
     *
     * @param key 键
     * @param time 生存时间（秒）
     */
    public void expire(String key, long time) {
        redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 根据key，获取值
     * @param key 键
     * @return 获取到的值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    public boolean haskey(String key){
        return redisTemplate.hasKey(key);
    }
    /**
     * 删除指定信息。
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
