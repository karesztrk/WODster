package dao;

import java.util.List;

import model.box.Box;
import play.db.jpa.JPA;

public class BoxDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = -4781043848612844190L;

	/**
     * Return a page of computer
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy Computer property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
	public static Page<Box> page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(w) from Box w where lower(w.name) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        @SuppressWarnings("unchecked")
				List<Box> data = JPA.em()
            .createQuery("from Box w where lower(w.name) like ? order by w." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page<Box>(data, total, page, pageSize);
    }
	
	public static Box find(Long id) {
		return findById(Box.class, id);
	}
	
}
