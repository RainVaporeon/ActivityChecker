package io.github.rainvaporeon.activitychecker.annotations;

import java.lang.annotation.*;

/**
 * Describes the json schema for this given structure
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Schema {
    String value();
}
