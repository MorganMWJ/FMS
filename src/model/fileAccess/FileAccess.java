package model.fileAccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import util.Dialogs;
import view.GUI;
import model.Date;
import model.Expense;

public class FileAccess {
	
	private GUI gui;
	private final static String EXPENSES_FILE_NAME = "expenseStorage";

	public FileAccess(GUI gui){
		this.gui = gui;
	}
	
	/**
	 * 
	 * Reads the expenses and categories from the text file.
	 * 
	 * @param cateogyList - List for the loaded categories to go into.
	 */
	public ArrayList<Expense> readFromFile(ArrayList<String> cateogyList){
		ArrayList<Expense> expenseList = new ArrayList<Expense>(); 
		try {			
			Scanner inFile = new Scanner(new BufferedReader(new FileReader(EXPENSES_FILE_NAME)));
			while(inFile.hasNextLine()){
				//read in date from the line
				inFile.useDelimiter(":");
				int day = inFile.nextInt();
				int month = inFile.nextInt();
				int year = inFile.nextInt();
				Date date = new Date(day,month,year);
				
				//read in other info from line
				
				String name = inFile.next();
				float cost = inFile.nextFloat();
				String category = inFile.next();
				
				inFile.nextLine();
				
				//create and add new expense into loaded list
				expenseList.add(new Expense(date, name, cost, category));
				
				//add category to list if it is not already
				if(!cateogyList.contains(category)){
					cateogyList.add(category);
				}
			}
			inFile.close();
		
		}catch (FileNotFoundException e) {
			Dialogs.displayErrorMessage(gui, "The file: " + EXPENSES_FILE_NAME + " cannot be found. \nExiting Program.", "ERROR");
			System.exit(0);
		}catch (Exception e){
			Dialogs.displayErrorMessage(gui, "Unknown error has occured when trying to load file\nExiting Program.", "ERROR");
			System.exit(0);
		}
		//sort the expenses in order of date (just in case)
		Collections.sort(expenseList);
		return expenseList;
	}
	
	/**
	 * Writes the expenses (and implicitly therefore the categories) to the text file.
	 * @param expenseList - All expenses to save
	 */
	public void writeToFile(ArrayList<Expense> expenseList) {
		try(FileWriter fw = new FileWriter(EXPENSES_FILE_NAME, false) ; BufferedWriter bw = new BufferedWriter(fw) ; PrintWriter outfile = new PrintWriter(bw, false);){
			for(Expense e : expenseList){
				if(e != expenseList.get(expenseList.size()-1)){
					outfile.println(e.toString());
				}
				else{
					outfile.print(e.toString());
				}
			}
			Dialogs.displayMessage(gui, "Changes have been successfully saved to the file.", "Saved!");
		} 
		catch (IOException e) {
			Dialogs.displayErrorMessage(gui, "Problem when trying to write to file: " + EXPENSES_FILE_NAME, "ERROR");
		}
}


}
