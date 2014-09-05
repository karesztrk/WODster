package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;

@Entity
@Table(name = "workout")
public class Workout implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -4515044294659609056L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	public String name;

	@DateTime(pattern="yyyy-MM-dd") 
	public Date date;

	@Required
	public String note;
	
	public String photo;
	
}
