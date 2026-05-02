package menus;

import dao.PacienteDAO;
import model.Paciente;
import model.enums.Sexo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class MenuPacientes {

    private PacienteDAO pacienteDAO = new PacienteDAO();
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

    private LocalDate leerFecha(Scanner scanner) {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.print("Error: Formato incorrecto. Usa YYYY-MM-DD: ");
            }
        }
    }

    private Sexo leerSexo(Scanner scanner) {
        while (true) {
            try {
                return Sexo.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.print("Error: Debes escribir Hombre, Mujer u Otro: ");
            }
        }
    }
    public void iniciar(Scanner scanner) {
        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1: listarPacientes(); break;
                case 2: registrarPaciente(scanner); break;
                case 3: actualizarPaciente(scanner); break;
                case 4: eliminarPaciente(scanner); break;
                case 5: listarPacientesHabitacion(); break;
                case 6: buscarPacientePorId(scanner); break;
                case 0: System.out.println("Saliendo del módulo de pacientes..."); break;
                default: System.out.println("Opción no disponible.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n--- GESTION DE PACIENTES ---");
        System.out.println("1. Listar todos los pacientes");
        System.out.println("2. Ingresar nuevo paciente");
        System.out.println("3. Actualizar datos de paciente");
        System.out.println("4. Eliminar paciente");
        System.out.println("5. Listar pacientes ordenados por habitación");
        System.out.println("6. Buscar paciente por ID");
        System.out.println("0. Volver al menu principal");
        System.out.print("Opción: ");
    }

    private void listarPacientes() {
        System.out.println("\n--- Listado de Pacientes ---");
        List<Paciente> pacientes = pacienteDAO.listarPacientes();
        if (pacientes.isEmpty()) {
            System.out.println("No hay registros.");
        } else {
            pacientes.forEach(System.out::println);
        }
    }

    private void registrarPaciente(Scanner scanner) {
        System.out.println("\n--- Registro de Paciente ---");
        System.out.print("NHC: ");
        String nhc = scanner.nextLine();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Sexo (Hombre/Mujer/Otro): ");
        Sexo sexo = leerSexo(scanner);

        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        LocalDate fechaNac = leerFecha(scanner);

        System.out.print("ID Habitacion: ");
        int idHab = leerEntero(scanner);

        try {
            pacienteDAO.insertarPaciente(new Paciente(nhc, nombre, apellidos, sexo, fechaNac, idHab));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos. Comprueba que el ID de la habitación existe realmente. ");
        }
    }

    private void actualizarPaciente(Scanner scanner) {
        System.out.println("\n--- Actualizar Paciente ---");
        System.out.print("ID del paciente a modificar: ");
        int idAct = leerEntero(scanner);

        System.out.print("Nuevo NHC: ");
        String nNhc = scanner.nextLine();

        System.out.print("Nuevo Nombre: ");
        String nNombre = scanner.nextLine();

        System.out.print("Nuevos Apellidos: ");
        String nApellidos = scanner.nextLine();

        System.out.print("Nuevo Sexo (Hombre/Mujer/Otro): ");
        Sexo nSexo = leerSexo(scanner);

        System.out.print("Nueva Fecha (YYYY-MM-DD): ");
        LocalDate nFecha = leerFecha(scanner);

        System.out.print("Nueva Habitación (ID): ");
        int nHab = leerEntero(scanner);

        try {
            pacienteDAO.actualizarPaciente(new Paciente(idAct, nNhc, nNombre, nApellidos, nSexo, nFecha, nHab));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos. Es posible que el ID de habitación no exista.");
        }
    }

    private void eliminarPaciente(Scanner scanner) {
        System.out.println("\n--- Eliminar Paciente ---");
        System.out.print("Introduce el ID del paciente a eliminar: ");
        int idEliminar = leerEntero(scanner); // Usamos el validador

        try {
            pacienteDAO.eliminarPaciente(idEliminar);
        } catch (RuntimeException e) {
            System.out.println("Error: No se pudo eliminar al paciente. Revise dependencias en la base de datos.");
        }
    }

    private void buscarPacientePorId(Scanner scanner) {
        System.out.println("\n--- Buscar Paciente ---");
        System.out.print("Introduce el ID del paciente que buscas: ");
        int id = leerEntero(scanner);

        Paciente paciente = pacienteDAO.obtenerPacientePorId(id);

        if (paciente != null) {
            System.out.println("Paciente encontrado: " + paciente.toString());
        } else {
            System.out.println("Error: No se ha encontrado ningún paciente con ese ID.");
        }
    }

    private void listarPacientesHabitacion() {
        System.out.println("\n--- Pacientes por Habitación ---");
        List<String> lista = pacienteDAO.listarPacientesConHabitacion();
        if (lista.isEmpty()) {
            System.out.println("No hay pacientes asignados a habitaciones.");
        } else {
            lista.forEach(System.out::println);
        }
    }
}