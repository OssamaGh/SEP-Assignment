import model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class PredictionProjectionWindow extends JFrame{
	private JTextField price = new JTextField(9);
	private JTextField savings = new JTextField(9);
	private JTextField weeks = new JTextField(9);
	
	public PredictionProjectionWindow(){
	    setup();
	    build();
	    pack();
	    setVisible(true);
	}
		
	private void setup(){
	    setLocation(200,200);
	    setTitle("Prediction Weekly Projection");//set the title of the window, need to align this left though
	}
		
	private void build(){  
	    Box box = Box.createHorizontalBox();
	    box.add(new PredictionProjectionPanel());
	    add(box);
	}
		
	private class PredictionProjectionPanel extends JPanel{
	    public PredictionProjectionPanel(){
	        build();  
	    }
	        
	    private void build(){
	        Box box = Box.createVerticalBox();
	        box.add(pair("Enter item price", price, 0));
	        box.add(pair("No. of Weeks", weeks, 0));
	        box.add(pair("Savings p. Week", savings, 0));
	        setSize(price,100,20);
	        price.setText("");
	        weeks.setText("");
	        savings.setEditable(false);
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
	        box.add(button("Calculate", new ButtonListener()));
	        box.add(Box.createHorizontalStrut(10));
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
	        	if(!price.getText().isEmpty()){
	        		savings.setText("$" + Double.toString(Double.parseDouble(price.getText()) / Double.parseDouble(weeks.getText())));
	        	}
	        }
	    }
	}
}