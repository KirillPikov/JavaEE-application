package com.mediasoft.bookstore.common.loggable;

import org.apache.log4j.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Loggable
public class LoggingInterceptor {

    private Logger logger = Logger.getLogger(LoggingInterceptor.class);

    @AroundInvoke
    public Object logMethod(InvocationContext invocationContext) throws Exception {
        logger.info("Entering into: " + invocationContext.getTarget().toString() + " | " + invocationContext.getMethod().toString());
        try {
            return invocationContext.proceed();
        } finally {
            logger.info("Exiting from: " + invocationContext.getTarget().toString() + " | " + invocationContext.getMethod().toString());
        }
    }
}