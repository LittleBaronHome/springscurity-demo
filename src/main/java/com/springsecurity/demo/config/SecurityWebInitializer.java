package com.springsecurity.demo.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * WebApplicationInitializer是web.xml的取代，会在加载web项目的时候被加载
 * AbstractSecurityWebApplicationInitializer实现了WebApplicationInitializer，并配置了springSecurityFilterChain
 *
 * 相当于在Web.xml中添加了以下配置：
 * <filter>
 *     <filter-name>springSecurityFilterChain</filter-name>
 *     <filter-class>
 *         org.springframework.web.filter.DelegatingFilterProxy
 *     </filter-class>
 * </filter>
 *
 * <filter-mapping>
 *     <filter-name>springSecurityFilterChain</filter-name>
 *     <url-pattern>/*</url-pattern>
 * </filter-mapping>
 *
 */
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer { }
