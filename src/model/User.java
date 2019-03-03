package model;

import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

public class User implements Serializable {
	double balance, balanceThreshold, spendingThreshold;
	private LinkedList<Transaction> transactions = new LinkedList<Transaction>();
	private LinkedList<Transaction> savingsGoals = new LinkedList<Transaction>();
	private LinkedList<RecurringTransaction> recurringTransactions = new LinkedList<RecurringTransaction>();

	public User() {
		balance = 0;
		balanceThreshold = 0;
		spendingThreshold = 99999999;// sentinel value
	}

	public Boolean balanceBelowThreshold() {
		return balance < balanceThreshold;
	}

	public boolean aboveSpendingThreshold() {
		return income(weeklyExpenses()) > spendingThreshold;
	}

	public void setBalanceThreshold(double threshold) {
		this.balanceThreshold = threshold;
		System.out.println("The balance threshold is now " + this.balanceThreshold);
	}

	public void setSpendingThreshold(double threshold) {
		this.spendingThreshold = threshold;
		System.out.println("The spending threshold is now " + this.spendingThreshold);
	}
	
	public double getBalance(){
		return balance;
	}

	public void addTransaction(String item, String date, String desc, String cat, double cost, String frequency) {
		this.balance += cost;
		transactions.add(new Transaction(item, date, desc, cat, cost, frequency));
	}
	
	public void addTransaction(String item, Calendar date, String desc, String cat, double cost, String frequency) {
		this.balance += cost;
		transactions.add(new Transaction(item, date, desc, cat, cost, frequency));
	}

	public void addRecurringTransaction(String theItem, String theRecurringDate,
			String theDescription, String theCategory, double theAmount, String theFrequency) {
		recurringTransactions.add(new RecurringTransaction(theItem, stringToCalendar(theRecurringDate), theDescription, theCategory, theAmount, theFrequency));
	}
	
	private Calendar stringToCalendar(String date){
		try{
			return dateToCalendar(stringToDate(date));
		}
		catch(Exception e){
			return null;
		}
	}
	
	public void addSavingsGoal(String item, String date, String desc, String cat, double cost, String frequency) {
		savingsGoals.add(new Transaction(item, date, desc, cat, cost, frequency));
	}

	public String viewSavingGoals() {
		String s = "List Of Desired Purchase Item: " + "\n";
		for (Transaction savingGoal : savingsGoals) {
			s += " " + savingGoal.toString();
		}
		System.out.println(s);
		return s;
	}

	public int weeklyProjection(double price) {
		double noOfWeeks = (price - this.balance) / income();
		int weeksRounded = (int) Math.round(noOfWeeks);// roundup weeks
		if(weeksRounded<0)
			return 0;
		return weeksRounded;
	}

	public int getDayofDate(String inputDate) {
		String[] result = inputDate.split("/", 2);
		int day = Integer.parseInt(result[0]);
		return day;
	}
	
	private String calendarToString(Calendar date){
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("dd/MM/yyyy");
        return df.format(date.getTime());
	}

	public boolean thisWeek(int inputDay) {
		Calendar cal = Calendar.getInstance();
		int secondDate = inputDay;
		int day = todaysDay(cal);
		int vDay = day + 7;
		if (vDay > 31)
			vDay = vDay - 31;
		if (inputDay + 7 > 31)
			secondDate = inputDay - 31;
		if (inputDay >= day && secondDate < vDay)
			return true;
		return false;
	}

	public double income() {
		double totalIncome = 0;
		for (RecurringTransaction weeklyTransaction : recurringTransactions) {
			if (thisWeek(getDayofDate(calendarToString(weeklyTransaction.getDate())))) {
				totalIncome += weeklyTransaction.getCost();
			}
		}
		if (totalIncome == 0)//avoid division by 0 is some cases
			totalIncome = 1;
		return totalIncome;
	}

	public Transaction getTransaction(String itemName) {
		for (Transaction transaction : transactions) {
			if (transaction.getItemName().equals(itemName))
				return transaction;
		}
		return null;
	}

	public RecurringTransaction getRecurringTransaction(String itemName) {
		for (RecurringTransaction transaction : recurringTransactions) {
			if (transaction.getItemName().equals(itemName))
				return transaction;
		}
		return null;
	}

	public void remove(Transaction transaction) {
		balance -= transaction.getCost();// if cost > 0 then the income will be
											// deducted and if -ve then balance
											// will be increased
		transactions.remove(transaction);
	}

	public Date stringToDate(String date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setLenient(false);
		return sdf.parse(date);
	}

