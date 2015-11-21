package mavnat.ex1.redblacktree.Test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import mavnat.ex1.redblacktree.*;

public class TestUtils 
{

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
				node.getColor() != nodeCheck.isRed())
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
	
	
	@Test
	public void test_INSERT() 
	{
		final int MAX_INSERTION = 2000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		java.util.Random gen = new java.util.Random();
       
		int[] insertionList = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionList[i] = nextKey;
			
			t.insert(nextKey, Integer.toString(nextKey)); 
			greatT.insert(nextKey, Integer.toString(nextKey));
			
		
			if (!TestUtils.CheckTrees(t, greatT))
			{
				fail("Non match tree: insertion keys %n " + insertionList.toString());
			}
		}
	}

	
	
	@Test
	public void test_DELETE() 
	{
		final int MAX_INSERTION = 2000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		java.util.Random gen = new java.util.Random();
       
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			t.insert(nextKey, Integer.toString(nextKey)); 
			greatT.insert(nextKey, Integer.toString(nextKey));
			
		}
		
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("Non match tree: insertion keys %n " + insertionArr.toString());
		}
		
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int deleteKey = deletionArr[i];
			
			t.delete(deleteKey);
			greatT.delete(deleteKey);
			
			if (!TestUtils.CheckTrees(t, greatT))
			{
				fail("Non match tree: deletion keys %n " + deletionArr.toString() + " ,index " + deleteKey);
			}
			
		}
		
		
	}
}
