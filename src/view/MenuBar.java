package view;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import appController.FMS;



@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{

	MenuBar(FMS model){
		
		//to align menu on right of menu bar
		this.add(Box.createHorizontalGlue());
		
		JMenu optionMenu = new JMenu("Options");
		
		JMenuItem changeLoad = new JMenuItem("Change Load File");
		changeLoad.setActionCommand("fileChange");
		changeLoad.addActionListener(model);
		optionMenu.add(changeLoad);
		
		JMenuItem save = new JMenuItem("Save Changes");
		save.setActionCommand("saveToFile");
		save.addActionListener(model);
		optionMenu.add(save);
		
		JMenuItem undo = new JMenuItem("Undo Changes");
		undo.setActionCommand("undoChanges");
		undo.addActionListener(model);
		optionMenu.add(undo);
		
		optionMenu.addSeparator();
		
		JMenuItem exit = new JMenuItem("Exit");
		exit.setActionCommand("exit");
		exit.addActionListener(model);
		optionMenu.add(exit);
		
		this.add(optionMenu);
		
		
	}
	
}
