package com.leandro.webeventos.controller.encoder;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Encode64 {

	
	public static String encode64(byte[] imagem) {
		byte[] encodeBase64;
		String baseEncode64 = null;
		encodeBase64 = Base64.getEncoder().encode(imagem);
		try {
			baseEncode64 = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return baseEncode64;
	}
}
