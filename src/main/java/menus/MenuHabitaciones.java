package menus;

import dao.HabitacionDAO;
import model.Habitacion;
import model.enums.Estado;

import java.util.List;
import java.util.Scanner;

public class MenuHabitaciones {

    private HabitacionDAO habitacionDAO = new HabitacionDAO();

    // Aplicamos métodos de validación adicionales
    private int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Error: Introduce un número válido: ");
            }
        }
    }

    private Estado leerEstado(Scanner scanner) {
        while (true) {
            try {
                return Estado.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.print("Error: Debes escribir Libre, Ocupada, Limpieza o Mantenimiento: ");
            }
        }
    }


    public void iniciar(Scanner scanner) {
        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner); // Usamos el validador

            switch (opcion) {
                case 1: listarHabitaciones(); break;
                case 2: registrarHabitacion(scanner); break;
                case 3: actualizarHabitacion(scanner); break;
                case 4: eliminarHabitacion(scanner); break;
                case 0: System.out.println("Saliendo del módulo de habitaciones..."); break;
                default: System.out.println("Opción no reconocida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n--- GESTION DE HABITACIONES ---");
        System.out.println("1. Listar todas las habitaciones");
        System.out.println("2. Registrar nueva habitación");
        System.out.println("3. Actualizar datos de habitación");
        System.out.println("4. Eliminar habitación");
        System.out.println("0. Volver al menu principal");
        System.out.print("Opción: ");
    }

    private void listarHabitaciones() {
        System.out.println("\n--- Listado de Habitaciones ---");
        List<Habitacion> habitaciones = habitacionDAO.listarHabitaciones();
        if (habitaciones.isEmpty()) {
            System.out.println("No hay habitaciones registradas.");
        } else {
            habitaciones.forEach(System.out::println);
        }
    }

    private void registrarHabitacion(Scanner scanner) {
        System.out.println("\n--- Registro de Habitación ---");

        System.out.print("Numero de habitación: ");
        int num = leerEntero(scanner); // Usamos el validador

        System.out.print("Planta (ej. Primera): ");
        String planta = scanner.nextLine();

        System.out.print("Estado (Libre/Ocupada/Limpieza/Mantenimiento): ");
        Estado estado = leerEstado(scanner); // Usamos el validador

        // Solo envolvemos la llamada al DAO para capturar errores de SQL
        try {
            habitacionDAO.insertarHabitacion(new Habitacion(num, planta, estado));
        } catch (RuntimeException e) {
            System.out.println("Error: No se ha podido registrar la habitación. Comprueba los datos.");
        }
    }

    private void actualizarHabitacion(Scanner scanner) {
        System.out.println("\n--- Actualizar Habitación ---");

        System.out.print("ID de la habitación a modificar: ");
        int idActualizar = leerEntero(scanner);

        System.out.print("Nuevo numero: ");
        int nuevoNum = leerEntero(scanner);

        System.out.print("Nueva planta: ");
        String nuevaPlanta = scanner.nextLine();

        System.out.print("Nuevo estado (Libre/Ocupada/Limpieza/Mantenimiento): ");
        Estado nuevoEstado = leerEstado(scanner);

        try {
            habitacionDAO.actualizarHabitacion(new Habitacion(idActualizar, nuevoNum, nuevaPlanta, nuevoEstado));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos al actualizar. Verifica que el ID exista.");
        }
    }

    private void eliminarHabitacion(Scanner scanner) {
        System.out.println("\n--- Eliminar Habitación ---");
        System.out.print("ID de la habitación a eliminar: ");
        int idEliminar = leerEntero(scanner); // Usamos el validador

        try {
            habitacionDAO.eliminarHabitacion(idEliminar);
        } catch (RuntimeException e) {
            System.out.println("Error: No se puede eliminar. Es posible que haya pacientes asignados a esta habitación.");
        }
    }
}