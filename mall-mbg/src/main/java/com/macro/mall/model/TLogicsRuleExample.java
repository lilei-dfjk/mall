package com.macro.mall.model;

import java.util.ArrayList;
import java.util.List;

public class TLogicsRuleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TLogicsRuleExample() {
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

        public Criteria andLogisTypeIsNull() {
            addCriterion("logis_type is null");
            return (Criteria) this;
        }

        public Criteria andLogisTypeIsNotNull() {
            addCriterion("logis_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogisTypeEqualTo(Short value) {
            addCriterion("logis_type =", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeNotEqualTo(Short value) {
            addCriterion("logis_type <>", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeGreaterThan(Short value) {
            addCriterion("logis_type >", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeGreaterThanOrEqualTo(Short value) {
            addCriterion("logis_type >=", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeLessThan(Short value) {
            addCriterion("logis_type <", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeLessThanOrEqualTo(Short value) {
            addCriterion("logis_type <=", value, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeIn(List<Short> values) {
            addCriterion("logis_type in", values, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeNotIn(List<Short> values) {
            addCriterion("logis_type not in", values, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeBetween(Short value1, Short value2) {
            addCriterion("logis_type between", value1, value2, "logisType");
            return (Criteria) this;
        }

        public Criteria andLogisTypeNotBetween(Short value1, Short value2) {
            addCriterion("logis_type not between", value1, value2, "logisType");
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

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andBrandTypeIsNull() {
            addCriterion("brand_type is null");
            return (Criteria) this;
        }

        public Criteria andBrandTypeIsNotNull() {
            addCriterion("brand_type is not null");
            return (Criteria) this;
        }

        public Criteria andBrandTypeEqualTo(String value) {
            addCriterion("brand_type =", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeNotEqualTo(String value) {
            addCriterion("brand_type <>", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeGreaterThan(String value) {
            addCriterion("brand_type >", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeGreaterThanOrEqualTo(String value) {
            addCriterion("brand_type >=", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeLessThan(String value) {
            addCriterion("brand_type <", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeLessThanOrEqualTo(String value) {
            addCriterion("brand_type <=", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeLike(String value) {
            addCriterion("brand_type like", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeNotLike(String value) {
            addCriterion("brand_type not like", value, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeIn(List<String> values) {
            addCriterion("brand_type in", values, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeNotIn(List<String> values) {
            addCriterion("brand_type not in", values, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeBetween(String value1, String value2) {
            addCriterion("brand_type between", value1, value2, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandTypeNotBetween(String value1, String value2) {
            addCriterion("brand_type not between", value1, value2, "brandType");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNull() {
            addCriterion("brand_name is null");
            return (Criteria) this;
        }

        public Criteria andBrandNameIsNotNull() {
            addCriterion("brand_name is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNameEqualTo(String value) {
            addCriterion("brand_name =", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotEqualTo(String value) {
            addCriterion("brand_name <>", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThan(String value) {
            addCriterion("brand_name >", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameGreaterThanOrEqualTo(String value) {
            addCriterion("brand_name >=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThan(String value) {
            addCriterion("brand_name <", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLessThanOrEqualTo(String value) {
            addCriterion("brand_name <=", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameLike(String value) {
            addCriterion("brand_name like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotLike(String value) {
            addCriterion("brand_name not like", value, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameIn(List<String> values) {
            addCriterion("brand_name in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotIn(List<String> values) {
            addCriterion("brand_name not in", values, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameBetween(String value1, String value2) {
            addCriterion("brand_name between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andBrandNameNotBetween(String value1, String value2) {
            addCriterion("brand_name not between", value1, value2, "brandName");
            return (Criteria) this;
        }

        public Criteria andNumberLimitIsNull() {
            addCriterion("number_limit is null");
            return (Criteria) this;
        }

        public Criteria andNumberLimitIsNotNull() {
            addCriterion("number_limit is not null");
            return (Criteria) this;
        }

        public Criteria andNumberLimitEqualTo(Integer value) {
            addCriterion("number_limit =", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitNotEqualTo(Integer value) {
            addCriterion("number_limit <>", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitGreaterThan(Integer value) {
            addCriterion("number_limit >", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("number_limit >=", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitLessThan(Integer value) {
            addCriterion("number_limit <", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitLessThanOrEqualTo(Integer value) {
            addCriterion("number_limit <=", value, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitIn(List<Integer> values) {
            addCriterion("number_limit in", values, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitNotIn(List<Integer> values) {
            addCriterion("number_limit not in", values, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitBetween(Integer value1, Integer value2) {
            addCriterion("number_limit between", value1, value2, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andNumberLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("number_limit not between", value1, value2, "numberLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitIsNull() {
            addCriterion("weight_limit is null");
            return (Criteria) this;
        }

        public Criteria andWeightLimitIsNotNull() {
            addCriterion("weight_limit is not null");
            return (Criteria) this;
        }

        public Criteria andWeightLimitEqualTo(Double value) {
            addCriterion("weight_limit =", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitNotEqualTo(Double value) {
            addCriterion("weight_limit <>", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitGreaterThan(Double value) {
            addCriterion("weight_limit >", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("weight_limit >=", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitLessThan(Double value) {
            addCriterion("weight_limit <", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitLessThanOrEqualTo(Double value) {
            addCriterion("weight_limit <=", value, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitIn(List<Double> values) {
            addCriterion("weight_limit in", values, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitNotIn(List<Double> values) {
            addCriterion("weight_limit not in", values, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitBetween(Double value1, Double value2) {
            addCriterion("weight_limit between", value1, value2, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andWeightLimitNotBetween(Double value1, Double value2) {
            addCriterion("weight_limit not between", value1, value2, "weightLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitIsNull() {
            addCriterion("price_limit is null");
            return (Criteria) this;
        }

        public Criteria andPriceLimitIsNotNull() {
            addCriterion("price_limit is not null");
            return (Criteria) this;
        }

        public Criteria andPriceLimitEqualTo(Double value) {
            addCriterion("price_limit =", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitNotEqualTo(Double value) {
            addCriterion("price_limit <>", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitGreaterThan(Double value) {
            addCriterion("price_limit >", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("price_limit >=", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitLessThan(Double value) {
            addCriterion("price_limit <", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitLessThanOrEqualTo(Double value) {
            addCriterion("price_limit <=", value, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitIn(List<Double> values) {
            addCriterion("price_limit in", values, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitNotIn(List<Double> values) {
            addCriterion("price_limit not in", values, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitBetween(Double value1, Double value2) {
            addCriterion("price_limit between", value1, value2, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andPriceLimitNotBetween(Double value1, Double value2) {
            addCriterion("price_limit not between", value1, value2, "priceLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitIsNull() {
            addCriterion("mix_number_limit is null");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitIsNotNull() {
            addCriterion("mix_number_limit is not null");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitEqualTo(Integer value) {
            addCriterion("mix_number_limit =", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitNotEqualTo(Integer value) {
            addCriterion("mix_number_limit <>", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitGreaterThan(Integer value) {
            addCriterion("mix_number_limit >", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("mix_number_limit >=", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitLessThan(Integer value) {
            addCriterion("mix_number_limit <", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitLessThanOrEqualTo(Integer value) {
            addCriterion("mix_number_limit <=", value, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitIn(List<Integer> values) {
            addCriterion("mix_number_limit in", values, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitNotIn(List<Integer> values) {
            addCriterion("mix_number_limit not in", values, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitBetween(Integer value1, Integer value2) {
            addCriterion("mix_number_limit between", value1, value2, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixNumberLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("mix_number_limit not between", value1, value2, "mixNumberLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitIsNull() {
            addCriterion("mix_weight_limit is null");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitIsNotNull() {
            addCriterion("mix_weight_limit is not null");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitEqualTo(Double value) {
            addCriterion("mix_weight_limit =", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitNotEqualTo(Double value) {
            addCriterion("mix_weight_limit <>", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitGreaterThan(Double value) {
            addCriterion("mix_weight_limit >", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("mix_weight_limit >=", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitLessThan(Double value) {
            addCriterion("mix_weight_limit <", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitLessThanOrEqualTo(Double value) {
            addCriterion("mix_weight_limit <=", value, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitIn(List<Double> values) {
            addCriterion("mix_weight_limit in", values, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitNotIn(List<Double> values) {
            addCriterion("mix_weight_limit not in", values, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitBetween(Double value1, Double value2) {
            addCriterion("mix_weight_limit between", value1, value2, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixWeightLimitNotBetween(Double value1, Double value2) {
            addCriterion("mix_weight_limit not between", value1, value2, "mixWeightLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitIsNull() {
            addCriterion("mix_price_limit is null");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitIsNotNull() {
            addCriterion("mix_price_limit is not null");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitEqualTo(Double value) {
            addCriterion("mix_price_limit =", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitNotEqualTo(Double value) {
            addCriterion("mix_price_limit <>", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitGreaterThan(Double value) {
            addCriterion("mix_price_limit >", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitGreaterThanOrEqualTo(Double value) {
            addCriterion("mix_price_limit >=", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitLessThan(Double value) {
            addCriterion("mix_price_limit <", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitLessThanOrEqualTo(Double value) {
            addCriterion("mix_price_limit <=", value, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitIn(List<Double> values) {
            addCriterion("mix_price_limit in", values, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitNotIn(List<Double> values) {
            addCriterion("mix_price_limit not in", values, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitBetween(Double value1, Double value2) {
            addCriterion("mix_price_limit between", value1, value2, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andMixPriceLimitNotBetween(Double value1, Double value2) {
            addCriterion("mix_price_limit not between", value1, value2, "mixPriceLimit");
            return (Criteria) this;
        }

        public Criteria andBrandMixIsNull() {
            addCriterion("brand_mix is null");
            return (Criteria) this;
        }

        public Criteria andBrandMixIsNotNull() {
            addCriterion("brand_mix is not null");
            return (Criteria) this;
        }

        public Criteria andBrandMixEqualTo(String value) {
            addCriterion("brand_mix =", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixNotEqualTo(String value) {
            addCriterion("brand_mix <>", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixGreaterThan(String value) {
            addCriterion("brand_mix >", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixGreaterThanOrEqualTo(String value) {
            addCriterion("brand_mix >=", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixLessThan(String value) {
            addCriterion("brand_mix <", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixLessThanOrEqualTo(String value) {
            addCriterion("brand_mix <=", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixLike(String value) {
            addCriterion("brand_mix like", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixNotLike(String value) {
            addCriterion("brand_mix not like", value, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixIn(List<String> values) {
            addCriterion("brand_mix in", values, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixNotIn(List<String> values) {
            addCriterion("brand_mix not in", values, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixBetween(String value1, String value2) {
            addCriterion("brand_mix between", value1, value2, "brandMix");
            return (Criteria) this;
        }

        public Criteria andBrandMixNotBetween(String value1, String value2) {
            addCriterion("brand_mix not between", value1, value2, "brandMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixIsNull() {
            addCriterion("type_mix is null");
            return (Criteria) this;
        }

        public Criteria andTypeMixIsNotNull() {
            addCriterion("type_mix is not null");
            return (Criteria) this;
        }

        public Criteria andTypeMixEqualTo(String value) {
            addCriterion("type_mix =", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixNotEqualTo(String value) {
            addCriterion("type_mix <>", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixGreaterThan(String value) {
            addCriterion("type_mix >", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixGreaterThanOrEqualTo(String value) {
            addCriterion("type_mix >=", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixLessThan(String value) {
            addCriterion("type_mix <", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixLessThanOrEqualTo(String value) {
            addCriterion("type_mix <=", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixLike(String value) {
            addCriterion("type_mix like", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixNotLike(String value) {
            addCriterion("type_mix not like", value, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixIn(List<String> values) {
            addCriterion("type_mix in", values, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixNotIn(List<String> values) {
            addCriterion("type_mix not in", values, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixBetween(String value1, String value2) {
            addCriterion("type_mix between", value1, value2, "typeMix");
            return (Criteria) this;
        }

        public Criteria andTypeMixNotBetween(String value1, String value2) {
            addCriterion("type_mix not between", value1, value2, "typeMix");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagIsNull() {
            addCriterion("mix_type_flag is null");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagIsNotNull() {
            addCriterion("mix_type_flag is not null");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagEqualTo(Short value) {
            addCriterion("mix_type_flag =", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagNotEqualTo(Short value) {
            addCriterion("mix_type_flag <>", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagGreaterThan(Short value) {
            addCriterion("mix_type_flag >", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagGreaterThanOrEqualTo(Short value) {
            addCriterion("mix_type_flag >=", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagLessThan(Short value) {
            addCriterion("mix_type_flag <", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagLessThanOrEqualTo(Short value) {
            addCriterion("mix_type_flag <=", value, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagIn(List<Short> values) {
            addCriterion("mix_type_flag in", values, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagNotIn(List<Short> values) {
            addCriterion("mix_type_flag not in", values, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagBetween(Short value1, Short value2) {
            addCriterion("mix_type_flag between", value1, value2, "mixTypeFlag");
            return (Criteria) this;
        }

        public Criteria andMixTypeFlagNotBetween(Short value1, Short value2) {
            addCriterion("mix_type_flag not between", value1, value2, "mixTypeFlag");
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