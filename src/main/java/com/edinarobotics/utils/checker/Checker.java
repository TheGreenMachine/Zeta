package com.edinarobotics.utils.checker;

import frc.team1816.robot.Components;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * A utility class to run tests and check {@link Checkable}s.
 */
public class Checker {

    /**
     * Checks multiple {@link Checkable} objects.
     * Will throw the first exception it encounters
     * and returns true if all checks are passed.
     * @param checkables An array of {@link Checkable} objects, passable in spread form.
     * @return True if all checks passed. If at least one check fails, returns false.
     * @throws CheckFailException The first CheckFailException thrown from a {@link Checkable}.
     */
    public static boolean checkAll(Checkable ...checkables) throws CheckFailException {
        boolean returnValue = true;
        for (Checkable checkable : checkables) {
            returnValue = returnValue && checkable.check();
        }
        return returnValue;
    }

    /**
     * Finds all classes in the {@link frc.team1816.robot} package that
     * implement {@link Checkable} annotated with the {@link RunTest} annotation
     * and calls their check() methods.
     */
    public static void runTests() {
        // Get all declared fields of the components class
        Field[] fields = Components.class.getDeclaredFields();

        // Search package frc.team1816.robot for classes annotated with @RunTest
        Reflections reflections = new Reflections("frc.team1816.robot");
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(RunTest.class);

        // Loop through every annotated class
        for (Class<?> cls : annotated) {
            // Check if it implements Checkable
            if (Checkable.class.isAssignableFrom(cls)) {
                // Find a components field where the type is of the current annotated class class
                for (Field field : fields) {
                    if (cls.isAssignableFrom(field.getType())) {
                        try {
                            System.out.println("Checking class " + cls.getName() + "...");
                            // Invoke its check method
                            field.getType().getDeclaredMethod("check").invoke(field.get(Components.getInstance()));
                        } catch (InvocationTargetException ite) {
                            // Catch CheckFailExceptions thrown by invoke(), wrapped in an InvocationTargetException
                            if (ite.getCause() instanceof CheckFailException) {
                                System.out.println(ite.getCause().getMessage());
                            }
                            ite.printStackTrace();
                        } catch (Exception e) {
                            // Catch all other exceptions
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
