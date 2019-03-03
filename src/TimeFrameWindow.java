import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.RootModel;

public class TimeFrameWindow extends JFrame{
	private JComboBox timeframeLookup = new JComboBox();
	private String[] timeframe = {"Weekly", "Monthly", "Yearly"};
	private RootModel rootModel;
	
	public TimeFrameWindow(RootModel rootModel){
		this.rootModel=rootModel;
        setup();
        build();
        pack();
        setVisible(true);
	}
	
    private void setup(){
        setLocation(200,200);
        setTitle("Select Timeframe");//set the title of the window, need to align this left though
    }
    
    private void build(){  
    	Box box = Box.createHorizontalBox();
        box.add(new TimeFramePanel());
        add(box);
    }
    
    private void closeWindow(){
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private class TimeFramePanel extends JPanel{
    	public TimeFramePanel(){
	        build();  
	    }
    	
    	private void build(){
            Box box = Box.createVerticalBox();
            box.add(ComboBoxBox());
            box.add(buttonBox());
            add(box);
    	}
    	
    	private Box ComboBoxBox(){
    		Box box = Box.createHorizontalBox();
    		box.add(pairLists("Timeframe",timeframeLookup,30));
            optionList();
    		box.add(Box.createHorizontalStrut(10));
    		return box;
    	}
    	
		private Box pairLists(String label, JComboBox cBox, int i){
			Box box = Box.createHorizontalBox();
			box.add(Box.createHorizontalStrut(15));
			box.add(label(label));
			box.add(Box.createHorizontalStrut(i));
			box.add(cBox);
			return box;
		}
		
		private JLabel label(String Lname){
			JLabel label = new JLabel(Lname, SwingConstants.LEFT);
			label.setPreferredSize(new Dimension(80, 25));
			return label;
		}
    
    	private void optionList(){
    		for (int i = 0; i < 3; i++){
    			timeframeLookup.addItem(timeframe[i]);
    		}	
    	}
    
    	private void setSize(JComponent c, int x, int y){
    			c.setPreferredSize(new Dimension(x, y));
    			c.setMinimumSize(new Dimension(x, y));
    			c.setMaximumSize(new Dimension(x, y));
    	}
    
    	private Box buttonBox(){
    		Box box = Box.createHorizontalBox();
    		box.add(Box.createHorizontalStrut(30));
    		box.add(button("Go", new ButtonListener()));
    		box.add(Box.createHorizontalStrut(30));
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
    			//get the selected string
    			String timeFrameSelection = (String) (timeframeLookup.getSelectedItem());
    			System.out.println(timeFrameSelection);
    			
    			//pass them to the comparison window
    			new ComparisonWindow(rootModel, timeFrameSelection);
    			closeWindow();
    		}
    	}
    }
}