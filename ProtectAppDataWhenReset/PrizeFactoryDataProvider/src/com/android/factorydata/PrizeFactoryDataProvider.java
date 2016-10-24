package com.android.factorydata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class PrizeFactoryDataProvider extends ContentProvider {

    public static String prizeBackupDataDir = Environment.getDataDirectory().getAbsolutePath() + "/prize_backup";
    public static String prizeBackupData = prizeBackupDataDir + "/prize_factory_data";
	public static String imeiNo1 = new String();
	public static String imeiNo2 = new String();
	public static String snNo = new String();
	PhoneInfoHelper phoneDataHelper;
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		String data = null;
		try {
			data = prizeReadData(prizeBackupData);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		Log.e("lwq","insert:"+arg1);		
		String imei=arg1.getAsString("imei");
		prizeCreateData(imei);
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Log.e("lwq","onCreate");
		File factorydir = new File(prizeBackupDataDir);
		if (!factorydir.exists())
		  factorydir.mkdirs();
		phoneDataHelper=new PhoneInfoHelper(getContext());
		return false;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		Log.e("lwq","query");
		Cursor cursor=phoneDataHelper.queryItem(arg2, arg3);
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		Log.e("lwq","update");
		phoneDataHelper.update(arg1,arg2,arg3);
		return 0;
	}
	 /*--prize-factory reset restore-add --liuweiquan-20151029 start--*/
    public void prizeCreateData(String paramString) {
			 String OutText=paramString;			
			try{
				File myFile = new File(prizeBackupDataDir, "prize_factory_data");
			if (!myFile.exists()) {
				myFile.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(myFile, false);
			byte[] bytes = OutText.getBytes();
			fout.write(bytes);
			fout.flush();				
			fout.close();
			}	  
		 catch (Exception e) {
			e.printStackTrace();
			
		}
	  }
   
    public String prizeReadData(String fileName) throws IOException{   
      String res="";   
      try{   
             FileInputStream fin = new FileInputStream(fileName);   
      
             int length = fin.available();   
      
             byte [] buffer = new byte[length];   
             fin.read(buffer);       
      
             res = EncodingUtils.getString(buffer, "UTF-8");   
      
             fin.close();       
            }   
      
            catch(Exception e){   
             e.printStackTrace();   
            }   
            return res;   
    }   
	/*--prize-factory reset restore-add --liuweiquan-20151029 end--*/

}
