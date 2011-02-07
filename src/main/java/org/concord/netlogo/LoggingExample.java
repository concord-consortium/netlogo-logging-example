package org.concord.netlogo;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;

import org.nlogo.api.CompilerException;
import org.nlogo.api.NetLogoListener;
import org.nlogo.lite.InterfaceComponent;
import org.nlogo.log.LogMessage;
import org.nlogo.window.InvalidVersionException;
import org.nlogo.workspace.AbstractWorkspace;

public class LoggingExample {
    private static final long serialVersionUID = 1L;
    private static int instanceCount = 0;
    private InterfaceComponent app;
    private JFrame frame;
    private static LoggingExample mainPanel;
    
    public LoggingExample(JFrame frame) {
        super();
        instanceCount++;
        this.frame = frame;
        app = new InterfaceComponent(frame);
        app.listenerManager.addListener(new MyListener());
        AbstractWorkspace.isApplet(true);
    }
    
    public void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.getContentPane().add(app, BorderLayout.CENTER);
                    
                    // simple example. updates one global per tick
                    // URL modelUrl = LoggingExample.class.getResource("model.nlogo");
                    
                    // to test relative image loading
                    URL modelUrl = new URL("http://www.concord.org/~aunger/nlogo/waveGenerator-v2.nlogo");
                    
                    System.err.println("model URL is: " + modelUrl.toExternalForm());

                    loadModel(modelUrl);
                    
                    // doCommand("mysetup");
                } catch (InvalidVersionException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        final JFrame mainFrame = initFrame("Netlogo Logging Example ");
        
        mainPanel = new LoggingExample(mainFrame);
        mainPanel.init();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                mainFrame.pack();
                mainFrame.setVisible(true);
            }
        });
    }
    
    private static JFrame initFrame(String title) {
        JFrame mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.getContentPane().add(getNextModelButton(), BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setVisible(true);
        return mainFrame;
    }
    
    private static JButton getNextModelButton() {
        JButton button = new JButton("Next model");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                mainPanel.destroyModel();
                mainPanel.init();
            }
        });
        return button;
    }

    public void destroyModel(){
//        app = null;
    }

    private void loadModel(URL modelLocation) throws InvalidVersionException, IOException {
        app.setPrefix(modelLocation);
        String source = getStringFromUrl(modelLocation);
        app.openFromSource("Model", null, source);
    }
    
    private String getStringFromUrl(URL url) throws IOException {
        InputStream inputStream = url.openStream();
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte [] buffer = new byte[1024];
        int len;
        while((len = inputStream.read(buffer)) > 0) {
            bout.write(buffer, 0, len);
        }
        return bout.toString();
    }
    
    private void doCommand(String command)
    {
        try {
            app.commandLater(command);
        } catch (CompilerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public class MyListener implements NetLogoListener
	{
		public void modelOpened(String name) {
			System.out.println("opened model " + name) ;
		}
		public void buttonPressed(String buttonName) {
			System.out.println("user pressed button " + buttonName) ;
		}
		public void buttonStopped(String buttonName) { }
		public void sliderChanged(String name, double value, double min,
									double increment, double max, boolean valueChanged,
									boolean buttonReleased) { }
		public void switchChanged(String name, boolean value, boolean valueChanged) { }
		public void chooserChanged(String name, Object value, boolean valueChanged) { }
		public void inputBoxChanged(String name, Object value, boolean valueChanged) { }
		public void commandEntered(String owner, String text, char agentType, CompilerException errorMsg) { }
		public void tickCounterChanged(double ticks) {
			System.out.println("ticks = " + ticks) ;
		}
		public void possibleViewUpdate() { }

	}
}
