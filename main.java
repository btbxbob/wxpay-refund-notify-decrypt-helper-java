import java.lang.Math; // headers MUST be above the first class
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.KeyGenerator; 
import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.Security;
import java.util.Base64;


// one class needs to have a main() method
public class main
{
	public static void main(String[] args) {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		byte[] bytes;
		try {
			String content = "m+cqC5OWNek/jGSeSFjMwxqoQmt9x/XQEPRosjYHR2a6kosdkuS2hFlt1QP/ykVOCm6PXnC0tOc7gsPzP3aG4Hhn7wJwJAvMhSUJhOAOlnXtHupbyiwb9vNgqAwcSr04U1yoI8UemDCz+TnIsbldurZh6UKtDqSutp/KiutgJ/9crss+fh9hy9UcBKO60JkJgka79q6lkQoOKZh3kIHrFEZGFKcvCOJzx/heVtz8AHGoB/IuNV4Mh280FZM1TTe8V54eXgqHNAOdJCoYQuKu34tepA+a4sjCcPOmNU5wLCjEFQ/+w7Ad8U2i3bfaA713DPk5qV8IVSB1cMGZj+zZBGPT4OWBg0vZD4ZJCydf93e95CbxV7FuSPiFnZwjvsHBCA7DNGoAfSx72p5ZBcyCTFV9y4O9xTukHUmJNI8XK+JhR5Imz9u5422lfN5FcM6g4WdLDTVO/DiN4chaTUk9uqEiMqD2Bn3+ZWe/R91YDW8koG3qd7m/9y7sckptNQWU9fi+zk/AbCLHETiUIj4dtFxsZRTBUFIEmSl2ebcPEdowOLzjUe2uW/Qr8dDwFuWGsSYawYnsbsxliNc5DthzhcdB9MDkOab6hckUIC7639s6DKFP44Olgjc+tt5EfDpNxK0rHh4rhlCz9h5ZhzI7CVqJRx5pCLEBaJKntPeF9IWfj92VYMY7o8TesmqhiDWnGVGSK8vXDsQMAWIhHi2STvdVAZkaOTzF+cxVJsUgy2zVlGhNzlqjQZmLoXZ/Kiid7cfUQvg/Bqw4We6KRrAfTHplwjbghjVzqWsgJv8KI3cYjFJEtTy19a0z3yBrcthtjszmBEUyUG/d4O0DzGEG+JNB4VsMz/jWUJ2d2uJmpyvngyt5RkafRH4mCHWkNTPz5UCBZtJbvFzQ/VB/X077Apwgk7lfgULu1uVF9N108kRKQcrqoGEH/oZ6lo0wIXbITx+7lr91eMvu4JgGPSQW4MKbluSa28iwkE5YcnQbYd0=";			//1. 解base64
			bytes = base64Decode(content);
			//2. 取商户key的md5值，小写
			String key = MD5Encode("1234567890abcdef1234567890abcdef", "UTF-8").toLowerCase();
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
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding","BC");
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
