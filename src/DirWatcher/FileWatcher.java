package DirWatcher;

import java.util.*;
import java.io.*;

public abstract class FileWatcher extends TimerTask {
  private File file; //file updated occasionally to obtain new data from sensors
  private long numOfBytes;//number of bytes in the file 

  public FileWatcher( File file ) throws FileWatcherException
  {
    this.file = file;
    if(file.exists())
    	numOfBytes = file.length();//initialize number of bytes when file detected
    else
    	throw new FileWatcherException();
  }

  public final void run() {

    if (file.length() > numOfBytes)//check if file has been updated
    {
    	onChange(file, numOfBytes);
    	numOfBytes = file.length();
    }
  }
  
  protected abstract void onChange( File file, long previousFileSize );
}