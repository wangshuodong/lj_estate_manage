package com.ym.common.mybatis;

import java.sql.Connection;

import org.apache.ibatis.mapping.MappedStatement;

import com.ym.common.base.Pager;

public class OracleDialect implements Dialect {

    public String getPageSql(Pager page, String sql) {
        return null;
    }

    public void setTotalRecord(Pager page, MappedStatement mappedStatement, Connection connection) {

    }

}
