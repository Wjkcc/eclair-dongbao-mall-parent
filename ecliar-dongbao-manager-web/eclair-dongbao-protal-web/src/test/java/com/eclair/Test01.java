package com.eclair;/**
 * @author
 * @date
 **/

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Author
 * @Time 2021/2/4 14:43
 * @Description
 **/
public class Test01 {
    @Test
    public void test01() {
        ITesseract iTesseract = new Tesseract();
        // 添加语言包
        iTesseract.setDatapath("F:\\tessdata");
//        iTesseract.setLanguage();

        File f = new File("F:\\tessdata\\generate.jfif");
        try {
            final String s = iTesseract.doOCR(f);
            System.out.println("识别");
            System.out.println(s);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
