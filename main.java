import java.lang.Math; // headers MUST be above the first class
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator; 
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;


// one class needs to have a main() method
public class main
{
	public static void main(String[] args) {
		byte[] bytes;
		try {
			String content = "-- encrypted data --";
			//1. 解base64
			bytes = base64Decode(content);
			//2. 取商户key的md5值，小写
			String key = MD5Encode("-- key --", "UTF-8").toLowerCase();
			//3. 解AES-256-ECB
			String decryptStr = aesDecryptByBytes(bytes, key);
			
			System.out.println(decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
		//1. key
		SecretKeySpec skeySpec = new SecretKeySpec(decryptKey.getBytes("UTF-8"),"AES");
		//2. cipher
		Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		try{
		byte[] original = cipher.doFinal(encryptBytes);
		return new String(original);
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		}
     }
	 
	public static byte[] base64Decode(String base64Code) throws Exception{
		return Base64.getDecoder().decode(base64Code);  
    }
	
	
	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}
	
	private static String byteArrayToHexString(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(0xFF & bytes[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}
}