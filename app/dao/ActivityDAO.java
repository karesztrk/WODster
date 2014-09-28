package dao;

import java.util.List;

import model.blog.Activity;
import model.user.User;
import play.db.jpa.JPA;

public class ActivityDAO extends AbstractDAO { 

	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -6473438775916677507L; 

	@SuppressWarnings("unchecked")
	public static List<Activity> find(User user) {
		return (List<Activity>) JPA.em().createQuery("select act from Activity act where act.author = :user")
				.setParameter("user", user)
				.getResultList();
	}
}
