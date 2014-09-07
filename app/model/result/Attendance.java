package model.result;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import model.blog.Post;
import model.user.User;

@Entity
@Table(name = "attendance")
public class Attendance implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -2348654980412221407L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;

	@ManyToOne
	public User participant;
	
	@ManyToOne
	public Post subject;
}
