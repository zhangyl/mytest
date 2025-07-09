package com.zyl.mytest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.zyl.mytest.config.MyConfigBean;
import com.zyl.mytest.pojo.Foo;
import com.zyl.plugs.api.Greeting;
import org.pf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@SpringBootApplication
public class MyTestApp {

    public static ApplicationContext context = null;

	public static void main(String[] args) throws Exception {
		//development模式读取路径是class下面的配置, 这个程序是从外部jar包读取加载，所以不能用development
		System.setProperty("pf4j.mode", RuntimeMode.DEPLOYMENT.name());
		System.setProperty("pf4j.log", "DEBUG");

		Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.DEBUG);
		// jar插件管理器
		PluginManager pluginManager = new JarPluginManager();
		// 加载指定路径插件
		pluginManager.loadPlugin(Paths.get("C:\\Users\\snail\\workspace\\mytest\\plugs\\target\\plugs-1.0.0-SNAPSHOT.jar"));

		// 启动指定插件(也可以加载所有插件)
		pluginManager.startPlugin("welcome-plugin");

		// 执行插件
		List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
		for (Greeting greeting : greetings) {
			System.out.println(">>> " + greeting.getGreeting());
		}
		// 停止并卸载指定插件
		pluginManager.stopPlugin("welcome-plugin");
		pluginManager.unloadPlugin("welcome-plugin");

	}

	public static void main0(String[] args) throws Exception {

		listFilesInAllJars();
		Foo foo = new Foo();
		foo.setNameEn("foo name");
		foo.setNameZh("福名称");
		foo.setName("福名称Name");

		Foo foo2 = new Foo();
		foo2.setNameEn("foo2 name");
		foo2.setNameZh("福2名称");
		foo2.setName("福2名称Name");

		System.out.println(foo.getNameBySpiLang());
		System.out.println(foo2.getName("zh"));

		context = SpringApplication.run(MyTestApp.class, args);
		MyConfigBean myConfigBean = context.getBean(MyConfigBean.class);



		System.out.println("MyTestApp started success. MyConfigBean.hello() = " + myConfigBean.hello());
	}

	public static void listFilesInAllJars() throws IOException {
		// 获取类加载器
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		// 获取所有资源URL
		Enumeration<URL> resources = classLoader.getResources("META-INF/services");

		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			if (resource.getProtocol().equals("jar")) {
				String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!"));

				try (JarFile jarFile = new JarFile(jarPath)) {
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
							System.out.println("JAR: " + jarPath + " -> " + entry.getName());
						}
					}
				}
			}
		}
	}

}
