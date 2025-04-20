package org.example;

import java.util.Scanner;

public class Main {

    private static void checkOption(String option, Scanner scanner) {
        switch (option){
            case "1" -> {
                System.out.println("Ha elegido la opción 1. Crear una nueva credencial.");
                ApplicationMenu.newCredentialQuestionnaire(scanner);
            }
            case "2" -> {
                System.out.println("Ha elegido recuperar datos de una credencial. \nIntroduzca el nombre del servicio.");
                String servicio = scanner.next();
                ApplicationMenu.findCredencial(servicio);
            }
            case "3" -> {
                System.out.println("Opcion 3");
            }
            case "4" -> {
                System.out.println("Opcion 4");
            }
            case "0" -> {
                System.out.println("Ha elegido salir de la aplicación!");
            }
            default -> {
                System.out.println("Opción no valida, elija otra del menú!");
            }
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String option = "";

        do {
            option = ApplicationMenu.principalMenu(scanner);
            checkOption(option, scanner);
        } while (!option.equals("0"));

        scanner.close();

    }
}