package dao;

import play.db.jpa.JPA;
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
		return (User) JPA.em().createQuery("select user from User user where user.email = :email")
				.setParameter("email", email)
				.getSingleResult();
	}
	
	public static User find(Long id) {
		return findById(User.class, id);
	}
}
