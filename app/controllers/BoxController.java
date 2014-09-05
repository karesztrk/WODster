package controllers;

import dao.BoxDAO;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.box.list;

public class BoxController extends Controller {

	@Transactional(readOnly = true)
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(list.render(BoxDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}
}
