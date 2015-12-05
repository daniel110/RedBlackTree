package mavnat.ex1.redblacktree.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import mavnat.ex1.redblacktree.*;

import mavnat.ex1.redblacktree.Test.Log.*;
import mavnat.ex1.redblacktree.Test.Utils.*;


/****
 * !!!! README !!!!
 * In order to use this test file - You must 
 * 
 * A) implement function in both RBTree and RBNode as follow:
 * 	
 * 		RBtree
 * 			1) public RBTree.RBNode getRoot()  	
 * 			2) public int getMaxKey() //Not exist in the given skeleton
 * 			3) public int getMinKey() //Not exist in the given skeleton 									 
 * 		RBNode
 * 			1) public int getKey()
 * 			2) public String getValue()
 *  		3) public boolean isRed()
 *			4) public RBTree.RBNode getParent() //Not exist in the given skeleton
 *
 * B) Add to the project the JUnit library - it is a built in library for automatic testing:
 * 					Click on Project -> Properties, select Java Build Path, Libraries -> Add Library, 
 * 																				then choose JUnit -> next + finish
 * 
 * C) Your RBTree should be inside mavant package, ( == mavnat folder)
 * 								or you may change all the "package mavnat." declerations to your package name 
 * 	  and Test Folder should be next to mavant folder;
 * 
 * 
 * Enjoy and Good Luck!
 *
 *
 *	note - you are more then welcome to add more tests - it is really easy!!
 *			1) create a new function with @Test above it - and it will be running automatically
 *			2) when your result was unexpected just use the fail() function;
 *
 *			Example:
 *	
 *			@Test
 *			public void My_Super_Smart_Test()
 *			{
 *				result = MyTree.Insert({1,2,3,4,5,6});
 *
 *				if (result != expected)
 *				{
 *					fail("the test was failed because .....");
 *				}
 *			}
 *		
 *
 */

public class Tests
{
	/** 
	 * Enable a Test to repeat X times using: @Repeat(times=X) tag above the test
	 *  
	 *  Example:
	 *  @Test
	 *  @Repeat(times=10) // repeat 10 times
	 *  public void My_Super_Smart_Test()
	 *	{
	 *		 ...
	 *	}
	 */
	@Rule
	public RepeatRule repeatRule = new RepeatRule();
	
	private static int Counter = 0;
	private static String getNextCounterVal()
	{
		return Integer.toString(Tests.Counter++);
	}
	private static Logger getUniqeLogger(String testName, boolean addLogFileHeader) throws IOException
	{
		Logger logFile = new Logger(testName + Tests.getNextCounterVal() + ".log" , ",", addLogFileHeader);
		
		return logFile;
	}
	private static Logger getUniqeLogger(String testName) throws IOException
	{
		return Tests.getUniqeLogger(testName, false);
	}
	
	
	/////////////////////////// Min Max Tests /////////////////////////////////////
	@Test
	public void test_min_max_only_root()
	{
		RBTree greatT = new RBTree();
		
		int insertKey = 10;
		greatT.insert(10, Integer.toString(insertKey));
		
		if (! Integer.toString(insertKey).equals(greatT.min()))
		{
			fail ("Your min is not updates when the tree has only root element");
		}

		if (! Integer.toString(insertKey).equals(greatT.max()))
		{
			fail ("Your max is not updates when the tree has only root element");
		}
	
	}
	
	@Test
	@Repeat(times=500)
	public void test_min_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 100;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		Arrays.sort(insertionArr);
		
