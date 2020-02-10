package com.capgemini.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CustomerCare {
	
	Logger logger=LoggerFactory.getLogger(CustomerCare.class);
	
	@AfterThrowing(pointcut = "execution(* com.capgemini.service.CustomerCareServiceImpl.*(..))", throwing = "exception")
	public void afterThrowinCustomerException(Exception exception)	{
		
		logger.error(exception.getMessage());
	}
	
}
