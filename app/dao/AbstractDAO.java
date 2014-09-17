package dao;

import java.io.Serializable;
import java.util.List;

import play.db.jpa.JPA;

public abstract class AbstractDAO implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 1638052167835026995L;

	/**
	 * Update this.
	 */
	public static void update(Serializable entity) {
		JPA.em().merge(entity);
	}

	/**
	 * Insert this.
	 */
	public static void save(Serializable entity) {
		JPA.em().persist(entity);
	}

	/**
	 * Delete this workout.
	 */
	public static void delete(Serializable entity) {
		JPA.em().remove(entity);
	}
	
	public static <T extends Serializable, ID extends Serializable> T findById(Class<T> entityType, ID id) {
		return JPA.em().find(entityType, id); 
	}
	
	/**
     * Used to represent a page.
     */
    public static class Page<T> {
        
        private final int pageSize;
        private final long totalRowCount;
        private final int pageIndex;
        
        private final List<T> list;
        
        public Page(List<T> data, long total, int page, int pageSize) {
            this.list = data;
            this.totalRowCount = total;
            this.pageIndex = page;
            this.pageSize = pageSize;
        }
        
        public long getTotalRowCount() {
            return totalRowCount;
        }
        
        public int getPageIndex() {
            return pageIndex;
        }
        
        public List<T> getList() {
            return list;
        }
        
        public boolean hasPrev() {
            return pageIndex > 1;
        }
        
        public boolean hasNext() {
            return (totalRowCount/pageSize) >= pageIndex;
        }
        
        public String getDisplayXtoYofZ() {
        	if(0 == totalRowCount) {
        		return ""; 
        	}
        	
            int start = ((pageIndex - 1) * pageSize + 1);
            int end = start + Math.min(pageSize, list.size()) - 1;
            return start + " to " + end + " of " + totalRowCount;
        }
        
    } 

}
