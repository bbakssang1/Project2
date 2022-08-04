package com.rooke;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;


// 롬복에서 제공하는 로깅 추상화 라이브러리
// 로깅 추상화란 로깅을 직접 하지 않고 로깅 구현체를 찾아 기능을 사용할수 있게 해주는 것
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.debug("==================================");
    log.debug("===============시작===============");
    log.debug("Request URI ====>" + request.getRequestURI());
    return HandlerInterceptor.super.preHandle(request, response, handler);
  }

  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView mav) throws Exception {
    log.debug("================끝================");
    log.debug("==================================");
    HandlerInterceptor.super.postHandle(request, response, handler, mav);

  }
}
