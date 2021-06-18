package com.eclair.code.impl;/**
 * @author
 * @date
 **/

import com.eclair.code.AbstractCode;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * 不采用单列，防止并发问题，导致属性改变
 * @Author
 * @Time 2021/2/4 10:31
 * @Description
 **/
@Data
public class GraphCode implements AbstractCode {

    private  String code;

    private  String picFormat = "jpeg";

    private  InputStream image;

    private  Integer high = 100;

    private  Integer width = 400;

    private static GraphCode instance;

    static {
        instance = new GraphCode();
    }

    private GraphCode() {
        System.out.println("init");
    }
    @Override
    public  String getName() {
        return "graphCode";
    }

    public static GraphCode getInstance() {
        return instance;
    }

    public synchronized void doWork() {
        // 图形缓冲，用于生成图形
        BufferedImage bufferedImage = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        // 画笔
        Graphics graphics = bufferedImage.getGraphics();
        // 填充颜色，画图形,白色
        graphics.setColor(new Color(255,255,255));
        // 矩形颜色填充
        graphics.fillRect(0,0,width,high);
        // 字体颜色样式大小
        graphics.setFont(new Font("宋体",Font.PLAIN,30));
        // code和随机数生成
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            sb.append(i1);
            graphics.setColor(new Color(47,79,79));
            graphics.drawString(i1+"",(width/6)*i+20, 55);
        }
        setCode(sb.toString());
        // 关闭
        graphics.dispose();

        InputStream inputStream = null;
        ByteOutputStream outputStream = new ByteOutputStream();
        // 赋值到inoutStream
        try {
            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            ImageIO.write(bufferedImage,picFormat,imageOutputStream);

            inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        setImage(inputStream);
    }

}
