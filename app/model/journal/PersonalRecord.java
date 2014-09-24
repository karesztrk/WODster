package model.journal;

import javax.persistence.Entity;
import javax.persistence.Table;

import model.training.result.Attendance;

import org.hibernate.annotations.Formula;

@Entity
@Table(name = "personal_record")
public class PersonalRecord extends Attendance {
	
	/** Serial version. */
	private static final long serialVersionUID = 451348973369598029L;
	
	@Formula(" (select p.title from post p where p.id = workout_id) ") 
	public String name;

}
