package com.github.prgrms.configures.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class SimplePageRequestHandlerMethodArgumentResolver
        implements HandlerMethodArgumentResolver {

    private static final String DEFAULT_OFFSET_PARAMETER = "offset";
    private static final String DEFAULT_SIZE_PARAMETER = "size";
    private static final Integer DEFAULT_OFFSET = 0;
    private static final Integer DEFAULT_SIZE = 5;
    private String offsetParameterName = DEFAULT_OFFSET_PARAMETER;
    private String sizeParameterName = DEFAULT_SIZE_PARAMETER;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pageable.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
            ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        String offsetString = webRequest.getParameter(this.offsetParameterName);
        String sizeString = webRequest.getParameter(this.sizeParameterName);
        Integer offset = offsetString == null ? DEFAULT_OFFSET : Integer.parseInt(offsetString);
        Integer size = sizeString == null ? DEFAULT_SIZE : Integer.parseInt(sizeString);
        return new SimplePageRequest(offset, size);
    }

    public void setOffsetParameterName(String offsetParameterName) {
        this.offsetParameterName = offsetParameterName;
    }

    public void setSizeParameterName(String sizeParameterName) {
        this.sizeParameterName = sizeParameterName;
    }

}
