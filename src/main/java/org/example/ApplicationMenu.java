package org.example;

import java.io.IOException;
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
                0. Salir.
                """);

        return scanner.next();
    }

    public static void newCredentialQuestionnaire(Scanner scanner) {
        System.out.println("Nombre del servicio. (Solo una palabra)");
        String servicio = scanner.next();
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
        //System.out.println("Cuantas contraseñas quieres generar? \n");
        //String cantidad = scanner.next();

        int intLongitud = parseLongitud(longitud);
        boolean isMayusculas = parseBoolean(mayusculas);
        boolean isMinusculas = parseBoolean(minusculas);
        boolean isNumeros = parseBoolean(numeros);
        boolean isSimbolos = parseBoolean(simbolos);
        //int intCantidad = Integer.parseInt(cantidad);

        PasswordGenerator passwordGenerator = new PasswordGenerator(intLongitud, isMayusculas, isMinusculas, isNumeros, isSimbolos);

        String password = passwordGenerator.generate();
        System.out.println("\nTu nueva contraseña segura es:\n🔐 " + password + "\n");
        /*
        if (intCantidad > 1) {
            for (int i = 0; i < intCantidad; i++) {
                System.out.println("🔐 " + passwordGenerator.generate());
            }
        } else {
            String password = passwordGenerator.generate();
            System.out.println("\nTu nueva contraseña segura es:\n🔐 " + password + "\n");
        }
        */
        Credencial credencial = new Credencial(servicio, usuario, password);
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
}
