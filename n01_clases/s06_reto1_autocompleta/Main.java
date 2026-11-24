public class Main {
    public static void main(String[] args) {
        Mesa m4 = new Mesa();
        m4.numero = 4;
        m4.ocupar(3);
        IO.println(m4.estado());
        m4.liberar();
        IO.println(m4.estado());
    }
}
