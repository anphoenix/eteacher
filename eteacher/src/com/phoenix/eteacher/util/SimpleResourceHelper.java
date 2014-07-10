package com.phoenix.eteacher.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

/** This class takes care of extracting handwriting resource files from the assets folder. */
public class SimpleResourceHelper
{
  public static final String TAG = "SimpleResourceHelper";

  private Context mContext;
  
  public SimpleResourceHelper(Context context)
  {
    mContext = context;
  }

  /** Get absolute an array of absolute paths for the given resources.
   * Extract resources from assets if required. */
  public String[] getResourcePaths(final String[] resources)
  {
    final String[] paths = new String[resources.length];
    
    final File cacheDir = mContext.getExternalCacheDir();
    
    for (int i=0; i<resources.length; i++) {
      File resourceFile = new File(cacheDir, resources[i]);
      if (resourceFile.exists()) {
    	  resourceFile.delete();
      }
      copyResourceFromAssets(resources[i], resourceFile);
      paths[i] = resourceFile.getAbsolutePath();
    }
    
    return paths;
  }
  
  /** Copy a handwriting resource file from assets to given destination file. */
  private void copyResourceFromAssets(final String resource, final File dest)
  {
    try {
      dest.getParentFile().mkdirs();
      
      BufferedInputStream in = new BufferedInputStream(mContext.getAssets().open(resource));
      BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
      
      byte[] buf = new byte[8192];
      int size;
      while ((size = in.read(buf)) != -1) {
        out.write(buf, 0, size);
      }
  
      in.close();
      out.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, "Cannot create file " + dest);
    } catch (IOException e) {
      Log.e(TAG, "Error while extracting resource " + resource + " from assets");
    }
  }
  
  public static boolean copyResourcesFromAssets(AssetManager am, final String subfolder, final String resourcePath, final String[] resources)
  {
    boolean done = true;
    try 
    {
      // Be sure target folder exists
      File resourceFolder = new File(resourcePath);
      resourceFolder.mkdirs();
    	
	  for (final String filename: resources)
	  {
        // Files
        String srcFilename = getPathFor(subfolder, filename);
        String dstFilename = getPathFor(resourcePath, filename);
        
        // Reading source file.
        InputStream is = am.open(srcFilename);
        byte[] buffer = new byte[is.available()];
        is.read(buffer);
        
        // Create destination file to copy into.
        File dst = new File(dstFilename);

        if (!dst.exists())
        {
          dst.createNewFile();
          FileOutputStream out = new FileOutputStream(dst);
          out.write(buffer);
          out.close();
        }
	  }
    } 
    catch (IOException e) 
    {
      done = false;
      e.printStackTrace();
    }
	return done;
  }
  
  private static String getPathFor(final String folder, final String filename)
  {
    String filepath = folder;
    if (!filepath.endsWith(java.io.File.separator))
      filepath += java.io.File.separator;
    if (filename.startsWith(java.io.File.separator))
      filepath += filename.substring(1);
    else
      filepath += filename;
    return filepath;
  }
}
