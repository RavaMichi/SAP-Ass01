package ass01.core.presentation;

import ass01.core.business.services.PluginParameter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PluginParameterDialog extends JDialog {

    private String pluginId;
    private JTextField idEBikeField;
    private JTextField userName;
    private JButton applyButton;
    private JButton cancelButton;
    private AppView app;
    private String userId;
    private String bikeId;

    public PluginParameterDialog(AppView owner, String plugin) {
        super(owner, plugin, true);
        initializeComponents();
        setupLayout();
        addEventHandlers();
        pack();
        setLocationRelativeTo(owner);
        app = owner;
        this.pluginId = plugin;
    }

    private void initializeComponents() {
        idEBikeField = new JTextField(15);
        userName = new JTextField(15);
        applyButton = new JButton("Apply");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("User id:"));
        inputPanel.add(userName);
        inputPanel.add(new JLabel("E-Bike id:"));
        inputPanel.add(idEBikeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addEventHandlers() {
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bikeId = idEBikeField.getText();
	            userId = userName.getText();
	            cancelButton.setEnabled(false);
                var params = new PluginParameter(userId, bikeId);
                app.callRentalService(s -> {
                    // apply plugin
                    s.applyPlugin(pluginId, params);
                });
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
