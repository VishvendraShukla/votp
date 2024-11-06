package com.votp.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {

  private final Logger logger = LoggerFactory.getLogger(Interceptor.class);


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    long requestStartTime = System.currentTimeMillis();
    String logId = UUID.randomUUID().toString();
    request.setAttribute("startTime", requestStartTime);
    request.setAttribute("logId", logId);
    String requestType = request.getMethod();
    logger.info(
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

    logger.info("Service provided in {} ms with {} requestId={}",
        requestEndTime - requestStartTime,
        responseStatus,
        logId
    );
  }
}