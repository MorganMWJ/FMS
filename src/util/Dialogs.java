package util;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Dialogs {
	
	/*
	 * The Overloads are one for the centre of the screen (JFrame)
	 * And one for the centre of the tab(JComponent)
	 * 
	 */
	
	public static boolean checkSure(JFrame component, String message) {	
		int response = JOptionPane.showConfirmDialog(component, message, "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	
		if (response == JOptionPane.NO_OPTION) {
	    	return false;
	    } 
	    else if (response == JOptionPane.YES_OPTION) {
	        return true;
	    } 
	    else{
	    	return false;
	    }		
	}
	
	public static boolean checkSure(JComponent component, String message) {	
		int response = JOptionPane.showConfirmDialog(component, message, "Confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	
		if (response == JOptionPane.NO_OPTION) {
	    	return false;
	    } 
	    else if (response == JOptionPane.YES_OPTION) {
	        return true;
	    } 
	    else{
	    	return false;
	    }		
	}
	
	public static void displayErrorMessage(JFrame component, String message, String title){
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	public static void displayErrorMessage(JComponent component, String message, String title){
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.ERROR_MESSAGE);
	}

	public static void displayMessage(JFrame component, String message, String title){ 
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void displayMessage(JComponent component, String message, String title){ 
		JOptionPane.showMessageDialog(component, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

}
