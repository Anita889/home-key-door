package com.example.homekeydoor.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import java.time.LocalDateTime;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class RestMappingExceptionResolver extends AbstractHandlerExceptionResolver {

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonConverter;

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
        MappingJackson2JsonView view = new MappingJackson2JsonView(jacksonConverter.getObjectMapper());
        view.setExtractValueFromSingleKeyModel(true);
        ModelAndView mv = new ModelAndView(view);
        mv.addObject(getResponseObject(request, ex, status));
        mv.setStatus(status);
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
