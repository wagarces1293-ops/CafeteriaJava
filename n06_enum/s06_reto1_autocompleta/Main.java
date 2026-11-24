public class Main {
    public static void main(String[] args) {
        for (Leche l : Leche.values()) {
            if (l.vegetal()) {
                IO.println(l + " +$" + l.recargo());
            }
        }
    }
}
