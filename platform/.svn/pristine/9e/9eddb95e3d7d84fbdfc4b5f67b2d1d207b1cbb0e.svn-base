package com.bsoft.framework.aspectj;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.alibaba.fastjson2.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.bsoft.common.annotation.DataSource;
import com.bsoft.common.utils.StringUtils;
import com.bsoft.framework.datasource.DynamicDataSourceContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 多数据源处理
 * 
 * @author fastbuild
 */
@Aspect
@Order(1)
@Component
public class DataSourceAspect
{
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.bsoft.common.annotation.DataSource)"
            + "|| @within(com.bsoft.common.annotation.DataSource)")
    public void dsPointCut()
    {

    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {
        DataSource dataSource = getDataSource(point);
        Map<String,Object> map = methodBefore(point);
        if (StringUtils.isNotNull(dataSource)){
            if(map.get("databaseType")!=null){
                DynamicDataSourceContextHolder.setDataSourceType(map.get("databaseType")+"");
            }else{
                DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
            }
        }

        try{
            return point.proceed();
        }
        finally{
            // 销毁数据源 在执行方法之后
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    public Map methodBefore(JoinPoint joinPoint) {
        // 打印请求内容
        Map<String, Object> paramMap = new HashMap<String, Object>();
        try {
            // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
            Object[] objs = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名
            for (int i = 0; i < objs.length; i++) {
                if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
                    paramMap.put(argNames[i], objs[i]);
                }
            }
            if (paramMap.size() > 0) {
                logger.info("\n方法:{}\n参数:{}", joinPoint.getSignature(), JSONObject.toJSONString(paramMap));
            }
        } catch (Exception e) {
            logger.error("AOP methodBefore:", e);
        }
        return paramMap;
    }

    /**
     * 获取需要切换的数据源
     */
    public DataSource getDataSource(ProceedingJoinPoint point)
    {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if (Objects.nonNull(dataSource))
        {
            return dataSource;
        }

        return AnnotationUtils.findAnnotation(signature.getDeclaringType(), DataSource.class);
    }
}
