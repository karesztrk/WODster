package controllers;

import java.util.Date;
import java.util.List;

import model.blog.Activity;
import model.user.Profile;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.asset.AssetUtils;
import util.exception.EmptyParameterException;
import util.permission.Identity;
import util.security.Security;
import views.html.index;
import views.html.user.activities;
import views.html.user.passwordChange;
import views.html.user.profile;
import views.html.user.settings;
import controllers.entity.PasswordChange;
import dao.ActivityDAO;
import dao.UserDAO;

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
		
		return settings(user);
	}
	
	public static Result settings(User user) { 
		return settings(user, null);
	}
	
	public static Result settings(User user, Form<PasswordChange> passwordChangeForm) { 
		Form<User> userForm = Form.form(User.class).fill(user);
		Form<Profile> profileForm = null == user.profile ? Form.form(Profile.class) : Form.form(Profile.class).fill(user.profile);

		Form<PasswordChange> passwordForm = null == passwordChangeForm ? Form.form(PasswordChange.class) : passwordChangeForm;
		return ok(settings.render(userForm, profileForm, passwordForm));
	}
	
	@Transactional
	public static Result save() {
		
		Form<Profile> profileForm = Form.form(Profile.class).bindFromRequest();
		MultipartFormData body = request().body().asMultipartFormData();
		
		User user = Identity.getAuthenticatedUser();
		
		if(null == user) {
			flash("error", "Please login");
			return badRequest(index.render());
		}
		
		// Update the user data
		user.name = body.asFormUrlEncoded().get("name")[0];
		
		// Save or update the profile
		if(null == user.profile) {
			user.profile = profileForm.get();
		} else {
			Profile newProfile = profileForm.get();
			user.profile.birth = newProfile.birth;
			user.profile.gender = newProfile.gender;
			user.profile.country = newProfile.country;
			user.profile.city = newProfile.city;
			user.profile.website = newProfile.website;
			user.profile.about = newProfile.about;
			
			
		}

		FilePart image = body.getFile("image");
		if (null != image) {
			user.profile.image = AssetUtils.moveFileToUploads(image.getFile(), image.getFilename(), AssetUtils.FileType.IMAGE);
		}
		
		UserDAO.save(user);
		
		return settings(user); 
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
			play.Logger.error("Sharing failed", e);
			return badRequest("Required paramter not found when executing 'attend' request: " + e.getParameterName());
		}
	}
	
	@Transactional
	public static Result changePassword() {
		Form<PasswordChange> form = Form.form(PasswordChange.class).bindFromRequest();

		if (form.hasErrors()) {
			form.data().clear();
			return badRequest(passwordChange.render(form));
		} 

		UserDAO.updatePassword(form.get().user, Security.getSecurePassword(form.get().newPassword));
		
		form.data().clear();
		return ok(passwordChange.render(form));
	}
}
