package com.baizhi.action;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.util.CreateValidateCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("identify")
public class YzmAction {
    @RequestMapping("obtain")
    public void obtain(String phone, HttpSession session) {
        CreateValidateCode code = new CreateValidateCode();
        String string = code.getString();
        String s = JSON.toJSONString(string);
        session.setAttribute(phone, string);
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4FoFb6ocu342WFNiYtyE", "smp8dnZWXvWAZ4uodDrKJHzTgfo4nM");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "多彩app");
        request.putQueryParameter("TemplateCode", "SMS_176942030");
        request.putQueryParameter("TemplateParam", "{\"code\":" + s + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("check")
    public String check(String phone, String code, HttpSession session) {
        Object attribute = session.getAttribute(phone);
        if (attribute.equals(code)) {
            return "success";
        } else {
            return "fail";
        }
    }
}
