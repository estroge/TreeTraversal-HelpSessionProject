/**
 * The <CODE>Tree</CODE> class contains a reference to the root 
 * 	of the tree and has other useful methods such as adding a 
 * 	TreeNode to the Tree, getting a reference to a specific TreeNode,
 * 	and beginning the help session for the user.
 * 
 * @author Erica Troge (erica.troge@stonybrook.edu) 106861428
 */
import java.util.Scanner;

public class Tree {
	private TreeNode root;

	/**
	 * Creates an instance of <code>Tree</code>.
	 * @param - none
	 * @postcondition an instance of Tree
	 */
	public Tree() {
		this.root = null;
	}

	/**
	 * Get the root TreeNode of this Tree
	 * @param - none
	 * @return the root TreeNode of this Tree
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Set the root TreeNode of this Tree
	 * @param root
	 * 	the root TreeNode of this Tree
	 * @postcondition root is changed to the given parameter
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * A method to add a TreeNode to this Tree
	 * @param node
	 * 	the node you wish to be added to this Tree
	 * @param parentLabel
	 * 	the location of the node as a child of this label
	 * @return true if the node was added and false if not
	 */
	public boolean addNode(TreeNode node, String parentLabel) {
		if (this.root == null) {
			this.root = node;
			return true;
		} else {
			TreeNode parentNode = getNodeReference(parentLabel);
			if (parentNode != null) {
				node.setParent(parentNode);
				return parentNode.addChild(node);
			}
		}
		return false;
	}

	/**
	 * A method to add a TreeNode to this Tree
	 * @param label
	 * 	the name of this TreeNode
	 * @param prompt
	 * 	the prompt or answer choices associated with this TreeNode
	 * @param message
	 * 	the question or message associated with this TreeNode
	 * @param parentLabel
	 * 	the location of the node as a child of this label
	 * @return true if the node was added and false if not
	 */
	public boolean addNode(String label, String prompt, String message,
			String parentLabel) {
		TreeNode node = new TreeNode();
		node.setLabel(label);
		node.setMessage(message);
		node.setPrompt(prompt);
		if (this.root == null) {
			this.root = node;
			return true;
		} else {
			TreeNode parentNode = getNodeReference(parentLabel);
			if (parentNode != null) {
				node.setParent(parentNode);
				return parentNode.addChild(node);
			}
		}
		return false;
	}

	/**
	 * A method that gets the reference to the TreeNode with the
	 * 	specified label.
	 * @param label
	 * 	the name of the TreeNode
	 * @return a reference to the TreeNode that has the given label,
	 * 	returns null if not found
	 */
	public TreeNode getNodeReference(String label) {
		if (this.root == null) {
			return null;
		} else {
			return findDecendant(this.root, label);
		}
	}

	/**
	 * A helper method for getNodeReference that traverses this Tree
	 * 	in order to find the decendant that matches the label parameter
	 * @param parent
	 * 	the parent TreeNode or first ancestor of the specified TreeNode
	 * @param label
	 * 	the name of the TreeNode
	 * @return the decendant that matches the label parameter, returns
	 * 	null if not found
	 */
	public TreeNode findDecendant(TreeNode parent, String label) {
		if (parent.getLabel().equals(label)) {
			return parent;
		}
		if (parent.isLeaf()) {
			return null;
		}
		for (int i = 0; i < parent.getChildren().length; i++) {
			if (parent.getChildren()[i] == null) {
				return null;
			}
			TreeNode decendant = findDecendant(parent.getChildren()[i], label);
			if (decendant != null) {
				return decendant;
			}
		}
		return null;
	}

	/**
	 * A method to start the question and answer session
	 * @param - none
	 * @postcondition the question and answer help session is commenced
	 */
	public void beginSession() {
		Scanner input = new Scanner(System.in);
		presentNode(this.root, input);
	}

	/**
	 * A helper method for beginSession that prints each question with
	 * 	it's corressponding answer choices and takes the users input.
	 * 	This method also has one of the extra credits for this homework
	 * 	which was to add a "back button".
	 * @param node
	 * 	the specified TreeNode
	 * @param input
	 * 	a Scanner object to take user input
	 */
	public void presentNode(TreeNode node, Scanner input) {
		System.out.println(node.getMessage());
		node.setFaqCount(node.getFaqCount() + 1); //counter for F.A.Q.
		if (node.isLeaf()) {
			System.out.println("Thank you for using the " +
					"automated help service!");
			return;
		}
		for (int i = 0; i < node.getChildren().length; i++) {
			TreeNode child = node.getChildren()[i];
			if (child == null) {
				break;
			}
			System.out.println((i + 1) + " " + child.getPrompt());
		}
		if (node.getParent() != null) {
			System.out.println("B Back One Level");
		}
		System.out.println("0 Exit Session\nChoice: ");
		String choice = input.next().toLowerCase();

		if (choice.equals("b") && node.getParent() != null) {
			presentNode(node.getParent(), input);
		} else {
			int intChoice = Integer.parseInt(choice);
			if (intChoice == 0) {
				return;
			} else {
				try {
					presentNode(node.getChildren()[intChoice - 1], input);
				} catch (Exception e) {
					System.out.println("Error processing choice");
					return;
				}
			}
		}
	}
}
