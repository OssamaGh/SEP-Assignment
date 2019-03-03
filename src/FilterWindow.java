import model.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FilterWindow extends JFrame{
	private JTextField keyWord = new JTextField(9);
	private RootModel rootModel;
	
	public FilterWindow(RootModel rootModel){
		this.rootModel=rootModel;
	    setup();
	    build();
	    pack();
	    setVisible(true);
	}
	
	private void setup(){
	    setLocation(200,200);
	    setTitle("Enter a search term");//set the title of the window, need to align this left though
	}
	
	private void build(){  
	    Box box = Box.createHorizontalBox();
	    box.add(new FilterPanel());
	    add(box);
	}
	
	private void closeWindow(){
	    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
		
	private class FilterPanel extends JPanel{
	    public FilterPanel(){
	        build();  
	    }
	        
	    private void build(){
	        Box box = Box.createVerticalBox();
	        box.add(pair("Enter a search term", keyWord, 0));
	        setSize(keyWord,100,20);
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
	        box.add(button("Search", new ButtonListener()));
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
	        	String[] temp = rootModel.user().getFilteredTransactions(keyWord.getText());
	        	if (!temp[0].equals(null)){
	        		JList<String> list = new JList<String>(rootModel.user().getFilteredTransactions(keyWord.getText()));
	        		new ListWindow(rootModel, "Filtered Transactions", list);
	        	}
	        	else{
	        		new NotificationWindow("There are no transactions to display");
	        	}
	        	closeWindow();
	        }
	    }
	}
}