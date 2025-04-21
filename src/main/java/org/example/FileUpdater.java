package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void update(Credencial credencial) throws IOException {
        List<Credencial> credencialList = findAll();
        List<Credencial> newCredentialList = credencialList.stream().filter(c -> !c.getService().equalsIgnoreCase(credencial.getService())).collect(Collectors.toList());
        newCredentialList.add(credencial);
        FileWriter fileWriter = new FileWriter(file, false);
        try {
            for (Credencial cr : newCredentialList){
                fileWriter.append(cr.toFile());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }

    public void delete(String servicio) throws IOException {
        List<Credencial> credencialList = findAll();
        List<Credencial> newCredentialList = credencialList.stream().filter(c -> !c.getService().equalsIgnoreCase(servicio)).collect(Collectors.toList());
        FileWriter fileWriter = new FileWriter(file, false);
        try {
            for (Credencial cr : newCredentialList){
                fileWriter.append(cr.toFile());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