	public void recurringTransactionsCheck() {
		Calendar cal = Calendar.getInstance();
		
		for (RecurringTransaction transaction : recurringTransactions) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			try {
				while(true){
					Date givenDate = sdf.parse(transaction.getDateString());
					System.out.println("tansaction date: " + givenDate + " current date: " + cal.getTime());
					if (givenDate.before(cal.getTime())){//if the givenDate is before the currentTime
						addTransaction(transaction.getItemName(), transaction.getDate(), transaction.getDescription(), transaction.getCategory(), transaction.getCost(), "Once");

						if (transaction.getFrequency().equals("Daily")) {
							Calendar newDate = dateToCalendar(givenDate);
							newDate.add(Calendar.DAY_OF_MONTH, 1);
							transaction.setDate(newDate);
						} else if (transaction.getFrequency().equals("Weekly")) {
							Calendar newDate = dateToCalendar(givenDate);
							newDate.add(Calendar.DAY_OF_MONTH, 7);
							System.out.println("setting the date to " + newDate.getTime());
							transaction.setDate(newDate);
						} else if (transaction.getFrequency().equals("Monthly")) {
							Calendar newDate = dateToCalendar(givenDate);
							newDate.add(Calendar.MONTH, 1);
							transaction.setDate(newDate);
						} else if (transaction.getFrequency().equals("Yearly")) {
							Calendar newDate = dateToCalendar(givenDate);
							newDate.add(Calendar.YEAR, 1);
							transaction.setDate(newDate);
						}
						System.out.println("set the date to " + transaction.getDate().getTime());

					}
					else{
						System.out.println("no need to update transactions");
						break;
					}
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
	
	public static Calendar dateToCalendar(Date date){ 
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  return cal;
		}

	private String getDate(int day, int month, int year) {
		if (day > 31) {
			day = 1;
			month += 1;
		}
		if (month > 12) {
			month = 1;
			year += 1;
		}
		String date = "" + day + "/" + month + "/" + year;
		// System.out.println("date");
		return date;
	}

	private int todaysDay(Calendar cal) {
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	private int todaysMonth(Calendar cal) {
		int month = cal.get(Calendar.MONTH);
		return month + 1;
	}

	private int todaysYear(Calendar cal) {
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public String viewTransactions() {
		String s = "List Of Transaction Item: " + "\n";
		for (Transaction transaction : transactions) {
			s += " " + transaction.toString();
		}
		System.out.println(s);
		return s;
	}

	public String viewRecurringTransactions() {
		String s = "List Of Transaction Item: " + "\n";
		for (RecurringTransaction transaction : recurringTransactions) {
			s += " " + transaction.toString();
		}
		System.out.println(s);
		return s;
	}

	public String[] getTransactionsStrings() {
		// viewTransactions();
		String[] values = new String[transactions.size()];
		int i = 0;
		for (Transaction transaction : transactions) {
			values[i] = transaction.getItemName();
			i++;
		}
		return values;
	}

	public String[] getSavingsGoalsStrings() {
		String[] values = new String[savingsGoals.size()];
		int i = 0;
		for (Transaction transaction : savingsGoals) {
			values[i] = transaction.getItemName();
			i++;
		}
		return values;
	}

	public String[] getRecurringTransactionsStrings() {
		String[] values = new String[recurringTransactions.size()];
		int i = 0;
		for (Transaction transaction : recurringTransactions) {
			values[i] = transaction.getItemName();
			i++;
		}
		return values;
	}

	private String formatMonth(String string) {
		return ("00" + string).substring(string.length());
	}

	public String weekDates(int timeFrameIndex) {
		Calendar cal = Calendar.getInstance();
		int day = todaysDay(cal);
		int month = todaysMonth(cal);
		int year = todaysYear(cal);

		if (day - 7 * timeFrameIndex < 1) {
			// decrement month
			if (month == 1) {
				month = 12;
				year--;
			} else {
				month--;
			}

			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				day = 31 - (day + 7 * timeFrameIndex);
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				day = 30 - (day + 7 * timeFrameIndex);
			} else {// month == 2 //ignoring leap years
				day = 28 - (day + 7 * timeFrameIndex);
			}
		} else {
			day += -7 * timeFrameIndex;
		}
		String s = "-" + day + "/" + formatMonth(Integer.toString(month)) + "/" + year;

		day = todaysDay(cal);
		month = todaysMonth(cal);
		year = todaysYear(cal);
		if (day - 7 * (timeFrameIndex + 1) < 1) {
			// decrement month
			if (month == 1) {
				month = 12;
				year--;
			} else {
				month--;
			}

			if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
				day = 31 + (day - 7 * (timeFrameIndex + 1));
			} else if (month == 4 || month == 6 || month == 9 || month == 11) {
				day = 30 + (day - 7 * (timeFrameIndex + 1));
			} else {// month == 2 //ignoring leap years
				day = 28 + (day - 7 * (timeFrameIndex + 1));
			}
		} else {
			day += -7 * (timeFrameIndex + 1);
		}

		return day + "/" + formatMonth(Integer.toString(month)) + "/" + year + s;
	}

	public void deleteTransaction(String transactionName) {
		Transaction transaction = getTransaction(transactionName);
		balance -= transaction.getCost();// if cost > 0 then the income will be
											// deducted and if -ve then balance
											// will be increased
		transactions.remove(transaction);
	}

	public String monthDates(int timeFrameIndex) {
		Calendar cal = Calendar.getInstance();
		int day = todaysDay(cal);
		int month = todaysMonth(cal);
		int year = todaysYear(cal);

		if (month - timeFrameIndex < 1) {
			month = 12 - timeFrameIndex;
			year--;
		} else {
			month -= timeFrameIndex;
		}
		String s = "-" + day + "/" + formatMonth(Integer.toString(month)) + "/" + year;

		day = todaysDay(cal);
		month = todaysMonth(cal);
		year = todaysYear(cal);
		if (month - (timeFrameIndex + 1) < 1) {
			month = 12 - timeFrameIndex;
			year--;
		} else {
			month -= (timeFrameIndex + 1);
		}
		return day + "/" + formatMonth(Integer.toString(month)) + "/" + year + s;
	}

	public String yearDates(int timeFrameIndex) {
		Calendar cal = Calendar.getInstance();
		String s = "-" + todaysDay(cal) + "/" + formatMonth(Integer.toString(todaysMonth(cal))) + "/"
				+ (todaysYear(cal) - timeFrameIndex);
		return todaysDay(cal) + "/" + formatMonth(Integer.toString(todaysMonth(cal))) + "/"
				+ (todaysYear(cal) - timeFrameIndex - 1) + s;
	}

	private String currentDateString() {
		Calendar cal = Calendar.getInstance();
		return todaysDay(cal) + "/" + formatMonth(Integer.toString(todaysMonth(cal))) + "/" + todaysYear(cal);
	}

	public LinkedList<Transaction> weeklyTransactions(int timeFrameIndex) {
		LinkedList<Transaction> weeklyTransactions = new LinkedList<Transaction>();
		for (Transaction transaction : transactions) {
			// if transaction is within the timeframe then add it to the list
			if (transaction.withinXWeeks("dd/MM/yyyy", timeFrameIndex)) {
				System.out.println("a trasaction is being added" + transaction.toString());
				weeklyTransactions.add(transaction);
			}
		}
		return weeklyTransactions;
	}

	public LinkedList<Transaction> monthlyTransactions(int timeFrameIndex) {
		LinkedList<Transaction> monthlyTransactions = new LinkedList<Transaction>();
		for (Transaction transaction : transactions) {
			// if transaction is within the timeframe then add it to the list
			if (transaction.withinXMonths("dd/MM/yyyy", timeFrameIndex)) {
				monthlyTransactions.add(transaction);
			}
		}
		return monthlyTransactions;
	}

	public LinkedList<Transaction> yearlyTransactions(int timeFrameIndex) {
		LinkedList<Transaction> yearlyTransactions = new LinkedList<Transaction>();
		for (Transaction transaction : transactions) {
			// if transaction is within the timeframe then add it to the list
			if (transaction.withinXYears("dd/MM/yyyy", timeFrameIndex)) {
				yearlyTransactions.add(transaction);
			}
		}
		return yearlyTransactions;
	}
	
	public LinkedList<Transaction> weeklyExpenses(){
		LinkedList<Transaction> weeklyExpenses = new LinkedList<Transaction>();
		for (Transaction transaction : transactions) {
			// if transaction is within the timeframe and is an expense then add it to the list
			if (transaction.withinXWeeks("dd/MM/yyyy", 0) && transaction.getCost() < 0) {
				weeklyExpenses.add(transaction);
			}
		}
		return weeklyExpenses;
	}

	public double income(LinkedList<Transaction> givenTransactions) {
		double sum = 0;
		for (Transaction transaction : givenTransactions) {
			sum += transaction.getCost();
		}
		return sum;
	}

	public String[] getFilteredTransactions(String keyWord) {
		String[] values = new String[transactions.size()];
		int i = 0;
		for (Transaction transaction : transactions) {
			if (transaction.hasKeyword(keyWord)) {
				values[i] = transaction.getItemName();
				i++;
			}
		}
		System.out.print("returning " + values[0]);
		if (i == 0) {
			return null;
		}
		return values;
	}
}