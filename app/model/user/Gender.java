package model.user;

/**
 * Gender type of a human.
 * 
 * @author Karesz
 */
public enum Gender {
	UNKNOWN, 
	MALE, 
	FEMALE;

	public String getValue() {
		return name();
	}
}
