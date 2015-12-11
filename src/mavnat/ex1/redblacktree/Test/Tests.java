package mavnat.ex1.redblacktree.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import mavnat.ex1.redblacktree.*;
import mavnat.ex1.redblacktree.RBTree_danielf1_dormendil.RBNode;
import mavnat.ex1.redblacktree.Test.Log.*;
import mavnat.ex1.redblacktree.Test.Utils.*;


/****
 * !!!! README !!!!
 * In order to use this test file - You must complete 3 steps: 
 * 
 * A) implement function in both RBTree and RBNode as follow:
 * 	
 * 		RBtree
 * 			1) public default constructor RBTree()
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
 * 					Click on "Project" -> "Properties", select "Java Build Path", "Libraries" -> "Add Library", 
 * 																				then choose "JUnit" -> next + finish
 * 
 * C) 1) Your RBTree should be inside mavant package, ( == mavnat folder)
 * 								or you may change all the "package mavnat." declerations to your package name 
 * 	  2) Test Folder should be next to mavant folder;
 * 
 * Errors logging:
 * 			When you run this Tests.java file you can see in a window which is called "JUnit" (next to Package Explorer)
 * 				 which tests was passed and where an error occurred. 
 * 			
 * 			Our logging mechanism is different from test to test.
 * 				Some of the tests would just print the error, like :"expected: 10, Got 20"
 * 				But Most of them also write the sequence of events which led to the error.
 * 				Since most of the tests generate random keys - when an error occurs the sequence of 
 * 							insertion is written to a log file - so you can recreate the tree.
 * 				The logging file is created at the project directory - 
 * 							and it's prefix is the Test's Name (== The test function name)
 * 								
 * 
 * 
 * Finally Before running this file:
 * 			1) above each test we wrote a short explanation about how the tests is working.
 * 			Use it when one of the tests fails - so you would understand exactly what the test is doing.
 * 			
 * 			2) In main Test Folder under JUnit_Result_Example folder - We added a picture, which show a 
 * 				typical result for running the tests - JUnit library has it's own Result windows, so if 
 * 				you are not familiar with it - take a look at Test/JUnit_Result_Example/JUnit.jpg
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
 *
 * @authors: Daniel Feldman, Dor Mendil
 */

