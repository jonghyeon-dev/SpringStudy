package com.sarang.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomXssFilter implements Filter {

  private FilterConfig filterConfig;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.filterConfig = filterConfig;
  }

  @Override
  public void destroy() {
    this.filterConfig = null;
  }

  //실제 XSS 필터링 실행하는 부분
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    log.info("Start XssFiltering");
			
    //들어온 HttpRequest 분해하여 xss 필터링
    HttpServletRequestWrapper requestWrapper = new XssServletWrapper((HttpServletRequest) request);
    chain.doFilter(requestWrapper, response);
  }
}
