package model.blog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import model.user.User;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "post")
public class Post implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -8852844815128062876L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@Required
	public String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;
	
	@ManyToOne
	public User user;
	
	public String image;
	
	@org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    public String content;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	public List<Comment> comments;
	
	public enum PostType {
		SIMPLE,
		WOD
	}
	
	public PostType type;
	
	public void addComment(User author, String content) {
		
		if(null == comments) {
			comments = new ArrayList<Comment>();
		}
		
	    Comment comment = new Comment();
	    comment.author = author;
	    comment.content = content;
	    comment.date = new Date();
	    comment.post = this;
	    comments.add(comment);
	}
}
