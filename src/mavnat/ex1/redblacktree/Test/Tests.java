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
	
	

	
	@Test
	public void test_INSERT_Down_Order()
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		TestUtils.insertToBothArray(t, greatT, 5);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 5");
		}
		
		TestUtils.insertToBothArray(t, greatT, 4);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 4");
		}
		
		TestUtils.insertToBothArray(t, greatT, 2);
		if (!TestUtils.CheckTrees(t, greatT))
		{
			fail("insert 2");
		}
		
		
	}
	
	@Test
	public void test_INSERT_Error_1() throws IOException
	{
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		int [] badList = new int[] {99660, 32514, 85126, 182106, 170016, 58525, 81075, 2250, 91818, 147032, 76321, 173692, 128576, 115616, 176629, 190345, 135031, 186505, 121263, 198606, 66602, 165096, 182083, 34256, 31322, 86286, 38536, 182473, 165808, 166433, 89289, 196243, 11229, 104600, 122, 51952, 34255};
		

		for (int i = 0; i< badList.length; i++)
		{
			int element = badList[i];
			
			TestUtils.insertToBothArray(t, greatT, element);
			if (!TestUtils.CheckTrees(t, greatT))
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
		
		Logger logFile = new Logger("file_insert_fuzzer_" + Integer.toString(gen.nextInt(999)) + ".log" , ",");
		
		final int MAX_INSERTION = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
			
       
		int[] insertionList = new int[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(MAX_INSERTION * 100);
			insertionList[i] = nextKey;
			
			if (false == TestUtils.insertToBothArray(t, greatT, nextKey))
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
	//@Repeat(times=10)
	public void test_DELETE_fuzzer() throws IOException 
	{
		java.util.Random gen = new java.util.Random();
		
		Logger logFile = new Logger("file_delete_fuzzer_" + Integer.toString(gen.nextInt(999)) + ".log" , ",");
		
		final int MAX_INSERTION = 5000;
		
		RedBTree<Integer,String> t = new RedBTree<Integer,String>();
		RBTree greatT = new RBTree();
		
		   
		Integer[] insertionArr = new Integer[MAX_INSERTION];
		
		for (int i=0; i<MAX_INSERTION; i++)
		{
			int nextKey = gen.nextInt(999999999);
			insertionArr[i] = nextKey;
			
			if (false == TestUtils.insertToBothArray(t, greatT, nextKey))
			{
				i--;
				continue;
			}
			
		}
		//log insertion list
		logFile.write(Arrays.toString(Arrays.copyOf(insertionArr, MAX_INSERTION+1)));
		
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
			
			// write basic info on each deleted node (color, parent color, children count, etc..)
			logger.write(TestUtils.getLogInfo(t.lookupNode(deleteKey), greatT));
			
			t.delete(deleteKey);
			greatT.delete(deleteKey);
			
			if (!TestUtils.CheckTrees(t, greatT))
			{
				logFile.write(Arrays.toString(deletionArr));
				logFile.write("Failed on Key " + deleteKey);
				logFile.close();
			}
			
		}		
	}
	
	
	
	
}