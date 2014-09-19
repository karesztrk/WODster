package model.training;

import javax.persistence.Entity;
import javax.persistence.Table;

import common.IndexedEnum;
import play.data.validation.Constraints.Required;
import model.blog.Post;

@Entity
@Table(name = "workout")
public class Workout extends Post {

	/** Serial version. */
	private static final long serialVersionUID = -4477614447315278372L;
	
	public boolean hero;
	
	public boolean personal;
	
	public enum ResultMeasurementType implements IndexedEnum {
		UNKNOWN,
		TIME,
		REPETITION;

		public String getValue() {
			return name();
		}
	}
	
	@Required
	public ResultMeasurementType resultType;
	
	public Workout() {
		super();
	}

	public Workout(String name) {
		super.title = name;
	}
	
	public Workout(String name, String description) {
		super();
		super.title = name;
		super.content = description;
	}
}
