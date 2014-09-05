package model.box;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

/**
 * The <i>Box</i> is the physical place where the athletes are doing their
 * workouts. In general it is a gym.
 */
@Entity
@Table(name = "box")
public class Box implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 2233641522157969072L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@Required
	public String name;

	public String description;

	public String photo;

	public String phone;

	public String website;
	
	@Email
	public String email;

	public String zip;

	public String country;

	public String city;

	public String street;
	
	public Double longitude;

	public Double latitude;

}
