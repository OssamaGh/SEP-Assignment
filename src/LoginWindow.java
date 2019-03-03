import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class LoginWindow extends JFrame{
	private JTextField userName = new JTextField(9);
	private RootModel rootModel;
	
	public LoginWindow(RootModel rootModel){
		this.rootModel=rootModel;
	    setup();
	    build();
	    pack();
	    setVisible(true);
	}
		
	private void setup(){
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //cannot use this since a self closing window will instead close entire program
	    setLocation(200,200);
	    setTitle("YourWallet Login");//set the title of the window, need to align this left though
	}
		
	private void build(){  
	    Box box = Box.createHorizontalBox();
	    box.add(new LoginPanel());
	    add(box);
	}
		
	private void closeWindow(){
	    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
		
	private class LoginPanel extends JPanel{
	    public LoginPanel(){
	        build();  
	    }
	        
	    private void build(){
	        Box box = Box.createVerticalBox();
	        box.add(pair("Enter your user name", userName, 0));
	        setSize(userName,100,20);
	        userName.setText("");
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
	        box.add(button("Login", new ButtonListener()));
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
	            //search for user name 
	        	if(!userName.getText().isEmpty()){
	        		try{
	        			rootModel.setFile(userName.getText());
	        			closeWindow();//close window
	        		}
	        		catch(IOException e1){
	        			System.out.println("file not read successfully");
	        		}
	        	}
	        }
	    }
	}
}