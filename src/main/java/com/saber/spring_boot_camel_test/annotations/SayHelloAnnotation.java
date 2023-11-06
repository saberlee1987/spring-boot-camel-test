package com.saber.spring_boot_camel_test.annotations;


import java.lang.annotation.*;

@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SayHelloAnnotation {

    String value() default "sayHello";
}
