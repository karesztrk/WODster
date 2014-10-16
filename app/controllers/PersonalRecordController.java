package controllers;

import java.util.List;

import model.journal.PersonalRecord;
import model.training.Workout;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import util.exception.EmptyParameterException;
import util.permission.Identity;
import views.html.journal.record.create;
import views.html.journal.record.edit;
import views.html.journal.record.list;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;

import dao.PersonalRecordDAO;
import dao.WorkoutDAO;

public class PersonalRecordController extends Controller {

	@Transactional(readOnly = true)
	public static Result list() {
		return ok(list.render());
	}
	
	@Transactional(readOnly = true)
	public static Result fetch() {
		
		User user = Identity.getAuthenticatedUser();
		
		List<PersonalRecord> records = PersonalRecordDAO.list(user);
		
		
		ObjectNode root = Json.newObject();
		root.put("aaData", Json.toJson(records));
		return ok(root);
	}
	
	public static Result create() {
		Form<PersonalRecord> form = Form.form(PersonalRecord.class);
		return ok(create.render(form));
	}
	
	@Transactional
	public static Result edit(Long id) {
		PersonalRecord pr = PersonalRecordDAO.find(id);
		
		Form<PersonalRecord> form = Form.form(PersonalRecord.class).fill(pr);
		return ok(edit.render(form));
	}
	
	@Transactional
	public static Result save() {
		Form<PersonalRecord> form = Form.form(PersonalRecord.class).bindFromRequest();
		
		PersonalRecord pr = form.get();
		pr.user = Identity.getAuthenticatedUser();
		
		String name = form.data().get("name");
		String workoutId = form.data().get("workout");
		
		// Name has higher precedence
		if(!Strings.isNullOrEmpty(name)) {
			
			// If name is defined the workout will be personal by default
			Workout workout = new Workout(name);
			workout.personal = true;
			workout.user = Identity.getAuthenticatedUser();
			WorkoutDAO.save(workout);
			pr.workout = workout;
		} else if(!Strings.isNullOrEmpty(workoutId)) { 
		
			
			pr.workout = WorkoutDAO.find(Long.parseLong(workoutId));
		} else {
			return badRequest("Couldn't handle request");
		}
		
		PersonalRecordDAO.save(pr);
		
		return ok(list.render());
	}
	
	@Transactional
	public static Result update() {
		
		Form<PersonalRecord> form = Form.form(PersonalRecord.class).bindFromRequest();
		
		PersonalRecordDAO.update(form.get());
		
		return ok(list.render()); 
	}
	
	@Transactional
	@BodyParser.Of(util.http.type.parser.SimpleFormParser.class)
	public static Result delete() {
		
		util.http.request.SimpleForm values = request().body().as(util.http.request.SimpleForm.class);
		
		try {
			String id = values.getAsRequired("id");
			PersonalRecordDAO.delete(Long.valueOf(id));
		} catch (EmptyParameterException e) {
			return badRequest("Required data not found");
		}
		
		return ok(list.render()); 
	}
}
