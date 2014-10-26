package controllers.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.common.base.Strings;
import dao.UserDAO;
import model.user.User;
import play.Logger;
import play.data.validation.Constraints.Required;
import play.data.validation.ValidationError;
import util.security.Security;

public class Login implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 7766112327831099783L;
	
	public Long id;

	@Required
	public String email;

	@Required
	public String password;

	public String userType;

    public String locale;

	// https://www.playframework.com/documentation/2.2.x/JavaForms
	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		errors.add(new ValidationError("email", ""));
		errors.add(new ValidationError("password", "Invalid email or password"));
		User user = UserDAO.find(email);

		if (null == user) {
			return errors;
		}

		if (!Security.checkPassword(password, user.password)) {
			return errors;
		}

		id = user.id;
		userType = user.getType();

        if(null != user.profile && !Strings.isNullOrEmpty(user.profile.locale)) {
            locale = user.profile.locale;
        } else {
            locale = Locale.getDefault().toString();
        }

		return null;
	}

}