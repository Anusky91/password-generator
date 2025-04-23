package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ApplicationMenu {

    static FileUpdater fileUpdater = new FileUpdater();

    public static String principalMenu(Scanner scanner) {
        System.out.println("""
                Bienvenido al Gestor de contraseñas Anusky!
                Que desea hacer?
                1. Crear una nueva contraseña/credencial.
                2. Recuperar una credencial.
                3. Modificar una credencial.
                4. Borrar una credencial.
                5. Recuperar los nombres de los servicios guardados.
                0. Salir.
                """);

        return scanner.next();
    }

    public static void newCredentialQuestionnaire(Scanner scanner) {
        System.out.println("Nombre del servicio. (Solo una palabra)");
        String servicio = scanner.next();

        PasswordDefinition result = getPasswordDefinition(scanner);

        PasswordGenerator passwordGenerator = new PasswordGenerator(result.intLongitud(),
                                                                result.isMayusculas(),
                                                                result.isMinusculas(),
                                                                result.isNumeros(),
                                                                result.isSimbolos());

        Credencial credencial = getCredencial(scanner, passwordGenerator, servicio, result.usuario());
        saveCredencial(credencial);
    }

    private static Credencial getCredencial(Scanner scanner, PasswordGenerator passwordGenerator, String servicio, String usuario) {
        System.out.println("¿Cuántas contraseñas quieres generar?");
        int cantidad = scanner.nextInt();
        List<String> posiblesPasswords = new ArrayList<>();
        int eleccion = 0;
        String passwordElegida = "";

        if (cantidad != 1) {
            for (int i = 0; i < cantidad; i++) {
                posiblesPasswords.add(passwordGenerator.generate());
                System.out.println((i + 1) + ". " + posiblesPasswords.get(i));
            }

            System.out.println("Elige la contraseña deseada (1-" + cantidad + "):");
            eleccion = scanner.nextInt();
            passwordElegida = posiblesPasswords.get(eleccion - 1);
        } else {
            passwordElegida = passwordGenerator.generate();
        }
        System.out.println("\nTu nueva contraseña segura es:\n🔐 " + passwordElegida + "\n");

        return new Credencial(servicio, usuario, passwordElegida);
    }


    public static void updateCredencial(Scanner scanner) {
        System.out.println("Introduzca el nombre del servicio que desea modificar");
        String servicio = scanner.next();
        PasswordDefinition result = getPasswordDefinition(scanner);

        PasswordGenerator passwordGenerator = new PasswordGenerator(result.intLongitud(),
                                                        result.isMayusculas(),
                                                        result.isMinusculas(),
                                                        result.isNumeros(),
                                                        result.isSimbolos());

        Credencial credencial = getCredencial(scanner, passwordGenerator, servicio, result.usuario());
        try {
            fileUpdater.update(credencial);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void findAllServicesNames() {
        try {
            List<Credencial> credencialList = fileUpdater.findAll();
            credencialList.stream().map(Credencial::getService)
                    .map(name -> name.toUpperCase().charAt(0) + name.substring(1))
                    .sorted(Comparator.naturalOrder())
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveCredencial(Credencial credencial) {
        System.out.println(credencial.toString());
        try {
            fileUpdater.create(credencial);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void findCredencial(String service) {
        try {
            List<Credencial> credencialList = fileUpdater.findAll();
            credencialList.stream().filter(credencial ->
                credencial.getService().equalsIgnoreCase(service)
            ).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteCredencial(Scanner scanner) {
        System.out.println("Introduzca el nombre del servicio que desea borrar");
        String servicio = scanner.next();
        try {
            fileUpdater.delete(servicio);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean parseBoolean(String siono) {
        return siono.equalsIgnoreCase("S");
    }

    private static int parseLongitud(String longitud) {
        try {
            return Integer.parseInt(longitud);
        } catch (NumberFormatException e) {
            return 12;
        }
    }
    private static PasswordDefinition getPasswordDefinition(Scanner scanner) {
        System.out.println("Nombre de usuario.");
        String usuario = scanner.next();
        System.out.println("Que longitud debe tener la contraseña: \n");
        String longitud = scanner.next();
        System.out.println("Quieres que tenga mayusculas? S/N \n");
        String mayusculas = scanner.next();
        System.out.println("Quieres que tenga minusculas? S/N \n");
        String minusculas = scanner.next();
        System.out.println("Quieres que tenga numeros? S/N \n");
        String numeros = scanner.next();
        System.out.println("Quieres que tenga simbolos? S/N \n");
        String simbolos = scanner.next();

        int intLongitud = parseLongitud(longitud);
        boolean isMayusculas = parseBoolean(mayusculas);
        boolean isMinusculas = parseBoolean(minusculas);
        boolean isNumeros = parseBoolean(numeros);
        boolean isSimbolos = parseBoolean(simbolos);
        PasswordDefinition result = new PasswordDefinition(usuario, intLongitud, isMayusculas, isMinusculas, isNumeros, isSimbolos);
        return result;
    }

    private record PasswordDefinition(String usuario,
                                      int intLongitud,
                                      boolean isMayusculas,
                                      boolean isMinusculas,
                                      boolean isNumeros,
                                      boolean isSimbolos) {
    }
}
