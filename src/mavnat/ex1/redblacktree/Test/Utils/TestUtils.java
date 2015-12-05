package mavnat.ex1.redblacktree.Test.Utils;

import mavnat.ex1.redblacktree.*;
import mavnat.ex1.redblacktree.RBTree.RBNode;
import mavnat.ex1.redblacktree.Test.Log.*;


public class TestUtils 
{
	
	public class CheckHeigtResult
	{
		public int height;
		public int blackHeight;
		public enumErrors state;
		public CheckHeigtResult(int b, int h, enumErrors s)
		{
			this.blackHeight = b;
			this.height = h;
			this.state = s;
		}
	}
	
	public enum enumErrors
	{
		OK,
		COLOR_SHOULD_BE_RED,
		COLOR_SHOULD_BE_BLACK,
		COLOR,
		MIN,
		MAX,
		KEY,
		VALUE,
		PARENT_SHOULD_NOT_NULL, 
		PARENT_SHOULD_NULL,
		PARENT_KEY,
		NULL_POINTER,
		HEIGHT_WRONG,
		RED_AFTER_RED,
		PARENT_MISMATCH
		
	}


	private static final int INDENT_STEP = 4;
	
	public static int insertToBothArray(RedBTree<Integer, String> t, RBTree greatT, int nextKey)
	{
		t.insert(nextKey, Integer.toString(nextKey));
		
		return greatT.insert(nextKey, Integer.toString(nextKey));
	}
	
	public static int deleteFromBothArray(RedBTree<Integer, String> t, RBTree greatT, int nextKey)
	{
		t.delete(nextKey);
		
		return greatT.delete(nextKey);
	}
	
	
    public static void printTree(RBTree n) {
        printHelper(n.getRoot(), 0);
    }
	
    public static void printTree(RBNode n) {
        printHelper(n, 0);
    }

    private static void printHelper(RBNode n, int indent) {
        if (n == null) {
            System.out.print("<empty tree>");
            return;
        }
        if (n.getRight() != null) {
            printHelper(n.getRight(), indent + INDENT_STEP);
        }
        for (int i = 0; i < indent; i++)
            System.out.print(" ");
        if (n.isRed() == false)
            System.out.println(n.getKey());
        else
            System.out.println("<" + n.getKey() + ">");
        if (n.getLeft() != null) {
            printHelper(n.getLeft(), indent + INDENT_STEP);
        }
    }
	
	public CheckHeigtResult CheckTreeBlackHeight(RBTree.RBNode nodeCheck, RBTree.RBNode parent)
	{
		int blackHeight = 0;
		int Height = 0;
		CheckHeigtResult res;
		
		if (nodeCheck == null)
		{
			return new CheckHeigtResult(0, 0, enumErrors.OK);
		}
		
		if (nodeCheck.getParent() != parent)
		{
			if (parent == null)
			{
				System.out.println("root rotated and his parent not updated");
			} else {
				System.out.println("parent mismatch: key: " + nodeCheck.getKey() + " parent: " + parent.getKey());
			}
			return new CheckHeigtResult(0, 0, enumErrors.PARENT_MISMATCH);
		}
		
		res = CheckTreeBlackHeight(nodeCheck.getLeft(), nodeCheck);
		if (enumErrors.OK != res.state)
		{
			return res;
		}
		blackHeight = res.blackHeight;
		Height = res.height;
		
		res = CheckTreeBlackHeight(nodeCheck.getRight(), nodeCheck);
		if (enumErrors.OK != res.state)
		{
			return res;
		}
		
		if (res.height < Height)
		{
			res.height = Height;
		}
		
		if (blackHeight != res.blackHeight)
		{
			res.state = enumErrors.HEIGHT_WRONG;
			return res;
		}
		
		res.height ++;
		if (nodeCheck.isRed() == false)
		{
			res.blackHeight += 1;
		}

		if (nodeCheck.getParent() != null)
		{
			if (nodeCheck.getParent().isRed() == true && nodeCheck.isRed() == true)
			{
				res.state = enumErrors.RED_AFTER_RED;
			}
		}
		
		return res;	
	}
	
	public static enumErrors RecCheckTree(Node<Integer,String> node, RBTree.RBNode nodeCheck)
	{
		if ((node == null) && (nodeCheck == null))
		{
			return enumErrors.OK;
		}
		
		if ((node == null) && (nodeCheck != null))
		{			
			return enumErrors.NULL_POINTER;
		}
		else if ((node != null) && (nodeCheck == null))
		{
			return enumErrors.NULL_POINTER;
		}

		if (node.key != nodeCheck.getKey())
		{
			return enumErrors.KEY;
		}
		
		if (!node.value.equals(nodeCheck.getValue()))
		{
			return enumErrors.VALUE;
		}
		
		if (node.getColor() != nodeCheck.isRed())
		{
			//	There is a bug with the color of the root after deleting.
			//	*The bug is in the test tree
			if (node.parent != null)
			{
				return enumErrors.COLOR;
			}			
		}
		
		if (null == node.parent && null != nodeCheck.getParent())
		{
			return enumErrors.PARENT_SHOULD_NOT_NULL;
		}
		
		if (null != node.parent && null == nodeCheck.getParent())
		{
			return enumErrors.PARENT_SHOULD_NULL;
		}
		
		if (!(null == node.parent && null == nodeCheck.getParent()))
		{
			if (node.parent.key != nodeCheck.getParent().getKey())
			{
				return enumErrors.PARENT_KEY;
			}
		}
		
		enumErrors res = RecCheckTree(node.left, nodeCheck.getLeft());
		
		if (res != enumErrors.OK)
		{
			return res;
		}
		
		return RecCheckTree(node.right, nodeCheck.getRight());
	}
	
	public static enumErrors CheckTrees(RedBTree<Integer,String> correctTree, RBTree treeToCheck)
	{
		return TestUtils.RecCheckTree(correctTree.root, treeToCheck.getRoot());
	}
	
	
	public static LogInfo getLogInfo(Node<Integer,String> node, RBTree t)
	{
		LogInfo info = new LogInfo();
		
		info.key = node.key;
		
		info.color = node.color.toString();
		
		if (null == node.parent )
		{
			info.parentColor = "Root";
		}
		else
		{
			info.parentColor = node.parent.color.toString();
		}
		
		int childrenCount = 0;
		if (null != node.left)
		{
			childrenCount++;
		}
		if (null != node.right)
		{
			childrenCount++;
		}
		info.childrenCount = childrenCount;
		
		// I think it's the black tree height
		info.blackHeight = RedBTree.verifyProperty5Helper(node, 0, -1);
		
		info.isMax = (node.key == t.getMaxKey()) ? true : false;
		info.isMin = (node.key == t.getMinKey()) ? true : false;
		
		return info;
	}

	public static int getCorrectRoot(RedBTree<Integer, String> t) 
	{
		return t.root.key;

	}

}
