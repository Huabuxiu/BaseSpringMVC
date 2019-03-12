package pers.hdh.annotation;


import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.METHOD})      //方法和类都可以映射路径
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DhRequestMapping {
/** 
* @Description: 路由映射 
* @Param:  
* @return:  
* @Author: Huabuxiu 
* @Date: 2019/3/12 
*/ 
    String value() default "";
}
