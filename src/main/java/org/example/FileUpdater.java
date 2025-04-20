package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUpdater {

    File file = new File("src/main/resources/data.txt");

    public void create(Credencial credencial) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append(credencial.toFile());
        fileWriter.flush();
        fileWriter.close();
    }

    public List<Credencial> findAll() throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<Credencial> credencialList = new ArrayList<>();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            String[] credentianParts = line.split(";");
            Credencial credencial = new Credencial(credentianParts[0], credentianParts[1], credentianParts[2]);
            credencialList.add(credencial);
        }
        return credencialList;
    }
}
