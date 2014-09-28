package controllers;

import java.util.List;

import dao.ActivityDAO;
import model.blog.Activity;
import model.user.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.permission.Identity;
import views.html.index;
import views.html.user.profile;

public class UserController extends Controller {

	@Transactional
	public static Result profile() {
		User user = Identity.getAuthenticatedUser();

		if(null == user) {
			flash("error", "Please login");
			return badRequest(index.render());
		}
		
		List<Activity> activities = ActivityDAO.find(user);
		
		return ok(profile.render(user, activities));
	}
}
