package controllers;

import model.user.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.user.profile;

public class UserController extends Controller {

	@Transactional
	public static Result profile() {
		User user = User.findByEmail(session().get("email"));
		return ok(profile.render(user));
	}
}
