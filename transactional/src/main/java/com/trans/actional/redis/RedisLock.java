//package com.trans.actional.redis;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.dao.DataAccessException;
//import org.springframework.data.redis.connection.RedisConnection;
//import org.springframework.data.redis.connection.RedisStringCommands;
//import org.springframework.data.redis.core.RedisCallback;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.types.Expiration;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import java.util.concurrent.TimeUnit;
//
//@Component
//public class RedisLock {
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    /**
//     * 加锁
//     * @param lockKey
//     * @param timeStamp
//     * @return
//     */
//    public boolean lock(String lockKey, String timeStamp) {
//        if (stringRedisTemplate.opsForValue().setIfAbsent(lockKey, timeStamp)) {
//            //对应setnx命令，可以成功设置，也就是key不存在，获得锁成功
//            return true;
//        }
//
//        //设置失败，获得锁失败
//        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作，防止死锁
//        String currentLock = stringRedisTemplate.opsForValue().get(lockKey);
//        //如果锁过期 currentLock不为空且小于当前时间
//        if (!StringUtils.isEmpty(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()) {
//            //如果lockKey对应的锁已经存在，获取上一次设置的时间戳之后并重置lockKey对应的锁的时间戳
//            String preLock = stringRedisTemplate.opsForValue().getAndSet(lockKey, timeStamp);
//
//            //假设两个线程同时进来这里，因为key被占用了，而且锁过期了。
//            //获取的值currentLock=A（get取的旧的值肯定是一样的），两个线程的timeStamp都是B，key都是K。锁时间已经过期了。
//            //而这里面的getAndSet一次只会一个执行，也就是一个执行之后，上一个的timeStamp已经变成了B。
//            //只有一个线程获取的上一个值会是A，另一个线程拿到的值是B。
//            if (!StringUtils.isEmpty(preLock) && preLock.equals(currentLock)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    /**
//     * 释放锁
//     * @param lockKey
//     * @param timeStamp
//     */
//    public void release(String lockKey, String timeStamp) {
//        try {
//            String currentValue = stringRedisTemplate.opsForValue().get(lockKey);
//            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(timeStamp)) {
//                //删除锁状态
//                stringRedisTemplate.opsForValue().getOperations().delete(lockKey);
//            }
//        } catch (Exception e) {
//            System.out.println("警报！警报！警报！解锁异常");
//        }
//    }
//
//    private String unlockLua = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//
//    public boolean tryLock(String key, String uniqueId, Long expireSeconds) {
//        Boolean lockedSuccess = stringRedisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                return redisConnection.set(key.getBytes(), uniqueId.getBytes(), Expiration.from(expireSeconds, TimeUnit.MILLISECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT);
//            }
//        });
//        if (lockedSuccess) {
//            return true;
//        }
//        return false;
//    }
//
//    public void unlock(String key, String uniqueId) {
//        stringRedisTemplate.execute(new RedisCallback<Boolean>() {
//            @Override
//            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                Object nativeConnection = redisConnection.getNativeConnection();
//                //集群模式和单机模式虽然执行脚本一样，但是没有共同的接口，所以只能分开执行
//                Long luaResult = 0L;
//                if (nativeConnection instanceof RedisProperties.Cluster) {
//                }
//                if (nativeConnection instanceof RedisProperties.Jedis) {
//                }
//                return false;
//            }
//        });
//    }
//}
