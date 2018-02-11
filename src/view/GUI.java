package view;

import javax.swing.*;

import util.Strings;
import view.tabs.EditTab;
import view.tabs.OverviewTab;
import view.tabs.ViewTab;
import appController.FMS;

@SuppressWarnings("serial")
public class GUI extends JFrame{
	
	private FMS model;
	
	private JTabbedPane tabs;	
	
	private ViewTab evTab;
	private EditTab eeTab;
	private OverviewTab overTab;
	
	public GUI(FMS model){
		
		super(Strings.PROGRAM_TITLE);
		this.model = model;
		
		//build tabbed pane and add three panels for the tabs
		tabs = new JTabbedPane(JTabbedPane.LEFT);
		tabs.addTab(Strings.EXPENSES_VIEW_TAB_NAME, evTab = new ViewTab(model));
		tabs.addTab(Strings.EXPENSES_EDIT_TAB_NAME, eeTab = new EditTab(model));
		tabs.addTab(Strings.OVERVIEW_TAB_NAME, overTab = new OverviewTab(model));
		
		//add menu bar must be added to frame before tabs
		this.setJMenuBar(new MenuBar(this.model));
		this.add(tabs);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(700,650);
		this.setResizable(false);
		this.setVisible(true);

	}

	public GUI(){
		super(Strings.PROGRAM_TITLE);
	}
	
	public ViewTab getExpenseViewTab() {
		return evTab;
	}
	
	public EditTab getExpenseEditTab() {
		return eeTab;
	}

	public OverviewTab getOverviewTab() {
		return overTab;
	}
		
}
