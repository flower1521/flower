package com.trans.actional.mybatis.interceptor;

import com.trans.actional.mybatis.holder.DynamicDataSourceHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * Created by lenovo on 2019/12/4.
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
@Component
public class MybatisInterceptor implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(MybatisInterceptor.class);

    //正则匹配 insert、delete、update 操作
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //判断是否是新事务，如果是新事务，则需要把事务属性存放到当前线程中
        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();

        //获取mybatis转换过来的CURD执行参数
        Object[] objects = invocation.getArgs();
        //MappedStatement维护了一条select|update|delete|insert节点的封装
        MappedStatement ms = (MappedStatement) objects[0];
        //默认主库写入操作
        String dbKey = DynamicDataSourceHolder.getDbType();

        if (synchronizationActive != true) {
            //SqlCommandType.SELECT Sql的类型 select|update|insert|delete
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replace("[\\t\\n\\r]", " ");
                logger.info("打印SQL语句：{}", sql);

                //如果是insert、delete、update操作，使用主库
                if (sql.toLowerCase().matches(REGEX)) {
                    logger.info("拦截执行数据库的请求：写请求");
                    dbKey = DynamicDataSourceHolder.MASTER_DB;
                } else {
                    //使用从库
                    logger.info("拦截执行数据库的请求：读请求");
                    dbKey = DynamicDataSourceHolder.SLAVE_DB;
                }
            }
        } else {
            //不受事务管理的默认主库
            dbKey = DynamicDataSourceHolder.MASTER_DB;
        }

        DynamicDataSourceHolder.setDbType(dbKey);
        //继续执行逻辑
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        //获取代理权
        if (o instanceof Executor) {
            //如果是Executor（执行增删改查操作），则拦截下来
            return Plugin.wrap(o, this);
        } else {
            return o;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //读取mybatis配置文件中属性
    }
}
