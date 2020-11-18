package org.geekbang;

import org.geekbang.entity.Student;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.StaticApplicationContext;

public class Main {

    public static void main(String[] args) {
        // 1
        ApplicationContext xmlContext = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) xmlContext.getBean("student");
        System.out.println(student);

        // 2
        String xmlPath = "./Week_05/homework-spring-bean/src/main/resources/beans.xml";
        ApplicationContext fileContext =
                new FileSystemXmlApplicationContext(xmlPath);
        Student student1 = (Student) fileContext.getBean("student");
        student1.setId(100002);
        student1.setName("witt");
        System.out.println(student1);

        // 3
        StaticApplicationContext staticContext = new StaticApplicationContext();
        staticContext.registerBean("student", Student.class);
        Student student2 = (Student) staticContext.getBean("student");
        student2.setId(100003);
        student2.setName("witt");
        System.out.println(student2);

        // 4
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(Student.class);
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("id", 100004);
        mutablePropertyValues.add("name", "witt");
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
        defaultListableBeanFactory.registerBeanDefinition("student4", genericBeanDefinition);
        Student student4 = defaultListableBeanFactory.getBean(Student.class);
        System.out.println(student4);

        // 5
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Student.class)
                .addPropertyValue("id", 100005)
                .addPropertyValue("name", "witt");
        beanFactory.registerBeanDefinition("student4", beanDefinitionBuilder.getBeanDefinition());
        Student student5 = beanFactory.getBean(Student.class);
        System.out.println(student5);
    }

}
