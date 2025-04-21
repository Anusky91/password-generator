package org.example;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Credencial {

    private final String service;
    private final String user;
    private final String password;

    public String toFile() {
        return service + ";" + user + ";" + password;
    }

    @Override
    public String toString() {
        return service + " -> Usuario: " + user + " Password: " + password;
    }
}
