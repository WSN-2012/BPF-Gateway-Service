/*
 * Copyright 2012 KTH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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