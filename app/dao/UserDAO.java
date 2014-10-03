package dao;

import javax.persistence.NoResultException;

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
		User user = null;
		try {
			user = (User) JPA.em().createQuery("select user from User user where user.email = :email")
			.setParameter("email", email)
			.getSingleResult();
		} catch (NoResultException e) {
			play.Logger.warn("No user found for email {0}", email);
		}
		return user;
	}
	
	public static User find(Long id) {
		return findById(User.class, id);
	}
	
	public static boolean updatePassword(User user, String password) {
		int rows = JPA.em().createQuery("update User user set user.password = :password where user.id = :id")
			.setParameter("id", user.id)
			.setParameter("password", password)
			.executeUpdate();
		
		return rows > 0 ? true : false;
	}
}
