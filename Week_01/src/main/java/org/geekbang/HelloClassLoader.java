package org.geekbang;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        HelloClassLoader helloClassLoader = new HelloClassLoader();
        Class<?> clazz = helloClassLoader.findClass("Hello");
        Object obj = clazz.getConstructor().newInstance();
        clazz.getMethod("hello").invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            URL resource = HelloClassLoader.class.getResource("/Hello.xlass");
            bytes = Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255 - bytes[i]);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}