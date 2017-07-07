package com.hxt.controller;

import com.hxt.service.DetectService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Huangxiutao on 2017/7/5.
 */
@RequestMapping("/api")
@Controller
public class ApiController {

    @Autowired
    DetectService detectService;

    @RequestMapping("/detect")
    @ResponseBody
    public String detect(){
        String  result=detectService.getDetectResult();
        return result;
    }

    @RequestMapping("/detect2")
    @ResponseBody
    public String detect2(@RequestParam("images") MultipartFile images){
        System.out.println("上传图片名："+images.getName());
        System.out.println("上传图片名OriginalFilename："+images.getOriginalFilename());
        JSONObject jsonObject;
        try {
            File imegeFilePath=new File("D://FaceIdentify//");
            if(!imegeFilePath.exists()){
                imegeFilePath.mkdirs();
            }
            File imageFile=new File(imegeFilePath,images.getOriginalFilename());
            imageFile.createNewFile();
            images.transferTo(imageFile);
            jsonObject = new JSONObject(detectService.getDetectResult(imageFile));
            System.out.println(jsonObject);
            //String xS=(String)jsonObject.get("location");

            /*BufferedImage image= ImageIO.read(imageFile);
            Graphics g=image.getGraphics();//生成画笔
            g.drawRect(x,y,width,height);//描绘人脸的矩形框
            OutputStream outImage = new FileOutputStream("FaceIdentify"+"_"+images.getOriginalFilename());
            JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
            enc.encode(image);


            outImage.close();*/


            return String.valueOf(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
