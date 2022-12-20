import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class TripleDES {
    
	 private static String dataText;
	 private static String keyText;
	 private static Cipher cipherObj;
	 private static DESedeKeySpec specification;
	 private static SecretKeyFactory key_fact;
	 private static SecretKey the_Key;
	 private static IvParameterSpec parameter;
	 public TripleDES(String dataText,String keyText) {
		 this.dataText=dataText;
		 this.keyText=keyText;
	 }
	 public static void pad(String text) {
		 while(text.length()%8!=0)
			 text+=' ';
		 dataText=text;
	 }
	 public static void generate_key(String key) throws Exception{
		   
		   try {

		        byte[] encryptKey =key.getBytes();
		        specification = new DESedeKeySpec (encryptKey);
                key_fact = SecretKeyFactory.getInstance("DESede");
		        the_Key = key_fact.generateSecret(specification);
		        			      
	    }
			   
		
      catch(Exception e) {
    	  
			e.printStackTrace();
		}
	    
		 
	 }
	 
	 public static String encrypt(String data,String key) throws Exception{

		try {
			 generate_key(key);
			 pad(data);
	         String cipherText;
	         cipherObj = Cipher.getInstance("DESede/CBC/PKCS5Padding");
	         parameter = new IvParameterSpec(new byte[]{12, 34, 56, 78, 90, 87, 65, 43});
	         cipherObj.init(Cipher.ENCRYPT_MODE, the_Key,parameter);
	         
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
			 generate_key(keyText);
			 
			 cipherObj=Cipher.getInstance("DESede/CBC/PKCS5Padding");
			 parameter = new IvParameterSpec(new byte[]{12, 34, 56, 78, 90, 87, 65, 43});
			 cipherObj.init(Cipher.DECRYPT_MODE, the_Key,parameter);
			
			 String plainText=new String(cipherObj.doFinal(Base64.getDecoder().decode(dataText)));
			 
			 return plainText;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		 return null;
	 }
	 
	 
		 
	 
}
