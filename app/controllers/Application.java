package controllers;

import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.security.Security;
import views.html.index;
import views.html.login;
import views.html.register;
import controllers.entity.Login;

public class Application extends Controller {

	/**
	 * Handle default path requests, redirect to computers list
	 */
	public static Result index() {
		return ok(index.render());
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
			return badRequest(login.render(form));
		} else {
			session().clear();
			session("email", form.get().email);
			session("userType", form.get().userType);
			flash("welcome", "Welcome to WODster");
			return ok(index.render());
		}
	}
	
	@Transactional
	public static Result register() {
		Form<User> form = Form.form(User.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(register.render(form));
		}

		User newUser = form.get();

		// TODO put into an authenticator
		if (null != User.findByEmail(newUser.email)) {
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

		form.get().save();

		session().clear();
		session("email", form.get().email);
		session("userType", form.get().getType());
		flash("welcome", "");
		return redirect(routes.Application.index());

	}
	
	public static Result signUp() {
		return ok(register.render(Form.form(User.class)));
	}

	/*
	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(list.render(WorkoutDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}

	public static Result edit(Long id) {
		Form<Workout> WorkoutForm = Form.form(Workout.class).fill(
				WorkoutDAO.findById(id));
		return ok(editForm.render(id, WorkoutForm));
	}

	public static Result update(Long id) {
		Form<Workout> WorkoutForm = Form.form(Workout.class).bindFromRequest();
		if (WorkoutForm.hasErrors()) {
			return badRequest(editForm.render(id, WorkoutForm));
		}
		
		WorkoutDAO.update(WorkoutForm.get());
		flash("success", "Workout " + WorkoutForm.get().name
				+ " has been updated");
		return ok(index.render());
	}

	public static Result create() {
		Form<Workout> WorkoutForm = Form.form(Workout.class);
		return ok(createForm.render(WorkoutForm));
	}

	@Transactional
	public static Result save() {
		Logger.info("Saving...");
		Form<Workout> WorkoutForm = Form.form(Workout.class).bindFromRequest();
		if (WorkoutForm.hasErrors()) {
			return badRequest(createForm.render(WorkoutForm));
		}
		
		WorkoutDAO.save(WorkoutForm.get());
		flash("success", "Workout " + WorkoutForm.get().name
				+ " has been created");
		return ok(index.render());
	}

	@Transactional
	public static Result delete(Long id) {
		
		WorkoutDAO.delete(id);
		flash("success", "Workout has been deleted");
		return ok(index.render());
	}*/
}
