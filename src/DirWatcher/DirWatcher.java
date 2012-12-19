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

public abstract class DirWatcher extends TimerTask {
  private String path;
  private File filesArray [];
  private HashMap<File, Long> dir = new HashMap<File, Long>();
  private DirFilterWatcher dfw;
  private ArrayList<Long> numOfBytes = new ArrayList<Long>();

  public DirWatcher(String path) {
    this(path, "");
  }

  public DirWatcher(String path, String filter) {
    this.path = path;
    dfw = new DirFilterWatcher(filter);
    filesArray = new File(path).listFiles(dfw);

    // transfer to the hashmap be used a reference and keep the
    // lastModfied value
    for(int i = 0; i < filesArray.length; i++) {
       dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
       numOfBytes.add(filesArray[i].length());
    }
  }

  public final void run() {
    HashSet<File> checkedFiles = new HashSet<File>();
    filesArray = new File(path).listFiles(dfw);

    // scan the files and check for modification/addition
    for(int i = 0; i < filesArray.length; i++) {
      Long current = (Long)dir.get(filesArray[i]);
      checkedFiles.add(filesArray[i]);
      if (current == null) {
        // new file
        dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
        numOfBytes.add(filesArray[i].length());
        onChange(filesArray[i], filesArray[i].length());
      }
      else if (current.longValue() != filesArray[i].lastModified()){
        // modified file
    	dir.put(filesArray[i], new Long(filesArray[i].lastModified()));
    	onChange(filesArray[i], numOfBytes.get(i));
    	numOfBytes.set(i,filesArray[i].length());
    	
        
      }
    }

    // now check for deleted files
    Set ref = ((HashMap)dir.clone()).keySet();
    ref.removeAll((Set)checkedFiles);
    Iterator it = ref.iterator();
    while (it.hasNext()) {
      File deletedFile = (File)it.next();
      dir.remove(deletedFile);
      numOfBytes.remove(it);
    }
  }

  protected abstract void onChange( File file, long previousFileSize );
}