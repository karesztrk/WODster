package controllers.entity;

import java.io.Serializable;

import model.user.User;
import play.data.validation.Constraints.Required;
import play.mvc.Controller;
import util.security.Security;

public class Login implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 7766112327831099783L;

	@Required
	public String email;

	@Required
	public String password;

	public String userType;

	// Somehow its invoked ... but cannot see how
	public String validate() {

		User user = User.findByEmail(email);
		if (null == user) {
			Controller.flash("error", "Unknown user");
			return "";
		}

		if (!Security.checkPassword(password, user.password)) {
			Controller.flash("error", "Invalid password");
			return ""; // means error?
		}

		userType = user.getType();

		return null;
	}
}