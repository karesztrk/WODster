package controllers;

import java.util.Date;
import java.util.List;

import controllers.entity.Login;
import dao.ActivityDAO;
import dao.UserDAO;
import model.blog.Activity;
import model.user.Profile;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import util.exception.EmptyParameterException;
import util.permission.Identity;
import views.html.index;
import views.html.user.profile;
import views.html.user.settings;
import views.html.user.activities;

public class UserController extends Controller {

	@Transactional
	public static Result profile(Long id) {
		User user = UserDAO.find(id);

		if(null == user) {
			flash("error", "Unknown user");
			return badRequest(index.render());
		}
		
		List<Activity> activities = ActivityDAO.find(user);
		
		return ok(profile.render(user, activities));
	}
	
	@Transactional
	public static Result settings() {
		User user = Identity.getAuthenticatedUser();
		
		if(null == user) {
			flash("error", "Please login");
			return badRequest(index.render());
		}

		
		return ok(settings.render(user, Form.form(Profile.class)));
	}
	
	@Transactional
	@BodyParser.Of(util.http.type.parser.SimpleFormParser.class)
	public static Result share() {
		util.http.request.SimpleForm values = request().body().as(util.http.request.SimpleForm.class);
		
		try {
			String content = values.getAsRequired("content");
			
			User user = Identity.getAuthenticatedUser();
			
			if(null == user) {
				return badRequest("Please login");
			}
			
			Activity act = new Activity();
			act.author = user;
			act.content = content;
			act.date = new Date();
			
			ActivityDAO.save(act);
			List<Activity> acts = ActivityDAO.find(user);
			
			return ok(activities.render(acts));
		} catch (EmptyParameterException e) {
			e.printStackTrace();
			return badRequest("Required paramter not found when executing 'attend' request: " + e.getParameterName());
		}
	}
}
