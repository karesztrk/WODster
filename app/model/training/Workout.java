package model.training;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.blog.Post;

import common.IndexedEnum;

@Entity
@Table(name = "workout")
public class Workout extends Post {

	/** Serial version. */
	private static final long serialVersionUID = -4477614447315278372L;
	
	public boolean hero;
	
	public boolean personal;
	
	public boolean girl;

    public Category category;

	public ResultMeasurementType resultType;
	
	public Workout() {
		super();
	}

	public Workout(String name) {
		super();
		super.title = name;
		super.content = "";
	}
	
	public Workout(String name, String description) {
		super();
		super.title = name;
		super.content = description;
	}
}
