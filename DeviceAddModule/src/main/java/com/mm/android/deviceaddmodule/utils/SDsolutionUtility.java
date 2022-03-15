package com.mm.android.deviceaddmodule.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.mm.android.deviceaddmodule.mobilecommon.utils.LogUtil;

import java.io.File;
import java.security.NoSuchAlgorithmException;


public class SDsolutionUtility {

	private static String mUsername;
	private static String md5name;
	private static String[] dirFolder = {"snapshot","video","mp4","thumb","facedetection","cache", "temp"};
	private static String ALBUM_PATH=Environment.getExternalStorageDirectory()+File.separator;


	private static Context mContext;


	public static void initContext(Context context) {
		mContext = context;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			ALBUM_PATH = mContext.getExternalFilesDir("demo").getAbsolutePath();
			LogUtil.debugLog("rrrrr","ALBUM_PATH::"+ALBUM_PATH);
		}
	}

	public static void createDir(String username)
	{
		mUsername = username.toLowerCase();
		try {
			//由于服务器不区分大小写，先统一将所有名字转成小写，再转MD5
			md5name = MD5Utility.getMD5(username.toLowerCase());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		String userPath = ALBUM_PATH + md5name;
		String newUserPath = ALBUM_PATH;
		File dirUserFile = new File(userPath);
		File newDirUserFile = new File(newUserPath);
		if (dirUserFile.exists()) {
			FileHelper.renameFile(dirUserFile, newDirUserFile);
		}


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
			for (int i = 0; i < dirFolder.length; i++) {
				String path = ALBUM_PATH+File.separator +dirFolder[i]+File.separator;
				LogUtil.debugLog("rrrrr","path::"+path);
				File dirEasy4ipFile = new File(path);
				if (!dirEasy4ipFile.exists()) {
					dirEasy4ipFile.mkdirs();
				}
			}

		} else {
			boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
			if (!sdCardExist) {
				//
			} else {
				for (int i = 0; i < dirFolder.length; i++) {
					String path = ALBUM_PATH + File.separator + dirFolder[i] + File.separator;
					File dirEasy4ipFile = new File(path);
					if (!dirEasy4ipFile.exists()) {
						dirEasy4ipFile.mkdirs();
					}
				}
			}
		}
	}



	public static String getCachePath()
	{
		return ALBUM_PATH+dirFolder[5]+File.separator;
	}

	public static String getTempPath(){
		return ALBUM_PATH+dirFolder[6]+File.separator;
	}
	
}
