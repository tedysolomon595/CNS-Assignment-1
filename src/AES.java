import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    
	 private static final String SALT = "ThisIsSalt";
	 private static String dataText;
	 private static SecretKeySpec secret_key_spec;
	 private static Cipher cipherObj;
	 private static SecretKeyFactory key_fact;
	 private static KeySpec key_spec;
	 private static IvParameterSpec parameter ;
	 private static byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};;
	 public static void generate_key(String key) throws Exception{
		   
		 try {
	            
	            

	            key_fact = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
	            key_spec = new PBEKeySpec(key.toCharArray(), SALT.getBytes(), 65536, 256);
	            SecretKey secret_key = key_fact.generateSecret(key_spec);
	            secret_key_spec = new SecretKeySpec(secret_key.getEncoded(), "AES");

	        } catch ( Exception e) {
	            e.printStackTrace();
	        }
	    
		 
	 }
	 
	 public static String encrypt(String dataText,String keyText) throws Exception{

		try {
			 parameter = new IvParameterSpec(iv);
			 generate_key(keyText);
	         String cipherText;
	         cipherObj = Cipher.getInstance("AES/CBC/PKCS5Padding");
	         cipherObj.init(Cipher.ENCRYPT_MODE, secret_key_spec,parameter);
	         
	         cipherText=Base64.getEncoder().encodeToString(cipherObj.doFinal(dataText.getBytes(StandardCharsets.UTF_8)));
	         
	         return cipherText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	 }
	 
	 public static String decrypt(String dataText,String keyText) throws Exception{
		try {
			 
			 parameter = new IvParameterSpec(iv);
			 cipherObj=Cipher.getInstance("AES/CBC/PKCS5Padding");
			 cipherObj.init(Cipher.DECRYPT_MODE, secret_key_spec,parameter);
			
			 String plainText=new String(cipherObj.doFinal(Base64.getDecoder().decode(dataText)));
			 
			 return plainText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 
		 
	 
}
