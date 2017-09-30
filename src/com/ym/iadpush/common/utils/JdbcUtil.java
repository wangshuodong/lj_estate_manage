package com.ym.iadpush.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcUtil {
	
	private static String username = SysConfigPropertiesHelper.getProperty("emob.jdbc.username");

	private static String password = SysConfigPropertiesHelper.getProperty("emob.jdbc.password");

	private static String url = SysConfigPropertiesHelper.getProperty("emob.jdbc.jdbcUrl");

	private static String driver = SysConfigPropertiesHelper.getProperty("emob.jdbc.driverClass");

	private static Connection con;

	private static ResultSet rs = null;

	private static Statement stmt = null;

	/**
	 * 
	 * @Author Julian 2014-4-5 上午11:20:20
	 * @return
	 * @Desc: <p>
	 *        获取数据库链接
	 *        </p>
	 */
	public static synchronized Connection getConnection() {
		try {
			if (con == null) {
				Class.forName(driver);
				con = DriverManager.getConnection(url, username, password);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public static synchronized Statement getStatement(){
		if(stmt == null){
			try {
				stmt = getConnection().createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return stmt;
	}
	
	public static int executeUpdate(String sql) throws SQLException, ClassNotFoundException{
		try {
			int result = getStatement().executeUpdate(sql);
			return  result;
		} catch (SQLException  e) {
			// TODO: handle exception
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			int result = stmt.executeUpdate(sql);
			return result;
		}
	}
	
	public static int executeUpdate(String sql,String[] args) throws SQLException, ClassNotFoundException{
		try {
			int result = getStatement().executeUpdate(sql, args);
			return  result;
		} catch (SQLException  e) {
			// TODO: handle exception
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			int result = stmt.executeUpdate(sql, args);
			return result;
		}
	}
	
	public static ResultSet executeQuery(String sql) throws SQLException, ClassNotFoundException{
		
		try {
			ResultSet resultSet = getStatement().executeQuery(sql);
			return resultSet;
		} catch (SQLException  e) {
			// TODO: handle exception
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			return resultSet;
		}
	}
	
	
	/**
	 * 
	 * @Author Julian 2014-4-5 上午11:26:37
	 * @Desc: <p>关闭数据库链接</p>
	 */
	public static synchronized void close() {

		try {
			if (rs != null) {
				rs.close();
			}
			if(stmt != null){
				stmt.close();
			}
			if(con != null){
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
