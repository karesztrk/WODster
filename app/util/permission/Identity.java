package util.permission;

import java.io.Serializable;

import model.user.User;
import dao.UserDAO;
import play.mvc.Controller;

public class Identity implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -5571082323051268789L;

	public static boolean isAuthenticated() {
		String email = Controller.session().get("email");
		return null != email && !email.isEmpty();
	}
	
	public static User getAuthenticatedUser() {
		
		String userEmail = getAuthenticatedUserEmail();
		if(null == userEmail || userEmail.isEmpty()) {
			return null;
		}
		
		return UserDAO.find(userEmail);
	}
	
	public static Long getAuthenticatedUserId() {
        return Long.parseLong(Controller.session().get("id"));
	}
	
	public static String getAuthenticatedUserEmail() {
        return Controller.session().get("email");
	}
}
