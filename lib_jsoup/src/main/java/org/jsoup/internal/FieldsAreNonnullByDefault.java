package org.jsoup.internal;


import androidx.annotation.NonNull;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@NonNull
@Retention(value = RetentionPolicy.CLASS)

/**
 Indicates that fields types are not nullable, unless otherwise specified by @Nullable.
 @see javax.annotation.ParametersAreNonnullByDefault
 */
public @interface FieldsAreNonnullByDefault {
}
