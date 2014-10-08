package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import model.journal.PersonalRecord;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import util.permission.Identity;
import views.html.journal.record.create;
import views.html.journal.record.list;
import dao.PersonalRecordDAO;

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
	
	@Transactional
	public static Result delete(Long id) {
		PersonalRecordDAO.delete(id);
		return list();
	}
	
	public static Result create() {
		Form<PersonalRecord> form = Form.form(PersonalRecord.class);
		return ok(create.render(form));
	}
	
	public static Result save() {
		
		
		
		return ok(list.render());
	}
}
