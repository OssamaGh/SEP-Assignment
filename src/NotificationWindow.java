import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class NotificationWindow extends JFrame{
	public NotificationWindow(String message){
        setup();
        build(message);
        pack();
        setVisible(true);
  	}
	
    private void setup(){
        setLocation(250,200);
        setTitle("Warning");//set the title of the window, need to align this left though
     }
    
    private void build(String message){  
    	Box box = Box.createHorizontalBox();
        box.add(new NotificationPanel(message));
        add(box);
    }
    
    private void closeWindow(){
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
	
	private class NotificationPanel extends JPanel{
        public NotificationPanel(String message){
            setup();
            build(message);  
        }
        
        private void build(String message){
            Box box = Box.createVerticalBox();
            box.add(labelBox(message));
            //box.add(new JLabel(message));
            box.add(Box.createVerticalStrut(10));
            box.add(buttonBox());
            add(box); 
        }
        
        private Box labelBox(String message){
        	JTextArea output = new JTextArea(message);
            output.setEditable(false);
            JScrollPane outputPane = new JScrollPane(output,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        	Box box = Box.createHorizontalBox();
        	box.add(Box.createHorizontalStrut(10));
        	setSize(output, 250, 150);
        	box.add(outputPane);
        	box.add(Box.createHorizontalStrut(10));
        	return box;
        }
		
        private JButton button(String label, ButtonListener listener){
            JButton button = new JButton(label);
            button.addActionListener(listener);
            setSize(button, 150, 30);
            return button;  
        }
        
        private void setSize(JComponent c, int x, int y){
            c.setPreferredSize(new Dimension(x, y));
            c.setMinimumSize(new Dimension(x, y));
            c.setMaximumSize(new Dimension(x, y));
        }
        
        private Box buttonBox(){
        	Box box = Box.createHorizontalBox();
        	box.add(Box.createHorizontalStrut(10));
        	box.add(button("Ok", new ButtonListener()));
        	box.add(Box.createHorizontalStrut(10));
        	return box;
        }
        
        private class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
                closeWindow();
            }
        }	
	}
}