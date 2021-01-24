package com.eclair.dongbaoums.Handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * mybatisplus自动填充更新和创建时间
 * @description:
 * @author: wjk
 * @date: 2021/1/21 21:27
 **/
public class MyHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("ddsada");
        this.setFieldValByName("createTime",new Date(), metaObject);
        this.setFieldValByName("updateTime",new Date(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("update_time",new Date(), metaObject);
    }
}
