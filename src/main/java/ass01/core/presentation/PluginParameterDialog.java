package ass01.core.presentation;

import ass01.core.domain.services.PluginParameter;
import ass01.core.domain.services.RentalServicePlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Adapted from AddEBikeDialog
 * 
 */
public class PluginParameterDialog extends JDialog {

    private RentalServicePlugin plugin;
    private JTextField idEBikeField;
    private JTextField userName;
    private JButton applyButton;
    private JButton cancelButton;
    private AppView app;
    private String userId;
    private String bikeId;

    public PluginParameterDialog(AppView owner, RentalServicePlugin plugin) {
        super(owner, plugin.operationName(), true);
        initializeComponents();
        setupLayout();
        addEventHandlers();
        pack();
        setLocationRelativeTo(owner);
        app = owner;
        this.plugin = plugin;
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
                    s.applyPlugin(plugin.operationName(), params);
                });
                // add end-ride dialog
//                new RideSimulationControlPanel(userRiding, bikeId, app).display();
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
    
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//        	PluginParameterDialog dialog = new PluginParameterDialog(null);
//            dialog.setVisible(true);
//        });
//    }
    
}
