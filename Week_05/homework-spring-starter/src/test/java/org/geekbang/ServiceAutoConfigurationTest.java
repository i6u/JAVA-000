package org.geekbang;

import org.geekbang.service.MyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ServiceAutoConfigurationTest {

    @Resource
    private MyService myService;

    @Test
    public void myServiceTest() {
        String result = myService.showMe();
        assertEquals("I'm ok", result);
    }
}