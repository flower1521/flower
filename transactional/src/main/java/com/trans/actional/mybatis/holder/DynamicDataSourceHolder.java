package com.trans.actional.mybatis.holder;

public class DynamicDataSourceHolder {
    /**
     * 使用ThreadLocal保存数据源的key
     */
    public static final String MASTER_DB = "master";
    public static final String SLAVE_DB = "slave";

    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static String getDbType() {
        //返回当前线程的唯一的序列
        String db = threadLocal.get();
        if (null == db) {
            db = MASTER_DB;
        }
        return db;
    }

    /**
     * 设置线程的数据源
     * @param dbType
     */
    public static void setDbType(String dbType) {
        threadLocal.set(dbType);
    }

    /**
     * 清洗数据源
     */
    public static void cleanDbType() {
        threadLocal.remove();
    }
}
