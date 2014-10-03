package controllers.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;

import model.user.User;
import play.data.validation.ValidationError;
import util.permission.Identity;
import util.security.Security;

public class PasswordChange implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -5147216103367161567L;

	public String oldPassword;
	
	public String newPassword;
	
	public String confirmation;
	
	public User user;
	
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		
		User authenticatedUser = Identity.getAuthenticatedUser();
		
		if(null == authenticatedUser) {
			errors.add(new ValidationError("password", "Unknown user"));
			return errors;
		}
		
		if(!isNewPasswordValid()) {
			errors.add(new ValidationError("newPassword", ""));
			errors.add(new ValidationError("confirmation", "Invalid values found"));
			return errors;
		}

		if (!Security.checkPassword(oldPassword, authenticatedUser.password)) {
			errors.add(new ValidationError("oldPassword", "Wrong password"));
			return errors;
		}
		
		this.user = authenticatedUser;
		
		return null;
	}
	
	private boolean isNewPasswordValid() {
		return !Strings.isNullOrEmpty(newPassword) && !Strings.isNullOrEmpty(confirmation) && 
				confirmation.equals(newPassword);
	}
}
