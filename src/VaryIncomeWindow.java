import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class VaryIncomeWindow extends JFrame{
	private JTextField price = new JTextField(9);
	private JTextField income = new JTextField(9);
	private JTextField weeks = new JTextField(9);
	private RootModel rootModel;
	
	public VaryIncomeWindow(RootModel rootModel){
		this.rootModel=rootModel;
	    setup();
	    build();
	    pack();
	    setVisible(true);
	}
	
	private void setup(){
	    setLocation(200,200);
	    setTitle("Vary Income");//set the title of the window, need to align this left though
	}
	
	private void build(){  
	    Box box = Box.createHorizontalBox();
	    box.add(new VaryIncomePanel());
	    add(box);
	}
	
	private class VaryIncomePanel extends JPanel{
	    public VaryIncomePanel(){
	        build();  
	    }
	        
	    private void build(){
	        Box box = Box.createVerticalBox();
	        box.add(pair("Enter item price", price, 0));
	        box.add(pair("Enter hypothetical income", income, 0));
	        box.add(pair("Number of weeks", weeks, 0));
	        setSize(price,100,20);
	        price.setText("");
	        income.setText("");
	        weeks.setEditable(false);
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
	        	weeks.setText(Double.toString((Double.parseDouble(price.getText()) - rootModel.user().getBalance()) / Double.parseDouble(income.getText()))); 
	        }
	    }
	}
}