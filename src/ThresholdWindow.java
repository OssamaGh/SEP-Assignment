import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ThresholdWindow extends JFrame{
	private JTextField threshold = new JTextField(9);
	private RootModel rootModel;
	private String type; //type refers to either balance or spending threshold
	
	public ThresholdWindow(RootModel rootModel, String type){
		this.rootModel = rootModel;
		this.type = type;
        setup();
        build();
        pack();
        setVisible(true);
	}
	
    private void setup(){
        setLocation(200,200);
        setTitle("Edit " + type + "Threshold");//set the title of the window, need to align this left though
    }
    
    private void build(){  
    	Box box = Box.createHorizontalBox();
        box.add(new ThresholdPanel());
        add(box);
    }
    
    private void closeWindow(){
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private class ThresholdPanel extends JPanel{
        public ThresholdPanel(){
            setup();
            build();  
        }
        
        private void setup(){
        }
        
        private void build(){
            Box box = Box.createVerticalBox();
            box.add(pair("Enter a " + type + " Threshold", threshold, 0));
            setSize(threshold,100,20);
            box.add(buttonBox());
            add(box); 
        }
        
        private Box pair(String label, JTextField field, int padding) {
            Box box = Box.createHorizontalBox();
            box.add(new JLabel(label));
            box.add(Box.createHorizontalStrut(10));
            box.add(field);
            box.add(Box.createHorizontalStrut(padding));
            return box;
        }
        
        private void setSize(JComponent c, int x, int y){
            c.setPreferredSize(new Dimension(x, y));
            c.setMinimumSize(new Dimension(x, y));
            c.setMaximumSize(new Dimension(x, y));
        }
        private Box buttonBox(){
        	Box box = Box.createHorizontalBox();
        	box.add(button("Set threshold", new ButtonListener()));
        	box.add(Box.createHorizontalStrut(10));
        	box.add(button("Cancel", new ButtonListener()));
        	return box;
        }
        
        private JButton button(String label, ButtonListener listener){
            JButton button = new JButton(label);
            button.addActionListener(listener);
            setSize(button, 150, 30);
            return button;  
        }
        
        private class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
            	if (e.getActionCommand().equals("Set threshold")){
            		if (type.equals("Balance")){
            			rootModel.user().setBalanceThreshold(Double.parseDouble(threshold.getText()));
            		}
            		else{
            			rootModel.user().setSpendingThreshold(Double.parseDouble(threshold.getText()));
            		}
            		
            	}
            	
            	try {
            		rootModel.store();
            	}
            	catch(Exception e1){}
            	
                //close window
                closeWindow();
            }
        }
    }
}