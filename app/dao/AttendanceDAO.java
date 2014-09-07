package dao;

import model.blog.Post;
import model.user.User;
import play.db.jpa.JPA;

public class AttendanceDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = 6078391970240137615L;

	public static boolean isAttendee(Post post, User user) {
		long count = (Long) JPA.em().createQuery("select count(*) from Attendance att where att.subject = :post and att.participant = :user")
			.setParameter("post", post)
			.setParameter("user", user)
			.getSingleResult();

		return count > 0;
	}
}
