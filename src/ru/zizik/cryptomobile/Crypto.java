package ru.zizik.cryptomobile;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;

import ru.zizik.cryptomobile.rsa.RSAKeyPair;
import android.util.Base64;

public class Crypto {

	private String originalMessage;
	private int iKey;
	private String sKey;
	char cAlpha[] = {
			'а',
			'б',
			'в',
			'г',
			'д',
			'е',
			'ж',
			'з',
			'и',
			'й',
			'к',
			'л',
			'м',
			'н',
			'о',
			'п',
			'р',
			'с',
			'т',
			'у',
			'ф',
			'х',
			'ч',
			'ш',
			'щ',
			'ъ',
			'ы',
			'ь',
			'э',
			'ю',
			'Я'
	};
	char table[][];
	public Crypto() {
	}
	public void setOriginalMessage(String original) {
		this.originalMessage = original;
	}

	public void setKey(int iKey) {
		this.iKey = iKey;
	}

	public void setKey(String sKey) {
		this.sKey = sKey;
	}
	//ифр –езарЯ
	//iKey - ключ
	public String cesarCrypt() {
		String crypted = "";
		for (int i=0; i<this.originalMessage.length(); i++) {
			for (int j=0;j<cAlpha.length;j++) {
				if (this.originalMessage.charAt(i)==cAlpha[j]) {
					int a = j+iKey;
					while(a>30) {
						a = a-30;
					}
					crypted+=cAlpha[a];
				} 
			}
		}
		return crypted;
	}

	public String vijnerCrypt() {
		if (sKey.length()>this.originalMessage.length()) {
			sKey = sKey.substring(0, this.originalMessage.length());
		} 
		while (sKey.length()<this.originalMessage.length()) {
			int a = originalMessage.length() - this.sKey.length();
			if (a>=sKey.length()) {
				sKey+=sKey;
			} else {
				sKey = sKey + sKey.substring(0,a);
			}
		}
		this.table = new char[cAlpha.length][cAlpha.length];
		for (int i=0; i<cAlpha.length; i++) {
			for (int j=0; j<cAlpha.length; j++) {
				int a = j+i;
				while (a>cAlpha.length-1) 
					a=a-cAlpha.length;
				table[i][j] = cAlpha[a];
			}
		}
		String crypted = "";
		int a = 0;
		int b = 0;
		for (int i=0; i<originalMessage.length(); i++) {
			for (int j=0;j<cAlpha.length;j++)  {
				if (originalMessage.charAt(i)==cAlpha[j])
					a = j;
				if (sKey.charAt(i)==cAlpha[j])
					b = j;}
			crypted += table[b][a];
		}
		return crypted;
	}

	public String base64Crypt() {
		byte[] data = null;
		try {
			data = originalMessage.toLowerCase().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String base64 = Base64.encodeToString(data, Base64.DEFAULT);

		// Receiving side
		return base64;
	}

	private final String privateKeyPathName = android.os.Environment.getExternalStorageDirectory()
			+ File.separator + "Android" + File.separator
			+ "data" + File.separator
			+ "private.key";
	private final String publicKeyPathName = android.os.Environment.getExternalStorageDirectory()
			+ File.separator + "Android" + File.separator
			+ "data" + File.separator
			+ "public.key";
	private final String transformation = "RSA/ECB/PKCS1Padding";
	private final String encoding = "UTF-8";

	public String rsaEncrypt() {
		try {

			RSAKeyPair rsaKeyPair = new RSAKeyPair(2048);
			rsaKeyPair.toFileSystem(privateKeyPathName, publicKeyPathName);

			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(IOUtils.toByteArray(new FileInputStream(publicKeyPathName)));

			Cipher cipher = Cipher.getInstance(transformation);
			cipher.init(Cipher.ENCRYPT_MODE, KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec));

			return Base64.encodeToString(cipher.doFinal(originalMessage.getBytes(encoding)), Base64.DEFAULT);   

		} catch(Exception exception) {
			Assert.fail("The testEncryptDecryptWithKeyPairFiles() test failed because: " + exception.getMessage());
		}
		return null;
	}


	public String crypt(int crypt) {
		switch (crypt) {
		case 0:
			return cesarCrypt();
		case 1:
			return vijnerCrypt();
		case 2:
			return base64Crypt();
		case 3:
			return rsaEncrypt();

		}
		return "Error!";
	}
}
