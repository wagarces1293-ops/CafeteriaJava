public class Main {
    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        // 1. Crear reservas
        agenda.crear("Valeria", 4, "20:00");
        agenda.crear("Martin", 2, "18:30");
        agenda.crear("Ana", 6, "19:15");

        IO.println("--- Reservas en la Agenda (Listar) ---");
        for (Reserva r : agenda.listar()) {
            IO.println(r);
        }

        // 2. Buscar por id con Optional
        int idBuscado = 2;
        var encontrada = agenda.buscar(idBuscado);
        if (encontrada.isPresent()) {
            IO.println("Encontrada #" + idBuscado + ": " + encontrada.get());
        }

        // Caso limite: buscar id inexistente
        var inexistente = agenda.buscar(99);
        IO.println("¿Existe id 99? " + inexistente.isPresent());

        // 3. Actualizar hora
        agenda.actualizarHora(2, "18:45");
        IO.println("Tras actualizar hora: " + agenda.buscar(2).get());

        // 4. Eliminar reserva
        agenda.eliminar(1);
        IO.println("Tras eliminar #1, total activas: " + agenda.listar().size());

        // 5. Reto Extra: Listado ordenado cronologicamente por hora
        IO.println("--- Reservas Ordenadas por Hora ---");
        for (Reserva r : agenda.listarOrdenadoPorHora()) {
            IO.println(" * " + r);
        }

        // Caso limite: validar rechazo de personas fuera de rango
        try {
            agenda.crear("Grupo Masivo", 12, "21:00");
        } catch (IllegalArgumentException e) {
            IO.println("Capturado rechazo de personas: " + e.getMessage());
        }
    }
}
