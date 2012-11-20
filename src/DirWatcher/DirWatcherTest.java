package DirWatcher;


public class DirWatcherTest {
  public static void main(String args[]) {
        String path = "/home/natalia/Eclipseworkspace/WSN-Linux/src/testFileUpdateCheck/directory";
    	try
    	{
    		PeriodicDirWatcher pdw = new PeriodicDirWatcher(path, 1000);
    	}
    	catch(FileWatcherException fwe)
    	{
    		fwe.printStackTrace();
    	}
  }
}