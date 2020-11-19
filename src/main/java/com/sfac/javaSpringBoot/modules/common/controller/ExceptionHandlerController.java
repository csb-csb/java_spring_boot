package com.sfac.javaSpringBoot.modules.common.controller;

import com.sfac.javaSpringBoot.modules.common.vo.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseBody
    public Result<String> handle(){
        return new Result<String>(Result.ResultStatus.FAILE.status,"",
                "/common/403");
    }


}
