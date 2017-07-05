package com.hxt.service;

import com.baidu.aip.util.Base64Util;
import com.hxt.utils.FileUtil;
import com.hxt.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * 人脸检测
 * Created by Huangxiutao on 2017/7/5.
 */
@Service
public class DetectService {
    @Autowired
    AuthService authService;
    /**
     * 代码中所需工具类
     * FileUtil,Base64Util,HttpUtil请从
     * https://ai.baidu.com/file/BA73D199EED14D8AA5FC5A4BF4BDDA34
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/88C6E86FB5D141889391693FC84504B1
     * 下载
     */
    public String getDetectResult(){
        // 人脸检测 url
        String detectUrl = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
        // 本地文件路径
        String filePath = "C:\\Users\\Tao\\Pictures\\8c508b22720e0cf39d26bb0a0246f21fbf09aa74.jpg";
        try {
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            System.out.println("imgStr:" + imgStr);
            // face_fields 自定之指定返回的人脸特征值
            String params =
                    "max_face_num=5&face_fields=age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities&"
                            + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = authService.getAuth();
            String result = HttpUtil.post(detectUrl, accessToken, params);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
