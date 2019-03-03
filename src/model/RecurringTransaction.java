package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecurringTransaction extends Transaction implements Serializable{
	String frequency;
	final static String newline = "\n";
	
	/*public RecurringTransaction(String theItem, String theRecurringDate, String theEntryDate, String theDescription, String theCategory, double theAmount, String theFrequency){
			itemName = theItem;
			description = theDescription;
			category = theCategory;
			cost = theAmount;
			frequency = theFrequency;
			//stringEntryDate = theEntryDate;
			//dueDate = theRecurringDate;
			
			setDate(stringToCalendar(theEntryDate));
	}*/
	
	public RecurringTransaction(String theItem, String date, String theDescription, String theCategory, double theAmount, String theFrequency){
		itemName = theItem;
		description = theDescription;
		category = theCategory;
		cost = theAmount;
		frequency = theFrequency;
		setDate(stringToCalendar(date));
	}
	
	public RecurringTransaction(String theItem, Calendar date, String theDescription, String theCategory, double theAmount, String theFrequency){
		itemName = theItem;
		description = theDescription;
		category = theCategory;
		cost = theAmount;
		frequency = theFrequency;
		setDate(date);
	}
	
	private Calendar stringToCalendar(String date){
		try{
			return dateToCalendar(stringToDate(date));
		}
		catch(Exception e){
			return null;
		}
	}
	
	public Date stringToDate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		return sdf.parse(date);
	}
	
	public static Calendar dateToCalendar(Date date){ 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
	
	public double getCost(){
		return cost;
	}

	public void setCost(double cost){
		this.cost = cost;
	}

	public String getCategory(){
		return category;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getItemName(){
		return itemName;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}
	
	public String getFrequency(){
		return frequency;
	}

	public void setFrequency(String frequency){
		this.frequency = frequency;
	}

	public String toString() {
		return  getItemName() + " | " + getDescription() + " | " + getCost() + " | " + getFrequency() + " | " + " | " + " | " + getDate() + newline;
		//return  getItemName() + " | " + getDescription() + " | " + getCost() + " | " + getFrequency() + " | " + getEntryDate() +" | " + getDueDate() + " | " + getDate() + newline;
	}
}
