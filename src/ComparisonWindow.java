import model.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;
import javax.swing.table.*;
import java.text.*;

public class ComparisonWindow extends JFrame{
	private RootModel rootModel;
	private String timeFrame;
	
	public ComparisonWindow(RootModel rootModel, String timeFrame){
		this.rootModel = rootModel;
		this.timeFrame = timeFrame;
        setup();
        build();
        pack();
        setVisible(true);
	}
	
    private void setup(){
        setLocation(200,200);
        setTitle("Comparison View");//set the title of the window, need to align this left though
    }    
    
    private void build(){  
    	Box box = Box.createHorizontalBox();
        box.add(new ComparisonPanel());
        add(box);
    }
    
    private void closeWindow(){
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
    
    private class ComparisonPanel extends JPanel{
    	private String[] headers = {"Time", "Change in balance"};
    	private JTable table = new JTable(data(), headers);
    	    
    	public ComparisonPanel(){
    		setup();
	        build();  
	    }
    	
    	private void setup(){
    		TableColumnModel model = table.getColumnModel();
	        model.getColumn(0).setPreferredWidth(160);
	        model.getColumn(1).setPreferredWidth(160); 
    	}
    	
    	private void build(){
            Box box = Box.createVerticalBox();
            box.add(tableBox());
            box.add(buttonBox());
            add(box);
    	}
    	
    	private Box tableBox(){
    		Box box = Box.createVerticalBox();
    		box.add(table.getTableHeader());
	        box.add(table);
    		return box;
    	}
    	
    	private String timeFrameDates(int timeFrameIndex){
    		if (timeFrame.equals("Weekly")){
    			return rootModel.user().weekDates(timeFrameIndex);
    		}
    		else if (timeFrame.equals("Monthly")){
    			return rootModel.user().monthDates(timeFrameIndex);
    		}
    		else{
    			return rootModel.user().yearDates(timeFrameIndex);
    		}
    	}
    	
    	private String[] data(int timeFrameIndex){
        	String[] data = new String[2];
            data[0] = timeFrameDates(timeFrameIndex);
            data[1] = "$" + formatted(rootModel.user().income(rootModel.user().weeklyTransactions(timeFrameIndex)));
            return data;
        }
        
        private String[][] data(){
            String[][] data = new String[4][2];
            for (int i = 0; i < 4; i++)
                data[i] = data(i);
            return data; 
        }
    	
        private String formatted(double amount){
        	DecimalFormat form = new DecimalFormat("###,##0.00");
            return form.format(amount); 
        }
    
    	private void setSize(JComponent c, int x, int y){
    		c.setPreferredSize(new Dimension(x, y));
    		c.setMinimumSize(new Dimension(x, y));
    		c.setMaximumSize(new Dimension(x, y));
    	}
    	
    	private Box buttonBox(){
    		Box box = Box.createHorizontalBox();
    		box.add(Box.createHorizontalStrut(30));
    		box.add(button("Ok", new ButtonListener()));
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
    			closeWindow();
    		}
    	}
    }
}