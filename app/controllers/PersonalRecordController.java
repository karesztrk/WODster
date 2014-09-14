package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.journal.record.list;
import dao.PersonalRecordDAO;

public class PersonalRecordController extends Controller {

	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order, String filter) {
		return ok(list.render(PersonalRecordDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}
}
