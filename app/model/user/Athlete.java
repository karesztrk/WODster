package model.user;

import java.io.Serializable;


/**
 * The most common user of the application.
 * 
 * @author Karesz
 */
//@Entity
//@Table(name = "athlete")
//@DiscriminatorValue("athlete")
public class Athlete implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 3950898570919998421L;

//	@Transient
//	private static final String TYPE = Athlete.class.getSimpleName();

	/** Height of the athlete. */
	public Double height;

	/** Weight of the athlete. */
	public Double weight;

	/** Gender of the athlete. */
	public Gender gender;

	/*@Override
	public String getType() {
		return TYPE;
	}*/
}
