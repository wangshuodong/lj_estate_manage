package com.ym.iadpush.manage.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeveloperReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeveloperReportExample() {
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

        public Criteria andAddateIsNull() {
            addCriterion("addate is null");
            return (Criteria) this;
        }

        public Criteria andAddateIsNotNull() {
            addCriterion("addate is not null");
            return (Criteria) this;
        }

        public Criteria andAddateEqualTo(Date value) {
            addCriterionForJDBCDate("addate =", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateNotEqualTo(Date value) {
            addCriterionForJDBCDate("addate <>", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateGreaterThan(Date value) {
            addCriterionForJDBCDate("addate >", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("addate >=", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateLessThan(Date value) {
            addCriterionForJDBCDate("addate <", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("addate <=", value, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateIn(List<Date> values) {
            addCriterionForJDBCDate("addate in", values, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateNotIn(List<Date> values) {
            addCriterionForJDBCDate("addate not in", values, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("addate between", value1, value2, "addate");
            return (Criteria) this;
        }

        public Criteria andAddateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("addate not between", value1, value2, "addate");
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

        public Criteria andOperUidIsNull() {
            addCriterion("oper_uid is null");
            return (Criteria) this;
        }

        public Criteria andOperUidIsNotNull() {
            addCriterion("oper_uid is not null");
            return (Criteria) this;
        }

        public Criteria andOperUidEqualTo(Integer value) {
            addCriterion("oper_uid =", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidNotEqualTo(Integer value) {
            addCriterion("oper_uid <>", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidGreaterThan(Integer value) {
            addCriterion("oper_uid >", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("oper_uid >=", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidLessThan(Integer value) {
            addCriterion("oper_uid <", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidLessThanOrEqualTo(Integer value) {
            addCriterion("oper_uid <=", value, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidIn(List<Integer> values) {
            addCriterion("oper_uid in", values, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidNotIn(List<Integer> values) {
            addCriterion("oper_uid not in", values, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidBetween(Integer value1, Integer value2) {
            addCriterion("oper_uid between", value1, value2, "operUid");
            return (Criteria) this;
        }

        public Criteria andOperUidNotBetween(Integer value1, Integer value2) {
            addCriterion("oper_uid not between", value1, value2, "operUid");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
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

        public Criteria andEarnIsNull() {
            addCriterion("earn is null");
            return (Criteria) this;
        }

        public Criteria andEarnIsNotNull() {
            addCriterion("earn is not null");
            return (Criteria) this;
        }

        public Criteria andEarnEqualTo(BigDecimal value) {
            addCriterion("earn =", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnNotEqualTo(BigDecimal value) {
            addCriterion("earn <>", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnGreaterThan(BigDecimal value) {
            addCriterion("earn >", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("earn >=", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnLessThan(BigDecimal value) {
            addCriterion("earn <", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnLessThanOrEqualTo(BigDecimal value) {
            addCriterion("earn <=", value, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnIn(List<BigDecimal> values) {
            addCriterion("earn in", values, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnNotIn(List<BigDecimal> values) {
            addCriterion("earn not in", values, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("earn between", value1, value2, "earn");
            return (Criteria) this;
        }

        public Criteria andEarnNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("earn not between", value1, value2, "earn");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIsNull() {
            addCriterion("last_update is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIsNotNull() {
            addCriterion("last_update is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateEqualTo(Date value) {
            addCriterion("last_update =", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNotEqualTo(Date value) {
            addCriterion("last_update <>", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateGreaterThan(Date value) {
            addCriterion("last_update >", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update >=", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLessThan(Date value) {
            addCriterion("last_update <", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateLessThanOrEqualTo(Date value) {
            addCriterion("last_update <=", value, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateIn(List<Date> values) {
            addCriterion("last_update in", values, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNotIn(List<Date> values) {
            addCriterion("last_update not in", values, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateBetween(Date value1, Date value2) {
            addCriterion("last_update between", value1, value2, "lastUpdate");
            return (Criteria) this;
        }

        public Criteria andLastUpdateNotBetween(Date value1, Date value2) {
            addCriterion("last_update not between", value1, value2, "lastUpdate");
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