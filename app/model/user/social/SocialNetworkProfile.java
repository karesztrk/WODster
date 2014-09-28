package model.user.social;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "social_profile")
public class SocialNetworkProfile implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = -1783388014305824586L;

	/** Unique identifier. */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	public SocialNetwork type;
	
	/** Unique identifier of the owner inside the 3rd party system. */
	public String socialProfileId;

	public long stat = 0;
}


