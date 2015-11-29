package mavnat.ex1.redblacktree.Test.Log;

import mavnat.ex1.redblacktree.Test.Log.LogInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Logger
{
	private final static String[] FILE_HEADER = {"Key", "Color", "Parent Color", "Childern Count", "Black Height", "Is Max", "Is Min" };
	
	public BufferedWriter bw;
	public String logInfoDelimiter;
	
	public Logger(String fileName, String logInfoDelimiter, boolean addLogFileHeader) throws IOException
	{
		FileWriter fw = new FileWriter(fileName); 
		this.bw = new BufferedWriter(fw);
		
		this.logInfoDelimiter = logInfoDelimiter;
		
		StringBuilder builder = new StringBuilder();
		
		if (addLogFileHeader)
		{
			for(String str:Logger.FILE_HEADER)
			{
				builder.append(str + this.logInfoDelimiter);
			}
			
			this.write(builder.toString());
		}
	}
	
	public Logger(String fileName, String logInfoDelimiter) throws IOException
	{
		this(fileName, logInfoDelimiter, false);
	}
	
	public void write(String str) throws IOException
	{
		this.bw.write(str);
		this.bw.newLine();
	}
	
	public void write(LogInfo info) throws IOException
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(Integer.toString(info.key) + logInfoDelimiter);
		builder.append(info.color + logInfoDelimiter);
		builder.append(info.parentColor + logInfoDelimiter);
		builder.append(Integer.toString(info.childrenCount) + logInfoDelimiter);
		builder.append(Integer.toString(info.blackHeight) + logInfoDelimiter);
		builder.append(Boolean.toString(info.isMax) + logInfoDelimiter);
		builder.append(Boolean.toString(info.isMin));
		
		this.bw.write(builder.toString());
		this.bw.newLine();
	}
	
	public void close() throws IOException
	{
		this.bw.flush();
		this.bw.close();
	}
}