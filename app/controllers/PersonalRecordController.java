package controllers;

import model.journal.PersonalRecord;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.journal.record.create;
import views.html.journal.record.list;
import dao.PersonalRecordDAO;

public class PersonalRecordController extends Controller {

	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order, String filter) {
		return ok(list.render(PersonalRecordDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}
	
	@Transactional
	public static Result delete(Long id) {
		PersonalRecordDAO.delete(id);
		return redirect(routes.PersonalRecordController.list(0, "name", "asc", ""));
	}
	
	public static Result create() {
		Form<PersonalRecord> form = Form.form(PersonalRecord.class);
		return ok(create.render(form));
	}
	
	public static Result save() {
		return ok();
	}
}
