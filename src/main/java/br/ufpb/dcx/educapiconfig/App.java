package br.ufpb.dcx.educapiconfig;

import java.awt.event.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class App extends JFrame implements ActionListener {
    static JTextField textField;
    static JFrame frame;
    static JButton button;
    static JLabel label;
    static HashMap<String, Object> config;
    public static final Path fileName = Paths.get("config.json");

    App() {
    }

    public static void loadConfig() {
        try {
            config = new HashMap<>();
            String content = new String(Files.readAllBytes(fileName));
            ObjectMapper mapper = new ObjectMapper();
            config = (HashMap<String, Object>) mapper.readValue(content, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void salvarConfig() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = (String) mapper.writeValueAsString(config);
            Files.writeString(fileName, jsonString, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    public static void main(String[] args) {

        loadConfig();

        frame = new JFrame("EducAPI-Config");

        label = new JLabel("API URL: ");

        button = new JButton("Salvar");

        App te = new App();
        button.addActionListener(te);

        textField = new JTextField(30);
        textField.setText((String)config.get("domain"));

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.add(label);

        panel.add(textField);
        panel.add(button);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 200);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals("Salvar")) {
            config.put("domain", textField.getText());
            salvarConfig();
        }
    }
}
