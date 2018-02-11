package view.tabs;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import model.Cash;
import util.Strings;

/**
 * Class is the superclass of both expense tabs providing both with
 *  the common table they share while reducing code duplication.
 * 
 * @author Morgan Jones
 *
 */
@SuppressWarnings("serial")
public abstract class Tab extends JPanel{
	
	protected JTable table;
	protected final String[] tableColumns = {Strings.TABLE_HEADER_DATE, Strings.TABLE_HEADER_NAME, Strings.TABLE_HEADER_COST, Strings.TABLE_HEADER_CATEGORY, "ID"};
	protected JComboBox<String> categoryDropDown;
	
	
	public Tab(){
		categoryDropDown = new JComboBox<String>();
	}
	
	/**
	 * Initialises the table shared between the two expenses tabs.
	 * 
	 * @param expenses - The expenses that are to be displayed in the
	 *  table on program start up. (All expenses available)
	 */
	public void buildTable(ArrayList<Cash> expenses) {
		//constructs new JTable and makes all cells non editable
		table = new JTable(){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};
		
		//fill the table with content (data)
		loadTableContent(expenses);
		
		//fills the space for the table even if it doesn't have many rows
		table.setFillsViewportHeight(true);
		//allow rows to be selected
		table.setRowSelectionAllowed(true);
		//allow multiple intervals to be selected
		table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(new JScrollPane(table));
	}
	
	/**
	 * 
	 * Reloads the JTable with new expense information to display.
	 * 
	 * @param newExpenses - The list of expenses to now be displayed by the common table.
	 */
	public void loadTableContent(ArrayList<Cash> newExpenses) {
		
		//get all cells of data into a 2D string array
		String[][] data = new String[newExpenses.size()][5];
		int index = 0;
		for(Cash e : newExpenses){
			data[index][0] = e.getDate().toString();
			data[index][1] = e.getName();
			data[index][2] = Cash.currencyFormat(e.getCost());
			data[index][3] = e.getCategory();
			//hidden value used to uniquely Identify expenses
			data[index][4] = e.getId() + "";
			index++;
		}
		
		//set the new data of the table
		table.setModel(new DefaultTableModel(data, tableColumns));
	}
	
	/**
	 * Reload what is in the category choice input drop down
	 * @param newCategories
	 */
	public void loadDropDownContent(ArrayList<String> newCategories){
		//remove previous categories
		categoryDropDown.removeAllItems();
		if(this instanceof ViewTab){
			//add the default option
			categoryDropDown.addItem("No Category");
		}
		//add the new categories
		for(String c : newCategories){
			categoryDropDown.addItem(c);
		}
	}
	
	/**
	 * Clears the input given by the user in this tab
	 */
	public abstract void clearInputFields();
}
