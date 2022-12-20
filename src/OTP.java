import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.spec.SecretKeySpec;

public class OTP {

	    private static String dataTxt;
	    private static String key;
	    private static SecureRandom secure_key;
	    private static String secure_key_in_bits;
	    public OTP(String data,String key) {
	    	this.dataTxt=data;
	    	this.key=key;
	    }
	    
	   public static void generate_key(String data,String key) throws Exception{
			   
		      secure_key_in_bits="";
		      int randomValues= 0;
			   try {
				      secure_key=new SecureRandom();
				      int index=0;
				      while(index<data.length()) {
				    	  randomValues=secure_key.nextInt(256);
				    	  secure_key_in_bits+=decToBin(randomValues,8);
				    	  index++;
				      }
		        }
				   
			
	        catch(Exception e) {
				
				e.printStackTrace();
			}
		    
			 
		 }
	   public static void generate_message_in_bits(String data) {
		   try {
			    dataTxt=charToBinary(data);
		   }
		   catch(Exception e) {
			   e.printStackTrace();
		   }
	   }
		public static String decToBin(int value,int numberBits) {
		    	int updating_value=value;
		    	int index=0;
		    	String bits="";
		    	while(updating_value!=0 || index<numberBits) {
		    		int remainder=updating_value%2;
		    		updating_value/=2;
		    		bits+=remainder;
		    		index++;
		    	}
		    	return reverse(bits);
		    }
		 public static String reverse(String data) {
			 int index=data.length()-1;
			 String value="";
			 while(index>=0) {
				 value+=data.charAt(index);
				 index--;
			 }
			 return value;
		 }
		 public static String pad(String text) {
			 while(text.length()%8!=0) {
				 text+=' ';
			 }
			 return text;
		 }
		 public static String charToBinary(String data) {
			 String value="";
			 int index=0;
			 while(index<data.length()) {
				 int dataInAscii=(int)data.charAt(index);
				 value+=decToBin(dataInAscii,8);
				 index++;
			 }
			return value;
		 }
		 public static int not(int string) {
		 	int x=0;
		 	if(string==0) {
		 		x=1;
		 	}
		 	else if(string==1) {
		 		x=0;
		 	}
		 	return x;
		 }
			
		 public static String xor_operation(String data,String key) {
		 	
		 	String xor_value="";
		 	for(int i=0;i<data.length();i++) {
		 		int x=Integer.parseInt(String.valueOf(data.charAt(i)));
		 		int y=Integer.parseInt(String.valueOf(key.charAt(i)));
		 		int res=(not(x)*y)+(not(y)*x);
		 		xor_value+=Integer.toString(res);
		 	}
		 	return xor_value;
		 }
		 public static int binaryToDec(String value) {
		 	int x=0;
		 	for(int i=0;i<value.length();i++) {
		 		int length=value.length()-1;
		 		x+=Integer.parseInt(String.valueOf(value.charAt(i)))*Math.pow(2,length-i);
		 	}
		 	return x;
		 }
		 
        public static String binaryToCharacters(String data) {
		 	
		 	int index=0;
		 	String value = "";
		 	while(index<data.length()) {
		 		String indexValue="";
		 		for(int j=index;j<index+8;j++) {	
		 			indexValue+=data.charAt(j);
		 		}
		 		int index_value=binaryToDec(indexValue);
		 		value+=(char)index_value;
		 		index+=8;
		 	}
		 	
		 	return value;
		 }
 
        public static String encrypt(String data,String key) {
        	try {
				generate_key(data,key);
				generate_message_in_bits(data);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
        	return binaryToCharacters(xor_operation(dataTxt,secure_key_in_bits));
        }
        
        public static String decrypt(String data,String key) {
        	try {
				generate_message_in_bits(data);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
        	return  binaryToCharacters(xor_operation(dataTxt,secure_key_in_bits));
        }
}
