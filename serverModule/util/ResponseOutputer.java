package serverModule.util;

public class ResponseOutputer {
    private static StringBuilder stringBuilder = new StringBuilder();

    public static void append(Object o) {
        stringBuilder.append(o);
    }

    public static void appendTable(Object o1, Object o2) {
        stringBuilder.append(String.format("%-37s%-1s%n", o1, o2));
    }

    public static String getString() {
        return stringBuilder.toString();
    }

    public static String getAndClear() {
        String toReturn = stringBuilder.toString();
        stringBuilder.delete(0, stringBuilder.length());
        return toReturn;
    }

    public static void clear() {
        stringBuilder.delete(0, stringBuilder.length());
    }
}
