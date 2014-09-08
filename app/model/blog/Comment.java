package model.blog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.user.User;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

    /** Serial version. */
	private static final long serialVersionUID = -2994994091330003981L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@Required
	@ManyToOne
	public User author;
    
    public Date date;
     
    @org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    public String content;
    
    @ManyToOne
    @Required
    public Post post;
}
