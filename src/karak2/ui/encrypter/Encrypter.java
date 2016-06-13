package karak2.ui.encrypter;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.management.InvalidAttributeValueException;
import javax.management.OperationsException;

import karak2.core.encrypter.AesPasswordEncypter;
import karak2.core.encrypter.EncryptedData;
import karak2.core.encrypter.MapToTextSerializer;
import karak2.core.encrypter.Secret;

public class Encrypter {

	private String currentPass;
	private Secret currentSecret;
	
	public String unlock(String encryptedText, String password) throws OperationsException {
		try {
			Secret secret;
			Map<String, String> map = MapToTextSerializer.Deserialize(encryptedText);
			EncryptedData ed = EncryptedData.fromSerializedMap(map);
			secret = Secret.CreateDecryption(password, ed);
			return AesPasswordEncypter.Decrypt(secret, ed);
		} catch (InvalidAttributeValueException | NoSuchAlgorithmException | InvalidKeySpecException
				| IllegalArgumentException | InvalidKeyException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | UnsupportedEncodingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			throw new OperationsException(e.toString());
		}
	}

	public String lock(String plainText, String password, String passwordReminder) throws OperationsException {
		try {
			if (!password.equals(currentPass))
			{
				currentPass = password;
				currentSecret = Secret.CreateForNewEncryption(currentPass);	
			}
			EncryptedData ed = AesPasswordEncypter.Encrypt(currentSecret, plainText);
			ed.setPasswordReminder(passwordReminder);
			HashMap<String, String> map = new HashMap<String, String>();
			ed.toSerializedMap(map);
			return MapToTextSerializer.Serialize(map);
		} catch (InvalidAttributeValueException | NoSuchAlgorithmException | InvalidKeySpecException
				| IllegalArgumentException | InvalidKeyException | NoSuchPaddingException
				| InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			throw new OperationsException(e.toString());
		}

	}

	public String changePassword(String encryptedText, String oldpassword, String newpasswrod) {
		return "recrypted";
	}

	public String getPasswordReminder(String encryptedText) throws UnsupportedEncodingException {
		Map<String, String> map = MapToTextSerializer.Deserialize(encryptedText);
		EncryptedData ed = EncryptedData.fromSerializedMap(map);
		return ed.getPasswordReminder();
	}
}
