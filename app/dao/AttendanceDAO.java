package dao;

import model.training.Workout;
import model.user.User;
import play.db.jpa.JPA;

public class AttendanceDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = 6078391970240137615L;

	public static boolean isAttendee(Workout workout, User user) {
		long count = (Long) JPA.em().createQuery("select count(*) from Attendance att where att.workout = :workout and att.user = :user")
			.setParameter("workout", workout)
			.setParameter("user", user)
			.getSingleResult();

		return count > 0;
	}

}
