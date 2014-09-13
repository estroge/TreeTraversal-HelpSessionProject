/**
 * The <CODE>TreeNode</CODE> class stores various data about each node
 * 	in the tree. The data fields include the label, message and prompt.
 * 	As well as an array of each nodes children (up to 9 children).
 * 	It also contains two extra data fields for the two extra credits
 * 	(the back choice and my F.A.Q. method).
 * 
 * @author Erica Troge (erica.troge@stonybrook.edu) 106861428
 */
public class TreeNode {
	private String label;
	private String message;
	private String prompt;

	private TreeNode[] children; // array of pointers to children
	private TreeNode parent = null; // for the back choice
	private int faqCount = 0; // for the FAQ extra credit

	/**
	 * Returns an instance of <code>TreeNode</code>.
	 * @param - none
	 */
	public TreeNode() {
	}
	
	/**
	 * Returns an instance of <code>TreeNode</code>.
	 * @param - numOfChildren
	 * 	the number of children this TreeNode has
	 * @return An instance of TreeNode with the specified values
	 */
	public TreeNode(int numOfChildren) {
		this.children = new TreeNode[numOfChildren];
	}

	/**
	 * Get the label of this TreeNode
	 * @param - none
	 * @return the label of this TreeNode
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Set the label of this TreeNode
	 * @param label
	 * 	the label or name of this TreeNode
	 * @postcondition label is changed to the given parameter
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Get the message/question of this TreeNode
	 * @param - none
	 * @return the message/question of this TreeNode
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message/question of this TreeNode
	 * @param message
	 * 	the message or question of this TreeNode
	 * @postcondition message is changed to the given parameter
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the prompt/possible answers of this TreeNode
	 * @param - none
	 * @return the prompt/possible answers of this TreeNode
	 */
	public String getPrompt() {
		return prompt;
	}

	/**
	 * Set the prompt/possible answers of this TreeNode
	 * @param prompt
	 * 	the prompt or possible answers of this TreeNode
	 * @postcondition prompt is changed to the given parameter
	 */
	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	/**
	 * Get the array of children of this TreeNode
	 * @param - none
	 * @return the array of children of this TreeNode
	 */
	public TreeNode[] getChildren() {
		return children;
	}

	/**
	 * Set the array of children of this TreeNode
	 * @param children
	 * 	the array of children (TreeNodes) of this TreeNode
	 * @postcondition children is changed to the given parameter
	 */
	public void setChildren(TreeNode[] children) {
		this.children = children;
	}

	/**
	 * Get the parent TreeNode of this TreeNode
	 * @param - none
	 * @return the parent TreeNode of this TreeNode
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * Set the parent TreeNode of this TreeNode
	 * @param parent
	 * 	the parent TreeNode of this TreeNode
	 * @postcondition parent is changed to the given parameter
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * Get the count of frequently asked questions of this TreeNode
	 * @param - none
	 * @return the count of frequently asked questions of this TreeNode
	 */
	public int getFaqCount() {
		return faqCount;
	}

	/**
	 * Set the count of frequently asked questions of this TreeNode
	 * @param faqCount
	 * 	the count of frequently asked questions of a specifc TreeNode
	 * @postcondition faqCount is changed to the given parameter
	 */
	public void setFaqCount(int faqCount) {
		this.faqCount = faqCount;
	}

	/**
	 * A method to add TreeNodes(children) to this TreeNode's children array
	 * @param child
	 * 	a TreeNode whose parent is this TreeNode
	 * @return true if the child was added, or false if not
	 */
	public boolean addChild(TreeNode child) {
		if (this.children == null) {
			this.children = new TreeNode[9];
		}
		for (int i = 0; i < this.children.length; i++) {
			if (children[i] == null) {
				children[i] = child;
				return true;
			}
		}
		return false;
	}

	/**
	 * A method to determine if this TreeNode is a leaf
	 * @param - none
	 * @return true is this TreeNode is a leaf and false if not
	 */
	public boolean isLeaf() {
		if (children == null) {
			return true;
		}
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * A method that traverses the Tree in preorder and prints out each 
	 * 	TreeNode's label, prompt and message.
	 * @param - none
	 * @postcondition each TreeNode is printed out in preorder traversal
	 */
	public void preOrder() {
		System.out.println(this.getLabel());
		System.out.println(this.getPrompt());
		System.out.println(this.getMessage());
		System.out.println("");
		if (this.isLeaf()) {
			return;
		}
		for (TreeNode node : this.getChildren()) {
			if (node != null)
				node.preOrder();
		}
	}

	/**
	 * A method that goes through the TreeNodes in a Tree and prints 
	 * 	all the TreeNodes that have an faqCount of the maximum value
	 * 	or maximum - 1 value, not including zero.
	 * @param root
	 * 	the root TreeNode of the Tree
	 * @postcondition the most frequently asked questions are printed
	 */
	public void printMaxFAQ(TreeNode root) {
		if ((this.getFaqCount() == root.getFaqCount() || this.getFaqCount() 
				== (root.getFaqCount() - 1)) && (this.getFaqCount() != 0 && !this.isLeaf())) {
			System.out.println(this.getMessage());
		}

		if (this.isLeaf()) {
			return;
		}
		for (TreeNode node : this.getChildren()) {
			if (node != null)
				node.printMaxFAQ(root);
		}
	}
}
