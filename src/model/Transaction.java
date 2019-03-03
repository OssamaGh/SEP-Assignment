package model;

import java.io.Serializable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Transaction implements Serializable{

	double cost;
	String category, itemName, description, frequency, stringDate;
	Calendar date;
	final static String newline = "\n";
	
	public Transaction(){
		//leave this in, removing it causes errors
	}
	
	public Transaction(String theItem, String theDate, String theDescription, String theCategory, double theAmount, String theFrequency){
		itemName = theItem;
		stringDate = theDate;
		description = theDescription;
		category = theCategory;
		cost = theAmount;
		frequency = theFrequency;
		setDate(stringToCalendar(theDate));
	}
	
	public Transaction(String theItem, Calendar theDate, String theDescription, String theCategory, double theAmount, String theFrequency){
		itemName = theItem;
		description = theDescription;
		category = theCategory;
		cost = theAmount;
		frequency = theFrequency;
		setDate(theDate);
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
	
	public void edit(String itemName, String description, String category, double cost){
		this.cost = cost;
		this.category = category;
		this.itemName = itemName;
		this.description = description;
	}
	
	public double getCost(){
		return cost;
	}
	
	public String getStringDate(){
		return stringDate;
	}
	
	public String getCategory(){
		return category;
	}
	
	public String getFrequency(){
		return frequency;
	}
	
	public String getItemName(){
		return itemName;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public void setDate(Calendar date){
		this.date = date;
	}
	
	public String toString() {
		return  getItemName() + " | " + getDescription() + " | " + getCost() + " | " + getFrequency() + " | " +getStringDate() + newline;
	}
	
	public boolean hasKeyword(String keyWord){
		return (category.toLowerCase()).contains(keyWord.toLowerCase()) || (itemName.toLowerCase()).contains(keyWord.toLowerCase()) || (description.toLowerCase()).contains(keyWord.toLowerCase());
	}
	
	private int todaysDay(Calendar cal){
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	private int todaysMonth(Calendar cal){
		return cal.get(Calendar.MONTH) + 1;
	}
	
	private int todaysYear(Calendar cal){
		return cal.get(Calendar.YEAR);
	}
	
	public String getDateString(){
		return formatMonth(Integer.toString(todaysDay(date))) + "/" + formatMonth(Integer.toString(todaysMonth(date))) + "/" + todaysYear(date);
	}
	
	private String formatMonth(String string){
		return ("00" + string).substring(string.length());
	}
	
	public boolean withinXWeeks(String dateFormat, int x){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			//Date givenDate = sdf.parse(getDateString());
			Date givenDate = sdf.parse(getDateString());
			
			// get current date
			Calendar laterDate = Calendar.getInstance();
			laterDate.add(Calendar.DAY_OF_MONTH, -x*7);
			System.out.println(laterDate.getTime());

			// get earlier date
			Calendar earlierDate = Calendar.getInstance();
			earlierDate.add(Calendar.DAY_OF_MONTH, -(x+1)*7);
			System.out.println(earlierDate.getTime());	
			
			return (givenDate.before(laterDate.getTime()) && givenDate.after(earlierDate.getTime()));
		}
		catch(ParseException e){
			return false;
		}
	}
	
	public boolean withinXMonths(String dateFormat, int x) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			Date givenDate = sdf.parse(getDateString());
			
			// get current date
			Calendar laterDate = Calendar.getInstance();
			laterDate.add(Calendar.MONTH, -x);

			// get earlier date
			Calendar earlierDate = Calendar.getInstance();
			earlierDate.add(Calendar.MONTH, -(x+1));
			
			return (givenDate.before(laterDate.getTime()) && givenDate.after(earlierDate.getTime()));
		}
		catch(ParseException e){
			return false;
		}
	}
	
	public boolean withinXYears(String dateFormat, int x){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setLenient(false);
		try {
			Date givenDate = sdf.parse(getDateString());
			
			// get current date
			Calendar laterDate = Calendar.getInstance();
			laterDate.add(Calendar.YEAR, -x);
			
			// get earlier date
			Calendar earlierDate = Calendar.getInstance();
			earlierDate.add(Calendar.YEAR, -(x+1));
			
			return (givenDate.before(laterDate.getTime()) && givenDate.after(earlierDate.getTime()));
		}
		catch(ParseException e){
			return false;
		}
	}
}