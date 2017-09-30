package com.ym.common.utils;

import java.util.Random;

public class RandomUtil {

	public static int[] num = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	public static String[] alp = { "a", "b", "c", "d", "e", "f", "g" };
	public static String[] Calp = { "A", "B", "C", "D", "E", "F", "G" };


	public static String getRandom(int n) {
		
		String r = "";

		for (int i = 0; i < n; i++) {
			// 获取类型
			Random random = new Random();

			int t = random.nextInt(3);

			if (t == 0) {
				// 数字
				int index = random.nextInt(10);
				r += num[index];
			} else if (t == 1) {
				// 大写字母
				int index = random.nextInt(7);
				r += Calp[index];
			} else if (t == 2) {
				// 小写字母
				int index = random.nextInt(7);
				r += alp[index];
			}

		}
		return r;
	}

}
