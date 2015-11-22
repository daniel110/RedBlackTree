package mavnat.ex1.redblacktree;


/**
 * RBTree
 *
 * An implementation of a Red Black Tree with non-negative, distinct integer
 * keys and values
 * 
 * @authors
 * 			1) dormendil ; Dor Mendil ; 200968873
 * 			2) danielf1 ; Daniel Feldman ; 302575436
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
		public void setParentNode(RBNode parentNode)
		{
			this.parentNode = parentNode;
		}
		////////////////////////////
		
		/**
		 * @param node
		 * Some RBNode that you want his uncle.
		 * @return
		 * Null if the node is root or node parent is root.
		 * Otherwise, return the uncle.
		 * Uncle can still be null even if grandparent is not null!
		 */
		public RBNode getUncle()
		{
			RBNode parent = this.getParent();
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
			} 
			else 
			{
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
		public boolean isUncleRed()
		{
			RBNode uncle = this.getUncle();
			if (uncle != null)
			{
				return uncle.isRed();
			}
			//	False if no uncle was found.
			return false;
		}
		
		
		public boolean isLeftChild()
		{
			if ((this.getParent() != null) && (this.getParent().getLeft() == this))
			{
				return true;
			}
			return false;
		}
		public boolean isRightChild()
		{
			if ((this.getParent() != null) && (this.getParent().getRight() == this))
			{
				return true;
			}
			return false;
		}

	}
	
	/**
	 * SearchKeyInSubTreeResult Class is the object that searchKeyInSubTree returns.
	 * It allows us to use that function in order to find a Node and for insert a new node.
	 */
	public class SearchKeyInSubTreeResult
	{
		
		/**
		 * The Parent member is Node that is the lowest node that should
		 * have the key we looked for, within one of it's children.
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
			} 
			else if ((current_key > key) && (current_node.getLeft() != null))
			{
				current_node = current_node.getLeft();
			} 
			else if ((current_key < key) && (current_node.getRight() != null))
			{
				current_node = current_node.getRight();					
			} 
			else 
			{
				//	The children we need is null (Key not found)
				//	But we still return the parent in case we want to insert a new key.
				return new SearchKeyInSubTreeResult(null, current_node);
			}			
		}
		
		//	We would get here only if sub_tree_root is null.
		return null;
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
	 * 
	 * @error return -2 if something bad happened
	 */
	public int insert(int k, String v) 
	{
		RBNode newNode = new RBNode(k, v); // default color is red
		
		//	Check if the key is already in the tree.
		SearchKeyInSubTreeResult searchResult = this.searchKeyInSubTree(this.rootNode, k);
		
		if (null == searchResult)
		{
			// root is null
			this.rootNode = newNode;
			this.rootNode.setColor(false);
			
			// update the number of nodes in the tree + min and max nodes
			this.nodesCount += 1;
			this.updateMinMax(newNode);
			
			return 0;
		}
		else if (null != searchResult.Result)
		{
			return -1;
		}
		
		// update the number of nodes in the tree + min and max nodes
		this.nodesCount += 1;
		this.updateMinMax(newNode);
				
		//	Here key is not in the tree and we have a parent to put the new node in
		if (searchResult.Parent.getKey() > k)
		{
			searchResult.Parent.setLeftNode(newNode);
		} 
		else 
		{
			searchResult.Parent.setRightNode(newNode);
		}
		// update new node parent
		newNode.setParentNode(searchResult.Parent);
		
		
		// now we will balance the tree (if necessary)
		return insertBalancer(newNode, 0);
		
	}

	public int insertBalancer(RBNode node, int colorSwitchCounter)
	{
		if (node == this.rootNode)
		{
			// if we are here the root must be red
			node.isRed = false;
			return ++colorSwitchCounter;
		}
		
		
		if (node.isRed && node.parentNode.isRed)
		{
			if (node.isUncleRed())
			{
				/// Case 1 
				colorSwitchCounter += 3;
				return insertBalancer(this.insertCase1(node), colorSwitchCounter);
			}
			
			boolean isParentLeftChild =  this.isParentLeftChild(node);
			
			node = this.insertCase2(node, isParentLeftChild);
			if (null == node)
			{
				return -2;
			}
			
			node = this.insertCase3(node, isParentLeftChild);
			if (null == node)
			{
				return -2;
			}
			colorSwitchCounter += 2;
		}
		
		return colorSwitchCounter;
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
		RBNode root = this.getRoot();
		RBNode deleted_node = null;
		
		//	Check if the key is already in the tree.
		SearchKeyInSubTreeResult search_result = this.searchKeyInSubTree(root, k);
		if ((search_result == null) || (search_result.Result == null) || (root == null))
		{
			return -1;
		}
		
		deleted_node = search_result.Result;
		
		
		
		return deleted_node.getKey();
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
	
	private void updateMinMax(RBNode newNode)
	{
		//	Deal with max and min
		if ((this.minNode == null) || (this.minNode.key > newNode.getKey()))
		{
			this.minNode = newNode;
		}
		if ((this.maxNode == null) || (this.maxNode.key < newNode.getKey()))
		{
			this.maxNode = newNode;
		}
	}
	
	////// cases for insertion ////////////
	
	/**
	 * 
	 * @param node
	 * 
	 * @note: the only insertion case that can be looped
	 */
	private RBNode insertCase1(RBNode node)
	{
		RBNode parent = node.getParent();
		
		parent.setColor(false);
		node.getUncle().setColor(false);
		
		RBNode grandpa = parent.getParent();
		grandpa.setColor(true);
		
		return grandpa;
	}
	
	
	private RBNode insertCase2(RBNode node, boolean isParentLeftChild)
	{
		if ((isParentLeftChild && node.isRightChild()))
		{
			return this.leftRotate(node.getParent());
		}
		else if (!isParentLeftChild && node.isLeftChild())
		{
			return this.rightRotate(node.getParent());
		}
		
		return node;
	}
	
	private RBNode insertCase3(RBNode node, boolean isParentLeftChild)
	{
		if (isParentLeftChild)
		{
			node = this.rightRotate(node.getParent().getParent());
		}
		else
		{
			node = this.leftRotate(node.getParent().getParent());
		}
		
		if (node == null)
		{
			return null;
		}
		
		node.setColor(true);
		node.getParent().setColor(false);
		
		return node.getParent();
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
		// check validity
		if (null == node || null == node.getParent() || null == node.getParent().getParent())
		{
			return false;
		}
		
		RBNode parent = node.getParent();
		if (parent.getParent().leftNode == parent)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * @param node
	 * The X node in the Rotation.png diagram!
	 * @return
	 * The same X node. Null if rotate could not be performed.
	 */
	private RBNode leftRotate(RBNode node)
	{
		//save node parent info
		RBNode nodeParent = node.getParent();
		boolean isNodeLeftChild = node.isLeftChild();
		////
				
		RBNode right_child = node.getRight();
		if (right_child == null)
		{
			return null;
		}
		
		RBNode left_child_of_right_child = right_child.getLeft();
		node.setRightNode(left_child_of_right_child);
		if (null != left_child_of_right_child)
		{
			left_child_of_right_child.setParentNode(node);
		}
		
		node.setParentNode(right_child);
		
		right_child.setLeftNode(node);
		
		this.updateParentInfoAfterRotation(nodeParent, right_child, isNodeLeftChild);
		
		return node;
	}
	
	/**
	 * @param node
	 * The Y node in the Rotation.png diagram!
	 * @return
	 * The same Y node. Null if rotate could not be performed.
	 */
	private RBNode rightRotate(RBNode node)
	{
		//save node parent info
		RBNode nodeParent = node.getParent();
		boolean isNodeLeftChild = node.isLeftChild();
		////
		
		RBNode left_child = node.getLeft();
		if (left_child == null)
		{
			return null;
		}
		
		RBNode right_child_of_left_child = left_child.getRight();
		node.setLeftNode(right_child_of_left_child);
		if (null != right_child_of_left_child)
		{
			right_child_of_left_child.setParentNode(node);
		}
		
		node.setParentNode(left_child);
		
		left_child.setRightNode(node);
		
		this.updateParentInfoAfterRotation(nodeParent, left_child, isNodeLeftChild);
		
		return node;
	}
	
	/**
	 * 
	 * @param nodeParent		the parent of the node parameter (== NodeParam) 
	 * 												given in rightRotate or leftRotate (see their doc)
	 * @param newLocalRoot		the new local root node after the rotation
	 * @param isNodeLeftChild   does the NodeParam was left or right child before the rotation
	 */
	private void updateParentInfoAfterRotation(RBNode nodeParent, RBNode newLocalRoot, boolean isNodeLeftChild)
	{
		newLocalRoot.setParentNode(nodeParent);
		
		if (null == nodeParent)
		{
			this.rootNode = newLocalRoot;
			return;
		}
		
		// if null != nodeParent 
		if (isNodeLeftChild)
		{
			nodeParent.setLeftNode(newLocalRoot);
		}
		else
		{
			nodeParent.setRightNode(newLocalRoot);
		}
	}
	
	/////////////////////////////////////
	
	
	
	////// Testing helper functions //////
	
	public int getMaxKey()
	{
		return maxNode.getKey();
	}
	
	public int getMinKey()
	{
		return minNode.getKey();
	}
	//////////////////////////////////////

}
