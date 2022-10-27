class Primitive {
    public static boolean toPrimitive(Boolean b) {
        boolean result = !java.util.Objects.equals(b, null) ? Boolean.valueOf(b) : false;
        return result;
    }
}
