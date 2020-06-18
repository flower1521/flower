package com.trans.springBeansTest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * BeanNameAware：注入当前bean对应beanName
 * BeanFactoryAware：注入当前BeanFactory容器的引用
 * BeanFactoryAware：BeanFactoryAware和init-method是Spring为bean初始化提供的扩展点
 * DisposableBean：DisposableBean和destroy-method是Spring为bean销毁提供的扩展点
 * InitializingBean：对Bean进行初始化逻辑的扩展，在afterPropertiesSet()方法中可以写入我们的初始化逻辑
 * create by lcl on 2020/6/16 10:56
 */
public class Person implements BeanFactoryAware, BeanNameAware, InitializingBean, DisposableBean {
    private String name;
    private String address;
    private int phone;

    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("【构造器】调用Person的构造器实例化");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("【注入属性】注入属性name");
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        System.out.println("【注入属性】注入属性address");
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        System.out.println("【注入属性】注入属性phone");
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", beanFactory=" + beanFactory +
                ", beanName='" + beanName + '\'' +
                '}';
    }

    //这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
        this.beanFactory = beanFactory;
    }

    //这是BeanNameAware接口方法
    @Override
    public void setBeanName(String s) {
        System.out.println("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
        this.beanName = s;
    }

    //这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
    }

    //这是DiposibleBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("【DiposibleBean接口】调用DiposibleBean.destroy()");
    }

    //通过<bean>的init-method属性指定的初始化方法
    public void myInit() {
        System.out.println("【init-method】调用<bean>的init-method属性指定的初始化方法");
    }

    //通过<bean>的destroy-method属性指定的初始化方法
    public void myDestory() {
        System.out.println("【destroy-method】调用<bean>的destroy-method属性指定的初始化方法");
    }
}
