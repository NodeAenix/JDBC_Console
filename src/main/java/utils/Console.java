package utils;

import model.Departamento;
import model.DepartamentoDAO;
import model.Empleado;
import model.EmpleadoCol;
import model.EmpleadoDAO;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Console {

    private Scanner sc;
    private EmpleadoDAO empleadoDAO;
    private DepartamentoDAO departamentoDAO;
    private String input;

    public Console() {
        sc = new Scanner(System.in);
        empleadoDAO = new EmpleadoDAO();
        departamentoDAO = new DepartamentoDAO();
        displayMenu();
    }

    private void displayMenu() {
        boolean loop = true;

        while (loop) {
            System.out.println("+---------------------------------------+");
            System.out.println("|  GESTOR DE EMPLEADOS Y DEPARTAMENTOS  |");
            System.out.println("| [1] > Mostrar datos                   |");
            System.out.println("| [2] > Buscar dato                     |");
            System.out.println("| [3] > Añadir dato                     |");
            System.out.println("| [4] > Actualizar dato                 |");
            System.out.println("| [5] > Borrar dato                     |");
            System.out.println("| [0] > Salir                           |");
            System.out.println("+---------------------------------------+");
            System.out.print(">>> ");
            input = sc.next();

            switch (input) {
                case "0" -> {
                    System.out.println("Saliendo... Adiós! :)");
                    loop = false;
                }
                case "1" -> displayDataMenu();
                case "2" -> displaySearchMenu();
                case "3" -> displayAddMenu();
                case "4" -> displayUpdateMenu();
                case "5" -> deleteMenu();
                default -> showInvalidInputMessage();
            }
        }
    }

    private void showInvalidInputMessage() {
        System.out.println("\n[!] Error: entrada no válida.");
    }

    private void displayDataMenu() {
        System.out.println("+---------------------------------------+");
        System.out.println("| [1] > Mostrar empleados               |");
        System.out.println("| [2] > Mostrar departamentos           |");
        System.out.println("|                                       |");
        System.out.println("| Introduzca otra cosa para volver.     |");
        System.out.println("+---------------------------------------+");
        System.out.print(">>> ");
        input = sc.next();

        if ("1".equals(input)) {
            for (Empleado e : empleadoDAO.getAllEmpleados()) {
                System.out.println(e);
            }
        } else if ("2".equals(input)) {
            for (Departamento d : departamentoDAO.getAllDepartamentos()) {
                System.out.println(d);
            }
        }
    }

    private void displaySearchMenu() {
        System.out.println("+---------------------------------------+");
        System.out.println("| [1] > Buscar empleado                 |");
        System.out.println("| [2] > Buscar departamento             |");
        System.out.println("|                                       |");
        System.out.println("| Introduzca otra cosa para volver.     |");
        System.out.println("+---------------------------------------+");
        System.out.print(">>> ");
        input = sc.next();

        int column;
        boolean order;
        String value;
        if  ("1".equals(input)) {
            try {
                System.out.println("- Elija la columna:");
                System.out.println("\t[1] ID\n\t[2] NOMBRE\n\t[3] EDAD\n\t[4] DPTO_ID");
                System.out.print(">>> ");
                column = sc.nextInt();
                if (column < 1 || column > 4) {
                    System.out.println("[!] Rango de columnas no válido.");
                } else {
                    System.out.println("- Introduzca el valor por el que buscar.");
                    System.out.print(">>> ");
                    sc.nextLine(); // clear scanner buffer
                    value = sc.nextLine();
                    System.out.println("- ¿Ordenar ascendiente? [1] = Sí");
                    System.out.print(">>> ");
                    order = sc.nextInt() == 1;

                    Optional.ofNullable(
                                empleadoDAO.getEmpleadosByColumnOrderBy(
                                        EmpleadoCol.fromIndex(column),
                                        value.trim(),
                                        order
                                ))
                            .ifPresentOrElse(
                                    list -> list.forEach(System.out::println),
                                    () -> System.out.println("No se encontraron empleados.")
                            );
                }
            } catch (InputMismatchException e) {
                System.out.println("[!] Entrada no válida.");
            }
        } else if ("2".equals(input)) {
            try {
                System.out.println("- Elija la columna:");
                System.out.println("\t[1] ID\n\t[2] NOMBRE");
                System.out.print(">>> ");
                column = sc.nextInt();
                if (column < 1 || column > 2) {
                    System.out.println("[!] Rango de columnas no válido.");
                } else {
                    System.out.println("- Introduzca el valor por el que buscar.");
                    System.out.print(">>> ");
                    sc.nextLine(); // clear scanner buffer
                    value = sc.nextLine();
                    System.out.println("- ¿Ordenar descendientemente? [1] = Sí");
                    System.out.print(">>> ");
                    order = sc.nextInt() == 1;
                    if (column == 1) {
                        Optional.ofNullable(departamentoDAO.getDepartamentoById(Integer.parseInt(value), order))
                                .ifPresentOrElse(
                                        System.out::println,
                                        () -> System.out.println("No se encontraron departamentos.")
                                );
                    } else {
                        Optional.ofNullable(departamentoDAO.getDepartamentosByNombre(value, order))
                                .ifPresentOrElse(
                                        list -> list.forEach(System.out::println),
                                        () -> System.out.println("No se encontraron departementos.")
                                );
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("[!] Entrada no válida.");
            }
        }
    }

    private void displayAddMenu() {
        System.out.println("+---------------------------------------+");
        System.out.println("| [1] > Añadir empleado                 |");
        System.out.println("| [2] > Añadir departamento             |");
        System.out.println("|                                       |");
        System.out.println("| Introduzca otra cosa para volver.     |");
        System.out.println("+---------------------------------------+");
        System.out.print(">>> ");
        input = sc.next();

        if ("1".equals(input)) {
            System.out.println("- Introduzca los datos separadaos por comas de la siguiente manera:");
            System.out.println("\tNOMBRE, EDAD, DEPARTAMENTO_ID");
            System.out.print(">>> ");
            sc.nextLine(); // clear scanner buffer
            input = sc.nextLine();

            String[] values = input.trim().split("\\s*,\\s*", 3);
            if (values.length != 3) {
                System.out.println("[!] Faltan datos.");
            } else {
                int rowsAffected = empleadoDAO.addEmpleado(new Empleado(values[0], Integer.parseInt(values[1]), Integer.parseInt(values[2])));
                System.out.println("Hecho! Filas afectadas: " + rowsAffected);
            }

        } else if ("2".equals(input)) {
            System.out.println("- Introduzca el nombre del departamento:");
            System.out.print(">>> ");
            sc.nextLine(); // clear scanner buffer
            input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("[!] Faltan datos.");
            } else {
                int rowsAffected = departamentoDAO.addDepartamento(input.trim());
                System.out.println("Hecho! Filas afectadas: " + rowsAffected);
            }
        }
    }

    private void displayUpdateMenu() {
        System.out.println("+---------------------------------------+");
        System.out.println("| [1] > Actualizar empleado             |");
        System.out.println("| [2] > Actualizar departamento         |");
        System.out.println("|                                       |");
        System.out.println("| Introduzca otra cosa para volver.     |");
        System.out.println("+---------------------------------------+");
        System.out.print(">>> ");
        input = sc.next();

        if ("1".equals(input)) {
            System.out.println("- Introduzca los datos separadaos por comas de la siguiente manera:");
            System.out.println("\tID, NOMBRE, EDAD, DEPARTAMENTO_ID");
            System.out.print(">>> ");
            sc.nextLine(); // clear scanner buffer
            input = sc.nextLine();

            String[] values = input.trim().split("\\s*,\\s*", 4);
            if (values.length != 4) {
                System.out.println("[!] Faltan datos.");
            } else {
                int rowsAffected = empleadoDAO.updateEmpleado(new Empleado(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3])));
                System.out.println("Hecho! Filas afectadas: " + rowsAffected);
            }

        } else if ("2".equals(input)) {
            System.out.println("- Introduzca el id del departamento:");
            System.out.print(">>> ");
            int id = sc.nextInt();
            System.out.println("- Introduzca el nombre del departamento:");
            System.out.print(">>> ");
            sc.nextLine(); // clear scanner buffer
            input = sc.nextLine();

            if (input.isBlank()) {
                System.out.println("[!] Faltan datos.");
            } else {
                int rowsAffected = departamentoDAO.updateDepartamento(new Departamento(id, input.trim()));
                System.out.println("Hecho! Filas afectadas: " + rowsAffected);
            }
        }
    }

    private void deleteMenu() {
        System.out.println("+---------------------------------------+");
        System.out.println("| [1] > Borrar empleado                 |");
        System.out.println("| [2] > Borrar departamento             |");
        System.out.println("|                                       |");
        System.out.println("| Introduzca otra cosa para volver.     |");
        System.out.println("+---------------------------------------+");
        System.out.print(">>> ");
        input = sc.next();

        if ("1".equals(input)) {
            System.out.println("- Introduzca el ID del empleado:");
            System.out.print(">>> ");
            int id = sc.nextInt();
            int rowsAffected = empleadoDAO.deleteEmpleadoById(id);
            System.out.println("Hecho! Filas afectadas: " + rowsAffected);
        } else if ("2".equals(input)) {
            System.out.println("- Introduzca el ID del departamento:");
            System.out.print(">>> ");
            int id = sc.nextInt();
            int rowsAffected = departamentoDAO.deleteDepartamentoById(id);
            System.out.println("Hecho! Filas afectadas: " + rowsAffected);
        }
    }
}
