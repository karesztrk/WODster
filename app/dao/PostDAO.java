package dao;

import java.util.List;

import model.blog.Post;
import play.db.jpa.JPA;

public class PostDAO extends AbstractDAO {

	/** Serial version. */
	private static final long serialVersionUID = -3001030328659683470L;

	public static Page<Post> page(int page, int pageSize, String sortBy, String order, String filter) {
        if(page < 1) page = 1;
        Long total = (Long)JPA.em()
            .createQuery("select count(p) from Post p where lower(p.title) like ?")
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .getSingleResult();
        @SuppressWarnings("unchecked")
				List<Post> data = JPA.em()
            .createQuery("from Post p where lower(p.title) like ? order by p." + sortBy + " " + order)
            .setParameter(1, "%" + filter.toLowerCase() + "%")
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        return new Page<Post>(data, total, page, pageSize);
    }
	
	public static void updateContent(Post post) {
		JPA.em()
			.createQuery("update Post p set p.title = :title, p.content = :content where p.id = :id")
			.setParameter("id", post.id)
			.setParameter("title", post.title)
			.setParameter("content", post.content)
			.executeUpdate();
	}

	public static Post find(Long id) {
		return findById(Post.class, id);
	}
	
	public static Post find(String title) {
		Post post = (Post) JPA.em()
            .createQuery("from Post p where lower(p.title) like :title")
            .setParameter("title", "%" + title.toLowerCase() + "%")
            .getSingleResult();
		return post;
	}
}
