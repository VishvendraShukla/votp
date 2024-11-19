package com.vishvendra.votp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class Interceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long requestStartTime = System.currentTimeMillis();
    String logId = UUID.randomUUID().toString();
    request.setAttribute("startTime", requestStartTime);
    request.setAttribute("logId", logId);
    String requestType = request.getMethod();
    log.info(
        "Incoming Request from address={} for URL={} HTTP {} requestId={}",
        request.getRemoteAddr(),
        request.getRequestURI(),
        requestType,
        logId
    );
    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    long requestEndTime = System.currentTimeMillis();
    long requestStartTime = (long) request.getAttribute("startTime");
    request.removeAttribute("startTime");
    String logId = (String) request.getAttribute("logId");
    int responseStatus = response.getStatus();

    log.info("Service provided in {} ms with {} requestId={}",
        requestEndTime - requestStartTime,
        responseStatus,
        logId
    );
  }
}