package com.ym.common.mybatis;

import java.sql.Connection;

import org.apache.ibatis.mapping.MappedStatement;

import com.ym.common.base.Pager;

public interface Dialect {

    public enum Type {
        DB2, Derby, H2, HSQL, Informix, MySQL, Oracle, PostgreSQL, Sybase
    }

    /**
     * 分页查询语句
     * 
     * @param page
     * @param sql
     * @return
     */
    public String getPageSql(Pager page, String sql);

    /**
     * 设置记录总数
     * 
     * @param page
     * @param mappedStatement
     * @param connection
     */
    public void setTotalRecord(Pager page, MappedStatement mappedStatement, Connection connection);
}
