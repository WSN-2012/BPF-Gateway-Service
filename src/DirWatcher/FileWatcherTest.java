package DirWatcher;

import java.io.*;


public class FileWatcherTest 
{
  public static void main(String args[]) 
  {
    // monitor a file
	File file = new File("/home/natalia/Eclipseworkspace/WSN-Linux/src/testFileUpdateCheck/sample");
	try
	{
		PeriodicFileWatcher pfw = new PeriodicFileWatcher(file, 1000);
	}
	catch(FileWatcherException fwe)
	{
		fwe.printStackTrace();
	}
  }  
}