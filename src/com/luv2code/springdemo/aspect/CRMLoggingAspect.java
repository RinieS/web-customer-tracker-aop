package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	//set up logger 
	
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//set up pointcut declaration
	@Pointcut ("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage () {
		
	}
	
	//do the same fpr service and dao 
	@Pointcut ("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage () {
		
	}
	
	@Pointcut ("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage () {
		
	}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	
	private void forAppFlow() {
		
	}
	
	//add @Before advice
	@Before("forAppFlow()") 
	public void before (JoinPoint theJoinPoint) {
		//display method we are calling 
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("===> in the @Before advice: calling method:  "+ theMethod);
		//display the arguments to the method 
		//get the arguments 
		Object[]args = theJoinPoint.getArgs();
		//loop thrue and display 
		for(Object tempArg: args ) {
			myLogger.info("===>> argument: "+ tempArg);
		}
	}
	
	
	
	
	//add @AfterReturning advice
	
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			
			)
	public void afterReturning (JoinPoint theJoinPoint, Object theResult) {
		//display method we are returning from 
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("***> in the @AfterReturning advice: from method:  "+ theMethod);
		
		//display data returned 
		myLogger.info("***> result: "+ theResult);
		
		
	}

}