		String expected = Integer.toString(insertionArr[0]);
		String result = greatT.min();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_min_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,MAX_INSERTION)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	
	@Test
	@Repeat(times=500)
	public void test_max_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 100;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		Arrays.sort(insertionArr);
		
		String expected = Integer.toString(insertionArr[MAX_INSERTION-1]);
		String result = greatT.max();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_max_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,MAX_INSERTION)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	/////////////////////////// End of Min Max Tests ////////////////////////////
	
	
	
	////////////////////////// Array Tests //////////////////////////////////////
	@Test
	@Repeat(times=100)
	public void test_keysToArray() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 50;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		int[] insertionArr = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		Arrays.sort(insertionArr);
		
		int[] expected = insertionArr;
		int[] result = greatT.keysToArray();
		if (!Arrays.equals(result, expected))
		{
			Logger logFile = Tests.getUniqeLogger("test_keysToArray");
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,MAX_INSERTION)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	
	
	@Test
	@Repeat(times=100)
	public void test_valusToArray() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 50;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		int[] insertionArr = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		Arrays.sort(insertionArr);
		
		String[] expected=Arrays.toString(insertionArr).split("[\\[\\]]")[1].split(", "); 
		String[] result = greatT.valuesToArray();
		if (!Arrays.equals(result, expected))
		{
			Logger logFile = Tests.getUniqeLogger("test_valusToArray");
			
			logFile.write("Expected : " + Arrays.toString(Arrays.copyOf(insertionArr,MAX_INSERTION)));
			logFile.write("Got : " + Arrays.toString(result));
			logFile.close();
			
			fail("test_valusToArray failed - see log file");
		}
	}
	////////////////////////// End Of Array Tests ///////////////////////////////
	
	///////////////////////////  Size Tests /////////////////////////////////////
	
	@Test
	@Repeat(times=100)
	public void test_empty() throws IOException 
	{
		RBTree greatT = new RBTree();
		
		if (!greatT.empty())
		{
			fail("Expected empty. tree not empty");
		}
		   
		greatT.insert(1, "1");
		
		if (greatT.empty())
		{
			fail("Expected not empty. tree is empty");
		}
		
		greatT.delete(1);
		
		if (!greatT.empty())
		{
			fail("Expected empty after delete. tree not empty");
		}
		
	}
	
	@Test
	@Repeat(times=100)
	public void test_size() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = gen.nextInt(300);
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		int[] insertionArr = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		int size = greatT.size();
		if (MAX_INSERTION != size)
		{
			fail("Expcted size: " + MAX_INSERTION + ", Got: " + size);
		}
	}
	
	
	///////////////////////////  End of Size Tests /////////////////////////////
	
	
	///////////////////////  Search + getRoot Tests /////////////////////////
	
	@Test
	@Repeat(times=100)
	public void test_search()
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 50;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		int keyNotInTree = gen.nextInt(5000);
		String result = greatT.search(keyNotInTree);
		if (null != result)
		{
			fail("looked for " + keyNotInTree + " but it was found, even though the tree is empty");
		}	
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(5000);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] searchArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		
		final int CHECK_SEARCH_COUNT = 20;
		for (int i=0; i<CHECK_SEARCH_COUNT; i++)
		{
			int serachKey = searchArr[i];
			result = greatT.search(serachKey);
			if (null == result || !result.equals(Integer.toString(serachKey)))
			{
				fail("looked for " + serachKey + " but it was not found in the tree");
			}			
		}
		
		keyNotInTree = 6000;
		result = greatT.search(keyNotInTree);
		if (null != result)
		{
			fail("looked for " + keyNotInTree + " but it was, found even though it was not in the tree");
		}	
		
	}
	
	@Test
	@Repeat(times=100)
	public void test_getRoot()
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 50;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		int keyNotInTree = gen.nextInt(5000);
		String result = greatT.search(keyNotInTree);
		if (null != result)
		{
			fail("looked for " + keyNotInTree + " but it was found, even though the tree is empty");
		}	
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(5000);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		int correctRootKey = TestUtils.getCorrectRoot(t);
		if (greatT.getRoot().getKey() != correctRootKey)
		{
			fail("expected " + correctRootKey + " To be root, Got: " + greatT.getRoot().getKey());
		}	
		
	}
	
	///////////////////// End Of Search + getRoot Tests /////////////////////////
	
	
	////////////////////////// Insertion Tests //////////////////////////////////
	@Test
	public void test_INSERT_Down_Order()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		TestUtils.insertToBothArray(t, greatT, 5);
		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("insert 5");
		}
		
		TestUtils.insertToBothArray(t, greatT, 4);
		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("insert 4");
		}
		
		TestUtils.insertToBothArray(t, greatT, 2);
		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("insert 2");
		}
		
		
	}
	
	@Test
	public void test_INSERT_Arr() throws IOException
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		int [] badList = new int[] {99660, 32514, 85126, 182106, 170016, 58525, 81075, 2250, 91818, 147032, 76321, 173692, 128576, 115616, 176629, 190345, 135031, 186505, 121263, 198606, 66602, 165096, 182083, 34256, 31322, 86286, 38536, 182473, 165808, 166433, 89289, 196243, 11229, 104600, 122, 51952, 34255};
		

		for (int i = 0; i< badList.length; i++)
		{
			int element = badList[i];
			
			TestUtils.insertToBothArray(t, greatT, element);
			if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
			{
				fail("Blat!!");
			}
		}
	}
	
	
	
	@Test
	@Repeat(times=10)
	public void test_INSERT_fuzzer() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int MAX_INSERTION = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
			
       
		int[] insertionList = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(MAX_INSERTION * 100);
			insertionList[i] = nextKey;
			
			int colorCount = TestUtils.insertToBothArray(t, greatT, nextKey);
			if (-1 == colorCount)
			{
				i--;
				continue;
			}
		
			if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
			{
				Logger logFile = Tests.getUniqeLogger("test_INSERT_fuzzer");
				logFile.write(Arrays.toString(Arrays.copyOf(insertionList, i+1)));
				logFile.close();
				
				fail("Non match tree: insertion keys - see file.log");
			}
		}
	}
	////////////////// End Of Insertion Tests ///////////////////////////////////////

	
	////////////////////////// Deletion Tests //////////////////////////////////////
	@Test
	public void test_DELETE_Simple() throws IOException
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		TestUtils.enumErrors res;

			
		TestUtils.insertToBothArray(t, greatT, 7);
		TestUtils.insertToBothArray(t, greatT, 10);
		TestUtils.insertToBothArray(t, greatT, 3);
		
		TestUtils.deleteFromBothArray(t, greatT, 7);
		
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail(res.toString());
		}
		
		TestUtils.deleteFromBothArray(t, greatT, 3);
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail(res.toString());
		}
		
		TestUtils.deleteFromBothArray(t, greatT, 10);
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail(res.toString());
		}

	}

	
	@Test
	@Repeat(times=10)
	public void test_DELETE_fuzzer() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		Logger logFile = Tests.getUniqeLogger("test_DELETE_fuzzer", true);
		
		final int MAX_INSERTION = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(500000);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}
			
		}
		//log insertion list
		logFile.write(Arrays.toString(Arrays.copyOf(insertionArr, MAX_INSERTION)));
		
		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("Non match tree: insertion keys %n " + insertionArr.toString());
		}
		
		/// !!! The test starts here !!! ///
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		TestUtils aaa= new TestUtils();

		for (int i=0; i<MAX_INSERTION; i++)
		{
			int deleteKey = deletionArr[i];
			
			// write basic info on each deleted node (color, parent color, children count, etc..)
			logFile.write(TestUtils.getLogInfo(t.lookupNode(deleteKey), greatT));
			greatT.delete(deleteKey);
			
			TestUtils.CheckHeigtResult res = aaa.CheckTreeBlackHeight(greatT.getRoot(), null);
			if (res.state != TestUtils.enumErrors.OK)
			{
				System.out.println("After state:");
				TestUtils.printTree(greatT);
				logFile.write(Arrays.toString(Arrays.copyOf(deletionArr, i+1)));
				logFile.write("Failed on Key " + deleteKey);
				logFile.write("Failed error " + res.state.toString());
				logFile.close();
				
				fail("test_DELETE_fuzzer, see log file");
			}
			
		}	
		
		logFile.close();
	}

	//////////////////////////End of Deletion Tests //////////////////////////////////
	
}