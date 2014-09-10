package controllers;

import model.blog.Post.PostType;
import play.db.jpa.Transactional;
import play.mvc.Result;

public class WODController extends BlogController {

	@Transactional
	public static Result save() {
		
		Result result = save(PostType.WOD);
		
		if(null != result) {
			return result;
		}

		flash("success", "Workout has been created");
		return redirect("/workouts");
	}
	
	@Transactional
	public static Result update(Long id) {
		Result result = update(id, PostType.WOD);
		if(null != result) {
			return result;
		}
		
		flash("success", "Workout has been updated");
		return redirect("/workouts/" + id);
	}
}
