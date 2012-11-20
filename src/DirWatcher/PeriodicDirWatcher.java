package DirWatcher;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Timer;

import service.Service;

public class PeriodicDirWatcher extends DirWatcher {
	Timer timer;
	byte[] buf;

	public PeriodicDirWatcher(String path, long periodMs) throws FileWatcherException
	{
	  super(path);
	  this.timer = new Timer();
	  timer.schedule(this, periodMs, periodMs);//set timer to start checking file when the method is called and every periodMs
	}
	
    protected void onChange( File file, long previousFileSize ) 
    {
  	  try
  	  {
  		  RandomAccessFile raf = new RandomAccessFile(file, "r");
  		  
  		  if(((int)raf.length()-previousFileSize)>0){
  			  raf.seek(previousFileSize);
  			  buf = new byte[(int)(raf.length()-previousFileSize)];
  		  	  raf.read(buf, 0, (int)(raf.length()-previousFileSize));
  		  }else{
  			  buf = new byte[(int)(raf.length())];
  			  raf.read(buf);
  		  }
  		  Service.send( buf);
  		  //System.out.println(new String(buf));
  	  }
  	  catch(Exception e)
  	  {
  		  e.printStackTrace();
  	  }
    }

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public byte[] getBuf() {
		return buf;
	}

	public void setBuf(byte[] buf) {
		this.buf = buf;
	}

}


