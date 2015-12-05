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
	private static final int INDENT_STEP = 4;

	private RBNode rootNode;
	
	private RBNode minNode;
	private RBNode maxNode;
	
	private int nodesCount;
	
	private int currentOperationSwitchColorCoutner;
	
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
		 * Some RBNode that you want his brother.
		 * @return
		 * Null if the node is root.
		 * Otherwise, return the brother.
		 * brother can still be null even not root.
		 */
		public RBNode getBrother()
		{
			RBNode parent = this.getParent();
			if (parent == null)
			{
				return null;
			}

			//	If we are the left child of his parent, we want his brother.
			if (this == parent.getLeft())
			{
				return parent.getRight();
			} 
			else 
			{
				return parent.getLeft();			
			}
		}
		
		public boolean isLeaf()
		{
			if ((this.rightNode == null) && (this.leftNode == null))
			{
				return true;
			}
			return false;
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
		 * The Result member is the actual Node with the wanted key.
		 * If no node found, this is null and the Parent member
		 * should be the parent of the new key.
		 */
		public RBNode Result;
		/**
		 * The parent of the Result node.
		 * If no node has been found then this is the potential parent
		 * of the key.
		 */
		public RBNode Parent;

		
		public SearchKeyInSubTreeResult(RBNode result, RBNode parent)
		{
			this.Result = result;
			this.Parent = parent;
		}
		public SearchKeyInSubTreeResult(RBNode parent)
		{
			this.Result = null;
			this.Parent = parent;
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
		if ((this.empty() == true) || (this.maxNode == null))
		{
			return null;
		}
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
		if ((this.empty() == true) || (this.maxNode == null))
		{
			return null;
		}
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
			//	Should not be here (Root is null)
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
	 * SearchKeyInSubTreeResult object that contains the result RBNode with its parent.
	 * If no RBNode found then the object will contain only a potential parent.
	 * Null is returned if the root is null and there is no potential
	 * parent OR if sub_tree_root is null.
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
				//	Return the Node that has been found with its parent
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
				//	But we still return the potential parent in case we want to insert a new key.
				return new SearchKeyInSubTreeResult(current_node);
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
		// If so, we want the most left child (of the right child).
		//
		// If it does not have a right child, then the next node is the parent.
		// The parent could be null. In that case, we don't have a next node.
		// BUT if we are the right child of our parent then we need to find the first
		// grand parent that we are the left child of him.
	
		RBNode walker = node;
		
		//	Check right child.
		if (walker.getRight() != null)
		{
			walker = walker.getRight();
			while (walker.getLeft() != null)
			{
				walker = walker.getLeft();
			}
			return walker;
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
	 * @param node
	 * The node to get his predecessor 
	 * @return
	 * Return the predecessor node in the tree.
	 * If no predecessor node then null.
	 */
	public RBNode getPresecessor(RBNode node)
	{
		//	Same logic as successor but right and left switched
		RBNode walker = node;
		
		//	Check right child.
		if (walker.getLeft() != null)
		{
			walker = walker.getLeft();
			while (walker.getRight() != null)
			{
				walker = walker.getRight();
			}
			return walker;
		}
		
		//	Look for the first parent that we are his right child.
		while (walker.getParent() != null)
		{
			if (walker.getParent().getRight() == walker)
			{
				return walker.getParent();
			}	
			walker = walker.getParent();
		}
		
		//	No parent found.
		return null;
	}
	
	/**
	 * @brief setColorAndUpdateCounter - calls node.setColor(isRed) + increase currentOperationSwitchColorCoutner
	 * 			!! IMPORTAMT !!!
	 * 			before using this function this.resetColorSwitchCounter() should be called;
	 * 
	 * @param node		node to upadte it's color
	 * @param isRed		true = if the new color should be red, false = the color should be black
	 * 
	 * @note			the function increase currentOperationSwitchColorCoutner only if the 
	 * 					the new color is different from the current one.
	 */
	private void setColorAndUpdateCounter(RBNode node, boolean isRed)
	{
		if (node != null)
		{
			if(isRed != node.isRed())
			{
				this.currentOperationSwitchColorCoutner +=1;
			}
			node.setColor(isRed);
		}
	}
	
	private void resetColorSwitchCounter()
	{
		this.currentOperationSwitchColorCoutner = 0;
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
		//reset the color switch counter
		this.resetColorSwitchCounter();
		
		RBNode newNode = new RBNode(k, v); // default color is red
		
		//	Check if the key is already in the tree.
		SearchKeyInSubTreeResult searchResult = this.searchKeyInSubTree(this.rootNode, k);
		
		// searchResult == null only if root=null
		if (null == searchResult)
		{
			this.rootNode = newNode;
			this.setColorAndUpdateCounter(this.rootNode, false);
			
			// update the number of nodes in the tree + min and max nodes
			this.nodesCount += 1;
			this.updateMinMax(newNode);
			
			return this.currentOperationSwitchColorCoutner;
		}
		// if the key already exist the searchResult.Result will the relevant RBNode, else null
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
		return insertBalancer(newNode);
	}

	public int insertBalancer(RBNode node)
	{
		if ((node == this.rootNode) || (node.parentNode == null))
		{
			// if we are here the root must be red
			this.setColorAndUpdateCounter(node, false);
			
			return this.currentOperationSwitchColorCoutner;
		}
		
		
		if (node.isRed() && node.parentNode.isRed())
		{
			if (node.isUncleRed() == true)
			{
				/// Case 1 - Move the "red problem" to the grandpa and balance again if necessary
				return insertBalancer(this.insertCase1(node));
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
		}
		
		return this.currentOperationSwitchColorCoutner;
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
		//reset the color switch counter
		this.resetColorSwitchCounter();
		
		RBNode root = this.getRoot();
		RBNode currentNode = null;

		
		//	Check if the key is already in the tree.
		SearchKeyInSubTreeResult search_result = this.searchKeyInSubTree(root, k);
		if ((search_result == null) || (search_result.Result == null) || (root == null))
		{
			return -1;
		}
		
		currentNode = search_result.Result;
		
		return this.deleteNode(currentNode);
	}

	private int deleteNode(RBNode currentNode)
	{
		RBNode SuccessorNode = null;
		RBNode doubleBlack = null;
		boolean isNullNode = false;
		
		//	If the node is a lonely root
		if (this.nodesCount == 1)
		{
			this.rootNode = null;
			this.nodesCount = 0;
			this.maxNode = null;
			this.minNode = null;
			return 0;
		}
		
		if (currentNode.leftNode != null && currentNode.rightNode != null)
		{
			//	We must have successor because we have two children
			SuccessorNode = this.getSuccessor(currentNode);
			if (SuccessorNode == null)
			{
				return -2;
			}
				
			//	Replace key and value between the current and the successor
			//	and delete the successor.
			currentNode.key = SuccessorNode.key;
			currentNode.value = SuccessorNode.value;
			return this.deleteNode(SuccessorNode);
		}
		
		//	Update min and max
		if (currentNode == this.maxNode)
		{
			this.maxNode = this.getPresecessor(currentNode);
		}
		if (currentNode == this.minNode)
		{
			this.minNode = this.getSuccessor(currentNode);
		}		
		
		if (currentNode.leftNode == null && currentNode.rightNode == null)
		{
			isNullNode = true;
			doubleBlack = currentNode;				
		}
		else if (currentNode.leftNode != null)
		{
			doubleBlack = currentNode.leftNode;
			this.replaceNode(currentNode, currentNode.leftNode);
		}
		else if (currentNode.rightNode != null)
		{
			doubleBlack = currentNode.rightNode;
			this.replaceNode(currentNode, currentNode.rightNode);
		}

		//	Update count
		this.nodesCount -= 1;
		
		//	if currentNode is red then we don't change balance.
		if (doubleBlack.isRed == false)
		{
			this.deleteBalancer(doubleBlack);
		}
		else if (isNullNode == false)
		{
			this.setColorAndUpdateCounter(doubleBlack, false);
		}
		
		//	If doubleBlack was a leaf then replace it with null.
		if (isNullNode)
		{
			this.replaceNode(doubleBlack, null);
		}	
		
		return this.currentOperationSwitchColorCoutner;		
	}
	
    public void print() {
        printHelper(this.rootNode, 0);
    }

    private static void printHelper(RBNode n, int indent) {
        if (n == null) {
            System.out.print("<empty tree>");
            return;
        }
        if (n.rightNode != null) {
            printHelper(n.rightNode, indent + INDENT_STEP);
        }
        for (int i = 0; i < indent; i++)
            System.out.print(" ");
        if (n.isRed == false)
            System.out.println(n.key);
        else
            System.out.println("<" + n.key + ">");
        if (n.leftNode != null) {
            printHelper(n.leftNode, indent + INDENT_STEP);
        }
    }
	
	private int deleteNodeOld(RBNode currentNode)
	{
		RBNode SuccessorNode = null;
		RBNode doubleBlack = null;
		RBNode replaceWith = null;
		
		//	If-children: Check if the node is a leaf
		if (currentNode.leftNode == null && currentNode.rightNode == null)
		{
			//	If the node is a lonely root
			if (currentNode.parentNode == null)
			{
				this.rootNode = null;
				this.nodesCount = 0;
				this.maxNode = null;
				this.minNode = null;
				return 0;
			}

			//	If currentNode is red then we finish first thing after the if-children block.
			
			if (currentNode.isRed == false)
			{
				RBNode brother = currentNode.getBrother();
				if (brother == null)
				{
					//	Brother must exist so we have a problem.
					return -2;
				}
				
				if ((currentNode.parentNode.isRed == true) && (brother.isRed == false))
				{
					//	In this case we don't have double-black and we can finish
					this.setColorAndUpdateCounter(brother, true);
					this.setColorAndUpdateCounter(currentNode.parentNode, false);
					this.replaceNode(currentNode, null);
					return this.currentOperationSwitchColorCoutner;
				}
				else if ((currentNode.parentNode.isRed == false) && (brother.isRed == false))
				{
					this.setColorAndUpdateCounter(brother, true);
					doubleBlack = currentNode.parentNode;
					//	replaceWith is already null here
					//	replaceWith = null;
				}
				else
				{
					//	Parent red & brother res is not possible
					//	Parent black and brother red is not possible because me and brother are leafs.
					return -2;
				}

			}
			
		} else if (currentNode.leftNode != null) {
			
			//	We have only left child
			//this.replaceNodeFromParent(currentNode, currentNode.leftNode);
			doubleBlack = currentNode.leftNode;
			replaceWith = currentNode.leftNode;
			this.setColorAndUpdateCounter(replaceWith, false);
			//currentNode.leftNode = null;
			//currentNode.parentNode = null;
			
		} else if (currentNode.rightNode != null) {
			
			//	We have only right child
			//this.replaceNodeFromParent(currentNode, currentNode.rightNode);
			doubleBlack = currentNode.rightNode;
			replaceWith = currentNode.rightNode;
			this.setColorAndUpdateCounter(replaceWith, false);
			//currentNode.rightNode = null;
			//currentNode.parentNode = null;
			
		} else {
			
			SuccessorNode = this.getSuccessor(currentNode);
			
			//	We must have successor because we have two children
			if (SuccessorNode == null)
			{
				return 0;
			}
				
			//	Replace key and value between the current and the successor
			//	and delete the successor.
			currentNode.key = SuccessorNode.key;
			currentNode.value = SuccessorNode.value;
			return this.deleteNode(SuccessorNode);
			
		}

		//	Update count
		this.nodesCount -= 1;
		
		//	update min and max
		//	Root deletion handled above so min and max are diffrent at this point
		if (currentNode == this.maxNode)
		{
			this.maxNode = this.getPresecessor(currentNode);
		}
		if (currentNode == this.minNode)
		{
			this.minNode = this.getSuccessor(currentNode);
		}
		
		//	Replace the nodes
		this.replaceNode(currentNode, replaceWith);
		
		//	if currentNode is red then we don't change balance.
		if (currentNode.isRed == true)
		{
			return this.currentOperationSwitchColorCoutner;
		}
		
		//	At this point double-black should be set
		return this.deleteBalancer(doubleBlack);
		
	}
	
	public int deleteBalancer(RBNode doubleBlack)
	{
		RBNode brother = doubleBlack.getBrother();
		RBNode parent = doubleBlack.getParent();
		
		//	If brother exist then so does parent
		//	Brother must exist because doubleBlack is black
		if (doubleBlack == null || brother == null)
		{
			return this.currentOperationSwitchColorCoutner;
		}
		
		//	root cancels double-black
		if (this.rootNode == doubleBlack)
		{
			return this.currentOperationSwitchColorCoutner;
		}
		
		//	Check case 0.1
		if (doubleBlack.isLeaf() && brother.isLeaf())
		{
			//	Brother must be black so we check parent
			this.setColorAndUpdateCounter(brother, true);
			if (parent.isRed)
			{
				this.setColorAndUpdateCounter(parent, false);
				return this.currentOperationSwitchColorCoutner;
			}
			return this.deleteBalancer(parent);
		}
		
		
		//	Check case 1
		if (parent.isRed == false && brother.isRed == true)
		{
			doubleBlack = this.deleteRotate(doubleBlack, parent, brother);
			this.setColorAndUpdateCounter(parent, true);
			this.setColorAndUpdateCounter(brother, false);
			brother = doubleBlack.getBrother();
			parent = doubleBlack.getParent();
		}

		
		if (brother.isRed == false)
		{
			//	Check case 2
			if ((brother.getLeft().isRed == false) && (brother.getRight().isRed == false))
			{
				this.setColorAndUpdateCounter(brother, true);
				return this.deleteBalancer(parent);
			}
			//	Check case 3
			if ((doubleBlack.isLeftChild() == true) && (brother.getLeft().isRed == true) && (brother.getRight().isRed == false))
			{
				this.setColorAndUpdateCounter(brother, true);
				this.setColorAndUpdateCounter(brother.getLeft(), false);
				this.deleteRotate(brother.getRight(), brother, brother.getLeft());
			}
			else if ((doubleBlack.isRightChild() == true) && (brother.getLeft().isRed == false) && (brother.getRight().isRed == true))
			{
				this.setColorAndUpdateCounter(brother, true);
				this.setColorAndUpdateCounter(brother.getRight(), false);
				this.deleteRotate(brother.getLeft(), brother, brother.getRight());
			}
			
			//	Update in case changed in case 3
			brother = doubleBlack.getBrother();
			parent = doubleBlack.getParent();
			
			//	Check case 4 TODO: check
			if ((doubleBlack.isLeftChild() == true) && (brother.getRight().isRed == true))
			{
				this.setColorAndUpdateCounter(brother, parent.isRed);	// TODO: is it count?
				this.setColorAndUpdateCounter(parent, false);
				this.setColorAndUpdateCounter(brother.getRight(), false);
				this.deleteRotate(doubleBlack, parent, brother);
			}
			else if ((doubleBlack.isRightChild() == true) && (brother.getLeft().isRed == true))
			{
				this.setColorAndUpdateCounter(brother, parent.isRed);	// TODO: is it count?
				this.setColorAndUpdateCounter(parent, false);
				this.setColorAndUpdateCounter(brother.getLeft(), false);
				this.deleteRotate(doubleBlack, parent, brother);
			}
		}
		
		return this.currentOperationSwitchColorCoutner;
	}
	
	/**
	 * Replace the node from his parent. Update his parent with newNode
	 * and update newNode with the new parent.
	 * No color is changed and no children are changed except for the parent-node relationship.
	 * @param node
	 * The node to be replaced with newNode.
	 * Note that node does not changed
	 * @param newNode
	 * Can be null. if parent new child will be null.
	 * @return
	 */
	private void replaceNode(RBNode node, RBNode newNode)
	{
		if (node.parentNode != null)
		{
			if (node.isLeftChild() == true)
			{
				node.parentNode.leftNode = newNode;
			} else {
				node.parentNode.rightNode = newNode;
			}
		} 
		else 
		{
			this.rootNode = newNode;
		}
		if (newNode != null)
		{
			newNode.parentNode = node.parentNode;
		}
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
		
		while ((array_index < this.nodesCount) && (walker != null))
		{
			res[array_index++] = walker.getKey();
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
		
		while ((array_index < this.nodesCount) && (walker != null))
		{
			res[array_index++] = walker.getValue();
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
	 * Move the red up the tree.
	 * node must have a parent, a grandpa and an uncle!!
	 * @param node
	 * @return grandpa node
	 * 
	 * @note: the only insertion case that can be looped
	 */
	private RBNode insertCase1(RBNode node)
	{
		RBNode parent = node.getParent();
		
		this.setColorAndUpdateCounter(parent, false);
		this.setColorAndUpdateCounter(node.getUncle(), false);
		
		RBNode grandpa = parent.getParent();
		this.setColorAndUpdateCounter(grandpa, true);
		
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
		
		this.setColorAndUpdateCounter(node, true);
		this.setColorAndUpdateCounter(node.getParent(), false);
		
		return node.getParent();
	}
	/////////////////////////////////////
	
	////// cases for deletion ///////////

	private RBNode deleteRotate(RBNode node, RBNode parent, RBNode brother)
	{
		
		if (this.rootNode == parent)
		{
			this.rootNode = brother;
		}
		
		replaceNode(parent, brother);
		parent.setParentNode(brother);
		
		if (node.isLeftChild() == true)
		{
			parent.setRightNode(brother.getLeft());
			brother.setLeftNode(parent);
		}
		else if (node.isRightChild() == true)
		{
			parent.setLeftNode(brother.getRight());
			brother.setRightNode(parent);
		}
				
		return node;
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
