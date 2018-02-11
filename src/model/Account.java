package model;

public class Account {
	
	private int amount;
	private String type;
	
	public Account(int amount, String type){
		this.amount = amount;
		this.type = type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void expense(int expenseAmount){
		amount -= expenseAmount;
	}
	
	public void income(int incomeAmount){
		amount += incomeAmount;
	}
	
	@Override
	public String toString() {
		return type + ": " + amount;
	}
	
}
