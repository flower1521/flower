//package com.trans.actional.redis;
//
//import redis.clients.jedis.Jedis;
//
//import java.util.Collections;
//
///**
// * create by lcl on 2020/1/8 11:36
// */
//public class RedisTool {
//    private static final String LOCK_SUCCESS = "OK";
//    private static final String SET_IF_NOT_EXISTS = "NX";
//    private static final String SET_WITH_EXPIRE_TIME = "PX";
//    private static final Long RELEASE_SUCCESS = 1L;
//
//    /**
//     * 尝试获取分布式锁
//     * @param jedis
//     * @param lockKey
//     * @param requestId
//     * @param expireTime
//     * @return
//     */
//    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
//        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXISTS, SET_WITH_EXPIRE_TIME, expireTime);
//        if (result.equals(LOCK_SUCCESS)) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 释放分布式锁
//     * @param jedis
//     * @param lockKey
//     * @param requestId
//     * @return
//     */
//    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {
//        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
//        Object result =jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
//        if (RELEASE_SUCCESS.equals(result)) {
//            return true;
//        }
//        return false;
//    }
//}
