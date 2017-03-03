package com.ssc.ssgm.fx.refdata.base;

public class test {
	public static void main (String[] args) throws Exception {
		FXPassEncryptor tes = new FXPassEncryptor();
		String pwd = tes.decrypt("3%t2qp%acznr52a6461626e6%", 1988);
		System.out.println(pwd);
		String pwdEncryptString = tes.encrypt("nbadba", 1988);
		System.out.println(pwdEncryptString);

	}
}
