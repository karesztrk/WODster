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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import model.user.User;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
public class Post implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -8852844815128062876L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@Required
	@NotEmpty
	public String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Required
	@NotNull
	public Date createdAt = new Date();
	
	@ManyToOne
	@JsonIgnore
	public User user;
	
	public String image;
	
	@org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
	@JsonIgnore
    public String content;
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
	@JsonIgnore
	public List<Comment> comments;

    @Formula(" (select count(*) from comment c where c.post_id = id) ")
    public Integer commentCount;

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
