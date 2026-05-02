package menus;

import dao.ProcedimientoDAO;
import model.Procedimiento;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuProcedimientos {

    private ProcedimientoDAO procedimientoDAO = new ProcedimientoDAO();

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

    private LocalDateTime leerFechaHora(Scanner scanner) {
        while (true) {
            try {
                return LocalDateTime.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.print("Error: Formato incorrecto. Usa YYYY-MM-DDTHH:MM (recuerda la 'T' en medio): ");
            }
        }
    }

    public void iniciar(Scanner scanner) {
        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner); // Usamos el validador

            switch (opcion) {
                case 1: listarProcedimientos(); break;
                case 2: registrarProcedimiento(scanner); break;
                case 3: actualizarProcedimiento(scanner); break;
                case 4: eliminarProcedimiento(scanner); break;
                case 5: verHistorialPaciente(scanner); break;
                case 0: System.out.println("Saliendo del módulo de procedimientos..."); break;
                default: System.out.println("Opción no disponible en este menu.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n--- GESTION DE PROCEDIMIENTOS ---");
        System.out.println("1. Listar procedimientos");
        System.out.println("2. Registrar nuevo procedimiento");
        System.out.println("3. Actualizar procedimiento existente");
        System.out.println("4. Eliminar procedimiento");
        System.out.println("5. Buscar historial de un paciente");
        System.out.println("0. Volver al menu principal");
        System.out.print("Opción: ");
    }

    private void listarProcedimientos() {
        System.out.println("\n--- Listado de Procedimientos ---");
        List<Procedimiento> historial = procedimientoDAO.listarProcedimientos();
        if (historial.isEmpty()) {
            System.out.println("No hay procedimientos registrados en el sistema.");
        } else {
            historial.forEach(System.out::println);
        }
    }

    private void registrarProcedimiento(Scanner scanner) {
        System.out.println("\n--- Nuevo Registro ---");

        System.out.print("Fecha y hora (YYYY-MM-DD): ");
        LocalDateTime fechaHora = leerFechaHora(scanner);

        System.out.print("Observaciones/Tratamiento aplicado: ");
        String observaciones = scanner.nextLine();

        System.out.print("ID del Paciente receptor: ");
        int idPaciente = leerEntero(scanner); // Usamos el validador

        System.out.print("ID del Personal responsable: ");
        int idPersonal = leerEntero(scanner); // Usamos el validador

        try {
            procedimientoDAO.insertarProcedimiento(new Procedimiento(fechaHora, observaciones, idPaciente, idPersonal));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos: Verifica que los IDs del paciente y personal existan.");
        }
    }

    private void actualizarProcedimiento(Scanner scanner) {
        System.out.println("\n--- Actualizar Procedimientos ---");

        System.out.print("ID del Procedimiento a modificar: ");
        int idActualizar = leerEntero(scanner);

        System.out.print("Nueva Fecha y hora (YYYY-MM-DD): ");
        LocalDateTime nFechaHora = leerFechaHora(scanner);

        System.out.print("Nuevas Observaciones: ");
        String nObservaciones = scanner.nextLine();

        System.out.print("Nuevo ID del Paciente: ");
        int nIdPaciente = leerEntero(scanner);

        System.out.print("Nuevo ID del Personal: ");
        int nIdPersonal = leerEntero(scanner);

        try {
            procedimientoDAO.actualizarProcedimiento(new Procedimiento(idActualizar, nFechaHora, nObservaciones, nIdPaciente, nIdPersonal));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos al actualizar. Asegúrate de que los IDs sean correctos.");
        }
    }

    private void eliminarProcedimiento(Scanner scanner) {
        System.out.println("\n--- Eliminar Procedimiento ---");
        System.out.print("Introduce el ID del procedimiento a eliminar: ");
        int idEliminar = leerEntero(scanner); // Usamos el validador

        try {
            procedimientoDAO.eliminarProcedimiento(idEliminar);
        } catch (RuntimeException e) {
            System.out.println("Error al intentar eliminar el registro en la base de datos.");
        }
    }

    private void verHistorialPaciente(Scanner scanner) {
        System.out.println("\n--- Historial del Paciente ---");
        System.out.print("Introduce el ID del paciente a buscar: ");
        int idBuscado = leerEntero(scanner);

        List<String> historial = procedimientoDAO.verHistorialDePaciente(idBuscado);
        if (historial.isEmpty()) {
            System.out.println("No se encontraron procedimientos para este paciente.");
        } else {
            historial.forEach(System.out::println);
        }
    }
}