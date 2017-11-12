package com.example.inject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.CLASS)
public @interface Inject {
    String value() default ""
}
