package com.ym.common.mybatis;

import java.sql.Connection;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;

import com.ym.common.base.Pager;

@Intercepts(@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }))
public class PageInterceptor implements Interceptor {

    private final static Log log = LogFactory.getLog(PageInterceptor.class);
    private Dialect.Type databaseType = null;
    private Dialect dialect = null;

    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectHelper.getValueByFieldName(handler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();
            Object obj = boundSql.getParameterObject();
            if (obj instanceof Pager) {
                Pager page = (Pager) obj;
                if (page.isPage()) {
                    MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate,
                            "mappedStatement");
                    this.setDatabaseType(mappedStatement); // 设置数据库
                    Connection connection = (Connection) invocation.getArgs()[0];
                    String sql = boundSql.getSql();
                    switch (databaseType) {
                        case MySQL:
                            dialect = new MySql5Dialect();
                            break;
                        case Oracle:
                            dialect = new OracleDialect();
                            break;
                        default:
                            throw new Exception("自动分页不支持制定的Dialect配置");
                    }
                    dialect.setTotalRecord(page, mappedStatement, connection);
                    String pageSql = dialect.getPageSql(page, sql);
                    ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return invocation.proceed();
    }

    public void setDatabaseType(MappedStatement mappedStatement) {
        try {
            Configuration configuration = mappedStatement.getConfiguration();
            databaseType = Dialect.Type.valueOf(configuration.getDatabaseId());
        } catch (Exception e) {
            log.error("mybatis 配置错误-->未配置dialect");
            e.printStackTrace();
        }
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties properties) {

    }

}
