package com.ym.iadpush.common.utils;

public class FormatUtil {
    /**
     * 根据输入字符得到该字符的注记码，如果遇到重改为冲
     */

    public static String getMemocode(String ss) {
        String[] temp = new String[ss.length()];

        // 将一些复杂汉字转换成简单的，否则得不到正确的声母,注意一一对应
        String oldString = "重鑫镪缤镔昊潇晗怡睿榕徕曜晟缙碚轲瀚铎娅婷枰潺钶奚桦";
        String newString = "cxqbbhxhyrrlycjbkhdytpckxh";

        for (int i = 0; i < temp.length; i++) {
            int index = oldString.indexOf(ss.substring(i, i + 1));

            if (index != -1) {
                temp[i] = SpallUtils.spall(newString.substring(index, index + 1));
            } else {
                temp[i] = SpallUtils.spall(ss.substring(i, i + 1));
            }
        }

        String result = "";
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null && !temp[i].trim().equals("")) {
                result = result + temp[i].substring(0, 1);
            }
        }
        return result.toUpperCase();
    }
    /**
     * 生成组织机构代码
     */
    public static String getOrgCode(String maxCode) {
        long code = Long.parseLong(maxCode) + 1;
        //String orgCode = DecimalFormatUtils.format(maxCode, code+1);
        String orgCode = "00" + code;
        return orgCode;
    }

}
