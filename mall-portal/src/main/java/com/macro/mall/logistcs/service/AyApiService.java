package com.macro.mall.logistcs.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.macro.mall.logistcs.model.IdentityRecordModel;
import com.macro.mall.logistcs.model.LogisticTrackModel;
import com.macro.mall.logistcs.model.RecordModel;
import com.macro.mall.logistcs.util.HttpClientUtils;
import com.macro.mall.portal.domain.CommonResult;
import com.macro.mall.portal.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AyApiService extends AbstractLogicService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${ay.username}")
    private String memberId;
    @Value("${ay.password}")
    private String password;

    private final String AY_TOKEN = "ay.token";
    private final String auth_url = "http://auth.auexpress.com/api/token";
    private final String create_url = "http://aueapi.auexpress.com/api/AgentShipmentOrder/Crea";
    private final String upload_url = "http://aueapi.auexpress.com/api/PhotoIdUplo";
    private final String track_url = " http://aueapi.auexpress.com/api/ShipmentOrderTrack?OrderId=";
    private static final Logger logger = LoggerFactory.getLogger(AyApiService.class);

    //    @PostConstruct
    public void init() {
        String token = this.getToken();
    }


    private String getToken() {
        Object o = redisTemplate.opsForValue().get(AY_TOKEN);
        if (null != o) {
            return (String) o;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("MemberId", memberId);
        jsonObject.put("Password", password);
        String s = HttpClientUtils.httpPost(auth_url, jsonObject.toJSONString(), ImmutableMap.of("Content-Type", "text/json; charset=utf-8"));
        HashMap hashMap = JsonUtil.jsonToPojo(s, HashMap.class);
        if (!CollectionUtils.isEmpty(hashMap) && hashMap.containsKey("Token")) {
            String token = (String) hashMap.get("Token");
            redisTemplate.opsForValue().set(AY_TOKEN, token, 23, TimeUnit.HOURS);
            return token;
        }
        return s;
    }

    @Override
    public CommonResult recordLogicOrder(RecordModel recordModel) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("MemberId", memberId);
        jsonObject.put("BrandId", 1);
        jsonObject.put("SenderName", recordModel.getPosterName());
        jsonObject.put("SenderPhone", recordModel.getPosterPhone());
        jsonObject.put("ReceiverName", recordModel.getRecieverName());
        jsonObject.put("ReceiverPhone", recordModel.getRecieverPhone());
        jsonObject.put("ReceiverProvince", recordModel.getRecieverProvince());
        jsonObject.put("ReceiverCity", recordModel.getRecieverProvince());
        jsonObject.put("ReceiverAddr1", recordModel.getRecieverAddress());
        StringBuilder content = new StringBuilder();
        recordModel.getItems().forEach(item -> {
            content.append(item.getProductName())
                    .append(" ")
                    .append(item.getWeight())
                    .append("g ")
                    .append(item.getQuality())
                    .append(";");
        });
        jsonObject.put("ShipmentContent ", content.toString());
        Map<String, String> hashMap = this.httpPost(jsonObject.toJSONString(), create_url);
        logger.info("record order:{}, result is :{}", recordModel.getOrderId(), JsonUtil.objectToJson(hashMap));
        if (!CollectionUtils.isEmpty(hashMap) && hashMap.containsKey("Code") && hashMap.get("Code").equals("0")) {
            return new CommonResult().success(hashMap.get("Message"));
        }
        return new CommonResult().failed(JsonUtil.objectToJson(hashMap));
    }

    private Map<String, String> httpPost(String postBody, String url) {
        String token = this.getToken();
        String result = HttpClientUtils.httpPost(url, postBody, ImmutableMap.of("Content-Type", "text/json; charset=utf-8", "Authorization", token));
        return JsonUtil.jsonToPojo(result, HashMap.class);
    }

    @Override
    public CommonResult recordIdentity(IdentityRecordModel recordModel) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("PhotoFront", getBase64Image(recordModel.getIdentityFrontUrl()));
            jsonObject.put("PhotoRear", getBase64Image(recordModel.getIdentityFrontUrl()));
            Map<String, String> hashMap = this.httpPost(jsonObject.toJSONString(), upload_url);
            logger.info("upload identity name:{}, result is :{}", recordModel.getName(), JsonUtil.objectToJson(hashMap));
            if (!CollectionUtils.isEmpty(hashMap) && hashMap.containsKey("Code") && hashMap.get("Code").equals("0")) {
                return new CommonResult().success(hashMap.get("Message"));
            }
            return new CommonResult().failed(JsonUtil.objectToJson(hashMap));
        } catch (Exception ex) {
            logger.error("upload identity failed", ex);
        }
        return new CommonResult().failed();
    }

    @Override
    public CommonResult getLogicTrack(String logicNo) {
        String result = HttpClientUtils.httpGet(track_url + logicNo, "UTF-8", ImmutableMap.of("Content-Type", "text/json; charset=utf-8", "Authorization", getToken()));
        HashMap hashMap = JsonUtil.jsonToPojo(result, HashMap.class);
        logger.info("track no:{}, result:{}", logicNo, result);
        if (!CollectionUtils.isEmpty(hashMap) && hashMap.containsKey("Code") && hashMap.get("Code").equals("0")) {
            String trackList = (String) hashMap.get("TrackList");
            List<HashMap> hashMaps = JsonUtil.jsonToList(trackList, HashMap.class);
            if(!CollectionUtils.isEmpty(hashMaps)) {
                List<LogisticTrackModel> collect = hashMaps.stream().map(hashMap1 -> new LogisticTrackModel((String) hashMap1.get("StatusDetail"), (String) hashMap1.get("StatusTime"), (String) hashMap1.get("Location"))).collect(Collectors.toList());
                return new CommonResult().success(collect);
            }
        }
        return new CommonResult().failed(JsonUtil.objectToJson(hashMap));
    }
}
