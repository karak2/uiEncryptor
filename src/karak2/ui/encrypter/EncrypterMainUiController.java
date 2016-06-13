package karak2.ui.encrypter;


import java.io.UnsupportedEncodingException;

import javax.management.OperationsException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class EncrypterMainUiController {

	EncrypterViewModel vm = new EncrypterViewModel(new Encrypter());
	Encrypter model = new Encrypter();

	
	@FXML private Text actiontarget;
	@FXML private TextArea encryptedText;
	@FXML private PasswordField passwordField; 
	@FXML private TextArea plainText;
	@FXML private Button lockButton;
	@FXML private Button unlockButton;
	@FXML private TextField passwordReminder;
	@FXML private Label textBoxLabel_encryptedText;
	@FXML private Label textBoxLabel_plainText;
	
    
    @FXML protected void unlock(ActionEvent event) throws OperationsException, UnsupportedEncodingException {
    	plainText.setText(model.unlock(encryptedText.getText(), passwordField.getText()));
    	passwordReminder.setText(model.getPasswordReminder(encryptedText.getText()));
    	toStateUnlocked();
    }
    
    private void toStateUnlocked()
    {
    	plainText.setVisible(true);
    	lockButton.setVisible(true);
    	encryptedText.setVisible(false);
    	unlockButton.setVisible(false);
    	textBoxLabel_encryptedText.setVisible(false);
    	textBoxLabel_plainText.setVisible(true);
    }
    
    private void toStateLocked(){
    	plainText.setVisible(false);
    	lockButton.setVisible(false);
    	encryptedText.setVisible(true);
    	unlockButton.setVisible(true);
    	textBoxLabel_encryptedText.setVisible(true);
    	textBoxLabel_plainText.setVisible(false);
    }
    
    @FXML protected void lock(ActionEvent event) {
    	try {
			encryptedText.setText(model.lock(plainText.getText(), passwordField.getText(), passwordReminder.getText()));
			toStateLocked();
		} catch (OperationsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@FXML public void switchMode() 
	{
		if (encryptedText.isVisible())
		{
			toStateUnlocked();
		}
		else
		{
			toStateLocked();
		}
	}
	
}
