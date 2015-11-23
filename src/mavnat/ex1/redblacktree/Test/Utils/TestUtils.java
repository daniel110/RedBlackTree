package mavnat.ex1.redblacktree.Test.Utils;

import mavnat.ex1.redblacktree.*;
import mavnat.ex1.redblacktree.Test.Log.*;


public class TestUtils 
{
	public static boolean insertToBothArray(RedBTree<Integer, String> t, RBTree greatT, int nextKey)
	{
		if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
		{
			return false;
		}
		t.insert(nextKey, Integer.toString(nextKey));
		
		return true;
	}
	
	public static boolean RecCheckTree(Node<Integer,String> node, RBTree.RBNode nodeCheck)
	{
		if (node == null)
		{
			if (nodeCheck == null)
			{
				return true;
			}
			
			return false;
		}
		if (nodeCheck == null)
		{
			return false;
		}
		
		if (node.key != nodeCheck.getKey() || 
				!node.value.equals(nodeCheck.getValue()) || 
				node.getColor() != nodeCheck.isRed() ||
				(null != node.parent && null == nodeCheck.getParent()) ||
				(null == node.parent && null != nodeCheck.getParent()) ||
					(!(null == node.parent && null == nodeCheck.getParent()) && 
						node.parent.key != nodeCheck.getParent().getKey()))
		{
			return false;
		}
		
		if (!RecCheckTree(node.left, nodeCheck.getLeft()))
		{
			return false;
		}
		
		return RecCheckTree(node.right, nodeCheck.getRight());
	}
	
	public static boolean CheckTrees(RedBTree<Integer,String> correctTree, RBTree treeToCheck)
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

}
