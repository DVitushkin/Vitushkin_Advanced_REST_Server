package com.dunice.Vitushkin_Advanced_REST_Server.logging;

import com.dunice.Vitushkin_Advanced_REST_Server.models.LogEvent;
import com.dunice.Vitushkin_Advanced_REST_Server.repository.LogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerInterceptor implements HandlerInterceptor {
    private final LogRepository logRepository;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) throws Exception {
        if (ex != null) {
            ex.printStackTrace();
        }

        LogEvent logMessage;
        if (ex != null) {
            logMessage = LogEvent.builder()
                    .logLevel("INFO")
                    .request("[" + request.getMethod() + "]" + request.getRequestURI() + request.getParameterMap())
                    .exception(ex.getMessage())
                    .build();
        }
        else {
            logMessage = LogEvent.builder()
                    .logLevel("INFO")
                    .request("[" + request.getMethod() + "]" + request.getRequestURI() + request.getParameterMap())
                    .exception(null)
                    .build();
        }
        logRepository.save(logMessage);
        log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
    }
}
