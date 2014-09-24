package dao;

import java.util.List;

import model.training.Workout;
import play.db.jpa.JPA;

public class WorkoutDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = 7436903317411710204L;

	
	/**
	 * Looks up by id.
	 */
	public static Workout find(Long id) {
		return findById(Workout.class, id);
	}
	
	public static void delete(Long id) {
		JPA.em().createQuery("delete from Workout w where w.id = :id")
			.setParameter("id", id)
			.executeUpdate();
	}

	public static Page<Workout> page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(w) from Workout w where lower(w.title) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        @SuppressWarnings("unchecked")
				List<Workout> data = JPA.em()
            .createQuery("from Workout w where lower(w.title) like ? order by w." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page<Workout>(data, total, page, pageSize);
    }
	
	@SuppressWarnings("unchecked")
	public static List<Workout> find(String title) {
		List<Workout> workouts = (List<Workout>) JPA.em()
            .createQuery("from Workout w where lower(w.title) like :title")
            .setParameter("title", "%" + title.toLowerCase() + "%")
            .getResultList(); 
		return workouts;
	}
	
	public static void updateContent(Workout workout) {
		JPA.em()
			.createQuery("update Workout w set w.title = :title, w.content = :content where w.id = :id")
			.setParameter("id", workout.id)
			.setParameter("title", workout.title)
			.setParameter("content", workout.content)
			.executeUpdate();
	}
}
