import java.lang.reflect.*;

public class ClassDescriptionPrinter {
    public static void printClassDescription(String className) {
        try {
            ClassLoader classLoader = ClassDescriptionPrinter.class.getClassLoader();
            Class<?> clazz = classLoader.loadClass(className);

            System.out.println("Class Description for: " + className);
            System.out.println("-------------------------------");

            int modifiers = clazz.getModifiers();
            System.out.println("Modifiers: " + Modifier.toString(modifiers));
            System.out.println("Class Name: " + clazz.getSimpleName());

            Class<?> superclass = clazz.getSuperclass();
            if (superclass != null) {
                System.out.println("Superclass: " + superclass.getName());
            }

            Class<?>[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                System.out.println("Implemented Interfaces:");
                for (Class<?> iface : interfaces) {
                    System.out.println("  " + iface.getName());
                }
            }

            Field[] fields = clazz.getDeclaredFields();
            if (fields.length > 0) {
                System.out.println("Fields:");
                for (Field field : fields) {
                    System.out.println("  " + Modifier.toString(field.getModifiers()) +
                            " " + field.getType().getSimpleName() +
                            " " + field.getName());
                }
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Class not found: " + className);
        }
    }
}
