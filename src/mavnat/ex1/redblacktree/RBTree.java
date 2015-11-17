package mavnat.ex1.redblacktree;

/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 *
 */
public class RBTree 
{
	private RBNode rootNode;
	
	private RBNode minNode;
	private RBNode maxNode;
	
	private int nodesCount;
	
	
	/**
	 * public class RBNode
	 */
	public class RBNode 
	{
		//basic info
		private int key;
		private String value;
		
		//Tree connected info
		private RBNode leftNode;
		private RBNode rightNode;
		private RBNode parentNode;
		
		// special info for RBTree
		private boolean isRed;
		
		/**
		 * Ctor - create a node with basic parameters
		 * @param key
		 * @param value
		 * @param isRed
		 * 
		 * @note BE AWARE!! there is not option to set the value or the key of the RBNODE
		 */
		public RBNode(int key, String value, boolean isRed)
		{
			this.key = key;
			this.value = value;
			this.isRed = isRed;
			this.leftNode = null;
			this.rightNode = null;
			this.parentNode = null;
		}
		
		public RBNode(int key, String value)
		{
			this(key, value, true);
		}
		
		
		//////// getters //////////
		
		public String getValue()
		{
			return this.value;
		}	
		public int getKey()
		{
			return this.key;
		}
		public RBNode getLeft()
		{
			return this.leftNode;
		}
		public RBNode getRight()
		{
			return this.rightNode;
		}
		public RBNode getParent()
		{
			return this.parentNode;
		}
		public boolean isRed()
		{
			return this.isRed;
		}
		////////////////////////////
		
		///////// setters //////////
		public void setColor(boolean isRed)
		{
			this.isRed = isRed;
		}
		public void setLeftNode(RBNode leftNode)
		{
			this.leftNode = leftNode;
		}
		public void setRightNode(RBNode rightNode)
		{
			this.rightNode = rightNode;
		}
		public void setFatherNode(RBNode parentNode)
		{
			this.parentNode = parentNode;
		}
		////////////////////////////
	}

	/**
	 * public RBNode getRoot()
	 *
	 * returns the root of the red black tree
	 *
	 */
	public RBNode getRoot() 
	{
		return this.rootNode;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() 
	{
		if (0 == this.nodesCount)
		{
			return true;
		}
		
		return false;
	}
	

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() 
	{
		return this.nodesCount; // to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the value of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() 
	{
		return this.minNode.getValue();
	}

	/**
	 * public String max()
	 *
	 * Returns the value of the item with the largest key in the tree, or null
	 * if the tree is empty
	 */
	public String max() 
	{
		return this.maxNode.getValue();
	}
	
	/**
	 * public String search(int k)
	 *
	 * returns the value of an item with key k if it exists in the tree
	 * otherwise, returns null
	 */
	public String search(int k) 
	{
		return "42"; // to be replaced by student code
	}

	/**
	 * public int insert(int k, String v)
	 *
	 * inserts an item with key k and value v to the red black tree. the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were necessary. returns -1 if an item
	 * with key k already exists in the tree.
	 */
	public int insert(int k, String v) 
	{
		return 42; // to be replaced by student code
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of color
	 * switches, or 0 if no color switches were needed. returns -1 if an item
	 * with key k was not found in the tree.
	 */
	public int delete(int k) 
	{
		return 42; // to be replaced by student code
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() 
	{
		int[] arr = new int[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() 
	{
		String[] arr = new String[42]; // to be replaced by student code
		return arr; // to be replaced by student code
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */

}
