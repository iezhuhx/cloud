package com.kiiik.web.example.anno;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xyc on 2017/7/27 0027.
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestDateParam {
    String value() default "";

    DateFormatPattern pattern() default DateFormatPattern.YYYY_MM_DD_HH_MM_SS;

    boolean required() default true;
}
