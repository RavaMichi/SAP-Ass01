package ass01.core.adapters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPluginDialog extends JDialog {

    private JTextField idPlugin;
    private JTextField namePlugin;
    private JButton addButton;
    private JButton cancelButton;
    private AppView app;

    public AddPluginDialog(AppView owner) {
        super(owner, "Add plugin", true);
        initializeComponents();
        setupLayout();
        addEventHandlers();
        pack();
        setLocationRelativeTo(owner);
        app = owner;
    }

    private void initializeComponents() {
        idPlugin = new JTextField(15);
        namePlugin = new JTextField(15);
        addButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("Operation name:"));
        inputPanel.add(namePlugin);
        inputPanel.add(new JLabel("JAR name:"));
        inputPanel.add(idPlugin);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idPlugin.getText();
                String name = namePlugin.getText();
                cancelButton.setEnabled(false);

                // add the plugin
                app.addPlugin(id, name);

                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

}

