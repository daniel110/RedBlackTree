package mavnat.ex1.redblacktree.Test.Log;

import mavnat.ex1.redblacktree.Test.Log.LogInfo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class Logger
{
	public BufferedWriter bw;
	public String logInfoDelimiter;
	
	public Logger(String fileName, String logInfoDelimiter) throws IOException
	{
		FileWriter fw = new FileWriter(fileName); 
		this.bw = new BufferedWriter(fw);
		
		this.logInfoDelimiter = logInfoDelimiter;
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
		builder.append(Integer.toString(info.height) + logInfoDelimiter);
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