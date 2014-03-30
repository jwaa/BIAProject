package drenthwaa.bia.testing.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
public class SimpleFileWriter
{
	private File file;
	private PrintWriter writer;
	
	private boolean append = true;
	
	public SimpleFileWriter(String filename)
	{
		file = new File(filename);

		try
		{
			file.createNewFile();
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		openFile();
	}
	
	public SimpleFileWriter(String filename, boolean append)
	{
		file = new File(filename).getAbsoluteFile();

		if(!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}
		
		try
		{
			file.createNewFile();
		} catch(IOException e)
		{
			System.err.println(filename);
			e.printStackTrace();
			System.exit(0);
		}
		
		this.append = append;
		openFile();
	}
	
	private void openFile()
	{
		try
		{
			writer = new PrintWriter(new FileOutputStream(file, append));
		} catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	private void closeFile()
	{
		writer.flush();
		writer.close();
	}

	public void processLine(String line)
	{
		if(line == null)
		{
			Thread.dumpStack();
			System.exit(0);
		}
		
		if(file != null && writer != null)
		{
			writer.println(line);
		}
	}

	public void wrapUp()
	{
		closeFile();
	}

}
