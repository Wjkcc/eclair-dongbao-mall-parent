//package com.eclair.dongbaoums;
//
//
//import com.eclair.dongbaoums.entity.UmsMember;
//import com.eclair.dongbaoums.mapper.UmsMemberMapper;
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.UUID;
//
///**
// * @description:
// * @author: wjk
// * @date: 2021/1/17 22:08
// **/
////@RunWith(SpringRunner.class)
////@SpringBootTest(classes = UmsApplication.class)
//@SpringBootTest
//public class ApplicationTest {
//    @Resource
//     UmsMemberMapper umsMemberMapper;
//
//    @Test
//    public void testInsert() {
//        UmsMember t = new UmsMember();
//        t.setUsername("cpf21"+ UUID.randomUUID().toString().substring(4));
//        t.setStatus(0);
//        t.setPassword("1");
//        t.setNote("note");
//        t.setNickName("nick");
//        t.setEmail("email");
//        t.setCreateTime(new Date());
//        umsMemberMapper.insert(t);
//
//    }
//
//
//}
