package mavnat.ex1.redblacktree.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import mavnat.ex1.redblacktree.*;

import mavnat.ex1.redblacktree.Test.Log.*;

public class Tests
{
	private boolean insertToBothArray(RedBTree<Integer, String> t, RBTree greatT, int nextKey)
	{
		if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
		{
			return false;
		}
		t.insert(nextKey, Integer.toString(nextKey));
		
		return true;
	}
	
	@Test
	public void test_INSERT_Down_Order()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		this.insertToBothArray(t, greatT, 5);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 5");
		}
		
		this.insertToBothArray(t, greatT, 4);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 4");
		}
		
		this.insertToBothArray(t, greatT, 2);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 2");
		}
	}
	
	@Test
	public void test_INSERT_Error_1()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		int [] badList = new int[] {99660, 32514, 85126, 182106, 170016, 58525, 81075, 2250, 91818, 147032, 76321, 173692, 128576, 115616, 176629, 190345, 135031, 186505, 121263, 198606, 66602, 165096, 182083, 34256, 31322, 86286, 38536, 182473, 165808, 166433, 89289, 196243, 11229, 104600, 122, 51952};
		

		for (int i = 0; i< badList.length; i++)
		{
			int element = badList[i];
			if (51952 == element)
			{
				t.print();
			}
			
			this.insertToBothArray(t, greatT, element);
			if (!TestUtils.CheckTrees(t, greatT))
			{
				fail("Blat!!");
			}
		}
	}
	
	
	@Test
	public void test_INSERT_fuzzer() throws IOException 
	{
		Logger logFile = new Logger("file.log", ",");
		
		final int MAX_INSERTION = 2000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		java.util.Random gen = new java.util.Random();
       
		int[] insertionList = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(MAX_INSERTION * 100);
			insertionList[i] = nextKey;
			
			if (false == this.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}
		
			if (!TestUtils.CheckTrees(t, greatT))
			{
				logFile.write(Arrays.toString(Arrays.copyOf(insertionList, i+1)));
				logFile.close();
				
				fail("Non match tree: insertion keys - see file.log");
			}
		}
	}

	
	//@Test
	public void test_DELETE() throws IOException 
	{
		Logger logFile = new Logger("file_delete_fuzzer.log", ",");
		
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
		
		/// !!! The test starts here !!! ///
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		Logger logger = new Logger("logfile.log", ",");
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int deleteKey = deletionArr[i];
			
			t.delete(deleteKey);
			greatT.delete(deleteKey);
			
			logger.write(TestUtils.getLogInfo(t.lookupNode(deleteKey), greatT));
			
			if (!TestUtils.CheckTrees(t, greatT))
			{
				logFile.write(Arrays.toString(deletionArr));
				logFile.write("Failed on Key " + deleteKey);
				logFile.close();
			}
			
		}		
	}
	
	
	
	
}