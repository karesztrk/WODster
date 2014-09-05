package model.user;

/**
 * Gender type of a human.
 * 
 * @author Karesz
 */
public enum Gender {
	UNKNOWN(0), MALE(1), FEMALE(2);

	public int index;

	private Gender(int index) {
		this.index = index;
	}

	public static Gender resolve(int index) {
		for (Gender gender : values()) {
			if (index == gender.index) {
				return gender;
			}
		}
		return UNKNOWN;
	}
}
