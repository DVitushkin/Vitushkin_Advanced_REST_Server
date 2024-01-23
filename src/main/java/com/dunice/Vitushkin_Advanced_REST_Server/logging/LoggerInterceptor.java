package com.dunice.Vitushkin_Advanced_REST_Server.logging;

import java.util.Arrays;
import java.util.Map;

import com.dunice.Vitushkin_Advanced_REST_Server.models.LogEntity;
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

    private static String convertParamMapToString(Map<String, String[]> paramMap) {
        if (paramMap.isEmpty()) {
            return "";
        }
        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : paramMap.keySet()) {
            mapAsString.append(key).append("=").append(Arrays.toString(paramMap.get(key))).append(", ");
        }
        mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");
        return mapAsString.toString();
    }

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
        LogEntity logMsg = new LogEntity()
                .setHttpMethod(request.getMethod())
                .setRequestUri(request.getRequestURI())
                .setParameters(convertParamMapToString(request.getParameterMap()))
                .setHttpProtocol(request.getProtocol())
                .setCountry(request.getLocale().getDisplayCountry())
                .setJavaMethod(request.getMethod())
                .setStatusCode(String.valueOf(response.getStatus()))
                .setException(String.valueOf(ex));
        logRepository.save(logMsg);

        log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
    }
}
