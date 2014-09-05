package controllers;

import java.util.Date;

import model.blog.Post;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.asset.AssetUtils;
import util.permission.Identity;
import views.html.blog.create;
import views.html.blog.edit;
import views.html.blog.list;
import views.html.blog.view;
import dao.PostDAO;

public class BlogController extends Controller {

	@Transactional
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(list.render(PostDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter));
	}
	
	public static Result create() {
		Form<Post> form = Form.form(Post.class);
		return ok(create.render(form));
	}
	
	@Transactional
	public static Result view(Long id) {
		Post post = PostDAO.find(id);
		return ok(view.render(post));
	}
	
	@Transactional
	public static Result edit(Long id) {
		Form<Post> form = Form.form(Post.class).fill(PostDAO.find(id));
		return ok(edit.render(form));
	}
	
	@Transactional
	public static Result save() {
		Form<Post> form = Form.form(Post.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(create.render(form));
		}
		
		Post post = form.get();
		post.date = new Date();
		
		User user = Identity.getAuthenticatedUser();
		
		if(null == user) {
			flash("error", "Please login");
			return badRequest(create.render(form));
		}
		
		post.user = user;
		
		// Get the image
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart image = body.getFile("image");
		if (null != image) {
			String fileName = AssetUtils.moveFileToUploads(image.getFile(),
					image.getFilename(), AssetUtils.FileType.IMAGE);
			post.image = fileName;
		}
		
		PostDAO.save(post);
		flash("success", "Workout has been created");
		return redirect("/workouts");
	}
	
	@Transactional
	public static Result update(Long id) {
		Form<Post> form = Form.form(Post.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(edit.render(form));
		}
		
		Post post = form.get();
		post.id = id;
		PostDAO.updateContent(post);
		flash("success", "Workout has been updated");
		return redirect("/workouts/" + id);
	}

}
