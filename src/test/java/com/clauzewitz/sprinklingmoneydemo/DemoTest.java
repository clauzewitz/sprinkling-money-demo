package com.clauzewitz.sprinklingmoneydemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class DemoTest {
    @Test
    public void test() throws Exception {
        float test1 = 193824.1223f;
        System.out.println(test1);
        System.out.println((int) test1);

        System.out.println((10.0f / 20) >= 1);

        BigDecimal percentage = BigDecimal.ONE;
        BigDecimal calcPercentage = percentage.divide(new BigDecimal(200.0f), 2, RoundingMode.UP);
        System.out.println(calcPercentage.toString());
        System.out.println(calcPercentage.multiply(new BigDecimal(200.0f)).toString());
    }

//    @Disabled
//    @Test
//    public void test1() throws Exception {
//        List<Integer> a = new ArrayList<>();
//
//        a.add(1);
//        a.add(2);
//
//        a.stream().sorted();
//        String test = "\\\"GET\\s(.*)\\sHTTP\\/1.0\\\"\\s200";
//        test = "/images/NASA-logosmall.gif";
//
//        test = "\\[\\d{2}\\/Jul\\/\\d{4}:(\\d{2}:)\\1\\d{2}\\s-\\d{4}\\]\\s\\\"GET\\s.*?((.*?)\\.(gif|GIF))\\sHTTP\\/1.0\\\"\\s200a";
//
//        test = "\\[(\\d{2}\\/\\w{3}\\/\\d{4}:\\d{2}:\\d{2}:\\d{2})\\s-\\d{4}\\]\\s\\\"GET\\s.*?((.*?)\\.(gif|GIF))\\sHTTP\\/1.0\\\"\\s200";
//        test.replace("^.*\\/|\\.[^.]*$", "");
//        BufferedWriter writer = new BufferedWriter(new File());
//
//        Map<String, List<String>> tet  = new HashMap<>();
//
//        List<String> ddd = tet.get("asdfaf");
//
//
//
//
//
//
//        System.out.println(test);
//    }
}
