/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io;

import beans.Student;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author martin
 */
public class IOHandler {

    private static final Path IMPORT_PATH = Paths.get(System.getProperty("user.dir"), "src", "res", "Studentlist_3xHIF.csv");
    private static final Path EXPORT_PATH = Paths.get(System.getProperty("user.dir"), "src", "res", "Studentlist_3xHIF_Export.csv");

    public static List<Student> load() throws FileNotFoundException {
        final JFileChooser fc = new JFileChooser(Paths.get(System.getProperty("user.dir"), "src", "res").toFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SVG Files", "csv");
        fc.setFileFilter(filter);
        int result = fc.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return new BufferedReader(new FileReader(fc.getSelectedFile()))
                    .lines()
                    .skip(1)
                    .map(Student::fromLine)
                    .collect(Collectors.toList());
        }
        return null;
    }

    public static void export(List<Student> students) throws IOException {
        String result = students
                .stream()
                .map(Student::toCSVString)
                .collect(Collectors.joining("\n"));

        final JFileChooser fc = new JFileChooser(Paths.get(System.getProperty("user.dir"), "src", "res").toFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("SVG Files", "csv");
        fc.setFileFilter(filter);
        int resultCode = fc.showOpenDialog(null);

        if (resultCode == JFileChooser.APPROVE_OPTION) {
            FileWriter fw = new FileWriter(fc.getSelectedFile());
            result = "Klasse;Familienname;Vorname;Geschlecht;Geburtsdatum\n" + result;
            fw.write(result);
            fw.close();
        }
        
        JOptionPane.showMessageDialog(null, (String) "Export successful!");
    }
}
