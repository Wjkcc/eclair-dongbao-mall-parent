package com.eclair.code.impl;/**
 * @author
 * @date
 **/

import com.eclair.code.AbstractCode;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.FileDictionary;
import com.octo.captcha.component.word.wordgenerator.ComposeDictionaryWordGenerator;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

import java.awt.*;

/**
 * @Author
 * @Time 2021/4/19 14:39
 * @Description
 **/
public class JcaptchaCode implements AbstractCode {

    private static  ImageCaptchaService imageCaptchaService = getImageCaptchaService();

    public static ImageCaptchaService getService() {
        return imageCaptchaService;
    }

    /**
     * 验证码生成 要素
     * 背景
     * 字
     * 字体
     * @return
     */
    private static  ImageCaptchaService getImageCaptchaService() {
        // 背景生成
        UniColorBackgroundGenerator uniColorBackgroundGenerator = new UniColorBackgroundGenerator(120,60);
        int[] color = {0,255};
        // 随机颜色生成
        RandomRangeColorGenerator randomRangeColorGenerator = new RandomRangeColorGenerator(color,color,color);
        // 字体生成 最小字体数量和最大数量 字体颜色
        RandomTextPaster randomTextPaster = new RandomTextPaster(4,4, Color.black);
        // 字体大小 最大最小
        RandomFontGenerator randomFontGenerator = new RandomFontGenerator(20,35);
        // 组装图像 字体 背景 字
        ComposedWordToImage composedWordToImage = new ComposedWordToImage(randomFontGenerator,uniColorBackgroundGenerator,randomTextPaster);
        // 生成随机字符串
        ComposeDictionaryWordGenerator composeDictionaryWordGenerator = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
        // 图像和字生成工厂
        GimpyFactory gimpyFactory = new GimpyFactory(composeDictionaryWordGenerator,composedWordToImage);
        // 引擎生成
        GenericCaptchaEngine captchaEngine = new GenericCaptchaEngine(new CaptchaFactory[]{gimpyFactory});

        return new GenericManageableCaptchaService(captchaEngine,30,2000,2000);
    }

    @Override
    public String getName() {
        return "jcaptcha";
    }

}
