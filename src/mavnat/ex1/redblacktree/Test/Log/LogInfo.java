package mavnat.ex1.redblacktree.Test.Log;


public class LogInfo
{
	public int key;
	public String color;
	public String parentColor;
	public int childrenCount;
	public int blackHeight;
	public boolean isMin;
	public boolean isMax;
	
	public LogInfo(int key, String color, String parentColor, int childrenCount,
			int height, boolean isMin, boolean isMax)
	{
		this.key = key;
		this.color = color;
		this.parentColor = parentColor;
		this.childrenCount = childrenCount;
		this.blackHeight = height;
		this.isMax = isMax;
		this.isMin = isMin;
	}
	
	public LogInfo()
	{
		this(0, "Red", "Root", 0, 0, false, false);
	}
	
}
