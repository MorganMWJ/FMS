package view.tabs;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.Date;
import model.Cash;
import util.Dialogs;
import util.Strings;
import appController.FMS;

@SuppressWarnings("serial")
public class EditTab extends Tab{
	
	private JPanel addRemovePanel;
	private JPanel categoryEditPanel;
	
	private JTextField dateInput;
	private JTextField nameInput;
	private JTextField costInput;
	
	private JTextField categoryInput;
	
	public EditTab(FMS model) {
		super();
		
		addRemovePanel = buildAddRemovePanel(model);
		categoryEditPanel = buildCategoryEditPanel(model);
		this.add(addRemovePanel);
		this.add(categoryEditPanel);
		
		dateInput = new JTextField(12);
	    nameInput = new JTextField(12);
	    costInput = new JTextField(12);
	}

	private JPanel buildAddRemovePanel(FMS model) {
		JPanel addRemovePanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		

		JButton addButton = new JButton(Strings.ADD_BUTTON_TEXT);
		addButton.setActionCommand("addExpense");
		addButton.addActionListener(model);
		
		JButton removeButton = new JButton(Strings.REMOVE_BUTTON_TEXT);
		removeButton.setActionCommand("removeExpense");
		removeButton.addActionListener(model);
		
		addRemovePanel.add(addButton);
		addRemovePanel.add(removeButton);		
		
		//action panel Border
		TitledBorder borderTitle2 = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Strings.EE_ADD_REMOVE_AREA_TITLE);
		borderTitle2.setTitleJustification(TitledBorder.CENTER);
		addRemovePanel.setBorder(borderTitle2);
		
		return addRemovePanel;
	}
	
	private JPanel buildCategoryEditPanel(FMS model) {
		JPanel categoryEditPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
		
		JButton addButton = new JButton(Strings.ADD_CATEGORY_BUTTON_TEXT);
		addButton.setActionCommand("addCategory");
		addButton.addActionListener(model);
		
		categoryInput = new JTextField(12);
		
		JButton removeButton = new JButton(Strings.REMOVE_CATEGORY_BUTTON_TEXT);
		removeButton.setActionCommand("removeCategory");
		removeButton.addActionListener(model);
		
		categoryEditPanel.add(addButton);
		categoryEditPanel.add(categoryInput);
		categoryEditPanel.add(removeButton);
		
		TitledBorder borderTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), Strings.EE_CATEGORY_EDIT_AREA_TITLE);
		borderTitle.setTitleJustification(TitledBorder.CENTER);
		categoryEditPanel.setBorder(borderTitle);
		
		return categoryEditPanel;
	}

	/**
	 * 
	 * @return An array containing the IDs of selected expenses in the JTable
	 */
	public int[] getIdsOfSelectedExpenses(){	
		int rows[] = table.getSelectedRows();
		int idsToRemove[] = new int[rows.length];
		
		//for each row selected
		for(int i = 0; i<rows.length; i++){
			//get the id from the 5th hidden column
			idsToRemove[i] = Integer.parseInt((String) table.getValueAt(rows[i], 4));
		}
		
		return idsToRemove;	
	}

	public JPanel buildExpenseAddPanel() {
		JPanel expenseAddPanel = new JPanel();
	      
	    expenseAddPanel.setLayout(new BoxLayout(expenseAddPanel, BoxLayout.Y_AXIS));
	    expenseAddPanel.add(new JLabel(Strings.DATE_INPUT_PROMPT_TEXT, JLabel.CENTER));
	    expenseAddPanel.add(dateInput);
	    expenseAddPanel.add(Box.createVerticalStrut(10));
	    expenseAddPanel.add(new JLabel(Strings.NAME_INPUT_PROMPT_TEXT, JLabel.CENTER));
	    expenseAddPanel.add(nameInput);
	    expenseAddPanel.add(Box.createVerticalStrut(10));
	    expenseAddPanel.add(new JLabel(Strings.COST_INPUT_PROMPT_TEXT, JLabel.CENTER));
	    expenseAddPanel.add(costInput);
	    expenseAddPanel.add(Box.createVerticalStrut(10));
	    expenseAddPanel.add(new JLabel(Strings.CATEGORY_INPUT_PROMPT_TEXT, JLabel.CENTER));
	    expenseAddPanel.add(categoryDropDown);
	    expenseAddPanel.add(Box.createVerticalStrut(10));
	    
	    return expenseAddPanel;
	}
	
	
	public Cash displayAddDialog() {
		JPanel expenseAddPanel = buildExpenseAddPanel();
		Object[] options = {"ADD"};
	  
	  	int result = JOptionPane.showOptionDialog(this, expenseAddPanel,
	  			Strings.ADD_EXPENSE_POPUP_TITLE, JOptionPane.INFORMATION_MESSAGE,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	      
	  	if (result == JOptionPane.OK_OPTION) {
	  		Date date = getDateInput();
	  		String name = getNameInput();
	  		Float cost = getCostInput();
	  		String category = getCategoryInput();
	  		if(date != null && name != null && cost != null && category != null){
		  		return new Cash(date, name , cost, category);
	  		}
	  	}	  
		return null;
	}
	
	private Date getDateInput(){
		try{
			Date date = Date.parseDate(dateInput.getText());
			return date;
		}
		catch(Exception e){
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_DATE_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
		
	}
	
	private String getNameInput(){
		String name = nameInput.getText();
		if(name.equals("")){
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_NAME_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
		}
		return name;
	}
	
	private Float getCostInput(){
		try{
			Float input = Float.parseFloat(costInput.getText());
			if(input < 0.00){
				throw new Exception();
			}
			return input;
		}
		catch(Exception e){
			Dialogs.displayErrorMessage(this, Strings.INCORRECT_COST_POPUP_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
	}
	
	private String getCategoryInput(){
		return (String) categoryDropDown.getSelectedItem();
	}
	
	/**
	 * Clears the input given by the user in this tab
	 */
	@Override
	public void clearInputFields() {
		dateInput.setText("");
		nameInput.setText("");
		costInput.setText("");
		categoryDropDown.setSelectedIndex(0);
		categoryInput.setText("");
	}
	
	public String getCategoryText(){
		String input = categoryInput.getText().toUpperCase();
		if(input.equals("")){
			Dialogs.displayErrorMessage(this, Strings.MISSING_CATEGORY_INPUT_DIALOG_TEXT, Strings.ERROR_POPUP_TITLE);
			return null;
		}
		else{
			return categoryInput.getText();
		}
	}
}

