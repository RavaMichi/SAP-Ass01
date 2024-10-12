package ass01.core.presentation;

import ass01.core.domain.Ride;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RideSimulationControlPanel extends JFrame {

    private JButton stopButton;

    public RideSimulationControlPanel(String userId, String bikeId, AppView app) {
        super("Ongoing Ride: (" + userId + ", " + bikeId + ")");
    	setSize(400, 200);

        stopButton = new JButton("Stop Riding");
    	
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("Rider name: " + userId));
        inputPanel.add(new JLabel("Riding e-bike: " + bikeId));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(stopButton);

        setLayout(new BorderLayout(10, 10));
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.callRentalService(s -> s.endRide(userId, bikeId));
        		dispose();
            }
        });
    }
    
    public void display() {
    	SwingUtilities.invokeLater(() -> {
    		this.setVisible(true);
    	});
    }

}
