package com.cafe24.jblog.config;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import com.cafe24.security.AuthInterceptor;
import com.cafe24.security.AuthLoginInterceptor;
import com.cafe24.security.AuthLogoutInterceptor;
import com.cafe24.security.AuthUserHandlerArgumentResolver;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{
	
   // Message Converter
   @Bean
   public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter () {
      Jackson2ObjectMapperBuilder builder = 
            new Jackson2ObjectMapperBuilder()
               .indentOutput( true )
               .dateFormat( new SimpleDateFormat("yyyy-MM-dd") )
               .modulesToInstall( new ParameterNamesModule() );
      
      MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter( builder.build() );
      
      converter.setSupportedMediaTypes(Arrays.asList( new MediaType( "application", "json", Charset.forName("UTF-8") ) ));
      return converter;
   }
   
   @Bean
   public StringHttpMessageConverter stringHttpMessageConverter () {
      StringHttpMessageConverter converter = new StringHttpMessageConverter();
      converter.setSupportedMediaTypes(Arrays.asList( new MediaType( "text", "html", Charset.forName("UTF-8") ) ));
      
      return converter;
   }
   
   @Override
   public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      converters.add(mappingJackson2HttpMessageConverter());
      converters.add(stringHttpMessageConverter());
   }
	   
   @Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerArgumentResolver());
	}

	//Argument Resolver
	@Bean
	public AuthUserHandlerArgumentResolver authUserHandlerArgumentResolver() {
		return new AuthUserHandlerArgumentResolver();
	}
	
	@Bean
	public AuthInterceptor authInterceptor() {
		return new AuthInterceptor();
	}
	
	@Bean
	public AuthLoginInterceptor authLoginInterceptor() {
		return new AuthLoginInterceptor();
	}
	
	@Bean
	public AuthLogoutInterceptor authLogoutInterceptor() {
		return new AuthLogoutInterceptor();
	}
	
	//Interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authLoginInterceptor()).addPathPatterns("/user/auth");
		registry.addInterceptor(authLogoutInterceptor()).addPathPatterns("/user/logout");
		registry.addInterceptor(authInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/auth")
															.excludePathPatterns("/user/logout")
															.excludePathPatterns("/assets/**");
	}
	
	//Fileupload
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/image/**").addResourceLocations("file:/jblog-uploads");
		
	}
	
}
