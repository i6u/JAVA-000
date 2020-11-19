package org.geekbang.week01;

import org.junit.Test;


public class HelloClassLoaderTest {

    @Test
    public void classTest() throws Exception {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> clazz = helloClassLoader.findClass("Hello");
        Object obj = clazz.getConstructor().newInstance();
        clazz.getMethod("hello").invoke(obj);
    }
}