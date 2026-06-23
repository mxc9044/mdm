package com.mdm.common.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperationLogAspect {

    private final ObjectMapper objectMapper;

    @Around("@annotation(operationLog)")
    public Object around(ProceedingJoinPoint point, OperationLog operationLog) throws Throwable {
        long startTime = System.currentTimeMillis();
        String module = operationLog.module();
        String operation = operationLog.operation();
        MethodSignature signature = (MethodSignature) point.getSignature();

        Object result = null;
        int responseCode = 200;
        try {
            result = point.proceed();
            return result;
        } catch (Exception e) {
            responseCode = 500;
            throw e;
        } finally {
            long costTime = System.currentTimeMillis() - startTime;
            try {
                logOperation(module, operation, signature.getMethod().getName(),
                        getRequestUrl(), responseCode, costTime);
            } catch (Exception e) {
                log.warn("记录操作日志失败", e);
            }
        }
    }

    private void logOperation(String module, String operation, String method,
                              String url, int responseCode, long costTime) {
        log.info("操作日志 - 模块:{} 操作:{} 方法:{} URL:{} 响应码:{} 耗时:{}ms",
                module, operation, method, url, responseCode, costTime);
    }

    private String getRequestUrl() {
        try {
            ServletRequestAttributes attrs =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attrs != null) {
                HttpServletRequest request = attrs.getRequest();
                return request.getRequestURI();
            }
        } catch (Exception ignored) {}
        return "";
    }
}
