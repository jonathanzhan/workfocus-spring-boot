/**
 * Copyright  2014-2016 whatlookingfor@gmail.com(Jonathan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whatlookingfor.core.config;

import com.whatlookingfor.common.config.Global;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Spring mvc的配置
 *
 * @author Jonathan
 * @version 2016/5/25 17:49
 * @since JDK 7.0+
 */
//@EnableWebMvc
//@ComponentScan
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements EnvironmentAware {

	private final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	private RelaxedPropertyResolver propertyResolver;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
	}


//	/**
//	 * 定义Spring mvc的拦截器
//	 * @param registry
//	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 多个拦截器组成一个拦截器链
//		// addPathPatterns 用于添加拦截规则
//		// excludePathPatterns 用户排除拦截
//		registry.addInterceptor(new HandlerInterceptor() {
//			@Override
//			public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
//				logger.info("在请求处理之前进行调用（Controller方法调用之前）");
//				return true;
//			}
//
//			@Override
//			public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
//				logger.info("请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
//			}
//
//			@Override
//			public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
//				logger.info("在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行(主要是用于进行资源清理工作)");
//			}
//		});
//		super.addInterceptors(registry);
//	}

	/**
	 * 默认资源映射
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/").setCachePeriod(31536000);

		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

		// 访问myres根目录下的fengjing.jpg 的URL为 http://localhost:8080/myres/fengjing.jpg 不影响Spring Boot的默认的 /** 映射，可以同时使用。
		// 访问myres根目录下的fengjing.jpg 的URL为 http://localhost:8080/fengjing.jpg （/** 会覆盖系统默认的配置）
		// registry.addResourceHandler("/**").addResourceLocations("classpath:/myres/").addResourceLocations("classpath:/static/");
		super.addResourceHandlers(registry);
	}

	/**
	 * mvc:view-controller可以在不需要Controller处理request的情况，转向到设置的View
	 * <mvc:view-controller path="/" view-name="home"/>
	 * @param registry
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("redirect:"+ Global.getConfig("spring.mvc.view.index"));
	}


	/**
	 * 定义视图文件解析
	 * @return
	 */
//	@Bean
//	public InternalResourceViewResolver viewResolver() {
//		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//		logger.debug("sadsadas");
//		viewResolver.setPrefix("/WEB-INF/views/");
//		viewResolver.setSuffix(".jsp");
//		return viewResolver;
//	}


//	@Override
//	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		for (HttpMessageConverter<?> httpMessageConverter : converters) {
//			// 为 MappingJackson2HttpMessageConverter 添加 "application/javascript" 支持，用于响应JSONP的Content-Type
//			if(httpMessageConverter instanceof MappingJackson2HttpMessageConverter){
//				MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter)httpMessageConverter;
//				List<MediaType> medisTypeList = new ArrayList<>(convert.getSupportedMediaTypes());
//				medisTypeList.add(MediaType.valueOf("application/javascript;charset=UTF-8"));
//				convert.setSupportedMediaTypes(medisTypeList);
//				break;
//			}
//		}
//		super.extendMessageConverters(converters);
//	}
//
//	@Override
//	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//		configurer.defaultContentType(MediaType.TEXT_HTML)
////		.useJaf(true)
////		.favorPathExtension(true)
//				.mediaType("xml", MediaType.APPLICATION_XML)
//				.mediaType("pdf", MediaType.valueOf("application/pdf"))
//				.mediaType("json", MediaType.APPLICATION_JSON)
//				.mediaType("xls", MediaType.valueOf("application/vnd.ms-excel"))
//				.ignoreAcceptHeader(true);
//	}
//
//	/*
//	 * Configure ContentNegotiatingViewResolver
//	 */
//	@Bean
//	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//		resolver.setContentNegotiationManager(manager);
//
//		// Define all possible view resolvers
//		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//
//		resolvers.add(new JsonViewResolver());
//		resolvers.add(new PdfViewResolver());
//		resolvers.add(new XlsViewResolver());
//
//		resolver.setViewResolvers(resolvers);
//		return resolver;
//	}

	/**
	 * 注册bean验证
	 * @return
	 */
	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor(){
		MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
		return processor;
	}

}