public class Tests
{	
	/////////////////////////// Min Max Tests /////////////////////////////////////
	
	
	//*** Test the min and max RBTree function when the tree has only root ***//
	@Test
	public void test_min_max_only_root()
	{
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
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
	
	
	
	
	//*** Test RBTree.min(), runs 500 times:
	//					Each time we insert 100 keys to the tree, and then check the min function  ***//
	@Test
	@Repeat(times=500)
	public void test_min_Insert_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 100;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();	   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
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
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,INSERTION_COUNT)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	
	@Test
	@Repeat(times=500)
	public void test_min_Delete_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 100;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();	   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
			{
				i--;
				continue;
			}				
		}
		
		Integer[] logInsertionArr = Arrays.copyOf(insertionArr, INSERTION_COUNT);
		
		Arrays.sort(insertionArr);
		
		String expected = Integer.toString(insertionArr[0]);
		String result = greatT.min();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_min_Delete_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Insert was wrong - stoped, Expected: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
		
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		int stopIndex = INSERTION_COUNT/2;
		for (int i=0; i<stopIndex; i++)
		{
			greatT.delete(deletionArr[i]);
		}
		
		Integer [] deleteLeft = Arrays.copyOfRange(deletionArr, stopIndex, INSERTION_COUNT) ;
		
		Arrays.sort(deleteLeft);
		expected = Integer.toString(deleteLeft[0]);
		result = greatT.min();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_min_Delete_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Delete Part - Expected: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	
	
	//*** Test RBTree.max(). runs 500 times:
	//					Each time we insert 100 keys to the tree, and then check the max function  ***//
	@Test
	@Repeat(times=500)
	public void test_max_Insert_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 100;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothTrees(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		// sort the insertion keys
		Arrays.sort(insertionArr);
		
		String expected = Integer.toString(insertionArr[INSERTION_COUNT-1]);
		String result = greatT.max();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_max_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,INSERTION_COUNT)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	

	@Test
	@Repeat(times=500)
	public void test_max_Delete_Check() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 100;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothTrees(t, greatT, nextKey))
			{
				i--;
				continue;
			}				
		}
		
		Integer[] logInsertionArr = Arrays.copyOf(insertionArr, INSERTION_COUNT);
		// sort the insertion keys
		Arrays.sort(insertionArr);
		
		String expected = Integer.toString(insertionArr[INSERTION_COUNT-1]);
		String result = greatT.max();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_max_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
		

		
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		int stopIndex = INSERTION_COUNT/2;
		for (int i=0; i<stopIndex; i++)
		{
			greatT.delete(deletionArr[i]);
		}
		
		Integer [] deleteLeft = Arrays.copyOfRange(deletionArr, stopIndex, INSERTION_COUNT) ;
		
		Arrays.sort(deleteLeft);
		expected = Integer.toString(deleteLeft[deleteLeft.length-1]);
		result = greatT.max();
		if (!expected.equals(result))
		{
			Logger logFile = Tests.getUniqeLogger("test_min_Delete_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Delete Part - Expected: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
	}
	/////////////////////////// End of Min Max Tests ////////////////////////////
	
	
	
	////////////////////////// Array Tests //////////////////////////////////////
	
	// help function - for test_keysToArray_Delete test
	public int[] toPrimitive(Integer[] IntegerArray) 
	{

		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		return result;
	}
	
	
	//*** Test RBTree.keysToArray(). runs 100 times:
	//					Each time we insert 50 keys to the tree, and then check keysToArray function ***//
	@Test
	@Repeat(times=100)
	public void test_keysToArray_Insert() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 50;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();   
		int[] insertionArr = new int[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
			{
				i--;
				continue;
			}				
		}
		
		// sort the insertion keys
		Arrays.sort(insertionArr);
		
		int[] expected = insertionArr;
		int[] result = greatT.keysToArray();
		if (!Arrays.equals(result, expected))
		{
			Logger logFile = Tests.getUniqeLogger("test_keysToArray");
			
			logFile.write(Arrays.toString(Arrays.copyOf(insertionArr,INSERTION_COUNT)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
		
		
	}
	
	@Test
	@Repeat(times=100)
	public void test_keysToArray_Delete() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 50;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
			{
				i--;
				continue;
			}				
		}
		
		Integer[] logInsertionArr = Arrays.copyOf(insertionArr, INSERTION_COUNT);
		// sort the insertion keys
		Arrays.sort(insertionArr);
		
		int[] expected = this.toPrimitive(insertionArr);
		int[] result = greatT.keysToArray();
		if (!Arrays.equals(result, expected))
		{
			Logger logFile = Tests.getUniqeLogger("test_keysToArray");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Expexted: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
		
		
		
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		int stopIndex = INSERTION_COUNT/2;
		for (int i=0; i<stopIndex; i++)
		{
			greatT.delete(deletionArr[i]);
		}
		
		Integer[] deleteLeft = Arrays.copyOfRange(deletionArr, stopIndex, INSERTION_COUNT) ;
		
		int[] expected2 = this.toPrimitive(deleteLeft);
		Arrays.sort(expected2);
		
		int[] result2 = greatT.keysToArray();
		if (!Arrays.equals(result2, expected2))
		{
			Logger logFile = Tests.getUniqeLogger("test_min_Delete_Check");
			
			logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr,INSERTION_COUNT)));
			String messageLog = "Delete Part - Expected: " + expected + ", Got: " + result;
			logFile.write(messageLog);
			logFile.close();
			
			fail(messageLog);
		}
		
	}
	

	
	//*** Test RBTree.valusToArray(). runs 100 times:
	//					Each time we insert 50 keys to the tree, and then check valusToArray function ***//
	@Test
	@Repeat(times=100)
	public void test_valusToArray() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 50;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		   
		int[] insertionArr = new int[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
			{
				i--;
				continue;
			}				
		}
		
		// sort the insertion keys
		Arrays.sort(insertionArr);
		
		String[] expected=Arrays.toString(insertionArr).split("[\\[\\]]")[1].split(", "); 
		String[] result = greatT.valuesToArray();
		if (!Arrays.equals(result, expected))
		{
			Logger logFile = Tests.getUniqeLogger("test_valusToArray");
			
			logFile.write("Expected : " + Arrays.toString(Arrays.copyOf(insertionArr,INSERTION_COUNT)));
			logFile.write("Got : " + Arrays.toString(result));
			logFile.close();
			
			fail("test_valusToArray failed - see log file");
		}
	}
	////////////////////////// End Of Array Tests ///////////////////////////////
	
	///////////////////////////  Size Tests /////////////////////////////////////
	
	
	//*** Test RBTree.empty(). 
	//			check the function before any insertion, after one insertion, and after delete ***//
	@Test
	public void test_empty() throws IOException 
	{
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
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
			fail("Expected empty tree after delete. tree is not empty");
		}
		
	}
	
	
	//*** Test RBTree.size(), runs 100 time.
	// 				  Each time we: 1) get random number - which represent the number of insertion (INSERTION_COUNT)
	//								2) insert a random INSERTION_COUNT keys
	//								3) check tree size (should be INSERTION_COUNT)
	//								4) delete the last inserted key
	//								5) check tree size again (should be INSERTION_COUNT-1)	***//
	@Test
	@Repeat(times=100)
	public void test_size() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		final int INSERTION_COUNT = gen.nextInt(300) + 1; // +1 to avoid zero	
		
		if ( 0 != greatT.size())
		{
			fail("Before any insretion the size should be zero, Got: " + greatT.size());
		}
		
		   
		int[] insertionArr = new int[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
			{
				i--;
				continue;
			}				
		}
		
		int size = greatT.size();
		if (INSERTION_COUNT != size)
		{
			fail("Expcted size: " + INSERTION_COUNT + ", Got: " + size);
		}

		
		greatT.delete(insertionArr[INSERTION_COUNT-1]);
		
		size = greatT.size();
		if (INSERTION_COUNT-1 != size)
		{
			fail("Expcted size: " + (INSERTION_COUNT-1) + ", Got: " + size);
		}
	}
	
	
	///////////////////////////  End of Size Tests /////////////////////////////
	
	
	///////////////////////  Search + getRoot Tests /////////////////////////
	
	//*** Test RBTree.search(), runs 100 time.
	// 				  Each time we: 1) get random key number to check the the search function return null 
	//																	(the tree is empty)
	//								2) insert a random 50 keys
	//								3) check that 20 of those keys(random choosing) are indeed in the tree
	//								5) check again that a key which is not in the tree	***//
	@Test
	@Repeat(times=100)
	public void test_search()
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 50;
		
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		int keyNotInTree = gen.nextInt(5000);
		String result = greatT.search(keyNotInTree);
		if (null != result)
		{
			fail("looked for " + keyNotInTree + " but it was found, even though the tree is empty");
		}	
		   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(5000);
			insertionArr[i] = nextKey;
			
			if (-1 == greatT.insert(nextKey, Integer.toString(nextKey)))
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
	
	
	
	//*** Test RBTree.getRoot(), runs 100 time.
	// 				  Each time we  1) check that when the tree is empty getRoot() == null
	//								2) insert 50 random keys and then check getRoot()  ***// 
	@Test
	@Repeat(times=100)
	public void test_getRoot()
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 50;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		RBNode result = greatT.getRoot();
		if (null != result)
		{
			fail("When the tree is empty getRoot should return null, instead Got key of: " + result.getKey());
		}	
		   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(5000);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothTrees(t, greatT, nextKey))
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
	
	//*** Test RBTree.insert(k,v). simple tests - just insert 3 keys  ***// 
	@Test
	public void test_INSERT_Down_Order()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		TestUtils.insertToBothTrees(t, greatT, 5);
		
		TestUtils.enumErrors res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("insert 5, error: " + res.toString());
		}
		
		TestUtils.insertToBothTrees(t, greatT, 4);
		
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("insert 4, error: " + res.toString());
		}
		
		TestUtils.insertToBothTrees(t, greatT, 2);
		
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("insert 2, error: " + res.toString());
		}
		
		
	}
	
	
	//*** Test RBTree.insert(k,v). Very useful test case when an error occur in the test_INSERT_fuzzer Test.
	//	   Here you can edit the badList array to your own "bad list" - and the recreate the problematic tree 
	//		
	//		In general, the test insert the given keys one by one,
	//					and check after each insertion that the tree is correct. 
	//					
	//					At the end of the test their is commented line of "TestUtils.printTree(greatT);"
	//					This line prints the tree (from left to right - the root is the leftist number) 
	//					Red nodes or surrounded by <>.					
	//					You can use it for debug (in this test or any other test)		***//
	@Test
	public void test_INSERT_Arr() throws IOException
	{
		// create the tree which is used for testing (not your tree)
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();

		// create your tree
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		// this badList will be inserted one by one to the tree - in the for loop
		int [] badList = new int[] {99660, 32514, 85126};
		

		for (int i = 0; i< badList.length; i++)
		{
			int element = badList[i];
			
			// this function insert the key to both your tree - and the testHelper tree
			// your insert function is called like this: greatT.insert(element, Integer.toString(element))
			TestUtils.insertToBothTrees(t, greatT, element);
			
			// check that your tree and the testHelper tree are the same
			TestUtils.enumErrors res = TestUtils.CheckTrees(t, greatT);
			if (res != TestUtils.enumErrors.OK)
			{
				fail("Error: " + res.toString());
			}
		}
		
		//TestUtils.printTree(greatT);
	}
	
	
	//*** Test RBTree.insert(k,v).  runs 10 times. 
	//                        Each time insert 5000 random keys, and check each insertion the tree 
	//     Error: in case an error was occurred  a log file would be create in your project directory.
	// 				The log file name would start with "test_INSERT_fuzzer" and inside would be written
	//				the insertion array which lead to the problem.
	// 
	//				In order to recreate the tree - go to test_INSERT_Arr Test, and 
	// 					replace badList array to your own array. 
	//				Be aware that the error must have been in the last insertion 
	//					(since we check the tree after each insertion)                    ***//
	@Test
	@Repeat(times=10)
	public void test_INSERT_fuzzer() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		final int INSERTION_COUNT = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
			
       
		int[] insertionList = new int[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(INSERTION_COUNT * 100);
			insertionList[i] = nextKey;
			
			int colorCount = TestUtils.insertToBothTrees(t, greatT, nextKey);
			if (-1 == colorCount)
			{
				i--;
				continue;
			}
		
			TestUtils.enumErrors res = TestUtils.CheckTrees(t, greatT);
			if (res != TestUtils.enumErrors.OK)
			{
				Logger logFile = Tests.getUniqeLogger("test_INSERT_fuzzer");
				logFile.write(Arrays.toString(Arrays.copyOf(insertionList, i+1)));
				logFile.close();
				
				fail("Non match tree: insertion keys - see file.log. general error: " + res.toString());
			}
		}
	}
	////////////////// End Of Insertion Tests ///////////////////////////////////////

	
	////////////////////////// Deletion Tests //////////////////////////////////////
	
	//*** Test RBTree.delete(k). insert 7,10,3 and then delete 7,3,10    ***//
	@Test
	public void test_DELETE_Simple() throws IOException
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		TestUtils.enumErrors res;

			
		TestUtils.insertToBothTrees(t, greatT, 7);
		TestUtils.insertToBothTrees(t, greatT, 10);
		TestUtils.insertToBothTrees(t, greatT, 3);
		
		TestUtils.deleteFromBothArray(t, greatT, 7);
		
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("Error: " + res.toString());
		}
		
		TestUtils.deleteFromBothArray(t, greatT, 3);
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("Error: " + res.toString());
		}
		
		TestUtils.deleteFromBothArray(t, greatT, 10);
		res = TestUtils.CheckTrees(t, greatT);
		if (res != TestUtils.enumErrors.OK)
		{
			fail("Error: " + res.toString());
		}

	}

	
	//*** Test RBTree.delete(k). runs 10 times:
	//			Each time: 1) insert 5000 random keys
	//						2) write to test_DELETE_fuzzer log file all the insertion keys (in the insertion order)
	//						3) delete randomly all the inserted keys
	//							each delete we write to test_DELETE_fuzzer log file - basic info about the delete key
	//								(see Tests.Log package - LogInfo.java - for the full information which is saved)
	//								you can open the log in excel, it is saved in csv format with "," delimiter
	//							and also each delete we check the tree correctness: 
	//								(using our own function - not like the insertion which we compared to other tree)
	//								by checking the black height of the tree + on each node, the parent pointer ***//
	@Test
	@Repeat(times=10)
	public void test_DELETE_fuzzer() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		Logger logFile = Tests.getUniqeLogger("test_DELETE_fuzzer", true);
		
		final int INSERTION_COUNT = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		   
		Integer[] insertionArr = new Integer[INSERTION_COUNT];
		
		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int nextKey = gen.nextInt(500000);
			insertionArr[i] = nextKey;
			
			if (-1 == TestUtils.insertToBothTrees(t, greatT, nextKey))
			{
				i--;
				continue;
			}
			
		}
		
		Integer[] logInsertionArr = Arrays.copyOf(insertionArr, INSERTION_COUNT);

		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("Non match tree: insertion keys %n " + insertionArr.toString());
		}
		
		/// !!! The test starts here !!! ///
		List<Integer> insertionList = Arrays.asList(insertionArr);
		Collections.shuffle(insertionList);
		Integer[] deletionArr = insertionList.toArray(new Integer[insertionArr.length]);
		
		TestUtils aaa= new TestUtils();

		for (int i=0; i<INSERTION_COUNT; i++)
		{
			int deleteKey = deletionArr[i];
			
			// write basic info on each deleted node (color, parent color, children count, etc..)
			logFile.write(TestUtils.getLogInfo(greatT.getNodeByKey(deleteKey), greatT));
			greatT.delete(deleteKey);
			
			TestUtils.CheckHeigtResult res = aaa.CheckTreeBlackHeight(greatT.getRoot(), null);
			if (res.state != TestUtils.enumErrors.OK)
			{
				//log insertion list
				logFile.write(Arrays.toString(Arrays.copyOf(logInsertionArr, INSERTION_COUNT)));
				
				System.out.println("After state:");
				TestUtils.printTree(greatT);
				logFile.write(Arrays.toString(Arrays.copyOf(deletionArr, i+1)));
				logFile.write("Failed on Key " + deleteKey);
				logFile.write("Failed error " + res.state.toString());
				logFile.close();
				
				fail("test_DELETE_fuzzer, see log file. general error: " + res.state.toString());
			}
			
		}	
		
		logFile.close();
	}

	//////////////////////////End of Deletion Tests //////////////////////////////////
	
	
	@Test
	public void test_TABLE() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		Logger logFile = Tests.getUniqeLogger("test_DELETE_fuzzer", true);
		
		float[] deleteColor = new float[10];
		float[] insertColor = new float[10];
		
		for (int j=1; j<=10; j++)
		{
			
			final int INSERTION_COUNT = 10000 * j;
			
			RedBTree<Integer,String> t = new RedBTree<Integer,String>();
			RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
			
			   
			Integer[] insertionArr = new Integer[INSERTION_COUNT];
			
			for (int i=0; i<INSERTION_COUNT; i++)
			{
				int nextKey = gen.nextInt(Integer.MAX_VALUE);
				insertionArr[i] = nextKey;
				
				int color = TestUtils.insertToBothTrees(t, greatT, nextKey);
				insertColor[j-1] += color;
				if (-1 == color)
				{
					i--;
					continue;
				}
				
			}
			insertColor[j-1] /= INSERTION_COUNT;
			
			Arrays.sort(insertionArr);
			
	
			for (int i=0; i<INSERTION_COUNT; i++)
			{
				int deleteKey = insertionArr[i];
				int color = greatT.delete(deleteKey);
				deleteColor[j-1] += color;
		
			}	
			
			deleteColor[j-1] /= INSERTION_COUNT;
			
			String print = "%d - Delete Color:  %f ; Insert Color:  %f";
			System.out.println(String.format(print, j-1, deleteColor[j-1], insertColor[j-1])) ;
		}
		
		logFile.close();
		
	}
	
	
	// for delete_fuzzer debugging - uncomment the next line ("//@Test") to run this test
	//@Test
	public void test_DELETE_BADLIST()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree_danielf1_dormendil greatT = new RBTree_danielf1_dormendil();
		
		   
		int [] insertbadList = new int[] {99660, 32514, 85126};
		
		for (int i=0; i<insertbadList.length; i++)
		{
			int nextKey = insertbadList[i];
			
			if (-1 == TestUtils.insertToBothTrees(t, greatT, nextKey))
			{
				i--;
				continue;
			}
			
		}
		
		if (TestUtils.CheckTrees(t, greatT) != TestUtils.enumErrors.OK)
		{
			fail("Non match tree: cannot run delete test, see log file for the insertion list");
		}
		

		int [] deletebadList = new int[] {99660, 32514, 85126};
		
		TestUtils aaa= new TestUtils();

		for (int i=0; i<deletebadList.length; i++)
		{
			int deleteKey = deletebadList[i];
			
			greatT.delete(deleteKey);
			
			TestUtils.CheckHeigtResult res = aaa.CheckTreeBlackHeight(greatT.getRoot(), null);
			if (res.state != TestUtils.enumErrors.OK)
			{
				fail("Blat!");
			}
			
		}	
		
	}
	
	//////// !!!!!!  End of Tests section !!!!!! //////////
	
	
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
	
}