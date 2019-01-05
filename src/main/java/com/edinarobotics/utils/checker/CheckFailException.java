package com.edinarobotics.utils.checker;

/**
 * An exception thrown when a {@link Checkable}
 * check is failed. This exception contains a reference to the source class so
 * it can be traced back to the failed Checkable.
 */
public class CheckFailException extends Exception {
    private static final String TAG = "Checking failed!";
    private static final String TAG_WITH_CLASS_NAME = "Checking of %s failed!";

    private final Class<? extends Checkable> source;

    /**
     * Constructs a CheckFailException with default values:
     * No message, and no source class.
     *
     * Use of this constructor is not recommended.
     */
    public CheckFailException() {
        super(TAG);
        source = null;
    }

    /**
     * Constructs a CheckFailException with a provided source class but no message.
     * @param cls A Class object referring to the source class where the check failed.
     */
    public CheckFailException(Class<? extends Checkable> cls) {
        super(String.format(TAG_WITH_CLASS_NAME, cls.getName()));
        source = cls;
    }

    /**
     * Constructs a CheckFailException with a custom detail message,
     * but no reference to the source class.
     *
     * Use of this constructor is not recommended.
     *
     * @param msg The detail message.
     */
    public CheckFailException(String msg) {
        super(TAG + " " + msg);
        source = null;
    }

    /**
     * Constructs a CheckFailException with a custom detail message and
     * a reference to the source class where the check failed.
     *
     * @param msg The detail message.
     * @param cls A Class object referring to the source class where the check failed.
     */
    public CheckFailException(String msg, Class<? extends Checkable> cls) {
        super(String.format(TAG_WITH_CLASS_NAME, cls.getName()) + " " + msg);
        source = cls;
    }

    /**
     * Obtain a reference to the source Checkable where the exception was
     * thrown and the check failed.
     * @return A Class object referring to the source class where the check failed.
     */
    public Class<? extends Checkable> getSource() {
        return source;
    }
}
