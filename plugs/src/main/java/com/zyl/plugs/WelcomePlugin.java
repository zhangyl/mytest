package com.zyl.plugs;

import org.pf4j.Plugin;

public class WelcomePlugin extends Plugin {
    @Override
    public void start() {
        System.out.println("WelcomePlugin.start()");
    }

    @Override
    public void stop() {
        System.out.println("WelcomePlugin.stop()");
    }

    @Override
    public void delete() {
        System.out.println("WelcomePlugin.delete()");
    }
}