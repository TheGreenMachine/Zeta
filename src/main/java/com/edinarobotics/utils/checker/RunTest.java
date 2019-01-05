package com.edinarobotics.utils.checker;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation to annotate {@link com.edinarobotics.utils.checker.Checkable}
 * classes whose {@link com.edinarobotics.utils.checker.Checkable#check()} method
 * should be automatically called upon testInit().
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RunTest {
    /**
     * Whether or not to run the test. True by default.
     * @return True if the test will be run. False if not.
     */
    boolean enabled() default true;
}
