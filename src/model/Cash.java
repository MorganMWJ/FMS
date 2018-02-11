package model;

import java.text.DecimalFormat;

public class Cash implements Comparable<Cash>{

	private int id;
	private Date date;
	private String name;
	private float amount;
	private String category;
	
	private static final DecimalFormat currencyFormat = new DecimalFormat("0.00");
	
	private static int next_id = 1000;
	
	public Cash(Date date, String name, float cost, String category){
		this.id = next_id++;
		this.date = date;
		this.name = name;
		this.amount = cost;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getCost() {
		return amount;
	}
	
	public void setCost(float cost) {
		this.amount = cost;
	}
	
	public static String currencyFormat(float cost){
		return "£" + currencyFormat.format(cost);
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int compareTo(Cash otherExpense) {
		return this.date.compareTo(otherExpense.getDate());
	}
	
	//Used only to write expense to file
	@Override
	public String toString() {
		return date.getDay() + ":" + date.getMonth() + ":" + date.getYear() + ":" + name + ":" + amount + ":" + category + ":";
	}
	
}
