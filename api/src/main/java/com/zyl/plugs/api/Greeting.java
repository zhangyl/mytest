package com.zyl.plugs.api;

import org.pf4j.ExtensionPoint;


public interface Greeting extends ExtensionPoint {
    String getGreeting();
}
