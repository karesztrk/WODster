package controllers;

import model.user.User;
import play.Routes;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import util.permission.Identity;
import util.security.Security;
import views.html.landing;
import views.html.login;
import views.html.loginForm;
import views.html.register;
import views.html.dashboard.home;
import controllers.entity.Login;
import dao.UserDAO;

import java.util.Locale;

public class Application extends Controller {

	/**
	 * Handle default path requests, redirect to computers list
	 */
	public static Result index() {
		
		if(Identity.isAuthenticated()) {
			return ok(home.render());
		} 
		
		return ok(landing.render());
	}
	
	public static Result login() {

        return ok(login.render(Form.form(Login.class)));
	}

	public static Result logout() {
		session().clear();
		return login();
	}
	
	@Transactional
	public static Result authenticate() {
		Form<Login> form = Form.form(Login.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(loginForm.render(form));
		} 
		
		session().clear();
		session("email", form.get().email);
		session("id", form.get().id.toString());
		session("userType", form.get().userType);
        session("locale", form.get().locale);
		return ok(home.render());
	}
	
	@Transactional
	public static Result register() {
		Form<User> form = Form.form(User.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(register.render(form));
		}

		User newUser = form.get();

		// TODO put into an authenticator
		if (null != UserDAO.find(newUser.email)) {
			form.reject("email", "User already exists");
			return badRequest(register.render(form));
		}

		String passwordConfirm = form.data().get("confirm");
		if (null == passwordConfirm || "".equals(passwordConfirm)) {
			form.reject("confirm", "register.error.password.confirm");
			return badRequest(register.render(form));
		} else if (!newUser.password.equals(passwordConfirm)) {
			form.reject("confirm","register.error.password.notMatch");
			return badRequest(register.render(form));
		}

		Security.secureUserPassword(newUser);

		UserDAO.save(newUser);

		session().clear();
		session("email", form.get().email);
		session("id", form.get().id.toString());
		session("userType", form.get().getType());
        session("locale", Locale.getDefault().toString());
		flash("welcome", "");
        return redirect(controllers.routes.Application.index());

	}
	
	public static Result signUp() {

        return ok(register.render(Form.form(User.class)));
	}
	
	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",

				controllers.routes.javascript.Application.index(),
                controllers.routes.javascript.Application.authenticate(),
                controllers.routes.javascript.UserController.changePassword(),
                controllers.routes.javascript.PersonalRecordController.fetch(),
                controllers.routes.javascript.PersonalRecordController.update(),
                controllers.routes.javascript.PersonalRecordController.edit(),
                controllers.routes.javascript.WODController.fetch()

		));
	}
}
