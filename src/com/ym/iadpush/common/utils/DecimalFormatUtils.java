package com.ym.iadpush.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;


public class DecimalFormatUtils {
	private static DecimalFormat df;

	/**
	 * ���pattern��ʽ��double
	 * @param pattern
	 * @param double d
	 * @return
	 */
	public static double format(String pattern, double d) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		String str = df.format(d);
		return Double.parseDouble(str);
	}

	public static String formatDoubleToString(String pattern, double d) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		return df.format(d);
	}

	/**
	 * ���pattern��ʽ��pattern
	 * @param pattern
	 * @param float f
	 * @return
	 */
	public static float format(String pattern, float f) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		String str = df.format(f);
		return Float.parseFloat(str);
	}

	/**
	 * ��ʽ��
	 * @param pattern
	 * @param f
	 * @return
	 */
	public static String format(String pattern, long f) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		return df.format(f);
	}

	/**
	 * ��#.##��ʽ��double
	 * @param double
	 * @return
	 */
	public static double format(double d) {
		df = new DecimalFormat();
		df.applyPattern("#.##");
		String str = df.format(d);
		return Double.parseDouble(str);
	}

	/**
	 * ��#.##��ʽ.��ʽ��float
	 * @param float
	 * @return
	 */
	public static float format(float f) {
		df = new DecimalFormat();
		df.applyPattern("#.##");
		String str = df.format(f);
		return Float.parseFloat(str);
	}

	/**
	 * ���pattern��ʽ��pattern
	 * @param pattern
	 * @param int f
	 * @return
	 */
	public static String format(String pattern, int i) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		String str = df.format(i);
		return str;
	}

	/**
	 * ���pattern��ʽ��pattern
	 * @param pattern
	 * @param int f
	 * @return String
	 * <pre>
	 * Ex:
	 *  DecimalFormatUtils.financeFormat("##,###.000",new BigDecimal(123456.123))
	 * </pre>
	 */
	public static String financeFormat(String pattern, BigDecimal bigDecimal) {
		df = new DecimalFormat();
		df.applyPattern(pattern);
		String str = df.format(bigDecimal);
		return str;
	}

	public static void main(String args[]) {
		System.out.println(format("#", 123123.123));
	}
}
