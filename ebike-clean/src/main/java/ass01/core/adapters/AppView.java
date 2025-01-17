package ass01.core.adapters;

import ass01.core.business.entities.EBike;
import ass01.core.business.services.RentalService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.function.Consumer;


public class AppView extends JFrame {

	private static final long POLLING_TIME = 100;
    private VisualiserPanel centralPanel;
	private RentalService service;
	private JPanel topPanel = new JPanel();
    
    public AppView(RentalService service){
		this.service = service;
        setupView();
    }

    protected void setupView() {
        setTitle("EBike App");        
        setSize(800,600);
        setResizable(false);
        
        setLayout(new BorderLayout());

		// add plugin button
		JButton button = new JButton("Add plugin");
		button.addActionListener(e -> new AddPluginDialog(this).setVisible(true));
		topPanel.add(button);
		add(topPanel,BorderLayout.NORTH);

        centralPanel = new VisualiserPanel(800,500,service);
	    add(centralPanel,BorderLayout.CENTER);
	    	    		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
				System.exit(0);
			}
		});

		startPolling();
    }

	public void addPlugin(String pluginJar, String operationName) {
		service.addPlugin(operationName, pluginJar);

		JButton button = new JButton(operationName);
		button.addActionListener(e -> {
			JDialog d = new PluginParameterDialog(this, operationName);
			d.setVisible(true);
		});

		topPanel.add(button);
		validate();
	}

	/**
	 * Instead of being notified by the domain, it uses polling to update itself
	 * (like a client-server arch, where only the client makes requests)
	 */
	private void startPolling() {
		new Thread(() -> {
			while (true) {
                try {
					refreshView();
                    Thread.sleep(POLLING_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
		}).start();
	}

    public void display() {
    	SwingUtilities.invokeLater(() -> {
    		this.setVisible(true);
    	});
    }
    
    public void refreshView() {
    	centralPanel.refresh();
    }

	/**
	 * Execute an action on the service of this app
	 * @param action
	 */
	public void callRentalService(Consumer<RentalService> action) {
		action.accept(this.service);
	}

	private void log(String msg) {
		System.out.println("[EBikeApp] " + msg);
	}
    
    public static class VisualiserPanel extends JPanel {
        private long dx;
        private long dy;
        private RentalService app;
        
        public VisualiserPanel(int w, int h, RentalService app){
            setSize(w,h);
            dx = w/2 - 20;
            dy = h/2 - 20;
            this.app = app;
        }

        public void paint(Graphics g){
    		Graphics2D g2 = (Graphics2D) g;
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    		          RenderingHints.VALUE_ANTIALIAS_ON);
    		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
    		          RenderingHints.VALUE_RENDER_QUALITY);
    		g2.clearRect(0,0,this.getWidth(),this.getHeight());

			var state = app.getState();

            for (EBike b : state.bikes()) {
                var p = b.getLocation();
                int x0 = (int) (dx + p.x());
                int y0 = (int) (dy - p.y());
                g2.drawOval(x0, y0, 20, 20);
                g2.drawString(b.getId(), x0, y0 + 35);
                g2.drawString("(" + (int) p.x() + "," + (int) p.y() + ")", x0, y0 + 50);
            }
    		
    		var it2 = state.users().iterator();
    		var y = 20;
    		while (it2.hasNext()) {
    			var u = it2.next();
    			g2.drawRect(10,y,20,20);
		        g2.drawString(u.getId() + " - credit: " + u.getCredit(), 35, y+15);
		        y += 25;
    		};
            
        }
        
        public void refresh(){
            repaint();
        }
    }

	
	
//	public static void main(String[] args) {
//		var w = new EBikeApp();
//		w.display();
//	}
	
}
