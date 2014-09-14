package model.training;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workout")
public class Workout implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -4477614447315278372L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	public String name;
	
	public String description;
	
	public Workout() {
		super();
	}

	public Workout(String name) {
		super();
		this.name = name;
	}
	
	public Workout(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
}
