
package com.example.filemanagement;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {

    
    public static void readFileWithBufferedReader(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }

  
    public static void writeFileWithBufferedWriter(String filePath, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
            bw.newLine();
            bw.write("Ligne ajoutée avec succès.");

            System.out.println("Écriture terminée : " + filePath);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        }
    }


    public static void writeWithPrintWriter(String filePath) {
        try (PrintWriter pw = new PrintWriter(filePath, "UTF-8")) {
            pw.println(" Rapport des employés ");
            pw.printf("Nom: %s, Âge: %d, Poste: %s%n",
                    "Alice", 25, "Engineer");
            pw.printf("Nom: %s, Âge: %d, Poste: %s%n",
                    "Bob", 30, "Manager");
            pw.println("Fin du rapport.");

            if (pw.checkError()) {
                System.out.println("Une erreur est survenue pendant l'écriture.");
            } else {
                System.out.println("Rapport créé : " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Erreur PrintWriter : " + e.getMessage());
        }
    }

    public static void readWithFilesApi(String filePath) {
        try {
            Path path = Paths.get(filePath);

            for (String line : Files.readAllLines(path, StandardCharsets.UTF_8)) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture : " + e.getMessage());
        }
    }

  
    public static void writeWithFilesApi(String filePath, String content) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));

            System.out.println("Fichier écrit : " + filePath);

        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture : " + e.getMessage());
        }
    }

   
    public static void copyFile(String source, String destination) {
        try {
            Files.copy(
                Paths.get(source),
                Paths.get(destination),
                StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Fichier copié vers : " + destination);

        } catch (IOException e) {
            System.err.println("Erreur lors de la copie : " + e.getMessage());
        }
    }

    public static void moveFile(String source, String destination) {
        try {
            Files.move(
                Paths.get(source),
                Paths.get(destination),
                StandardCopyOption.REPLACE_EXISTING
            );

            System.out.println("Fichier déplacé vers : " + destination);

        } catch (IOException e) {
            System.err.println("Erreur lors du déplacement : " + e.getMessage());
        }
    }

    
    public static void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);

            if (Files.exists(path)) {
                Files.delete(path);
                System.out.println("Fichier supprimé : " + filePath);
            } else {
                System.out.println("Le fichier n'existe pas : " + filePath);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

  
    public static void readBinaryFile(String filePath) {
        try (BufferedInputStream bis =
                new BufferedInputStream(new FileInputStream(filePath))) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = bis.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }

            System.out.println("Fichier binaire lu : " + filePath);
            System.out.println("Taille : " + totalBytes + " octets");

        } catch (IOException e) {
            System.err.println("Erreur de lecture binaire : " + e.getMessage());
        }
    }

   
    public static void writeBinaryFile(String filePath, byte[] data) {
        try (BufferedOutputStream bos =
                new BufferedOutputStream(new FileOutputStream(filePath))) {

            bos.write(data);
            bos.flush();

            System.out.println("Fichier binaire créé : " + filePath);
            System.out.println("Taille écrite : " + data.length + " octets");

        } catch (IOException e) {
            System.err.println("Erreur d'écriture binaire : " + e.getMessage());
        }
    }

    
    public static void getFileMetadata(String filePath) {
        try {
            Path path = Paths.get(filePath);

            BasicFileAttributes attrs = Files.readAttributes(
                path, BasicFileAttributes.class
            );

            System.out.println(" Métadonnées : " + filePath + " ===");
            System.out.println("Taille : " + attrs.size() + " octets");
            System.out.println("Dernière modification : "
                    + attrs.lastModifiedTime());
            System.out.println("Est un répertoire : " + attrs.isDirectory());
            System.out.println("Est un fichier : " + attrs.isRegularFile());

        } catch (IOException e) {
            System.err.println("Erreur métadonnées : " + e.getMessage());
        }
    }

  
    public static void main(String[] args) {

        try {
           
          
        	Files.createDirectories(Paths.get("data"));
            
            Path employees = Paths.get("data/employees.txt");

            if (!Files.exists(employees)) {
                Files.write(
                    employees,
                    ("Alice,25,Engineer\n"
                    + "Bob,30,Manager\n"
                    + "Charlie,28,Designer\n").getBytes(StandardCharsets.UTF_8)
                );
            }

        } catch (IOException e) {
            System.out.println("Erreur de préparation : " + e.getMessage());
            return;
        }

        System.out.println(" 1. LECTURE BUFFEREDREADER");
        readFileWithBufferedReader("data/employees.txt");

        System.out.println("2. ECRITURE BUFFEREDWRITER");
        writeFileWithBufferedWriter(
            "data/output.txt", "Bonjour, ceci est un test."
        );

        System.out.println("3. ECRITURE PRINTWRITER");
        writeWithPrintWriter("data/report.txt");

        System.out.println("4. LECTURE AVEC FILES");
        readWithFilesApi("data/employees.txt");

        System.out.println("5. ECRITURE AVEC FILES");
        writeWithFilesApi(
            "data/nio_output.txt", "Fichier créé avec l'API Files."
        );

        System.out.println("6. COPIE ");
        copyFile(
            "data/employees.txt", "data/employees_backup.txt"
        );

        System.out.println("7. DEPLACEMENT");
        moveFile(
            "data/nio_output.txt", "data/moved_output.txt"
        );

        System.out.println(" 8. SUPPRESSION");
        deleteFile("data/moved_output.txt");
        deleteFile("data/fichier_inexistant.txt");

        System.out.println("9. ECRITURE BINAIRE ");
        byte[] donnees = {10, 20, 30, 40, 50, 60};
        writeBinaryFile("data/copy_binary.bin", donnees);

        System.out.println("10. LECTURE BINAIRE");
        readBinaryFile("data/copy_binary.bin");

        System.out.println("11. METADONNEES");
        getFileMetadata("data/employees.txt");

        System.out.println("FIN ");
    }
}
