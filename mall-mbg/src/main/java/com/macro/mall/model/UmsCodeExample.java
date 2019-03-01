package com.macro.mall.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UmsCodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UmsCodeExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCodeCodeIsNull() {
            addCriterion("code_code is null");
            return (Criteria) this;
        }

        public Criteria andCodeCodeIsNotNull() {
            addCriterion("code_code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeCodeEqualTo(String value) {
            addCriterion("code_code =", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeNotEqualTo(String value) {
            addCriterion("code_code <>", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeGreaterThan(String value) {
            addCriterion("code_code >", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code_code >=", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeLessThan(String value) {
            addCriterion("code_code <", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeLessThanOrEqualTo(String value) {
            addCriterion("code_code <=", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeLike(String value) {
            addCriterion("code_code like", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeNotLike(String value) {
            addCriterion("code_code not like", value, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeIn(List<String> values) {
            addCriterion("code_code in", values, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeNotIn(List<String> values) {
            addCriterion("code_code not in", values, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeBetween(String value1, String value2) {
            addCriterion("code_code between", value1, value2, "codeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCodeNotBetween(String value1, String value2) {
            addCriterion("code_code not between", value1, value2, "codeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIsNull() {
            addCriterion("type_code is null");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIsNotNull() {
            addCriterion("type_code is not null");
            return (Criteria) this;
        }

        public Criteria andTypeCodeEqualTo(String value) {
            addCriterion("type_code =", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotEqualTo(String value) {
            addCriterion("type_code <>", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeGreaterThan(String value) {
            addCriterion("type_code >", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("type_code >=", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLessThan(String value) {
            addCriterion("type_code <", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLessThanOrEqualTo(String value) {
            addCriterion("type_code <=", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeLike(String value) {
            addCriterion("type_code like", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotLike(String value) {
            addCriterion("type_code not like", value, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeIn(List<String> values) {
            addCriterion("type_code in", values, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotIn(List<String> values) {
            addCriterion("type_code not in", values, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeBetween(String value1, String value2) {
            addCriterion("type_code between", value1, value2, "typeCode");
            return (Criteria) this;
        }

        public Criteria andTypeCodeNotBetween(String value1, String value2) {
            addCriterion("type_code not between", value1, value2, "typeCode");
            return (Criteria) this;
        }

        public Criteria andCodeCNameIsNull() {
            addCriterion("code_c_name is null");
            return (Criteria) this;
        }

        public Criteria andCodeCNameIsNotNull() {
            addCriterion("code_c_name is not null");
            return (Criteria) this;
        }

        public Criteria andCodeCNameEqualTo(String value) {
            addCriterion("code_c_name =", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameNotEqualTo(String value) {
            addCriterion("code_c_name <>", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameGreaterThan(String value) {
            addCriterion("code_c_name >", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameGreaterThanOrEqualTo(String value) {
            addCriterion("code_c_name >=", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameLessThan(String value) {
            addCriterion("code_c_name <", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameLessThanOrEqualTo(String value) {
            addCriterion("code_c_name <=", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameLike(String value) {
            addCriterion("code_c_name like", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameNotLike(String value) {
            addCriterion("code_c_name not like", value, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameIn(List<String> values) {
            addCriterion("code_c_name in", values, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameNotIn(List<String> values) {
            addCriterion("code_c_name not in", values, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameBetween(String value1, String value2) {
            addCriterion("code_c_name between", value1, value2, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeCNameNotBetween(String value1, String value2) {
            addCriterion("code_c_name not between", value1, value2, "codeCName");
            return (Criteria) this;
        }

        public Criteria andCodeENameIsNull() {
            addCriterion("code_e_name is null");
            return (Criteria) this;
        }

        public Criteria andCodeENameIsNotNull() {
            addCriterion("code_e_name is not null");
            return (Criteria) this;
        }

        public Criteria andCodeENameEqualTo(String value) {
            addCriterion("code_e_name =", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameNotEqualTo(String value) {
            addCriterion("code_e_name <>", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameGreaterThan(String value) {
            addCriterion("code_e_name >", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameGreaterThanOrEqualTo(String value) {
            addCriterion("code_e_name >=", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameLessThan(String value) {
            addCriterion("code_e_name <", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameLessThanOrEqualTo(String value) {
            addCriterion("code_e_name <=", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameLike(String value) {
            addCriterion("code_e_name like", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameNotLike(String value) {
            addCriterion("code_e_name not like", value, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameIn(List<String> values) {
            addCriterion("code_e_name in", values, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameNotIn(List<String> values) {
            addCriterion("code_e_name not in", values, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameBetween(String value1, String value2) {
            addCriterion("code_e_name between", value1, value2, "codeEName");
            return (Criteria) this;
        }

        public Criteria andCodeENameNotBetween(String value1, String value2) {
            addCriterion("code_e_name not between", value1, value2, "codeEName");
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

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andValidStatusIsNull() {
            addCriterion("valid_status is null");
            return (Criteria) this;
        }

        public Criteria andValidStatusIsNotNull() {
            addCriterion("valid_status is not null");
            return (Criteria) this;
        }

        public Criteria andValidStatusEqualTo(Integer value) {
            addCriterion("valid_status =", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusNotEqualTo(Integer value) {
            addCriterion("valid_status <>", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusGreaterThan(Integer value) {
            addCriterion("valid_status >", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("valid_status >=", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusLessThan(Integer value) {
            addCriterion("valid_status <", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusLessThanOrEqualTo(Integer value) {
            addCriterion("valid_status <=", value, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusIn(List<Integer> values) {
            addCriterion("valid_status in", values, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusNotIn(List<Integer> values) {
            addCriterion("valid_status not in", values, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusBetween(Integer value1, Integer value2) {
            addCriterion("valid_status between", value1, value2, "validStatus");
            return (Criteria) this;
        }

        public Criteria andValidStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("valid_status not between", value1, value2, "validStatus");
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