package com.ym.common.utils;

import java.math.BigDecimal;

/**
 * 
 *                       
 * @Filename Arith.java
 *
 * @Description  用于精确数学计算的工具类
 *
 * @Version 1.0
 *
 * @Author lixingbiao
 *
 * @Email qing.hu2009@gmail.com
 *       
 * @History
 *<li>Author: lixingbiao</li>
 *<li>Date: 2014-2-13</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class Arith {
	
	/**
	 * 默认除法运算精度=15
	 */
	private static final int	DEF_DIV_SCALE	= 15;
	
	//这个类不能实例化
	private Arith() {
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
	    
	}
	
	/**
	 * 计算绝对值
	 * @param v1 需要计算的数
	 * @param size 保留位数
	 * @return
	 */
	public static double abs(double v1, int size) {
		
		v1 = Math.abs(v1);
		
		v1 = round(v1, size);
		
		return v1;
	}
	
	/**   
	 * 提供精确的加法运算
	 * @param v1 加数
	 * @param v2 加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		return add(v1, v2, 0, 0);
	}
	
	/**
	 * 把double数据转换成大数字类型并且字符串类型输出
	 * @param temp 需要转换的数据
	 * @param size 小数位数
	 * @return
	 */
	public static String getBigDecimalValue(double temp, int size) {
		BigDecimal bd = new BigDecimal(temp);
		bd = bd.setScale(size, BigDecimal.ROUND_HALF_UP);
		return bd.toString();
	}
	
	/**
	 * 提供精确的加法运算
	 * @param v1 加数
	 * @param v2 加数
	 * @param v3 加数
	 * @return 三个参数的和
	 */
	public static double add(double v1, double v2, double v3) {
		return add(v1, v2, v3, 0);
	}
	
	/**
	 * 提供精确的加法运算
	 * @param v1 加数
	 * @param v2 加数
	 * @param v3 加数
	 * @param v4 加数
	 * @return 四个参数的和
	 */
	public static double add(double v1, double v2, double v3, double v4) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.add(b2).add(b3).add(b4).doubleValue();
	}
	
	/**   
	 * 提供精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		return sub(v1, v2, 0, 0);
	}
	
	/**
	 * 提供精确的减法运算
	 * @param v1 减数
	 * @param v2 减数
	 * @param v3 减数
	 * @return 三个参数的差
	 */
	public static double sub(double v1, double v2, double v3) {
		return sub(v1, v2, v3, 0);
	}
	
	/**
	 * 提供精确的减法运算
	 * @param v1 减数
	 * @param v2 减数
	 * @param v3 减数
	 * @param v4 减数
	 * @return 四个参数的差
	 */
	public static double sub(double v1, double v2, double v3, double v4) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.subtract(b2).subtract(b3).subtract(b4).doubleValue();
	}
	
	/**   
	 * 提供精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		return mul(v1, v2, 1, 1);
	}
	
	/**   
	 * 提供精确的乘法运算
	 * @param v1 被乘数
	 * @param v2 乘数
	 * @param i 保留小数位数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2, int i) {
		double mulnum = mul(v1, v2, 1, 1);
		return round(mulnum, i);
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param v1 乘数
	 * @param v2 乘数
	 * @param v3 乘数
	 * @return 三个参数的积
	 */
	public static double mul(double v1, double v2, double v3) {
		return mul(v1, v2, v3, 1);
	}
	
	/**
	 * 提供精确的乘法运算
	 * @param v1 乘数
	 * @param v2 乘数
	 * @param v3 乘数
	 * @param v4 乘数
	 * @return 四个参数的积
	 */
	public static double mul(double v1, double v2, double v3, double v4) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		BigDecimal b3 = new BigDecimal(Double.toString(v3));
		BigDecimal b4 = new BigDecimal(Double.toString(v4));
		return b1.multiply(b2).multiply(b3).multiply(b4).doubleValue();
	}
	
	/**   
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
	 * 小数点以后10位，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}
	
	/**   
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指   
	 * 定精度，以后的数字四舍五入
	 * @param v1 被除数
	 * @param v2 除数
	 * @param scale 表示表示需要精确到小数点以后几位
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (v2 == 0) {
			v2 = 1;
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**   
	 * 提供精确的小数位四舍五入处理
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
