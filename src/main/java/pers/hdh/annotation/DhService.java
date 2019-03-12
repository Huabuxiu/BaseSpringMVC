package pers.hdh.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DhService {
    
    /** 
    * @Description: Service注释别名 
    * @Param:  
    * @return:  
    * @Author: Huabuxiu 
    * @Date: 2019/3/12 
    */ 
    String value() default "";
}
