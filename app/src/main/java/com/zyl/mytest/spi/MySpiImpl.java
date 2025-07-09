package com.zyl.mytest.spi;

import com.zyl.mymodule.MySpi;

public class MySpiImpl implements MySpi {
    @Override
    public String getName() {
        return "Custom";
    }

    @Override
    public String getLang() {
        return "Hello!";
    }
}
