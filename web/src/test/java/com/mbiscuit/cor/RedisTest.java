package com.mbiscuit.cor;

import com.mbiscuit.core.bill.pojo.BillDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;

public class RedisTest extends CoreApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void redis() {
        BillDetail billDetail = new BillDetail();
        billDetail.setAmount(new BigDecimal("222"));

        redisTemplate.opsForValue().set("a",billDetail);
        Object b = redisTemplate.opsForValue().get("a");
        System.out.println(b);
    }
}
