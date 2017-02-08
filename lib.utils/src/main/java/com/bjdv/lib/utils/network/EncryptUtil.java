package com.bjdv.lib.utils.network;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * 3DES加密，解密工具
 * @author bjdv
 *
 */
public class EncryptUtil {
	
	static String secretKey ="1!QAZ2@WSXCDE#3$4RFVBGT%5^6YHNMJU7&8*IK<.LO9(0P";//3DES密钥
	private final static String iv = "12481632";//向量
	private final static String encoding = "utf-8";

	/**
	 * 3DES加密
	 * @return
	 * @throws SysException 
	 * @throws Exception
	 */
	public static String encryptThreeDESECB(String plainText) throws SysException
	{  
		Key deskey = null;
        DESedeKeySpec spec;
        String res="";
		try {
			spec = new DESedeKeySpec(secretKey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
	        deskey = keyfactory.generateSecret(spec);  
	        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS7Padding");
	        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
	        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
	        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding)); 
	       res= Base64.encodeToString(encryptData, Base64.DEFAULT);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new SysException("", "加密程中出现异常", e);
		}  
        
        return res; 
	      
	}  
	  
	/**
	 * 3DES解密
	 * @param encryptText
	 * @return
	 * @throws SysException 
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static String decryptThreeDESECB(String encryptText) throws SysException
	{  
	        String res="";
			try {
				DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
				SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
				Key deskey = keyfactory.generateSecret(spec);
		        Cipher cipher = Cipher.getInstance("desede/CBC/PKCS7Padding");
		        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		        byte[] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.DEFAULT));
		        res=new String(decryptData,encoding);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (BadPaddingException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (InvalidKeySpecException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
				throw new SysException("", "解密程中出现异常", e);
			}  
	         
	        return  res;
	}  
}
