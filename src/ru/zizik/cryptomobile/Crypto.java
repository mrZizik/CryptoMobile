package ru.zizik.cryptomobile;

import java.io.UnsupportedEncodingException;

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
	public String crypt(int crypt) {
		switch (crypt) {
			case 0:
				return cesarCrypt();
			case 1:
				return vijnerCrypt();
			case 2:
				return base64Crypt();
			
		}
		return "Error!";
	}
}
