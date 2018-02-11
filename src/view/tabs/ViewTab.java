package view.tabs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import util.Dialogs;
import util.Strings;
import appController.FMS;

@SuppressWarnings("serial")
public class ViewTab extends Tab{
	
	private JPanel searchInputPanel;
	private JPanel listActionPanel;
	private JPanel sumActionPanel;
	
	private JTextField monthInput;
	private JTextField lessThanInput;
	private JTextField moreThanInput;
	
	public ViewTab(FMS model){
		super();
		
		monthInput = new JTextField();
		lessThanInput = new JTextField();
		moreThanInput = new JTextField();
		
		searchInputPanel = buildInputPanel(model);
		listActionPanel = buildListActionsPanel(model);
		sumActionPanel = buildSumActionsPanel(model);
		
		this.add(searchInputPanel);
		this.add(listActionPanel);
		this.add(sumActionPanel);
	}
	
	private JPanel buildInputPanel(FMS model) {
		//search panel components
		JPanel searchPanel = new JPanel(new GridLayout(0,4,10,5));
		
		JLabel monthCirteriaLabel= new JLabel(Strings.MONTH_INPUT_TEXT);
		JLabel categoryCriteriaLabel = new JLabel(Strings.CATEGORY_INPUT_TEXT);
		JLabel lowerThanCirteriaLabel = new JLabel(Strings.LESS_THAN_INPUT_TEXT);
		JLabel greaterThanCirteriaLabel = new JLabel(Strings.MORE_THAN_INPUT_TEXT);
	    
		searchPanel.add(monthCirteriaLabel);
		searchPanel.add(monthInput);
		searchPanel.add(categoryCriteriaLabel);
		searchPanel.add(categoryDropDown);
		searchPanel.add(lowerThanCirteriaLabel);
		searchPanel.add(lessThanInput);
		searchPanel.add(greaterThanCirteriaLabel);
		searchPanel.add(moreThanInput);
		
		//search Panel Border
		TitledBorder borderTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Strings.EV_INPUT_AREA_TITLE);
		borderTitle.setTitleJustification(TitledBorder.CENTER);
		searchPanel.setBorder(borderTitle);
		
		return searchPanel;
	}

	private JPanel buildListActionsPanel(FMS model){
		//action panel components
		JPanel listPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		
		JButton searchButton = new JButton(Strings.LIST_SEARCH_RESULT_BUTTON_TEXT);
		searchButton.setActionCommand("criteriaSearch");
		searchButton.addActionListener(model);
		
		JButton listAllButton = new JButton(Strings.LIST_ALL_BUTTON_TEXT);
		listAllButton.setActionCommand("listAll");
		listAllButton.addActionListener(model);
		
		listPanel.add(searchButton);
		listPanel.add(listAllButton);		
		
		//action panel Border
		TitledBorder borderTitle2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Strings.EV_LIST_AREA_TITLE);
		borderTitle2.setTitleJustification(TitledBorder.CENTER);
		listPanel.setBorder(borderTitle2);
		
		return listPanel;
	}
	
	private JPanel buildSumActionsPanel(FMS model){
		JPanel sumPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5)); 
		
		JButton sumDisplayedButton = new JButton(Strings.SUM_DISPLAYED_BUTTON_TEXT);
		sumDisplayedButton.setActionCommand("sumDisplayed");
		sumDisplayedButton.addActionListener(model);
		
		JButton sumSelectedButton = new JButton(Strings.SUM_SELECTED_BUTTON_TEXT);
		sumSelectedButton.setActionCommand("sumSelected");
		sumSelectedButton.addActionListener(model);
		
		sumPanel.add(sumDisplayedButton);
		sumPanel.add(sumSelectedButton);
		
		//sum panel border
		TitledBorder borderTitle2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Strings.EV_SUM_AREA_TITLE);
		borderTitle2.setTitleJustification(TitledBorder.CENTER);
		sumPanel.setBorder(borderTitle2);
		
		return sumPanel;
	}
	
	/**
	 * Gets the input given for the month search criteria
	 * @param gui - the view
	 * @return The month to search for, null if invalid or empty input
	 */
	public Integer getMonthInput() {
		try{
			String textInput = monthInput.getText();
			if(textInput.equals("")){
				return null;//no input given = valid
			}
			Integer input = Integer.parseInt(textInput);
			if(input > 0 && input < 13){
				return input;//input given = valid
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("ERROR: Bad input given for Month");
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_MONTH_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
	}

	/**
	 * Gets the input given for the category search criteria
	 * @param gui - the view
	 * @return The category to search for
	 */
	public String getCategoryInput() {
		String categoryInput = (String) categoryDropDown.getSelectedItem();
		if(categoryInput.equals("No Category")){
			return null;//no input given
		}
		return categoryInput;
	}

	/**
	 * Gets the input given for the less than search criteria
	 * @param gui - the view
	 * @return The amount to search for less than, null if invalid input
	 */
	public Float getLessThanInput() {
		try{
			String textInput = lessThanInput.getText();
			if(textInput.equals("")){
				return null;//no input given = valid
			}
			Float input = Float.parseFloat(textInput);
			if(input > 0.0){
				return input;//input given = valid
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("ERROR: Bad input given for Less Than");
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_LESSTHAN_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
	}

	/**
	 * Gets the input given for the more than search criteria
	 * @param gui - the view
	 * @return The amount to search for more than, null if invalid input
	 */
	public Float getMoreThanInput() {
		try{
			String textInput = moreThanInput.getText();
			if(textInput.equals("")){
				return null;//no input given = valid
			}
			Float input = Float.parseFloat(textInput);
			if(input >= 0.0){
				return input;//input given = valid
			}
			else{
				throw new Exception();
			}
		}
		catch(Exception e){
			System.out.println("ERROR: Bad input given for More Than");
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_MORETHAN_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
	}

	public	float sumSelectedExpenses() {
		Float sum = new Float(0);
		int rows[] = table.getSelectedRows();
		
		//for each row selected
		for(int i = 0; i<rows.length; i++){
			//get the string from the the cost column
			String tempStr = (String) table.getValueAt(rows[i],2);
			//remove the '£' char from the start of the string
			String floatStr = tempStr.substring(1);
			//parse the float and add it to the running sum
			sum += Float.parseFloat(floatStr);
		}
		
		return sum.floatValue();
	}
	
	/**
	 * Clears the input given by the user in this tab
	 */
	@Override
	public void clearInputFields() {
		monthInput.setText("");
		lessThanInput.setText("");
		moreThanInput.setText("");
		categoryDropDown.setSelectedIndex(0);
	}

}
