package org.example.DI;

import org.example.Database.DatabaseConnection;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BeanFactory {
    private static final List<Object> components = new ArrayList<>();

    public static void init(String basePackage) {
        try {
            // initialization DatabaseConnection
            Connection connection = DatabaseConnection.getConnection();
            // access DatabaseConnection instance
            DatabaseConnection databaseConnection = new DatabaseConnection();

            components.add(databaseConnection);

            List<Class<?>> componentClasses = scanComponents(basePackage);

            // Initialize other components
            for (Class<?> componentClass : componentClasses) {
                if (!componentClass.isInterface()) {
                    System.out.println("Creating instance of: " + componentClass.getName());
                    Object componentInstance = componentClass.getDeclaredConstructor().newInstance();
                    components.add(componentInstance);
                    System.out.println("Created instance of: " + componentClass.getName());
                }
            }

            // inject dependencies
            for (Object component : components) {
                System.out.println("Injecting dependencies for: " + component.getClass().getName());
                injectDependencies(component);
                System.out.println("Injected dependencies for: " + component.getClass().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static <T> T getBean(Class<T> beanClass) {
        System.out.println("Retrieving bean: " + beanClass.getName());
        T bean = components.stream()
                .filter(component -> beanClass.isAssignableFrom(component.getClass()))
                .map(beanClass::cast)
                .findFirst()
                .orElse(null);
        System.out.println("Retrieved bean: " + (bean != null ? bean.getClass().getName() : "null"));
        return bean;
    }

    public static void injectDependencies(Object component) throws IllegalAccessException {
        Field[] fields = component.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                System.out.println("Injecting dependency into: " + field.getName());
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.equals(Connection.class)) {
                    field.set(component, DatabaseConnection.getConnection());
                    System.out.println("Injected Connection into: " + field.getName());
                } else {
                    Object dependency = getBean(fieldType);
                    field.set(component, dependency);
                    System.out.println("Injected " + fieldType.getName() + " into: " + field.getName());
                }
            }
        }
    }

    private static List<Class<?>> scanComponents(String basePackage) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(basePackage))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner()));

        Set<Class<?>> componentClasses = reflections.getTypesAnnotatedWith(Component.class);

        // Filter out interfaces
        componentClasses.removeIf(Class::isInterface);

        return new ArrayList<>(componentClasses);
    }
}
