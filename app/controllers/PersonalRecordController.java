package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.journal.record.list;

public class PersonalRecordController extends Controller {

	public static Result list() {
		return ok(list.render());
	}
}
