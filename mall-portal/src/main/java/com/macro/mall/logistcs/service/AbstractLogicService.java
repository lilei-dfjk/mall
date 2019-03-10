package com.macro.mall.logistcs.service;

import com.macro.mall.logistcs.model.IdentityRecordModel;
import com.macro.mall.logistcs.model.RecordModel;
import com.macro.mall.portal.domain.CommonResult;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

public abstract class AbstractLogicService {

    public abstract CommonResult recordLogicOrder(RecordModel recordModel);

    public abstract CommonResult recordIdentity(IdentityRecordModel recordModel);

    public abstract CommonResult getLogicTrack(String logicNo);

    protected static String getBase64Image(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        byte[] bytes = IOUtils.toByteArray(url.openConnection().getInputStream());
        Base64Encoder encoder = new Base64Encoder();
        return encoder.encode(bytes);
    }

}
