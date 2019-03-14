package pers.hdh.annotation;


import java.lang.annotation.*;



@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DhQualifier {
    /** 
    * @Description: bean IOC注入的注释
    * @Param:  
    * @return:  
    * @Author: Huabuxiu 
    * @Date: 2019/3/14 
    */ 
    String value() default "";
}
