package com.macro.mall.logistcs.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdRecordBean implements Serializable {
    /**
     * 订单编号
     */
    private String orderNo = "";
    /**
     * 是否仓配
     */
    private String needWarehousing = "";
    /**
     * 仓库ID
     */
    private String warehouseCode = "";
    /**
     * 客户标识
     */
    private String enteCode = "";
    /**
     * 是否换单
     */
    private String needChangebill = "";
    /**
     * 跨境商户企业代
     * <p>
     * 码
     */
    private String cbeCode = "";
    /**
     * 跨境商户企业名
     * <p>
     * 称
     */
    private String cbeName = "";
    /**
     * 业务时间
     */
    private String createTime = "";
    /**
     * 支付企业代码
     */
    private String paymentEnteCode = "";
    /**
     * 支付企业名称
     */
    private String paymentName = "";
    /**
     * 支付交易号
     */
    private String paymentNo = "";
    /**
     * 物流电子运单号
     */
    private String logisticsNo = "0";
    /**
     * 电商服务平台企
     * <p>
     * 业代码
     */
    private String ecpCode = "";
    /**
     * 电商服务平台企
     * <p>
     * 业名称
     */
    private String ecpName = "";
    /**
     * 商品数量
     */
    private int productNum;
    /**
     * 支付人姓名
     */
    private String payerName = "";
    /**
     * 支付人身份证号
     */
    private String payerIdNumber = "";
    /**
     * 支付人手机号
     */
    private String telephone = "";
    /**
     * 发货人所在国家
     * <p>
     * （地区）代码
     */
    private String consignorCountry = "";
    /**
     * 发货人名称
     */
    private String consignor = "";
    /**
     * 发货人电话
     */
    private String consignorPhone = "";
    /**
     * 发货人地址
     */
    private String consignorAddress = "";
    /**
     * 收货人名称
     */
    private String consignee = "";
    /**
     * 收件人身份证号
     */
    private String consigneeIdNo = "";
    /**
     * 收货人电话
     */
    private String consigneePhon = "";
    /**
     * 收货人省份
     */
    private String consigneeProvince = "";
    /**
     * 收货人城市
     */
    private String consigneeCity = "";
    /**
     * 收货人区
     */
    private String consigneeDistrict = "";
    /**
     * 收货人街道
     */
    private String consigneeStreet = "";
    /**
     * 收货人详细地址
     */
    private String consigneeAddres = "";
    /**
     * 仓起运国（地区）代
     * <p>
     * 码
     */
    private String originCountryCode = "";
    /**
     * 国内物流企业代
     * <p>
     * 码 注：若“needChangebill”为Y时，此字段必填
     */
    private String logisticsCode = "";
    /**
     * 起运港代码
     */
    private String loadingPortCode = "";
    /**
     * 运费
     */
    private double freight;
    /**
     * 保费
     */
    private double insurance;

    private List<Goods> orderDetails;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Goods {
        /**
         * 商品货号
         */
        private String goodsNo;
        /**
         * 商品名称
         */
        private String goodsName;
        /**
         * 商品规格类型
         */
        private String goodsModel;
        /**
         * 商品条形码
         */
        private String barcode;
        /**
         * 海关10位商品编码
         */
        private String taxcode;
        /**
         * 原产国
         */
        private String country;
        /**
         * 商品品牌
         */
        private String goodsBrand;
        /**
         * 币制
         */
        private String currency;
        /**
         * 申报单位
         */
        private String declareUnit;
        /**
         * 申报数量
         */
        private double quantity;
        /**
         * 第二法定单位
         */
        private String qty2;
        /**
         * 第二法定数量
         */
        private double unit2;
        /**
         * 成交单位
         */
        private String unit;
        /**
         * 成交数量 大于0的整数
         */
        private int number;
        /**
         * 成交单价 为大于0的数字，不超过4位小数
         */
        private double price;
        /**
         * 净重 大于0的数值，最多4位小数
         */
        private double weight;
        /**
         * 是否赠品
         */
        private String isGift;
        /**
         * 备注
         */
        private String note;
    }
}
