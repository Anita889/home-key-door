package com.example.homekeydoor.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import java.time.LocalDateTime;

public class RestMappingExceptionResolver extends AbstractHandlerExceptionResolver {
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                              Object handler, Exception ex) {
        ex.printStackTrace();
        HttpStatus status = determineStatusCode(ex);
        ModelAndView mv = getModelAndView(request, ex, status);
        return mv;
    }

    private HttpStatus determineStatusCode(Exception ex) {
        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        if (responseStatusAnnotation != null) {
            return responseStatusAnnotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private ModelAndView getModelAndView(HttpServletRequest request, Exception ex, HttpStatus status) {
        RestExceptionResponse response = getResponseObject(request, ex, status);
        ModelAndView mv = new ModelAndView();
        mv.setStatus(status);
        mv.addObject(response);

        return mv;
    }

    private RestExceptionResponse getResponseObject(HttpServletRequest request, Exception ex, HttpStatus status) {
        RestExceptionResponse response = new RestExceptionResponse();
        response.setMessage(ex.getMessage());
        response.setStatus(status.value());
        response.setError(ex.getClass().getSimpleName());
        response.setPath(request.getServletPath());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }

}
