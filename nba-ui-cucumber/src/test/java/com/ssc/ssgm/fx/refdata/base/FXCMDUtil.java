package com.ssc.ssgm.fx.refdata.base;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FXCMDUtil {
	
	private static final Logger logger = Logger.getLogger(FXCMDUtil.class);
	
	public static List<String> runBAT(String batFilePath) throws Exception {
		String command = "cmd /c cmd.exe /c " + batFilePath + " exit";
		return executeCMD(command);
	}
	
	public static List<String> executeCMD(String command) throws Exception {
		Process process = Runtime.getRuntime().exec(command);
		List<String> data = new ArrayList<String>();

		InputStream in = process.getInputStream();
		Reader reader = new InputStreamReader(in);
		BufferedReader bReader = new BufferedReader(reader);
		for (String res = ""; (res = bReader.readLine()) != null;)
		{
			data.add(res);
		}
		bReader.close();
		reader.close();
		return data;
	}
	
	public static void runCMD(String command) throws Exception {
		Runtime.getRuntime().exec(command);
	}
	
	public static boolean isImageStarted(String imageName) {
		try {
			List<String> imagesList = executeCMD("tasklist");
			for (int i = 0; i < imagesList.size(); i++) {
				if (imagesList.get(i).contains(".exe")) {
					String currentImage = imagesList.get(i).split(".exe")[0];
					if (currentImage.equalsIgnoreCase(imageName)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(e,e);
			return false;
		}
		return false;
	}
}
