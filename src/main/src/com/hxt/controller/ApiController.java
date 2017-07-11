package com.hxt.controller;

import com.hxt.service.DetectService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

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
            Integer result_num= (Integer) jsonObject.get("result_num");//人脸数量
            BufferedImage image= ImageIO.read(imageFile);
            if(result_num>0){
                JSONArray jsonArray=((JSONArray)jsonObject.get("result"));
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject face=jsonArray.getJSONObject(i);
                    Integer rotation_angle=(Integer)face.get("rotation_angle");//人脸框相对于竖直方向的顺时针旋转角，[-180,180]
                    JSONObject location=(JSONObject) face.get("location");
                    Integer x=(Integer)location.get("left");
                    Integer y=(Integer)location.get("top");
                    Integer width=(Integer)location.get("width");
                    Integer height=(Integer)location.get("height");
                    Graphics2D graphics2D=image.createGraphics();//生成画笔
//                  g.drawRect(x,y,width,height);//描绘人脸的矩形框----2017年7月11日10:29:12  有些图片的人脸不是正的。。。。 用Graphics2D画图
                    Rectangle2D rectangle2D=new Rectangle2D.Double(x,y,width,height);

                    AffineTransform transform=new AffineTransform();//旋转
                    transform.rotate(rotation_angle*Math.PI/180,x,y);//以矩形框的左上角（x,y）为中心旋转
                    graphics2D.setColor(Color.green);//颜色
                    BasicStroke bs1 = new BasicStroke(5);// 笔画的轮廓（画笔宽度/线宽为5px）
                    graphics2D.setStroke(bs1);
                    graphics2D.setTransform(transform);
                    graphics2D.draw(rectangle2D);
                    //输出图像
                    //ImageIO.write(image,"PNG",new File(imegeFilePath,"FaceIdentify"+"_"+i+""+images.getOriginalFilename()));//这里会为每一张脸输出一张图，不合适。。

                    /*OutputStream outImage = new FileOutputStream("FaceIdentify"+"_"+images.getOriginalFilename());
                    JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(outImage);
                    enc.encode(image);
                    outImage.close();*/
                    //释放画笔
                    graphics2D.dispose();
                }
                ImageIO.write(image,"PNG",new File(imegeFilePath,"FaceIdentify"+"_"+images.getOriginalFilename()));
            }


            return String.valueOf(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
