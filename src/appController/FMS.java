package appController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.SwingUtilities;

import util.Dialogs;
import util.Strings;
import view.GUI;
import view.tabs.EditTab;
import view.tabs.ViewTab;
import model.Expense;
import model.Expenses;
import model.fileAccess.FileAccess;

/**
 * The main class interacts with both the model and view therefore is an action listener
 * holds all the current categories and a list of the expense currently displayed in the GUI JTable
 * 
 * @author Morgan Jones
 *
 */
public class FMS implements ActionListener{
	
	private GUI gui;
	
	private FileAccess fileAccess;
	
	private boolean changesMade;
	
	private Expenses allExpenses;
	
	//private ArrayList<String> categories;
	
	private ArrayList<Expense> displayedExpenses;
	
	public FMS(){
		
		FMS model = this;
		changesMade = false;
		ArrayList<String> tempCategories = new ArrayList<String>();
				
		//All stuff in here is put to run on the EDT because it effects the GUI
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//pass GUI the FMS model
				gui = new GUI(model);
				//set up file access
				fileAccess = new FileAccess(gui);
				
				/*LOADING EXPENSES*/
				//load expenses from file
				displayedExpenses = fileAccess.readFromFile(tempCategories);
				//set the expenses in the model
				allExpenses = new Expenses(displayedExpenses, tempCategories);
				
				//load the expenses into the evTab's JTable
				gui.getExpenseViewTab().buildTable(allExpenses.getCashflow());
				//load the categories into the evTab's JComboBox
				gui.getExpenseViewTab().loadDropDownContent(allExpenses.getCategories());
				//load the expenses into the eeTab's JTable
				gui.getExpenseEditTab().buildTable(allExpenses.getCashflow());
				//load the categories into the eeTab's JComboBox
				gui.getExpenseEditTab().loadDropDownContent(allExpenses.getCategories());				
			}
		});
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent E) {
		
		if(E.getActionCommand().equals("exit")){
			if (changesMade) {
				if (Dialogs.checkSure(gui, Strings.EXIT_WITHOUT_SAVE_CHECK_SURE)) {
					System.exit(0);
				}
			}
			else{
				System.exit(0);
			}
		}
		
		if(E.getActionCommand().equals("saveToFile")){
			if (changesMade) {
				if (Dialogs.checkSure(gui, Strings.SAVE_CHECK_SURE)) {
					fileAccess.writeToFile(allExpenses.getCashflow());
					changesMade = false;
				}
			}
			else{
				//display pop up no changes to save
				Dialogs.displayMessage(gui, Strings.NO_CHANGES_TO_SAVE_POPUP_TEXT, Strings.NO_CHANGES_POPUP_TITLE);
			}
		}
		
		if(E.getActionCommand().equals("undoChanges")){
			if (changesMade) {
				if (Dialogs.checkSure(gui, Strings.UNDO_CHECK_SURE)) {
					//empty out the categories in case any ones have been added since
					allExpenses.emptyCategories();
					//load the expenses last saved in the file
					displayedExpenses = fileAccess.readFromFile(allExpenses.getCategories());
					//set the expenses in the model
					allExpenses.setCashflow(displayedExpenses);
					//there are now no unsaved changes
					changesMade = false;
					//update the expenses the view with the new expenses to be displayed
					updateExpensesDisplayedInViewer(allExpenses.getCashflowToDisplay());
				}
			}
			else{
				//display pop up no changes to undo
				Dialogs.displayMessage(gui, Strings.NO_CHANGES_TO_UNDO_POPUP_TEXT, Strings.NO_CHANGES_POPUP_TITLE);
			}
		}
		
		if(E.getActionCommand().equals("listAll")){
			updateExpensesDisplayedInViewer(allExpenses.getCashflow());
			gui.getExpenseViewTab().clearInputFields();
		}
		
		if(E.getActionCommand().equals("sumDisplayed")){
			Dialogs.displayMessage(gui.getExpenseViewTab(), Strings.SUM_DISPLAYED_POPUP_TEXT + Expense.currencyFormat(sumExpenses(displayedExpenses)), Strings.SUM_POPUP_TITLE);
		}
		
		if(E.getActionCommand().equals("sumSelected")){
			//get sum of selected expenses from JTable
			float selectedSum = gui.getExpenseViewTab().sumSelectedExpenses();
			
			//sum those expenses
			Dialogs.displayMessage(gui.getExpenseViewTab(), Strings.SUM_SELECTED_POPUP_TEXT + Expense.currencyFormat(selectedSum), Strings.SUM_POPUP_TITLE);
		}
		
		if(E.getActionCommand().equals("addExpense")){
			Expense newExpense = gui.getExpenseEditTab().displayAddDialog();
			if(newExpense != null){
				//add the new expense to the list of expenses
				allExpenses.getCashflow().add(newExpense);
				//changes have now been made that are not saved
				changesMade = true;
				//update the expenses the view with the new expenses to be displayed
				updateExpensesDisplayedInViewer(allExpenses.getCashflowToDisplay());
			}
			gui.getExpenseEditTab().clearInputFields();
		}
		
		if(E.getActionCommand().equals("removeExpense")){
			//get expenses to remove from view
			int idsToRemove[] = gui.getExpenseEditTab().getIdsOfSelectedExpenses();
			if(idsToRemove.length == 0){
				Dialogs.displayErrorMessage(gui.getExpenseEditTab(), Strings.NO_ROW_SELECTED_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			}
			else{
				//remove them from the list of stored expenses
				allExpenses.removeExpenses(idsToRemove);
				//changes have now been made that are not saved
				changesMade = true;
				//update the expenses the view with the new expenses to be displayed
				updateExpensesDisplayedInViewer(allExpenses.getCashflowToDisplay());
			}
		}
		
		if(E.getActionCommand().equals("addCategory")){
			//get the text input from eeTab and add to the list of categories
			EditTab ee = gui.getExpenseEditTab();
			String c = ee.getCategoryText();
			if (c != null) {
				//check before action
				if (Dialogs.checkSure(ee, (Strings.ADD_CATEGORY_CHECK_SURE + c))) {
					try {
						allExpenses.addCategory(c);
						//reload the categories displayed in drop downs
						updateCategoriesDisplayedInViewer();
						//changes have now been made
						changesMade = true;
					} catch (Exception e) {
						Dialogs.displayErrorMessage(ee, Strings.CATEGORY_ALREADY_EXISTS_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
					}
				}
				//clear the eetab's inputs
				ee.clearInputFields();
			}	
		}
		
		if(E.getActionCommand().equals("removeCategory")){
			EditTab ee = gui.getExpenseEditTab();
			String c = ee.getCategoryText();
			if(c != null){
				if (Dialogs.checkSure(ee, (Strings.REMOVE_CATEGORY_CHECK_SURE + c))) {
					try {
						//remove from the categories list
						allExpenses.removeCategory(c);
						//remove all expenses with that category
						allExpenses.removeCashInCategory(c);
						//update the view's expenses
						updateExpensesDisplayedInViewer(allExpenses.getCashflowToDisplay());
						//update the view's categories
						updateCategoriesDisplayedInViewer();
						//changes have now been made
						changesMade = true;
						
					} catch (Exception e) {
						Dialogs.displayErrorMessage(ee, Strings.NOT_VALID_CATEGORY_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
					}
				}
				//clear the eetab's inputs
				ee.clearInputFields();
			}
		}
		
		if(E.getActionCommand().equals("criteriaSearch")){
			ViewTab tab = gui.getExpenseViewTab();
			
			//set the new search criteria
			allExpenses.getSearchCriteria().setMonth(tab.getMonthInput());
			allExpenses.getSearchCriteria().setCategory(tab.getCategoryInput());
			allExpenses.getSearchCriteria().setLessThan(tab.getLessThanInput());
			allExpenses.getSearchCriteria().setMoreThan(tab.getMoreThanInput());
			
			//update the expenses the view with the new expenses to be displayed
			updateExpensesDisplayedInViewer(allExpenses.getCashflowToDisplay());
		}
	}

	//small method to save duplicate code
	private void updateExpensesDisplayedInViewer(ArrayList<Expense> e){
		Collections.sort(e);
		displayedExpenses = e;
		gui.getExpenseViewTab().loadTableContent(displayedExpenses);
		gui.getExpenseEditTab().loadTableContent(displayedExpenses);
	}
	
	private void updateCategoriesDisplayedInViewer(){
		gui.getExpenseViewTab().loadDropDownContent(allExpenses.getCategories());
		gui.getExpenseEditTab().loadDropDownContent(allExpenses.getCategories());
	}
	
	/**
	 * Sums the costs of a list of expenses
	 * @param exs - list to sum
	 * @return the sum
	 */
	private float sumExpenses(ArrayList<Expense> exs){
		float sum = 0;
		for(Expense e : exs){
			sum += e.getCost();
		}
		return sum;
	}	

	public static void main(String[] args) {
		new FMS();
	}

}
