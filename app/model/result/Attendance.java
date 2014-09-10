package model.result;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import util.Configuration;
import model.blog.Post;
import model.user.User;

@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -2348654980412221407L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@ManyToOne
	@Required
	public User participant;
	
	public String result;
	
    @org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    public String note;
    
    @Required
    @DateTime(pattern = Configuration.DATE_PATTERN)
    @Temporal(TemporalType.DATE)
    public Date date;
	
	@ManyToOne
	@Required
	public Post subject;
}
