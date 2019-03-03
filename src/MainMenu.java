import model.*;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.*;

public class MainMenu extends JFrame implements View{
	private LinkedList<String> options = new LinkedList<String>();
	//private Dimension dimension = new Dimension(400,60);
	private RootModel rootModel;
	
	public MainMenu(RootModel rootModel){
		this.rootModel = rootModel;
		setup();
		build();
		pack();
		setVisible(true);
	}
	
    private void build(){    
        Box box = Box.createVerticalBox();
        box.add(new MainMenuPanel());
        add(box);
    }
	
	private void fillMenu(){
		options.add("Enter Transaction");
		options.add("View/Edit/Delete Transactions");
		options.add("View/Edit/Delete Recurring Transactions");
		options.add("Enter Saving Goal");
		options.add("View/Edit/Delete Saving Goals");
		options.add("Edit Spending Threshold");
		options.add("Edit Balance Warning Threshold");	
		options.add("View a filtered list of transactions");
		options.add("View a comparison screen");
		options.add("Income Weekly Projection");
		options.add("Prediction Weekly Projection");
		options.add("Vary Income Projection");
	}

	private void setup(){
		setTitle("Main Menu");
		setLocation(200, 100);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		fillMenu();
	}
	
	public void update(){	
	}
	
	private class MainMenuPanel extends JPanel implements View{
		public MainMenuPanel(){
            build();  
		}
		
		private void build(){
			setLayout(new BorderLayout());
				
			int no = options.size();
			Box box = Box.createVerticalBox();
			box.add(Box.createVerticalStrut(6));
				
			for(int i=0; i < no; i++){
				box.add(button(options.get(i), new ButtonListener()));
				box.add(Box.createVerticalStrut(5));
			}
				
			box.add(Box.createVerticalStrut(5));
			add(box);
		}
		
        private JButton button(String label, ButtonListener listener){
            JButton button = new JButton(label);
            button.addActionListener(listener);
            setSize(button,300);
            return button;  
        }
        
    	private void setSize(JComponent c, int x){
    		c.setPreferredSize(new Dimension(x, 40));
    		c.setMinimumSize(new Dimension(x, 40));
    		c.setMaximumSize(new Dimension(x, 40));
    	}
        
        public void update(){	
    	}
		
        private class ButtonListener implements ActionListener{
            public void actionPerformed(ActionEvent e){
            	//add other options in the menu here
            	if (e.getActionCommand().equals("Enter Transaction")){
            		new TransactionMenu(rootModel, "Transaction");
            	}
            	else if (e.getActionCommand().equals("View/Edit/Delete Transactions")){
                		new ListWindow(rootModel, "Transactions");
            	}
            	else if (e.getActionCommand().equals("Enter a Recurring Transaction")){
            		new TransactionMenu(rootModel, "Recurring Transaction");
            	}
            	else if (e.getActionCommand().equals("View/Edit/Delete Recurring Transactions")){
                	new ListWindow(rootModel, "Recurring Transactions");
            	}
            	else if (e.getActionCommand().equals("Enter Saving Goal")){
            		new TransactionMenu(rootModel, "Saving Goals");
            	}
            	else if (e.getActionCommand().equals("View/Edit/Delete Saving Goals")){
                	new ListWindow(rootModel, "Savings Goals");
            	}
            	else if (e.getActionCommand().equals("Edit Balance Warning Threshold")){
					new ThresholdWindow(rootModel, "Balance");
				}
            	else if (e.getActionCommand().equals("Edit Spending Threshold")){
					new ThresholdWindow(rootModel, "Spending");
            	}
            	else if (e.getActionCommand().equals("View a filtered list of transactions")){
					new FilterWindow(rootModel);
            	}
            	else if (e.getActionCommand().equals("View a comparison screen")){
					new TimeFrameWindow(rootModel);
            	}
            	else if (e.getActionCommand().equals("Income Weekly Projection")){
					new ProjectionWindow(rootModel);
            	}
            	else if (e.getActionCommand().equals("Prediction Weekly Projection")){
					new PredictionProjectionWindow();
            	}
            	else if (e.getActionCommand().equals("Vary Income Projection")){
					new VaryIncomeWindow(rootModel);
            	}
            }
        }
	}//end panel
}//end frame/window