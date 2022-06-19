package ru.levelp.srv.person.profile.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectFieldUtils {

    public static String getFullPropertyPath(Object object, String field, boolean includeClassName) {
        return getFullPropertyPath(object, null, field, includeClassName);
    }

    public static String getFullPropertyPath(Object object, Object target, String field, boolean includeClassName) {
        return includeClassName ? object.getClass().getSimpleName() + "." + getFullFieldPath(object, target, field)
                : getFullFieldPath(object, target, field);
    }

    @SneakyThrows
    private static String getFullFieldPath(Object object, Object target, String field) {
        String fullPath = "";
        final BeanMap beanMap = new BeanMap(object);
        PropertyUtilsBean pp = new PropertyUtilsBean();

        for (Object property : beanMap.keySet()) {
            var propertyName = "" + property;
            var propertyTargetClassName = "" + pp.getPropertyType(object, propertyName);

            if (propertyName.equals(field)) {
                if (target == null || object.equals(target)) {
                    fullPath = pp.getPropertyDescriptor(object, field).getName();
                    break;
                }
            } else if (propertyTargetClassName.contains("ru.levelp.srv.person.profile")) {
                String fullPropertyPath = getFullFieldPath(pp.getProperty(object, propertyName), target, field);
                if (StringUtils.isNoneBlank(fullPropertyPath)) {
                    fullPath = propertyName + "." + fullPropertyPath;
                    break;
                }
            }
        }
        return fullPath;
    }
}
