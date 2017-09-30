package com.ym.common.utils;

import java.util.List;


public class QueryResult {
    /**
     * 查询结果明细数据
     */
    private List<?> data;
    /**
     * 总记录数
     */
    private int count;
    /**
     * 汇总数据
     */
    private Object collect;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getCollect() {
        return collect;
    }

    public void setCollect(Object collect) {
        this.collect = collect;
    }

}
