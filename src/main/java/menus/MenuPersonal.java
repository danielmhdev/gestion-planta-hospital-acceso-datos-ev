package menus;

import dao.PersonalDAO;
import model.Personal;
import model.enums.Categoria;
import model.enums.Turno;

import java.util.List;
import java.util.Scanner;

public class MenuPersonal {

    private PersonalDAO personalDAO = new PersonalDAO();

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

    private Categoria leerCategoria(Scanner scanner) {
        while (true) {
            try {
                return Categoria.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.print("Error: Debes escribir Medicina, Enfermeria, Auxiliar o Celador: ");
            }
        }
    }

    private Turno leerTurno(Scanner scanner) {
        while (true) {
            try {
                return Turno.valueOf(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                System.out.print("Error: Debes escribir Mañana, Tarde o Noche: ");
            }
        }
    }

    public void iniciar(Scanner scanner) {
        int opcion = -1;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner); // Usamos el validador

            switch (opcion) {
                case 1: listarPersonal(); break;
                case 2: registrarPersonal(scanner); break;
                case 3: actualizarEmpleado(scanner); break;
                case 4: eliminarEmpleado(scanner); break;
                case 5: listarPersonalPorCategoria(); break;
                case 0: System.out.println("Saliendo del modulo de Personal..."); break;
                default: System.out.println("Opción no disponible.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n--- GESTION DE PERSONAL ---");
        System.out.println("1. Listar plantilla completa");
        System.out.println("2. Registrar nuevo personal");
        System.out.println("3. Actualizar datos de personal");
        System.out.println("4. Eliminar personal");
        System.out.println("5. Listar personal por categoría");
        System.out.println("0. Volver al menu principal");
        System.out.print("Opción: ");
    }

    private void listarPersonal() {
        System.out.println("\n--- Listado de Personal ---");
        List<Personal> plantilla = personalDAO.listarPersonal();
        if (plantilla.isEmpty()) {
            System.out.println("No hay personal registrado.");
        } else {
            plantilla.forEach(System.out::println);
        }
    }

    private void registrarPersonal(Scanner scanner) {
        System.out.println("\n--- Registrar Empleado ---");

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();

        System.out.print("Categoria (Medicina/Enfermeria/Auxiliar/Celador): ");
        Categoria categoria = leerCategoria(scanner); // Usamos el validador

        System.out.print("Turno (Mañana/Tarde/Noche): ");
        Turno turno = leerTurno(scanner); // Usamos el validador

        try {
            personalDAO.insertarPersonal(new Personal(nombre, apellidos, categoria, turno));
        } catch (RuntimeException e) {
            System.out.println("Error: No se ha podido registrar al personal. Comprueba los datos.");
        }
    }

    private void actualizarEmpleado(Scanner scanner) {
        System.out.println("\n--- Actualizar Personal ---");

        System.out.print("ID del personal a modificar: ");
        int idAct = leerEntero(scanner);

        System.out.print("Nuevo Nombre: ");
        String nNombre = scanner.nextLine();

        System.out.print("Nuevos Apellidos: ");
        String nApellidos = scanner.nextLine();

        System.out.print("Nueva Categoria (Medicina/Enfermeria/Auxiliar/Celador): ");
        Categoria nCategoria = leerCategoria(scanner);

        System.out.print("Nuevo Turno (Mañana/Tarde/Noche): ");
        Turno nTurno = leerTurno(scanner);

        try {
            personalDAO.actualizarPersonal(new Personal(idAct, nNombre, nApellidos, nCategoria, nTurno));
        } catch (RuntimeException e) {
            System.out.println("Error en la base de datos al actualizar. Asegúrate de que el ID es correcto.");
        }
    }

    private void eliminarEmpleado(Scanner scanner) {
        System.out.println("\n--- Eliminar Personal ---");
        System.out.print("ID del personal a eliminar: ");
        int idEliminar = leerEntero(scanner); // Usamos el validador

        try {
            personalDAO.eliminarPersonal(idEliminar);
        } catch (RuntimeException e) {
            System.out.println("Error: No se pudo eliminar al personal. Asegúrese de que no tiene procedimientos asignados.");
        }
    }
    private void listarPersonalPorCategoria() {
        System.out.println("\n--- Personal por Categoría ---");
        List<String> personalPorCategoria = personalDAO.listarPersonalPorCategoria();
        if (personalPorCategoria.isEmpty()) {
            System.out.println("No hay datos de personal registrados.");
        } else {
            personalPorCategoria.forEach(System.out::println);
        }
    }
}