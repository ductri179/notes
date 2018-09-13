package com.oscartran.notes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

//    private List<Note> notes;
    private static  BufferedReader stdin;
    private final String filePath = "D:\\garbage\\notes.txt";
    private final String separator = "-/-";
    private List<Note> notes;
    
    public App() {
//        this.notes = new ArrayList<Note>();
        stdin = new BufferedReader(new InputStreamReader(System.in));
    }

    private boolean showAllNotes() throws IOException {
        System.out.println("Notes list:");
        
        File file = new File(filePath);
        Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
        notes = new ArrayList<Note>();
        
        while(scanner.hasNextLine()) {
        	String line = scanner.nextLine();
        	String[] noteStrArr = line.split(separator);
        	notes.add(new Note(noteStrArr[0], noteStrArr[1]));
        }
        scanner.close();
        
        if (notes == null || notes.size() == 0) {
            System.out.println("There is no note out here.");
            return false;
        }

        for (int i = 0; i < notes.size(); i++) {
            System.out.println(i + ". " + notes.get(i).getTitle());
        }
        
        System.out.println("Choose 1 note to view: ");
        String num = stdin.readLine();
        showNote(Integer.parseInt(num));
        
        return true;
    }
    
    private void showNote(int num) {
    	System.out.println("Title: " + notes.get(num).getTitle());
    	System.out.println("Content: " + notes.get(num).getContent());
	}

	private void saveToFile(String title, String content) throws IOException {
    	BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
    	writer.append(title + separator + content + "\n");
    	writer.close();
    }
    
    private void addNewNode() throws IOException {
    	System.out.print("Title: ");
    	String title = stdin.readLine();
    	System.out.println("Content: ");
    	String content = stdin.readLine();
    	saveToFile(title, content);
    }

    private int printMenu() throws NumberFormatException, IOException {
        System.out.println("1. Show all notes");
        System.out.println("2. Add a note");
        System.out.println("3. Exit");
        System.out.println("Please choose one option.");
        String ipt = stdin.readLine();
        return Integer.parseInt(ipt);
    }

    public static void main( String[] args )
    {
        int opt = 0;
        App app = new App();
        try {
            opt = app.printMenu();
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format. Exit.");
            return;
        } catch (IOException e) {
			e.printStackTrace();
		}

        switch (opt) {
            case 1: 
				try {
					app.showAllNotes();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            break;
            case 2:
				try {
					app.addNewNode();
					app.showAllNotes();
				} catch (IOException e) {
					e.printStackTrace();
				}
            break;
        }
        System.out.println("Done.");
        return;
    }
}
