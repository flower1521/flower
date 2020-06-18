package com.trans.actional.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * create by lcl on 2020/1/8 10:31
 */
@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 添加字符串KEY
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public void addKey(String key, Object value, long expire, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, expire, timeUnit);
    }

    /**
     * 指定缓存失效时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据KEY获取VALUE
     * @param key
     * @return
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通获取缓存
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    private static final String unlockScript = "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n"
            + "then\n"
            + "    return redis.call(\"del\",KEYS[1])\n"
            + "else\n"
            + "    return 0\n"
            + "end";

    /**
     * 加锁，有阻塞
     * @param name
     * @param expire
     * @param timeout
     * @return
     */
    public String lock(String name, long expire, long timeout) {
        long startTime = System.currentTimeMillis();
        String token;
        do {
            token = tryLock(name, expire);
            if (token == null) {
                if ((System.currentTimeMillis() - startTime) > (timeout - 50)) {
                    break;
                } else {
                    try {
                        Thread.sleep(50);//try 50 per sec
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        } while (token == null);
        return token;
    }

    /**
     * 加锁，无阻塞
     * @param name
     * @param expire
     * @return
     */
    public String tryLock(String name, long expire) {
        String token = UUID.randomUUID().toString();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection conn = factory.getConnection();
        try {
            Boolean result = conn.set(name.getBytes(Charset.forName("UTF-8")), token.getBytes(Charset.forName("UTF-8")),
                    Expiration.from(expire, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
            if (result != null && result) {
                return token;
            }
        } finally {
            RedisConnectionUtils.releaseConnection(conn, factory);
        }
        return null;
    }

    /**
     * 解锁
     * @param name
     * @param token
     * @return
     */
    public boolean unlock(String name, String token) {
        byte[][] keysAndArgs = new byte[2][];
        keysAndArgs[0] = name.getBytes(Charset.forName("UTF-8"));
        keysAndArgs[1] = token.getBytes(Charset.forName("UTF-8"));
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection conn = factory.getConnection();
        try {
            Long result = conn.scriptingCommands().eval(unlockScript.getBytes(Charset.forName("UTF-8")), ReturnType.INTEGER, 1, keysAndArgs);
            if (result != null && result > 0) {
                return true;
            }
        } finally {
            RedisConnectionUtils.releaseConnection(conn, factory);
        }
        return false;
    }

    public void pipeline() {
        List<Object> resultList = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                byte[] key1 = "mykey1".getBytes();
                byte[] value1 = "字符串value".getBytes();
                connection.set(key1, value1);

                Map<byte[], byte[]> tuple = new HashMap<>();
                tuple.put("m_mykey1".getBytes(), "m_value1".getBytes());
                tuple.put("m_mykey2".getBytes(), "m_value2".getBytes());
                tuple.put("m_mykey3".getBytes(), "m_value3".getBytes());
                connection.mSet(tuple);

//                connection.get("m_mykey2".getBytes());

                return null;
            }
        });

        for (Object str : resultList) {
            System.out.println(str);
        }
    }

    public void pipeline2() {
        redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                for (int i = 0; i < 1000; i++) {
                    redisConnection.set(("pipe_" + i).getBytes(), ("value_" + i).getBytes());
                }
                return null;
            }
        });
    }

    public void scan(String pattern, Consumer<byte[]> consumer) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(Long.MAX_VALUE).match(pattern).build())) {
                    cursor.forEachRemaining(consumer);
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void scan(String pattern, Consumer<byte[]> consumer, long count) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                try (Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().count(count).match(pattern).build())) {
                    cursor.forEachRemaining(consumer);
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
