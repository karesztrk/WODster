package model.user;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NaturalId;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

/**
 * Represents the most basic type of the user in the application.
 * 
 * @author Karesz
 */
@Entity
// Dirty hack under PostgreSQL environment
@Table(name = "\"user\"")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 2224970112534231880L;

	@Transient
	private static final String TYPE = User.class.getSimpleName();
	
	/** Unique identifier. */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	/** Used email of the user. */
	@NaturalId
	@Email
	public String email;

	/** Password of the user. */
	@Required
	public String password;

	/** Full name of the user. */
	@Required
	public String name; 
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Profile profile;

	public String getType() {
		return TYPE;
	}
}
