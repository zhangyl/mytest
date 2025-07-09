package com.zyl.plugs.impl;


import com.zyl.plugs.api.Greeting;
import org.pf4j.Extension;


@Extension
public class WelcomeGreeting implements Greeting {

    public String getGreeting() {
        return "Welcome";
    }
}

