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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.varia.NullAppender;
import org.nlogo.api.CompilerException;
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
        AbstractWorkspace.isApplet(true);
    }
    
    public void init() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame.getContentPane().add(app, BorderLayout.CENTER);
                    
                    setupLogging();
                    
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
        Logger variablesLogger = Logger.getLogger("org.nlogo.log.Logger");
        variablesLogger.setLevel(Level.INFO);
        EventAppender eventAppender = new EventAppender();
        variablesLogger.addAppender(eventAppender);
        
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

    private void setupLogging() {
        Reader reader = getLoggerProperties();
        app.startLogging(reader, "user"+instanceCount);
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
    
    private Reader getLoggerProperties(){
        BufferedReader br = null;

        URL propertiesFile = LoggingExample.class.getResource("netlogo_logging.xml");
        if (propertiesFile != null){
            try {
                br = new BufferedReader(new InputStreamReader(propertiesFile.openStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return br;
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
}

/**
 * The EventAppender listens for every invocation of doAppend
 * by the Logger. The LogMessage that comes back can then
 * be parsed for information about the variable that has
 * changed and its new value.
 * 
 * Each log message has an array of attributes (e.g. "event" and
 * a type), and an array of elements, which consist of variable 
 * or button names and new values or actions.
 * 
 * Sample sets of attributes and elements:
 * 
 * [event type:globals, elements:[[tag:name, data:MY-RABBIT-ENERGY], [tag:value, data:5.0]] ] 
 * [event type:button, elements:[[tag:name, data:setup], [tag:action, data:released], [tag:releaseType, data:once]] ]
 * [event type:button, elements:[[tag:name, date:go], [tag:action, data:pressed], [tag:releaseType, data:user]] ]
 *
 */
class EventAppender extends NullAppender {
    @Override
    public void doAppend(LoggingEvent event) {
        // System.out.println("NDC: " + event.getNDC());
        LogMessage logMessage = (LogMessage) event.getMessage();
        
        HashMap<String, String> info = getEventInfo(logMessage);
        
        debugMessage(info);
    }
    
    private HashMap<String, String> getEventInfo(LogMessage message) {
        HashMap<String, String> info = new HashMap<String, String>();
        for (LogMessage element : message.elements) {
            info.put(element.tag, element.data);
        }
        return info;
    }
    
    private void debugMessage(HashMap<String, String> info) {
      System.out.println("\n----------");
      for (Map.Entry<String, String> e : info.entrySet()) {
          System.out.println(e.getKey() + ": " + e.getValue());
      }
    }
}
