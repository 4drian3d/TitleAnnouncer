package io.github._4drian3d.titleannouncer.common.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@BindingAnnotation
@Retention(RUNTIME)
public @interface DataFolder {
}
