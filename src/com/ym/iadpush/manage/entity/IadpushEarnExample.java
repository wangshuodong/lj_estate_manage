package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class IadpushEarnExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IadpushEarnExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andQidIsNull() {
            addCriterion("qid is null");
            return (Criteria) this;
        }

        public Criteria andQidIsNotNull() {
            addCriterion("qid is not null");
            return (Criteria) this;
        }

        public Criteria andQidEqualTo(Integer value) {
            addCriterion("qid =", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotEqualTo(Integer value) {
            addCriterion("qid <>", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidGreaterThan(Integer value) {
            addCriterion("qid >", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidGreaterThanOrEqualTo(Integer value) {
            addCriterion("qid >=", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidLessThan(Integer value) {
            addCriterion("qid <", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidLessThanOrEqualTo(Integer value) {
            addCriterion("qid <=", value, "qid");
            return (Criteria) this;
        }

        public Criteria andQidIn(List<Integer> values) {
            addCriterion("qid in", values, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotIn(List<Integer> values) {
            addCriterion("qid not in", values, "qid");
            return (Criteria) this;
        }

        public Criteria andQidBetween(Integer value1, Integer value2) {
            addCriterion("qid between", value1, value2, "qid");
            return (Criteria) this;
        }

        public Criteria andQidNotBetween(Integer value1, Integer value2) {
            addCriterion("qid not between", value1, value2, "qid");
            return (Criteria) this;
        }

        public Criteria andAppidIsNull() {
            addCriterion("appid is null");
            return (Criteria) this;
        }

        public Criteria andAppidIsNotNull() {
            addCriterion("appid is not null");
            return (Criteria) this;
        }

        public Criteria andAppidEqualTo(Integer value) {
            addCriterion("appid =", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotEqualTo(Integer value) {
            addCriterion("appid <>", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThan(Integer value) {
            addCriterion("appid >", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidGreaterThanOrEqualTo(Integer value) {
            addCriterion("appid >=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThan(Integer value) {
            addCriterion("appid <", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidLessThanOrEqualTo(Integer value) {
            addCriterion("appid <=", value, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidIn(List<Integer> values) {
            addCriterion("appid in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotIn(List<Integer> values) {
            addCriterion("appid not in", values, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidBetween(Integer value1, Integer value2) {
            addCriterion("appid between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andAppidNotBetween(Integer value1, Integer value2) {
            addCriterion("appid not between", value1, value2, "appid");
            return (Criteria) this;
        }

        public Criteria andEdateIsNull() {
            addCriterion("edate is null");
            return (Criteria) this;
        }

        public Criteria andEdateIsNotNull() {
            addCriterion("edate is not null");
            return (Criteria) this;
        }

        public Criteria andEdateEqualTo(Date value) {
            addCriterionForJDBCDate("edate =", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateNotEqualTo(Date value) {
            addCriterionForJDBCDate("edate <>", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateGreaterThan(Date value) {
            addCriterionForJDBCDate("edate >", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("edate >=", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateLessThan(Date value) {
            addCriterionForJDBCDate("edate <", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("edate <=", value, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateIn(List<Date> values) {
            addCriterionForJDBCDate("edate in", values, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateNotIn(List<Date> values) {
            addCriterionForJDBCDate("edate not in", values, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("edate between", value1, value2, "edate");
            return (Criteria) this;
        }

        public Criteria andEdateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("edate not between", value1, value2, "edate");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyIsNull() {
            addCriterion("earn_money is null");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyIsNotNull() {
            addCriterion("earn_money is not null");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyEqualTo(BigDecimal value) {
            addCriterion("earn_money =", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyNotEqualTo(BigDecimal value) {
            addCriterion("earn_money <>", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyGreaterThan(BigDecimal value) {
            addCriterion("earn_money >", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("earn_money >=", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyLessThan(BigDecimal value) {
            addCriterion("earn_money <", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("earn_money <=", value, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyIn(List<BigDecimal> values) {
            addCriterion("earn_money in", values, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyNotIn(List<BigDecimal> values) {
            addCriterion("earn_money not in", values, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("earn_money between", value1, value2, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andEarnMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("earn_money not between", value1, value2, "earnMoney");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andHourIsNull() {
            addCriterion("hour is null");
            return (Criteria) this;
        }

        public Criteria andHourIsNotNull() {
            addCriterion("hour is not null");
            return (Criteria) this;
        }

        public Criteria andHourEqualTo(Short value) {
            addCriterion("hour =", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotEqualTo(Short value) {
            addCriterion("hour <>", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourGreaterThan(Short value) {
            addCriterion("hour >", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourGreaterThanOrEqualTo(Short value) {
            addCriterion("hour >=", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourLessThan(Short value) {
            addCriterion("hour <", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourLessThanOrEqualTo(Short value) {
            addCriterion("hour <=", value, "hour");
            return (Criteria) this;
        }

        public Criteria andHourIn(List<Short> values) {
            addCriterion("hour in", values, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotIn(List<Short> values) {
            addCriterion("hour not in", values, "hour");
            return (Criteria) this;
        }

        public Criteria andHourBetween(Short value1, Short value2) {
            addCriterion("hour between", value1, value2, "hour");
            return (Criteria) this;
        }

        public Criteria andHourNotBetween(Short value1, Short value2) {
            addCriterion("hour not between", value1, value2, "hour");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andIcountIsNull() {
            addCriterion("icount is null");
            return (Criteria) this;
        }

        public Criteria andIcountIsNotNull() {
            addCriterion("icount is not null");
            return (Criteria) this;
        }

        public Criteria andIcountEqualTo(Integer value) {
            addCriterion("icount =", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountNotEqualTo(Integer value) {
            addCriterion("icount <>", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountGreaterThan(Integer value) {
            addCriterion("icount >", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("icount >=", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountLessThan(Integer value) {
            addCriterion("icount <", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountLessThanOrEqualTo(Integer value) {
            addCriterion("icount <=", value, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountIn(List<Integer> values) {
            addCriterion("icount in", values, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountNotIn(List<Integer> values) {
            addCriterion("icount not in", values, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountBetween(Integer value1, Integer value2) {
            addCriterion("icount between", value1, value2, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountNotBetween(Integer value1, Integer value2) {
            addCriterion("icount not between", value1, value2, "icount");
            return (Criteria) this;
        }

        public Criteria andIcountkouIsNull() {
            addCriterion("icountkou is null");
            return (Criteria) this;
        }

        public Criteria andIcountkouIsNotNull() {
            addCriterion("icountkou is not null");
            return (Criteria) this;
        }

        public Criteria andIcountkouEqualTo(Integer value) {
            addCriterion("icountkou =", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouNotEqualTo(Integer value) {
            addCriterion("icountkou <>", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouGreaterThan(Integer value) {
            addCriterion("icountkou >", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouGreaterThanOrEqualTo(Integer value) {
            addCriterion("icountkou >=", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouLessThan(Integer value) {
            addCriterion("icountkou <", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouLessThanOrEqualTo(Integer value) {
            addCriterion("icountkou <=", value, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouIn(List<Integer> values) {
            addCriterion("icountkou in", values, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouNotIn(List<Integer> values) {
            addCriterion("icountkou not in", values, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouBetween(Integer value1, Integer value2) {
            addCriterion("icountkou between", value1, value2, "icountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouNotBetween(Integer value1, Integer value2) {
            addCriterion("icountkou not between", value1, value2, "icountkou");
            return (Criteria) this;
        }

        public Criteria andAcountIsNull() {
            addCriterion("acount is null");
            return (Criteria) this;
        }

        public Criteria andAcountIsNotNull() {
            addCriterion("acount is not null");
            return (Criteria) this;
        }

        public Criteria andAcountEqualTo(Integer value) {
            addCriterion("acount =", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountNotEqualTo(Integer value) {
            addCriterion("acount <>", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountGreaterThan(Integer value) {
            addCriterion("acount >", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("acount >=", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountLessThan(Integer value) {
            addCriterion("acount <", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountLessThanOrEqualTo(Integer value) {
            addCriterion("acount <=", value, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountIn(List<Integer> values) {
            addCriterion("acount in", values, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountNotIn(List<Integer> values) {
            addCriterion("acount not in", values, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountBetween(Integer value1, Integer value2) {
            addCriterion("acount between", value1, value2, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountNotBetween(Integer value1, Integer value2) {
            addCriterion("acount not between", value1, value2, "acount");
            return (Criteria) this;
        }

        public Criteria andAcountkouIsNull() {
            addCriterion("acountkou is null");
            return (Criteria) this;
        }

        public Criteria andAcountkouIsNotNull() {
            addCriterion("acountkou is not null");
            return (Criteria) this;
        }

        public Criteria andAcountkouEqualTo(Integer value) {
            addCriterion("acountkou =", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouNotEqualTo(Integer value) {
            addCriterion("acountkou <>", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouGreaterThan(Integer value) {
            addCriterion("acountkou >", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouGreaterThanOrEqualTo(Integer value) {
            addCriterion("acountkou >=", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouLessThan(Integer value) {
            addCriterion("acountkou <", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouLessThanOrEqualTo(Integer value) {
            addCriterion("acountkou <=", value, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouIn(List<Integer> values) {
            addCriterion("acountkou in", values, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouNotIn(List<Integer> values) {
            addCriterion("acountkou not in", values, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouBetween(Integer value1, Integer value2) {
            addCriterion("acountkou between", value1, value2, "acountkou");
            return (Criteria) this;
        }

        public Criteria andAcountkouNotBetween(Integer value1, Integer value2) {
            addCriterion("acountkou not between", value1, value2, "acountkou");
            return (Criteria) this;
        }

        public Criteria andIcountkouActIsNull() {
            addCriterion("icountkou_act is null");
            return (Criteria) this;
        }

        public Criteria andIcountkouActIsNotNull() {
            addCriterion("icountkou_act is not null");
            return (Criteria) this;
        }

        public Criteria andIcountkouActEqualTo(BigDecimal value) {
            addCriterion("icountkou_act =", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActNotEqualTo(BigDecimal value) {
            addCriterion("icountkou_act <>", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActGreaterThan(BigDecimal value) {
            addCriterion("icountkou_act >", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("icountkou_act >=", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActLessThan(BigDecimal value) {
            addCriterion("icountkou_act <", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActLessThanOrEqualTo(BigDecimal value) {
            addCriterion("icountkou_act <=", value, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActIn(List<BigDecimal> values) {
            addCriterion("icountkou_act in", values, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActNotIn(List<BigDecimal> values) {
            addCriterion("icountkou_act not in", values, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("icountkou_act between", value1, value2, "icountkouAct");
            return (Criteria) this;
        }

        public Criteria andIcountkouActNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("icountkou_act not between", value1, value2, "icountkouAct");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}