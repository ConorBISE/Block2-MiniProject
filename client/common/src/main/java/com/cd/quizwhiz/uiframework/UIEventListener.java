package com.cd.quizwhiz.uiframework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation to be applied to methods of subclasses of UIPage to register
 * them as listeners for UI events.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface UIEventListener {
    String type();

    String id();
}
