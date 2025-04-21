package org.example;

import org.example.security.AESUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUpdater {

    File file = new File("src/main/resources/data.txt");

    public void create(Credencial credencial) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        String encrypted = AESUtils.encrypt(credencial.toFile());
        fileWriter.append(encrypted).append("\n");
        fileWriter.flush();
        fileWriter.close();
    }

    public List<Credencial> findAll() throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<Credencial> credencialList = new ArrayList<>();
        String encryptedLine = "";
        while ((encryptedLine = bufferedReader.readLine()) != null) {
            String decrypted = AESUtils.decrypt(encryptedLine);
            String[] credentianParts = decrypted.split(";");
            Credencial credencial = new Credencial(credentianParts[0], credentianParts[1], credentianParts[2]);
            credencialList.add(credencial);
        }
        return credencialList;
    }

    public void update(Credencial credencial) throws IOException {
        List<Credencial> credencialList = findAll();
        List<Credencial> newCredentialList = credencialList.stream()
                                            .filter(c -> !c.getService().equalsIgnoreCase(credencial.getService()))
                                            .collect(Collectors.toList());
        newCredentialList.add(credencial);
        FileWriter fileWriter = new FileWriter(file, false);
        try {
            for (Credencial cr : newCredentialList) {
                String encrypted = AESUtils.encrypt(cr.toFile());
                fileWriter.append(encrypted).append( "\n");
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
        List<Credencial> newCredentialList = credencialList.stream()
                                                .filter(c -> !c.getService().equalsIgnoreCase(servicio))
                                                .toList();
        FileWriter fileWriter = new FileWriter(file, false);
        try {
            for (Credencial cr : newCredentialList) {
                String encrypted = AESUtils.encrypt(cr.toFile());
                fileWriter.append(encrypted).append("\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }
}
