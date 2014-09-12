package controllers.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.user.User;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import util.security.Security;

public class Login implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 7766112327831099783L;

	@Required
	public String email;

	@Required
	public String password;

	public String userType;

	// https://www.playframework.com/documentation/2.2.x/JavaForms
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		errors.add(new ValidationError("email", ""));
		errors.add(new ValidationError("password", "Invalid email or password"));
		User user = User.findByEmail(email);

		if (null == user) {
			return errors;
		}

		if (!Security.checkPassword(password, user.password)) {
			return errors;
		}

		userType = user.getType();

		return null;
	}
}