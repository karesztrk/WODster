package model.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;

import model.user.social.SocialNetwork;
import model.user.social.SocialNetworkProfile;

import org.hibernate.validator.constraints.URL;


/**
 * The most common user of the application.
 * 
 * @author Karesz
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 7104598520249665143L;
	
	/** Unique identifier. */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	public Long id;
	
	@OneToOne
	public User user;

	/** Height of the user. */
	@Min(1)
	@play.data.validation.Constraints.Min(1)
	public Double height;

	/** Weight of the user. */
	@Min(1)
	@play.data.validation.Constraints.Min(1)
	public Double weight;

	/** Gender of the user. */
	public Gender gender;
	
	/** Site of the user. */
	@URL
	public String website;
	
	/** Date of birth of the user. */
	@Past
	public Date birth;
	
	/** Country of the user. */
	public String country;
	
	/** City of the user. */
	public String city;
	
	/** Used language by the user. */
	public String locale;
	
	/** Some details about the user. */
	@org.hibernate.annotations.Type(type="org.hibernate.type.StringClobType")
	public String about;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@MapKey(name = "type")
	@JoinColumns({
		@JoinColumn(name = "profile_id", referencedColumnName = "id")
	})
	public Map<SocialNetwork, SocialNetworkProfile> socialProfiles; 

}
