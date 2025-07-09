package com.zyl.mytest.pojo;


//import com.zyl.mymodule.spi.MySpi;

import java.util.ServiceLoader;

public class Foo {
//    private static MySpi mySpi;
//    static {
//        ServiceLoader<MySpi> loader = ServiceLoader.load(MySpi.class);
//        for (MySpi spi : loader) {
//            if ("Custom".equals(spi.getName())) {
//                mySpi = spi;
//                break;
//            }
//        }
//    }
    private String name;
    private String nameZh;
    private String nameEn;
    private Integer age;


    public String getName(String lang) {
        if("zh".equals(lang)) {
            return nameZh;
        }
        if("en".equals(lang)) {
            return nameEn;
        }
        return name;
    }

    public String getNameBySpiLang() {
//        if(mySpi != null) {
//            String lang = mySpi.getLang();
//            return getName(lang);
//        }
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
