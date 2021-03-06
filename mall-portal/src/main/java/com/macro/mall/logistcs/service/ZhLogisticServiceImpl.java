package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.bean.LogisticsOrderBean;
import com.macro.mall.logistcs.bean.LogisticsRuleBean;
import com.macro.mall.logistcs.bean.OrderBean;
import com.macro.mall.logistcs.bean.ProductItem;
import com.macro.mall.logistcs.cons.LogisticType;
import org.apache.axis.AxisFault;
import org.apache.axis.client.Call;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZhLogisticServiceImpl implements ZhLogisticService {

    @Autowired
    private ZhLogisticRuleService ruleService;
    @Autowired
    private ExpressCalcService expressCalcService;

//    @PostConstruct
    public void init() {
        OrderBean orderBean = new OrderBean();
        List<ProductItem> productItemList = new ArrayList<>();
//        productItemList.add(new ProductItem(11, 100, 300, "milk", "milk_baby", LogisticType.ZH));
//        productItemList.add(new ProductItem(6, 100, 300, "milk", "milk_man_bag", LogisticType.ZH));
        productItemList.add(new ProductItem(3, 100, 20, "food", "food", LogisticType.ZH));
        productItemList.add(new ProductItem(2, 20, 30, "wash", "yexh", LogisticType.ZH));
        orderBean.setProductItemList(productItemList);
        getLogisticOrders(orderBean);
    }

    @Override
    public List<LogisticsOrderBean> getLogisticOrders(OrderBean orderBean) {
        List<ProductItem> productItemList = orderBean.getProductItemList();
        if (CollectionUtils.isNotEmpty(productItemList)) {
            // 1. 分类 尽量同一类放在一个包裹里
            List<String> collect = productItemList.stream().map(item -> item.getRuleType()).distinct().collect(Collectors.toList());
            Map<String, List<ProductItem>> listMap = collect.stream().collect(Collectors.toMap(type -> type, type -> getListByType(type, productItemList)));
            // 2. 每一类创建一个订单
            List<LogisticsOrderBean> logisticsOrderBeans = new ArrayList<>();
            listMap.entrySet().stream().forEach(entry -> logisticsOrderBeans.addAll(createLogisticOrder(entry.getKey(), entry.getValue())));
            // 3. 合并订单
            List<LogisticsOrderBean> logisticsOrderBeans1 = combineOrder(logisticsOrderBeans);
            System.out.println(logisticsOrderBeans1);
            return logisticsOrderBeans1;
        }
        return new ArrayList<>();
    }

    private List<LogisticsOrderBean> combineOrder(List<LogisticsOrderBean> logisticsOrderBeans) {
        List<LogisticsOrderBean> lists = new ArrayList<>();
        logisticsOrderBeans.stream().forEach(orderBean -> {
            orderBean.getProductItemList().stream().forEach(productItem -> createLogisticsOrder(lists, productItem));
        });
        return lists;
    }

    private List<ProductItem> getListByType(String type, List<ProductItem> items) {
        return items.stream().filter(item -> item.getRuleType().equals(type)).collect(Collectors.toList());
    }

    private List<LogisticsOrderBean> createLogisticOrder(String ruleType, List<ProductItem> items) {
        List<LogisticsOrderBean> orders = new ArrayList<>();
        if (!CollectionUtils.isEmpty(items)) {
            // 第一类商品生成一个订单，如果不满足条件则生成多个订单，
            // 然后判断第二个商品，是否能够混装，然后以此类推
            items.stream().forEach(productItem -> {
                ProductItem itemTemp = new ProductItem();
                BeanUtils.copyProperties(productItem, itemTemp);
                createLogisticsOrder(orders, itemTemp);
            });
            return orders;
        }
        return new ArrayList<>();
    }

    private List<LogisticsOrderBean> createLogisticsOrder(List<LogisticsOrderBean> logisticsOrderBeans, ProductItem productItem) {
        if (null == productItem.getRuleBean()) {
            productItem.setRuleBean(ruleService.getLogisticsRulesByLogisType((short) productItem.getLogisticType().getValue(), productItem.getRuleBrandType()));
        }
        LogisticsRuleBean logisticsRules = productItem.getRuleBean();
        LogisticsOrderBean orderBean = new LogisticsOrderBean();
        ProductItem item = new ProductItem();
        boolean createFlag = true;
        if (CollectionUtils.isNotEmpty(logisticsOrderBeans)) {
            for (int i = 0; i < logisticsOrderBeans.size(); i++) {
                LogisticsOrderBean logisticsOrderBean = logisticsOrderBeans.get(i);
                if (!logisticsOrderBean.isFull()) {
                    List<ProductItem> productItemList = logisticsOrderBean.getProductItemList();
                    for (int j = 0; j < productItemList.size(); j++) {
                        ProductItem productItem1 = productItemList.get(j);
                        LogisticsRuleBean ruleBean = productItem1.getRuleBean();
                        // 满足混装需求
                        // 1. 先获取自己的混装需求，然后获取对方混装需求，取最小 最后判断总订单是否满足
                        if (compitableMix(ruleBean, productItem) && compitableMix(logisticsRules, productItem)) {
                            List<ProductItem> lists = new ArrayList<>();
                            lists.addAll(productItemList);
                            lists.add(productItem);
                            if (totalCompitableMix(lists)) {
                                BeanUtils.copyProperties(productItem, item);
                                logisticsOrderBean.getProductItemList().add(item);
                                logisticsOrderBean.setTotalNumber(logisticsOrderBean.getTotalNumber() + item.getNumber());
                                logisticsOrderBean.setTotalPrice(logisticsOrderBean.getTotalPrice() + item.getPrice() * item.getNumber());
                                logisticsOrderBean.setTotalWeight(logisticsOrderBean.getTotalWeight() + item.getWeight() * item.getNumber());
                                logisticsOrderBean.setExpressPrice(expressCalcService.getExpressPrice(item.getLogisticType(), logisticsOrderBean));
                                if (item.getNumber() > logisticsRules.getNumberLimit() && item.getWeight() > logisticsRules.getWeightLimit() && item.getPrice() > productItem.getPrice()) {
                                    orderBean.setFull(true);
                                } else {
                                    orderBean.setFull(false);
                                }
                                productItem.setNumber(0);
                                createFlag = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (createFlag) {
            BeanUtils.copyProperties(productItem, item);
            item.setNumber(0);
            int number = compitableSingleNumber(logisticsRules, productItem, productItem.getNumber());
            item.setNumber(number);
            productItem.setNumber(productItem.getNumber() - number);
            if (productItem.getNumber() > 0) {
                orderBean.setFull(true);
            }
            orderBean.getProductItemList().add(item);
            orderBean.setTotalNumber(orderBean.getTotalNumber() + item.getNumber());
            orderBean.setTotalPrice(orderBean.getTotalPrice() + item.getPrice() * item.getNumber());
            orderBean.setTotalWeight(orderBean.getTotalWeight() + item.getWeight() * item.getNumber());
            orderBean.setExpressPrice(expressCalcService.getExpressPrice(item.getLogisticType(), orderBean));
            logisticsOrderBeans.add(orderBean);
        }
        if (productItem.getNumber() == 0) {
            return logisticsOrderBeans;
        } else {
            return createLogisticsOrder(logisticsOrderBeans, productItem);
        }
    }

    private int compitableSingleNumber(LogisticsRuleBean ruleBean, ProductItem item, int number) {
        boolean flag = true;
        if (null == ruleBean) {
            return number;
        }
        if (null != ruleBean.getNumberLimit() && ruleBean.getNumberLimit() < number) {
            flag = false;
        }
        if (null != ruleBean.getPriceLimit() && ruleBean.getPriceLimit() < item.getPrice() * number) {
            flag = false;
        }
        if (null != ruleBean.getWeightLimit() && ruleBean.getWeightLimit() < item.getWeight() * number) {
            flag = false;
        }
        if (flag) {
            return number;
        }
        return compitableSingleNumber(ruleBean, item, --number);
    }

    private boolean compitableMix(LogisticsRuleBean ruleBean, ProductItem item) {
        if (null == ruleBean) {
            return true;
        }
        if (null != ruleBean.getMixNumberLimit() && ruleBean.getMixNumberLimit() < item.getNumber()) {
            return false;
        }
        if (null != ruleBean.getMixPriceLimit() && ruleBean.getMixPriceLimit() < item.getPrice() * item.getNumber()) {
            return false;
        }
        if (null != ruleBean.getMixWeightLimit() && ruleBean.getMixWeightLimit() < item.getWeight() * item.getNumber()) {
            return false;
        }
        return true;
    }

    private boolean compitableTotalMix(LogisticsRuleBean ruleBean, ProductItem item) {
        if (null == ruleBean) {
            return true;
        }
        if (null != ruleBean.getMixNumberLimit() && ruleBean.getMixNumberLimit() < item.getNumber()) {
            return false;
        }
        if (null != ruleBean.getMixPriceLimit() && ruleBean.getMixPriceLimit() < item.getPrice()) {
            return false;
        }
        if (null != ruleBean.getMixWeightLimit() && ruleBean.getMixWeightLimit() < item.getWeight()) {
            return false;
        }
        return true;
    }

    private boolean totalCompitableMix(List<ProductItem> list) {
        int amount = 0;
        double weight = 0;
        double price = 0;
        for (int i = 0; i < list.size(); i++) {
            int number = list.get(i).getNumber();
            amount += number;
            weight += list.get(i).getWeight() * number;
            price += list.get(i).getPrice() * number;
        }
        for (int i = 0; i < list.size(); i++) {
            if (!compitableTotalMix(list.get(i).getRuleBean(), new ProductItem(amount, weight, price))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws AxisFault {
        try {
            String endpoint = "http://www.zhonghuan.com.au:8085/API/cxf/au/recordservice?wsdl";
            // 直接引用远程的wsdl文件
            // 以下都是套路
            org.apache.axis.client.Service service = new org.apache.axis.client.Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setTimeout(2000);
            call.setOperationName(new QName("http://zh.au.service.RecordServiceI.com", "getRecordrtxml"));// WSDL里面描述的接口名称
            call.addParameter("stock",
                    org.apache.axis.encoding.XMLType.XSD_DATE,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            String xml = getxml();
            String result = (String) call.invoke(new Object[]{xml});
            // 给方法传递参数，并且调用方法
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public static String getxml() {
        String stock = "";
        stock += "<ydjbxx>";
        stock += "<chrusername>usertest</chrusername>";
        stock += "<chrstockcode>au</chrstockcode>";
        stock += "<chrpassword>123456</chrpassword>";
        stock += "<chryyrmc></chryyrmc>";
        stock += "<chrzydhm></chrzydhm>";
        stock += "<chrhbh></chrhbh>";
        stock += "<chrjckrq></chrjckrq>";
        stock += "<chrzl>1</chrzl>";
        stock += "<chrsjr>12</chrsjr>";
        stock += "<chrsjrdz>1213</chrsjrdz>";
        stock += "<chrsjrdh>13626994142</chrsjrdh>";
        stock += "<chrjjr>luyuan</chrjjr>";
        stock += "<chrjjrdh>0450494903</chrjjrdh>";
        stock += "<chrsfzhm>352227198407180525</chrsfzhm>";
        stock += "<ydhwxxlist>";
        stock += "<ydhwxx>";
        stock += "<chrpm>成人奶粉</chrpm>";
        stock += "<chrpp>德运</chrpp>";
        stock += "<chrggxh>900g</chrggxh>";
        stock += "<chrjz>5.00</chrjz>";
        stock += "<chrjs>2</chrjs>";
        stock += "</ydhwxx>";
        stock += "<ydhwxx>";
        stock += "<chrpm>成人奶粉</chrpm>";
        stock += "<chrpp>德运</chrpp>";
        stock += "<chrggxh>900g</chrggxh>";
        stock += "<chrjz>50.00</chrjz>";
        stock += "<chrjs>2</chrjs>";
        stock += "</ydhwxx>";
        stock += "</ydhwxxlist>";
        stock += "</ydjbxx>";
        return stock;
    }
}
