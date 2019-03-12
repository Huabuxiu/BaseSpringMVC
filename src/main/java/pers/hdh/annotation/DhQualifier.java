package pers.hdh.annotation;


import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD,ElementType.PARAMETER,ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface DhQualifier {
    String value() default "";
}
