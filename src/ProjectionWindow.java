import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ProjectionWindow extends JFrame{
	private JTextField price = new JTextField(9);
	private JTextField weeks = new JTextField(9);
	private RootModel rootModel;
	
	public ProjectionWindow(RootModel rootModel){
		this.rootModel=rootModel;
	    setup();
	    build();
	    pack();
	    setVisible(true);
	}
		
	private void setup(){
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //cannot use this since a self closing window will instead close entire program
	    setLocation(200,200);
	    setTitle("Weekly Projection");//set the title of the window, need to align this left though
	}
		
	private void build(){  
	    Box box = Box.createHorizontalBox();
	    box.add(new ProjectionPanel());
	    add(box);
	}
		
	private class ProjectionPanel extends JPanel{
	    public ProjectionPanel(){
	        build();  
	    }
	        
	    private void build(){
	        Box box = Box.createVerticalBox();
	        box.add(pair("Enter item price", price, 0));
	        box.add(pair("No. of Weeks", weeks, 0));
	        setSize(price,100,20);
	        price.setText("");
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
	        	if(!price.getText().isEmpty()){
	        		double income = rootModel.user().income(rootModel.user().weeklyTransactions(0));
	        		weeks.setText(Double.toString((Double.parseDouble(price.getText()) - rootModel.user().getBalance()) / income));
	        		//use my formula since ossama's is only returning 1 for some reason
	        	}
	        }
	    }
	}
}