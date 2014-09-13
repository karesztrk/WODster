package model.journal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Constraints.Required;
import model.blog.Post;
import model.user.User;

@Entity
@Table(name = "personal_record")
public class PersonalRecord implements Serializable {
	
	/** Serial version. */
	private static final long serialVersionUID = 451348973369598029L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	@OneToOne
	@Required
	public User user;
	
	@Required
	public Long result;

	@OneToOne
	public Post post;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Required
	public Date date;
	
}
