package model.blog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.user.User;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "activity")
@Inheritance(strategy = InheritanceType.JOINED)
public class Activity implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 936641410592660374L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@Required
	@ManyToOne
	public User author;
    
    public Date date;
     
    @org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    public String content;
	
}
