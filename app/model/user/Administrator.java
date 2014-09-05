package model.user;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Administrator of the application who possess all permission.
 * 
 * @author Karesz
 */
@Entity
@Table(name = "administrator")
public class Administrator extends User {

	/** Serial version. */
	private static final long serialVersionUID = -9080663554302582370L;

	@Transient
	private static final String TYPE = Administrator.class.getSimpleName();

	@Override
	public String getType() {
		return TYPE;
	}
}
