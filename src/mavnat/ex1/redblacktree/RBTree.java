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
	 * SearchKeyInSubTreeResult Class is the object that searchKeyInSubTree returns.
	 * It allows us to use that function in order to find a Node and for inset a new node.
	 */
	public class SearchKeyInSubTreeResult
	{
		
		/**
		 * The Parent member is Node that is the lowest node that should
		 * have key looked for, within one of it's children.
		 */
		public RBNode Parent;
		/**
		 * The Result member is the actual Node with the wanted key.
		 * If no node found, the Parent member should be the parent of the new key.
		 */
		public RBNode Result;
		
		public SearchKeyInSubTreeResult(RBNode result, RBNode parent)
		{
			this.Result = result;
			this.Parent = parent;
		}
		public SearchKeyInSubTreeResult()
		{
			this.Result = null;
			this.Parent = null;
		}		
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
		SearchKeyInSubTreeResult res = this.searchKeyInSubTree(this.getRoot(), k);
		if (res == null)
		{
			//	Should not be here
			return null;
		}
		RBNode node = res.Result;
		if (node == null)
		{
			//	Node not found
			return null;
		}
		
		return node.getValue();
	}
	
	
	/**
	 * @param node
	 * Some RBNode that you want his uncle.
	 * @return
	 * Null if the node is root or node parent is root.
	 * Otherwise, return the uncle.
	 * Uncle can still be null even if grandparent is not null!
	 */
	private RBNode getUncle(RBNode node)
	{
		RBNode parent = node.getParent();
		if (parent == null)
		{
			return null;
		}
		RBNode grandParent = parent.getParent();
		if (grandParent == null)
		{
			return null;
		}
		//	If the parent is the left child of his parent, we want his brother.
		if (parent == grandParent.getLeft())
		{
			return grandParent.getRight();
		} else {
			return grandParent.getLeft();			
		}
	}
	
	
	/**
	 * @param node
	 * Some RBNode that you want his uncle.
	 * @return
	 * True if the uncle of this node exist and is red.
	 * False otherwise.
	 */
	private boolean isUncleRed(RBNode node)
	{
		RBNode uncle = this.getUncle(node);
		if (uncle != null)
		{
			return uncle.isRed();
		}
		//	False if no uncle was found.
		return false;
	}
	
	/**
	 * @param sub_tree_root
	 * RBNode that represent a root of a sub-tree in which we want to search
	 * for a specific key.
	 * @param key
	 * The key to look for.
	 * @return
	 * The RBNode that has the given key within the sub-tree.
	 * null if key not found.
	 */
	private SearchKeyInSubTreeResult searchKeyInSubTree(RBNode sub_tree_root, int key)
	{
		RBNode current_node = sub_tree_root;
		int current_key = 0;
		
		while (current_node != null)
		{
			current_key = current_node.getKey();
			
			if (current_key == key)
			{
				//	Return the found Node with it's parent.
				return new SearchKeyInSubTreeResult(current_node, current_node.getParent());				
			} else if ((current_key > key) && (current_node.getLeft() != null))
			{
				current_node = current_node.getLeft();
			} else if ((current_key < key) && (current_node.getRight() != null))
			{
				current_node = current_node.getRight();					
			} else {
				//	The children we need is null (Key not found)
				//	But we still return the parent in case we want to insert a new key.
				return new SearchKeyInSubTreeResult(null, current_node);
			}			
		}
		
		//	We would get here only if sub_tree_root is null.
		return new SearchKeyInSubTreeResult();
	}
	
	/**
	 * @param node
	 * The node to get his successor 
	 * @return
	 * Return the successor node in the tree.
	 * If no successor node then null.
	 */
	public RBNode getSuccessor(RBNode node)
	{
		// First we want to check if this node has a right child,
		// he must be the next node.
		// If it does not then the next node is the parent.
		// The parent could be null. In that case, we don't have a next node.
		// BUT if we are the right child of our parent then we need to find the first
		// grand parent that we are the left child of him.
	
		RBNode walker = node;
		
		//	Check right child.
		if (walker.getRight() != null)
		{
			return walker.getRight();
		}
		
		//	Look for the first parent that we are his left child.
		while (walker.getParent() != null)
		{
			if (walker.getParent().getLeft() == walker)
			{
				return walker.getParent();
			}	
			walker = walker.getParent();
		}
		
		//	No parent found.
		return null;
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
		if (this.nodesCount <= 0)
		{
			return new int[0];
		}
		
		int[] res = new int[this.nodesCount];
		RBNode walker = this.minNode;
		int array_index = 0;
		
		while ((array_index < this.nodesCount) || (walker != null))
		{
			res[array_index] = walker.getKey();
			walker = this.getSuccessor(walker);			
		}
		
		return res;
	}

	/**
	 * public String[] valuesToArray()
	 *
	 * Returns an array which contains all values in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] valuesToArray() 
	{
		if (this.nodesCount <= 0)
		{
			return new String[0];
		}
		
		String[] res = new String[this.nodesCount];
		RBNode walker = this.minNode;
		int array_index = 0;
		
		while ((array_index < this.nodesCount) || (walker != null))
		{
			res[array_index] = walker.getValue();
			walker = this.getSuccessor(walker);			
		}
		
		return res;
	}

	/**
	 * If you wish to implement classes, other than RBTree and RBNode, do it in
	 * this file, not in another file.
	 */
	
	
	////// cases for insertion ////////////
	
	/**
	 * 
	 * @param node
	 * 
	 * @note: the only insertion case that can be looped
	 */
	private RBNode insertCase1(RBNode node)
	{
		return null;
	}
	
	
	private RBNode insertCase2(RBNode node, boolean isParentLeftChild)
	{
		return null;
	}
	
	private void insertCase3(RBNode node, boolean isParentLeftChild)
	{
		
	}
	/////////////////////////////////////
	
	////// cases for deletion ///////////
	private void deleteRedLeaf(RBNode node)
	{
		
	}
	
	private void deleteBlackParent_WithOneRedChild(RBNode node, boolean isLeftChild)
	{
		
	}
	
	private void deleteBlackLeaf_WithBlackParentAndRedSibling(RBNode node, boolean isParentLeftChild)
	{
		
	}
	
	
	
	private void deleteBlackLeaf_WithBlackParentAndBlackSibling(RBNode node)
	{
		
	}
	
	private RBNode deleteDoubleBlack_Case1(RBNode node, boolean isParentLeftChild)
	{
		return null;
	}
	
	/**
	 * 
	 * @param node
	 * @return
	 * 
	 * @note: the only deletion case that can be looped
	 */
	private RBNode deleteDoubleBlack_Case2_ParentBlack(RBNode node)
	{
		return null;
	}
	
	private RBNode deleteDoubleBlack_Case2_ParentRed(RBNode node)
	{
		return null;
	}
	
	private RBNode deleteDoubleBlack_Case3(RBNode node)
	{
		return null;
	}
	
	private RBNode deleteDoubleBlack_Case4(RBNode node)
	{
		return null;
	}
	////////////////////////////////////
	
	
	///////// helper functions /////////
	private boolean isParentLeftChild(RBNode node)
	{
		return true;
	}

	/**
	 * @param node
	 * The X node in the presentations!
	 * @return
	 * The same X node. Null if rotate could not be performed.
	 */
	private RBNode leftRotate(RBNode node)
	{
		RBNode right_child = node.getRight();
		if (right_child == null)
		{
			return null;
		}
		
		//	It could be null, we don't care.
		RBNode left_child_of_right_child = right_child.getLeft();
		
		node.setRightNode(left_child_of_right_child);
		right_child.setLeftNode(node);
		
		//	Done :)
		return node;
	}
	
	/**
	 * @param node
	 * The Y node in the presentations!
	 * @return
	 * The same Y node. Null if rotate could not be performed.
	 */
	private RBNode rightRotate(RBNode node)
	{
		RBNode left_child = node.getLeft();
		if (left_child == null)
		{
			return null;
		}
		
		//	It could be null, we don't care.
		RBNode right_child_of_left_child = left_child.getRight();
		
		node.setLeftNode(right_child_of_left_child);
		left_child.setRightNode(node);
		
		//	Done :)
		return node;
	}
	/////////////////////////////////////

	

}
