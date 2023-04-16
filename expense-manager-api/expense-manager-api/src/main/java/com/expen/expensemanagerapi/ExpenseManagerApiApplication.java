package com.expen.expensemanagerapi;

import com.expen.expensemanagerapi.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.io.File;

@SpringBootApplication
public class ExpenseManagerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseManagerApiApplication.class, args);
	}

	// Creating filter resgistraion bean
	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		AuthFilter authFilter = new AuthFilter();

		registrationBean.setFilter(authFilter);
		// we want to apply this filter to protected resources
		registrationBean.addUrlPatterns("/api/categories/*");

		return registrationBean;
	}

}
