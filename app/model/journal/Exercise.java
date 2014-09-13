package model.journal;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -4477614447315278372L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	public String name;

	public Exercise(String name) {
		super();
		this.name = name;
	}
	
}