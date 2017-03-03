package com.ssc.ssgm.fx.refdata.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * This class is used to encrypt and decrypt password 
 * @author e580217,e532258
 *
 */
public class FXPassEncryptor {
	
	public static String encrypt(String encontent, int securityKey) throws Exception {
		byte[] byteValue = encontent.getBytes();
		String content = byte2hex(byteValue);
		List<String> spit = new ArrayList<String>();
		while (content.length()>12) {
			String head = content.substring(0, 12);
			content = content.substring(12);
			spit.add(head);
		}
		spit.add(content);
		String transfer = "";
		for (String partValue : spit) {
			long temp = Long.parseLong(partValue,16);
			String temp2 = Long.toHexString(temp + securityKey);
			transfer += new StringBuffer(temp2).reverse().toString() + "%";
		}
		if (transfer.length()<15) {
			String middle = "%"+ getRandomString(4,4) +"%";
			int beforeLength = 15 - transfer.length();
			transfer = getRandomString(1,beforeLength) + middle + getRandomString(5,5) + transfer;
		}
		return transfer;
	}
	
	public static String decrypt(String decontent, int securityKey) {
		String[] splits = decontent.split("%");
		if (splits[1].length() == 4 && splits.length>2) {
			decontent = splits[2].substring(5);
		}
		splits = decontent.split("%");
		String result = "";
		for (String temp : splits) {
			if (temp.isEmpty()) {
				continue;
			}
			String originalVaule = new StringBuffer(temp).reverse().toString();      
	        try {
	        	long temp2 = Long.parseLong(originalVaule,16);
	        	String transfer = Long.toHexString(temp2 - securityKey);
	            byte[] byteValue = hexStr2ByteArr(transfer);
	            result += new String(byteValue);
	        }
	        catch (Exception e) {
	        	e.printStackTrace();
	        }  
		}
        return result;   
    }
	
	private static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}
	    
	private static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
	
	public static String getRandomString(int minLength, int maxLength) {
		Random random = new Random();
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		int randomLength = minLength + random.nextInt(maxLength-minLength+1);
		StringBuffer string = new StringBuffer("");
		for (int i = 0; i < randomLength; i++) {
			string.append(str[random.nextInt(36)]);
		}
		return string.toString();
	}
}
