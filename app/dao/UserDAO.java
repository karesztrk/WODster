package dao;

import model.user.User;

public class UserDAO extends AbstractDAO {

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -6982233927213571947L;

	/**
	 * Looks up a user by the email.
	 * 
	 * @param email
	 *            an email address
	 * @return the user or null
	 */
	public static User find(String email) {
		return findById(User.class, email);
	}
}
