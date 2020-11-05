package com.mbiscuit.core;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = CoreApplication.class)
public class CoreApplicationTests {

    @BeforeAll
    static void beforeAll() {
        System.setProperty("spring.config.additional-location", "file:${user.home}/mbiscuit/");
    }

    @Test
    void someTest() {
//        String a = new String("a");
//        System.out.println(a == a.intern());
//        String b = new String("a");
//        System.out.println(b == b.intern());
//        String a = new String("a");
//        System.out.println(a == "a");
//        System.out.println(a.intern() == "a");
//        String b = new String("b");
//        System.out.println(b == "b");
//        System.out.println(b.intern() == "b");

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
//        String str2 = "计算机软件";
//        System.out.println(str1.intern() == str2);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }
}
