package model.user;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

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

	/** Used email of the user. */
	@Id
	@Email
	public String email;

	/** Password of the user. */
	@Required
	public String password;

	/** Full name of the user. */
	@Required
	public String name; 

	/**
	 * Looks up a user by the email.
	 * 
	 * @param email
	 *            an email address
	 * @return the user or null
	 */
	public static User findByEmail(String email) {
		return JPA.em().find(User.class, email);
	}
	
    /**
     * Update this user.
     */
    public void update() {
        JPA.em().merge(this);
    }
    
    /**
     * Insert this new user.
     */
    public void save() {
        JPA.em().persist(this);
    }
    
    /**
     * Delete this user.
     */
    public void delete() {
        JPA.em().remove(this);
    }

	public String getType() {
		return TYPE;
	}
}
