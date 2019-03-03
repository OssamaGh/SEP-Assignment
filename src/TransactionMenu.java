import model.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TransactionMenu extends JFrame{
	private JTextField itemName = new JTextField(9);
	private JTextField amount = new JTextField(9);
	private JTextField description = new JTextField(9);
	private JTextField category = new JTextField(9);
	private String[] frequency = { "","Once","Daily", "Weekly", "Monthly", "Yearly" };
	private String[] typeList = { "","Income", "Expense" };
	private JComboBox<String> fc = new JComboBox<String>();//what does fc mean?
	private JComboBox<String> tc = new JComboBox<String>();//what does tc mean?
	private JComboBox days = new JComboBox();
	private JComboBox months = new JComboBox();
	private JComboBox years = new JComboBox();
	private RootModel rootModel;
	private String frequencySelection;
	private int daySelection;
	private int monthSelection;
	private int yearSelection;
	private String typeSelection;
	private int typeMultiplier;
	private String type; // type refers to either transaction or desired purchase item

	public TransactionMenu(RootModel rootModel, String type){
		this.type = type;
		this.rootModel = rootModel;
		setup();
		build();
		pack();
		setVisible(true);
	}
	
	public TransactionMenu(RootModel rootModel, String type, Transaction transaction){
		this.type = type;
		this.rootModel = rootModel;
		setup();
		build();
		pack();
		setTextFieldsTransaction(transaction);
		setVisible(true);
	}
	
	public TransactionMenu(RootModel rootModel, String type, RecurringTransaction recurringTransaction){
		this.type = type;
		this.rootModel = rootModel;
		setup();
		build();
		pack();
		setTextFieldsRTransaction(recurringTransaction);
		setVisible(true);
	}
	
	private void setTextFieldsTransaction(Transaction transaction){
		itemName.setText(transaction.getItemName());
		amount.setText(Double.toString(transaction.getCost()));
		description.setText(transaction.getDescription());
		category.setText(transaction.getCategory());
	}
	
	private void setTextFieldsRTransaction(RecurringTransaction recurringTransaction){
		itemName.setText(recurringTransaction.getItemName());
		amount.setText(Double.toString(recurringTransaction.getCost()));
		description.setText(recurringTransaction.getDescription());
		category.setText(recurringTransaction.getCategory());
	}

	private void setup(){
		setTitle("Enter " + type + " Menu");
		setLocation(800,200);
	}

	private void build(){
		Box box = Box.createHorizontalBox();
		box.add(new TransactionPanel());
		add(box);
	}

	private void closeWindow(){
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}

	private class TransactionPanel extends JPanel{
		public TransactionPanel(){
			dateSetup();
			listSetup();
			build();
		}

		private void build(){
			buildTransactionMenu();
			Box box = Box.createVerticalBox();
			box.add(fieldsBox());
			box.add(Box.createVerticalStrut(15));
			box.add(saveButton());
			add(box);
			setSize(650, 700);
		}
		
		private Box fieldsBox(){
			Box box = Box.createHorizontalBox();
			box.add(inputBox(), BorderLayout.WEST);
			box.add(listBox(), BorderLayout.EAST);
			return box;
		}

		private Box listBox(){
			Box box = Box.createVerticalBox();
			box.add(Box.createVerticalStrut(10));
			box.add(pairLists("Type",tc,30));
			box.add(Box.createHorizontalStrut(60));
			box.add(Box.createVerticalStrut(30));
			box.add(pairLists("Frequency",fc,30));
			box.add(Box.createHorizontalStrut(60));
			box.add(Box.createVerticalStrut(30));
			box.add(pairLists("Day",days,30));
			box.add(pairLists("Month",months,30));
			box.add(pairLists("Year",years,30));
			box.add(Box.createHorizontalStrut(60));
			return box;
		}
		
		private Box pair(String label, JTextField field, int i){
			Box box = Box.createHorizontalBox();
			box.add(Box.createHorizontalStrut(15));
			box.add(label(label));
			box.add(Box.createHorizontalStrut(i));
			box.add(field);
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
		
		public void buildTransactionMenu(){
			setSize(itemName, 200);    //Text Box Size ONLY
			setSize(amount, 200);
			setSize(description, 300);
			setSize(category, 200);
			if (type.equals("Recurring Transaction")){
				setSize(fc, 200); //frequency will only come up when entering a recurring transaction
			}
		}
		
		private void setSize(JComponent c, int x){
			c.setPreferredSize(new Dimension(x, 20));
			c.setMinimumSize(new Dimension(x, 20));
			c.setMaximumSize(new Dimension(x, 20));
		}
		
		private JLabel label(String Lname){
			JLabel label = new JLabel(Lname, SwingConstants.LEFT);
			label.setPreferredSize(new Dimension(80, 25));
			return label;
		}
		
		private Box saveButton(){
			Box box = Box.createHorizontalBox();
			box.add(Box.createHorizontalStrut(80));
			JButton button = new JButton("Save Transaction");
			button.addActionListener(new ButtonListener());
			box.add(Box.createHorizontalStrut(150));
			box.add(button);
			return box;
		}

		private int[] daysList(){
			int[] days = new int[32];
			for(int i = 1; i<32; i++)
			days[i]=i;
			return days;
		}
		
		private int[] monthsList(){
			int[] months = new int[13];
			for(int i = 1; i<13; i++)
			months[i]=i;
			return months;
		}
		
		private int[] yearsList(){
			int count = 1;
			int[] years = new int[22];
			for(int i = 2000; i<2021; i++){
				years[count]=i;
				count++;
			}
			return years;
		}
		
		private void dateSetup(){
			int count = 1;
			int count2 = 1;
			int count3 = 1;
			int[] daysList = daysList();
			int[] monthsList = monthsList();
			int[] yearsList = yearsList();
			days.addItem("");
			months.addItem("");
			years.addItem("");
			for (int i = 0; i < 31; i++){
				days.addItem(daysList[count++]);
			}
			
			days.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        daySelection=(Integer)((JComboBox) e.getSource()).getSelectedItem();
			        System.out.println(daySelection);
			      }
			});
			
			for (int i = 0; i <12; i++){
				months.addItem(monthsList[count2++]);
			}
			
			months.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			    	  monthSelection=(Integer)((JComboBox) e.getSource()).getSelectedItem();
				        System.out.println(	monthSelection);
			      }
			});
			
			for (int i = 0; i <21; i++){
				years.addItem(yearsList[count3++]);}
				years.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						yearSelection=(Integer)((JComboBox) e.getSource()).getSelectedItem();
						System.out.println(	yearSelection);
					}
				});
			}
		
		private Box inputBox(){
			Box box = Box.createVerticalBox();
			box.add(Box.createHorizontalBox());
			box.add(Box.createVerticalStrut(10));
			box.add(pair("Item Name", itemName, 50));
			box.add(Box.createVerticalStrut(3));
			box.add(pair("Amount($)", amount, 76));
			box.add(Box.createVerticalStrut(3));
			box.add(pair("Description", description, 10));
			box.add(Box.createVerticalStrut(3));
			box.add(pair("Category", category, 10));
			box.add(Box.createVerticalStrut(3));
			if (type.equals("Recurring Transaction")){
				box.add(Box.createVerticalStrut(8));
			}
			return box;
		}
		
		private void listSetup()
		{
			int count = 0;
			int count2 = 0;
			for (int i = 0; i < 6; i++)
				{fc.addItem(frequency[count++]);}
			fc.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        frequencySelection=(String) ((JComboBox) e.getSource()).getSelectedItem();
			        System.out.println(	frequencySelection);
			      }
			    });
			
			for (int i = 0; i <3; i++){
				tc.addItem(typeList[count2++]);
			}
			
			tc.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent e) {
			        typeSelection=(String) ((JComboBox) e.getSource()).getSelectedItem();
			        System.out.println(	typeSelection);
			        if(typeSelection.equals("Income"))
			        	typeMultiplier = 1;
			        else
			        	{typeMultiplier = -1;}
			      }
			});
		}
	}
	
	private String formatMonth(String string){
		return ("00" + string).substring(string.length());
	}

	private class ButtonListener implements ActionListener{
		private String getDate(int day, int month, int year){
			if(day>31){
				day=1;
				month+=1;
			}
			if(month>12){
				month=1;
				year+=1;
			}
			String date=""+day+"/"+formatMonth(Integer.toString(month))+"/"+year;
			System.out.println("date");
			return date;
		}
		
		public void recurringCheck(String selection){
			if(selection.equals("Daily")){
				rootModel.user().addRecurringTransaction(itemName.getText(), getDate(daySelection+1,monthSelection,yearSelection), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);	
				rootModel.user().viewRecurringTransactions();
			}
			else if(selection.equals("Weekly")){
				rootModel.user().addRecurringTransaction(itemName.getText(), getDate(daySelection+7,monthSelection,yearSelection), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);	
				rootModel.user().viewRecurringTransactions();
			}
			else if(selection.equals("Monthly")){
				rootModel.user().addRecurringTransaction(itemName.getText(), getDate(daySelection,monthSelection+1,yearSelection), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);	
				rootModel.user().viewRecurringTransactions();
			}
			else if(selection.equals("Yearly")){
				rootModel.user().addRecurringTransaction(itemName.getText(), getDate(daySelection,monthSelection,yearSelection+1), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);	
				rootModel.user().viewRecurringTransactions();
			}
		}
					
		public void actionPerformed(ActionEvent e){
			if (e.getActionCommand().equals("Save Transaction")){
				if (type.equals("Transaction")){
					if(frequencySelection.equals("Once")){
						rootModel.user().addTransaction(itemName.getText(), getDate(daySelection,monthSelection,yearSelection), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);
						rootModel.user().viewTransactions();
					}
					else{
						recurringCheck(frequencySelection);
					}
				}
				else if(type.equals("Saving Goal")){
						rootModel.user().addSavingsGoal(itemName.getText(), getDate(daySelection,monthSelection,yearSelection), description.getText(), category.getText(), Double.parseDouble(amount.getText())*typeMultiplier, frequencySelection);
				}
			}

            //save changes
            try {
            	rootModel.store();
            }
            catch(Exception e1){}
            closeWindow();
        }
	}
}