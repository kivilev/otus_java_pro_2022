package ru.kivilev;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class ReflectionUtils {

    public static boolean isAnnotationExists(Class clazz, Class<? extends Annotation> annotation) {
        return Arrays.stream(clazz.getMethods()).anyMatch(it -> it.isAnnotationPresent(annotation));
    }
}
