package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@RequiredArgsConstructor
public class PasswordGenerator {

    private final int longitud;
    private final boolean usarMayusculas;
    private final boolean usarMinusculas;
    private final boolean usarNumeros;
    private final boolean usarSimbolos;

    private char[] mayusculas = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private char[] minusculas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private char[] numeros = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private char[] simbolos = {'.', ',', '^', '<', '>', '!', '@', '#', '~', '$', '%', '&', '/', '(', ')', '=', '-', '*', '+', '{', '}', 'Ç'};

    public String generate() {
        if (!usarMayusculas && !usarMinusculas && !usarNumeros && !usarSimbolos) {
            throw new IllegalArgumentException("Debes seleccionar al menos un tipo de carácter.");
        }

        List<Character> characterList = getCharacterList();
        Collections.shuffle(characterList);
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < longitud; i++) {
            password.append(characterList.get(random.nextInt(characterList.size())));
        }

        return password.toString();
    }

    private List<Character> getCharacterList() {
        List<Character> characterList = new ArrayList<>();
        if (usarMayusculas) {
            for (char c : mayusculas) {
                characterList.add(c);
            }
        }
        if (usarMinusculas) {
            for (char c : minusculas) {
                characterList.add(c);
            }
        }
        if (usarNumeros) {
            for (char c : numeros) {
                characterList.add(c);
            }
        }
        if (usarSimbolos){
            for (char c : simbolos) {
                characterList.add(c);
            }
        }
        return characterList;
    }

}
