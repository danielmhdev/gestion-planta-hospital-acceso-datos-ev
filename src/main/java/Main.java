import menus.MenuHabitaciones;
import menus.MenuPacientes;
import menus.MenuPersonal;
import menus.MenuProcedimientos;

import java.util.Scanner;

public class Main {
    // Implementamos el metodo leerEntero que hemos usado anteriormente para validar.re
    private static int leerEntero(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Error: Introduce un número válido: ");
            }
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Instanciamos los menus:
        MenuPacientes menuPacientes = new MenuPacientes();
        MenuPersonal menuPersonal = new MenuPersonal();
        MenuHabitaciones menuHabitaciones = new MenuHabitaciones();
        MenuProcedimientos menuProcedimientos = new MenuProcedimientos();

        int opcionPrincipal = -1;

        do {
            System.out.println("\n==== SISTEMA CENTRAL DEL HOSPITAL ====");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Personal");
            System.out.println("3. Gestión de Habitaciones ");
            System.out.println("4. Gestión de Procedimientos");
            System.out.println("0. Salir del sistema");
            System.out.print("Selecciona una opción: ");
            opcionPrincipal = leerEntero(scanner);


            switch (opcionPrincipal) {
                case 1:
                    menuPacientes.iniciar(scanner);
                    break;
                case 2:
                    menuPersonal.iniciar(scanner);
                    break;
                case 3:
                    menuHabitaciones.iniciar(scanner);
                    break;
                case 4:
                    menuProcedimientos.iniciar(scanner);
                    break;
                case 0:
                    System.out.println("Cerrando la app...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } while (opcionPrincipal != 0);

        scanner.close();
    }
}