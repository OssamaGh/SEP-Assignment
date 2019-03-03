import model.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.event.*;

public class ListWindow extends JFrame{
	private JList<String> list;
	private String type;
	private RootModel rootModel;
	
	public ListWindow(RootModel rootModel, String type){
		this.rootModel = rootModel;
		this.type = type;
		setup();
		populateList();
        build();
        pack();
        setVisible(true);
	}
	
	public ListWindow(RootModel rootModel, String type, JList<String> list){
		this.rootModel = rootModel;
		this.type = type;
		this.list = list;
		setup();
        build();
        pack();
        setVisible(true);
	}
	
    private void closeWindow(){
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void setup(){   
    	setTitle("View " + type);
    	setLocation(500, 500);
    }
    
    private void populateList(){
    	
    	if (type.equals("Transactions")){
    		list = new JList<String>(rootModel.user().getTransactionsStrings());
		}
    	else if (type.equals("Savings Goals")){
    		list = new JList<String>(rootModel.user().getSavingsGoalsStrings());
		}
    	else if (type.equals("Recurring Transactions")){
    		list = new JList<String>(rootModel.user().getRecurringTransactionsStrings());
		}
    }

    private void build(){
    	Box box = Box.createVerticalBox();
        box.add(listBox());
        box.add(Box.createVerticalStrut(10));
        box.add(buttonsBox());
        add(box);
    }
    
    private Box listBox(){
    	Box box = Box.createVerticalBox();
    	list.setFixedCellWidth(80);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(new Listener());
        list.setBorder(BorderFactory.createLineBorder(Color.blue));
        box.add(list);
    	return box;
    }
    
    private Box buttonsBox(){
    	Box box = Box.createHorizontalBox();
    	box.add(Box.createHorizontalStrut(20));
    	box.add(button("Edit/View", new ButtonListener()));
    	box.add(Box.createHorizontalStrut(5));
    	box.add(button("Delete", new ButtonListener()));
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
	
    private class Listener implements ListSelectionListener{
    	public void valueChanged(ListSelectionEvent e){
    		System.out.println("adjust");
            if (e.getValueIsAdjusting())
                return;
            System.out.println("use");
         }
    }
    
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	if (e.getActionCommand().equals("Edit/View")){
        		System.out.print("opening a transation menu with the selected items values");
        		try{
        			new TransactionMenu(rootModel, type, rootModel.user().getTransaction(list.getSelectedValue()));
        		}
        		catch(Exception e1){
        			new TransactionMenu(rootModel, type, rootModel.user().getRecurringTransaction(list.getSelectedValue()));
        		}
        		finally{
        		}
			}
        	else if (e.getActionCommand().equals("Delete")){
        		rootModel.user().deleteTransaction(list.getSelectedValue());
        		System.out.print("deleting a transaction");
			}
        	
        	//store the file
        	try {
        		rootModel.store();
        	}
        	catch(Exception e1){}
            closeWindow();
        }
    }	
}
