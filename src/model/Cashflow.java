package model;

import java.util.ArrayList;

/**
 * Class responsible for holding all the expenses and querying them when appropriate
 * @author Morgan Jones
 *
 */
public class Cashflow {

	private ArrayList<Cash> cashflow;
	private ArrayList<String> categories;
	private Criteria crit;
	
	public Cashflow(ArrayList<Cash> cashflow, ArrayList<String> categories) {
		this.cashflow = cashflow;
		this.categories = categories;
		this.crit = new Criteria();
	}
	
	public void setCashflow(ArrayList<Cash> cashflow){
		this.cashflow = cashflow;
	}

	public ArrayList<Cash> getCashflow() {
		return cashflow;
	}
	
	public ArrayList<String> getCategories(){
		return categories;
	}
	
	public Criteria getSearchCriteria(){
		return crit;
	}
	
	/**
	 * Removes an expense by it's given id.
	 * @param id
	 */
	public void removeCash(int id){
		for(Cash c : cashflow){
			if(c.getId() == id){
				cashflow.remove(c);
				return;
			}
		}
	}
	
	/**
	 * Removes all expenses in a given category
	 * If that category is a criteria it unsets it.
	 * @param c
	 */
	public void removeCashInCategory(String category){
		ArrayList<Cash> toRemove = new ArrayList<Cash>();
		for(Cash c: cashflow){
			if(c.getCategory().equals(category)){
				toRemove.add(c);
			}
		}
		
		cashflow.removeAll(toRemove);
		
		//remove also as category criteria
		if(crit.getCategory()!=null && crit.getCategory().equals(category)){
			crit.setCategory(null);
		}
	}
	
	/**
	 * Gets a list of expenses to display in the view
	 *  based on the 4 saved criteria
	 * @return
	 */
	public ArrayList<Cash> getCashflowToDisplay() {
		
		ArrayList<Cash> toDisplay = new ArrayList<Cash>();
		
		switch(getCritieraCount(crit)){
			case 0:
				toDisplay = cashflow;
				break;
			
			case 1:
				if(crit.getProvided(0)){
					for(Cash c : cashflow){
						if (c.getDate().getMonth() == crit.getMonth()) {
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(1)){
					for(Cash c : cashflow){
						if (c.getCategory().equals(crit.getCategory())) {
							toDisplay.add(c);
						}
					}
					
				}
				else if(crit.getProvided(2)){
					for(Cash c : cashflow){
						if(c.getCost() <= crit.getLessThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(3)){
					for(Cash c : cashflow){
						if(c.getCost() >= crit.getMoreThan()){
							toDisplay.add(c);
						}
					}
				}
				break;
				
			case 2:
				if(crit.getProvided(3) && crit.getProvided(1)){
					for(Cash c : cashflow){
						if(crit.getMonth()==c.getDate().getMonth() && crit.getCategory().equals(c.getCategory())){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(0) && crit.getProvided(2)){
					for(Cash c : cashflow){
						if(crit.getMonth()==c.getDate().getMonth() && c.getCost() <= crit.getLessThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(0) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if(crit.getMonth()==c.getDate().getMonth() && c.getCost() >= crit.getMoreThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(1) && crit.getProvided(2)){
					for(Cash c : cashflow){
						if(crit.getCategory().equals(c.getCategory()) && c.getCost() <= crit.getLessThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(1) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if(crit.getCategory().equals(c.getCategory()) && c.getCost() >= crit.getMoreThan()){
							toDisplay.add(c);
						}
					}	
				}
				else if(crit.getProvided(2) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if(c.getCost() >= crit.getMoreThan() || c.getCost() <= crit.getLessThan()){
							toDisplay.add(c);
						}
					}	
				}
				break;		
				
			case 3:
				if(crit.getProvided(0) && crit.getProvided(1) && crit.getProvided(2)){
					for(Cash c : cashflow){
						if(crit.getMonth()==c.getDate().getMonth() && crit.getCategory().equals(c.getCategory()) && c.getCost() <= crit.getLessThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(0) && crit.getProvided(1) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if(crit.getMonth()==c.getDate().getMonth() && crit.getCategory().equals(c.getCategory()) && c.getCost() >= crit.getMoreThan()){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(0) && crit.getProvided(2) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if((crit.getMonth()==c.getDate().getMonth() && c.getCost() <= crit.getLessThan()) || (crit.getMonth()==c.getDate().getMonth() && c.getCost() >= crit.getMoreThan())){
							toDisplay.add(c);
						}
					}
				}
				else if(crit.getProvided(1) && crit.getProvided(2) && crit.getProvided(3)){
					for(Cash c : cashflow){
						if((crit.getCategory().equals(c.getCategory()) && c.getCost() <= crit.getLessThan()) || (crit.getCategory().equals(c.getCategory()) && c.getCost() >= crit.getMoreThan())){
							toDisplay.add(c);
						}
					}
				}
				break;
				
			case 4:
				for(Cash c : cashflow){
					if((crit.getMonth()==c.getDate().getMonth() && crit.getCategory().equals(c.getCategory()) && c.getCost() <= crit.getLessThan()) || (crit.getMonth()==c.getDate().getMonth() && crit.getCategory().equals(c.getCategory()) && c.getCost() >= crit.getMoreThan())){
						toDisplay.add(c);
					}
				}
				break;
		}
		
		return toDisplay;
	}

	/**
	 * Removes all expenses that match the list of IDs provided
	 * @param toRemove - A list of expense IDs to remove
	 * 
	 */
	public void removeExpenses(int[] removeIds) {
		
		ArrayList<Cash> toRemove = new ArrayList<Cash>();
		for(Cash c : cashflow){
			for(int id : removeIds){
				if(c.getId() == id){
					toRemove.add(c);
				}
			}
		}	
		cashflow.removeAll(toRemove);
	}
	
	public void addCategory(String toAdd) throws Exception{
		for(String c : categories){
			if(c.equals(toAdd)){
				throw new Exception();
			}
		}
		categories.add(toAdd);
	}
	
	public void removeCategory(String toRemove) throws Exception{
		for(String c : categories){
			if(c.equals(toRemove)){
				categories.remove(c);
				return;
			}
		}
		throw new Exception();
	}
	
	public void emptyCategories() {
		categories.removeAll(categories);
	}

	private int getCritieraCount(Criteria crit){
		int count = 0;
		for(boolean c : crit.getProvided()){
			if(c){
				count++;
			}
		}
		return count;
	}
}
