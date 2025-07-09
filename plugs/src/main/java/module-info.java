
open module com.zyl.mymodule.plugs {
    requires org.pf4j;
    requires com.zyl.mymodule.spi;
    exports com.zyl.plugs.impl;
    provides com.zyl.plugs.api.Greeting with com.zyl.plugs.impl.WelcomeGreeting;
}