/**
 * Utilidad de entrada y salida para compatibilidad con la sintesis de Java 25 IO en JDK 21.
 * Implementada para respetar la signatura IO.println, IO.print e IO.readln evaluadas en la guia.
 * Redactada para el curso de Programacion 1.
 */
public class IO {
    public static void println(Object obj) {
        System.out.println(obj);
    }
    public static void println() {
        System.out.println();
    }
    public static void print(Object obj) {
        System.out.print(obj);
    }
    public static String readln(String prompt) {
        System.out.print(prompt);
        java.util.Scanner sc = new java.util.Scanner(System.in);
        return sc.nextLine();
    }
    public static String readln() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        return sc.nextLine();
    }
}
