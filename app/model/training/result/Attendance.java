package model.training.result;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import model.journal.PersonalRecord;
import model.training.Workout;
import model.user.User;
import play.data.format.Formats.DateTime;
import play.data.validation.Constraints.Required;
import util.Configuration;

@Entity
@Table(name = "attendance")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Attendance implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -2348654980412221407L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@OneToOne
	@Required
	public User user;
	
	@Required
	@NotNull
	public long result;
	
    @org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
    public String note;
    
    @Required
    @DateTime(pattern = Configuration.DATE_PATTERN)
    @Temporal(TemporalType.DATE)
    public Date date;
	
    @OneToOne
	@Required
	public Workout workout;
    
    public PersonalRecord toPersonalRecord() {
    	PersonalRecord record = new PersonalRecord();
    	record.date = this.date;
    	record.note = this.note;
    	record.result = this.result;
    	record.user = this.user;
    	record.workout = this.workout;
    	return record;
    }
    
}
