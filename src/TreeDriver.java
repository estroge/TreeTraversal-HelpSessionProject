/**
 * The <CODE>TreeDriver</CODE> class contains the main method of the
 * 	program and displays the menu options.The main menu options are: 
 * 	load a tree, begin a help session and traverse the tree in preorder.
 * 	My two extra "creative" options are: display F.A.Q. and play background 
 * 	music.
 *  
 * @author Erica Troge (erica.troge@stonybrook.edu) 106861428
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.*;
import sun.audio.*;

public class TreeDriver {
	static Scanner input = new Scanner(System.in);
	private static Tree myTree = null;

	/**
	 * The main method that prompts the user pick a menu option.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Welcome!");

		do {
			printMenu();
		} while (takeMenuOption());
	}

	/**
	 * A method that prints the menu options.
	 * @param - none
	 * @precondition
	 * 	This Tree object has been instantiated.
	 * @postcondition
	 * 	A list of all menu options that can be performed on 
	 * 	the Tree/during this session.
	 **/
	public static void printMenu() {
		System.out.println("");
		System.out.println("L) Load a Tree");
		System.out.println("H) Begin a Help Session");
		System.out.println("T) Traverse the Tree in Preorder");
		System.out.println("F) Display F.A.Q."); // extra credit
		System.out.println("P) Play Background Music"); // extra credit
		System.out.println("Q) Quit");
		System.out.println("\nChoice: ");
	}

	/**
	 * A method that takes the menu option input from the user and
	 * 	then calls the corresponding method associated with it. It
	 * 	is also case insensitive.
	 * @param - none
	 * @postcondition
	 * 	The corresponding method that is associated with each menu
	 * 	option is called.
	 **/
	public static boolean takeMenuOption() {
		char menuOption = input.next().toLowerCase().charAt(0);
		switch (menuOption) {
		case 'l':
			boolean status = loadATree();
			if (status == false) {
				System.exit(0);
			}
			return true;
		case 'h':
			helpSession();
			return true;
		case 't':
			traverseTreePreorder();
			return true;
		case 'f':
			printFAQ();
			return true;
		case 'p':
			backgroundMusic();
			return true;
		case 'q':
			System.out.println("Goodbye!");
			System.exit(0);
		default:
			System.out.println("You have not entered a correct menu "
					+ "option, please try again.");
			return true;
		}
	}

	/**
	 * A method that prompts the user for a file name to build a new tree. 
	 * 	The entire tree will be recreated each time this menu option is chosen
	 * @param - none
	 * @return true if the Tree was created successfully and false if not
	 */
	private static boolean loadATree() {
		System.out.println("Enter the file name: ");
		String fileName = input.next();
		if (fileName.length() > 20) {
			while (!input.hasNext()) {
				System.out.print("ERROR Over 20 characters! " +
						"Enter file name again: ");
				input.next();
			}
		}		
		// re-write file without blank spaces
		FileReader reader;
		try {
			reader = new FileReader(fileName);
			BufferedReader buffReader = new BufferedReader(reader);
			FileWriter fileWriter = new FileWriter("newfile.txt");
			String fileLine;

			while ((fileLine = buffReader.readLine()) != null) {
				fileLine.trim();
				if (!fileLine.equals("")) {
					fileWriter.write(fileLine + "\r\n");
				}
			}
			reader.close();
			fileWriter.close();
		} catch (FileNotFoundException e1) {
			System.out.println("\nIssue: " + e1.getMessage() + "\n");
		} catch (IOException e2) {
			System.out.println("\nIssue: " + e2.getMessage() + "\n");
		}

		Scanner fileScanner = null;
		try {
			myTree = new Tree();
			fileScanner = new Scanner(new File("newfile.txt"));
			TreeNode root = readNode(fileScanner);
			if (root == null) {
				System.out.println("Could not read root element! " +
						"Try to load another tree!");
				return true;
			}
			myTree.addNode(root, null);
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine().trim();
				if (line.length() > 0) {
					String[] parentLine = line.split(" ");
					readNodes(fileScanner, parentLine[0],
							Integer.parseInt(parentLine[1]));
				}
			}
			System.out.println("Tree created successfully");

		} catch (FileNotFoundException e) {
			System.out.println("\nIssue: " + e.getMessage() + "\n");
		} finally {
			if (fileScanner != null) {
				fileScanner.close();
			}
		}
		return true;
	}

	/**
	 * A helper method for loadATree that reads the root TreeNode
	 * 	and determines if it is a viable root node.
	 * @param fileScanner
	 * 	a Scanner object that reads from the given file
	 * @return the root TreeNode or null if it could not be created
	 */
	private static TreeNode readNode(Scanner fileScanner) {
		TreeNode node = new TreeNode();
		int linesRead = 0;

		for (int i = 0; i < 3; i++) {
			if (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine().trim();
				if (line.length() > 0) {
					linesRead++;
					switch (i) {
					case 0: node.setLabel(line);
						break;
					case 1: node.setPrompt(line);
						break;
					case 2: node.setMessage(line);
						break;
					}
				} else {
					i--;
				}
			}
		}
		if (linesRead < 3) {
			return null;
		}
		return node;
	}

	/**
	 * A helper method for loadATree that reads all children TreeNodes
	 * 	of the corressponding parent. Creates the nodes dependant upon
	 * 	how many children the parent TreeNode says they have.
	 * @param fileScanner
	 * 	a Scanner object that reads from the given file
	 * @param parentLabel
	 * 	the location of the TreeNode as a child of this label
	 * @param numChildren
	 * 	the number of children this TreeNode has
	 */
	private static void readNodes(Scanner fileScanner, String parentLabel,
			int numChildren) {
		for (int i = 0; i < numChildren; i++) {
			String label = fileScanner.nextLine().trim();
			String prompt = fileScanner.nextLine().trim();
			String message = fileScanner.nextLine().trim();
			myTree.addNode(label, prompt, message, parentLabel);
		}
	}

	/**
	 * A method that calls beginSession in order to start the help
	 * 	session.
	 * @param - none
	 * @precondition this Tree should not be null
	 */
	private static void helpSession() {
		if (myTree == null) {
			System.out.println("Tree is empty!");
			return;
		}
		System.out.println("Help session starting...");
		myTree.beginSession();
	}

	/**
	 * A method that calls preOrder on this Tree.
	 * @param - none
	 * @precondition this Tree should not be null
	 */
	private static void traverseTreePreorder() {
		if (myTree == null) {
			System.out.println("Tree is empty!");
			return;
		}
		System.out.println("Traversing the tree in preorder: ");
		myTree.getRoot().preOrder();
		System.out.println("Thank you for using our automated help services!");
	}

	/**
	 * My first "creative idea" for the extra credit of this homework. This
	 * 	is a method that plays background music so the user can enjoy a song
	 * 	while going through the help session. You will have to make sure you
	 * 	do not restrict the use of AudioPlayer/AudioStream in your IDE. The
	 * 	song file I attached when submitted my assignment must be placed right
	 * 	outside the src folder.
	 */
	public static void backgroundMusic() {
		String songFile = "umbrella.aiff";
		InputStream in = null;
		try {
			in = new FileInputStream(songFile);
		} catch (FileNotFoundException e) {
			System.out.println("\nIssue: " + e.getMessage() + "\n");
		}
		AudioStream audioStream = null;
		try {
			audioStream = new AudioStream(in);
		} catch (IOException e) {
			System.out.println("\nIssue: " + e.getMessage() + "\n");
		}
		AudioPlayer.player.start(audioStream);
	}

	/**
	 * My second "creative idea" for the extra credit of this homework. This
	 * 	is a method that prints all the frequently asked questions. My 
	 * 	definition of F.A.Q. was the all the TreeNodes that have an 
	 * 	faqCount(user picked them) of the maximum value or maximum - 1 value,
	 * 	not including zero.
	 */
	private static void printFAQ() {
		System.out.println("The F.A.Q. are printed below...");
		TreeNode root = myTree.getRoot();
		myTree.getRoot().printMaxFAQ(root);
		System.out.println("\nThanks for using the F.A.Q. section!");
	}
}
