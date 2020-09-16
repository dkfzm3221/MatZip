package com.koreait.matzip;

import java.io.File;

import javax.servlet.http.Part;

public class FileUtils {
	public static void makeFolder(String path) {
		File dir = new File(path);
		
		if(!dir.exists()) { //폴더가 존재 하냐?
			dir.mkdirs();	//없으면 만들어라
		}
	}
	public static String getExt(String fileNm) {
		return fileNm.substring(fileNm.lastIndexOf("."));
	}


}
