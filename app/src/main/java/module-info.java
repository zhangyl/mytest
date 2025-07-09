//import com.zyl.mymodule.MySpi;

open module com.zyl.mymodule.spi.provider {
    requires com.zyl.mymodule.spi;
    requires spring.web;
    requires org.slf4j;
    requires micrometer.tracing;
    requires jakarta.annotation;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires kafka.clients;
    requires spring.kafka;
    requires org.pf4j;
    requires ch.qos.logback.classic;
//    uses MySpi;
//    provides MySpi with com.zyl.mytest.spi.MySpiImpl;
    uses com.zyl.plugs.api.Greeting;
}