package util.security;

import model.user.User;

import com.lambdaworks.crypto.SCryptUtil;

public class Security {

	public static User secureUserPassword(User user) {
		user.password = getSecurePassword(user.password);
		return user;
	}

	public static String getSecurePassword(String password) {
		return SCryptUtil.scrypt(password, 16, 8, 8);
	}

	public static boolean checkPassword(String password, String encrypedPassword) {
		return SCryptUtil.check(password, encrypedPassword);
	}

}
