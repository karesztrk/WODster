package dao;

import java.util.List;

import model.journal.PersonalRecord;
import model.training.result.Attendance;
import play.db.jpa.JPA;

public class PersonalRecordDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = -2175121066418171497L;
	
	@SuppressWarnings("unchecked")
	public static PersonalRecord findRecord(Attendance attendance) {
		if(null == attendance.workout) {
			throw new IllegalArgumentException("Workout of the attendance is mandatory");
		}
		
		List<PersonalRecord> records = (List<PersonalRecord>) JPA.em().createQuery("select p from PersonalRecord p where p.name = :name and result < :result order by p.date")
				.setParameter("name", attendance.workout.title)
				.setParameter("result", attendance.result)
				.getResultList();
		
		return records.isEmpty() ? null : records.get(0);
	}

	public static Page<PersonalRecord> page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(p) from PersonalRecord p where lower(p.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        @SuppressWarnings("unchecked")
				List<PersonalRecord> data = JPA.em()
            .createQuery("from PersonalRecord p where lower(p.name) like ? order by p." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page<PersonalRecord>(data, total, page, pageSize);
	}
	
	public static void delete(Long id) {
		JPA.em().createQuery("delete from PersonalRecord p where p.id = :id")
			.setParameter("id", id)
			.executeUpdate();
	}
}
