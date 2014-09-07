package controllers;

import java.util.Date;
import java.util.Map;

import model.blog.Post;
import model.blog.Post.PostType;
import model.result.Attendance;
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
import dao.AttendanceDAO;
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
		
		Result result = save(PostType.SIMPLE);
		if(null != result) {
			return result;
		}
		
		flash("success", "Post has been created");
		return redirect("/workouts");
	}
	
	protected static <T extends Post> Result save(PostType type) {
		Form<Post> form = Form.form(Post.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(create.render(form));
		}
		
		Post post = form.get();
		post.date = new Date();
		post.type = type;
		
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
		
		// Yeah ... null means OK 
		return null;
	}
	
	@Transactional
	public static Result update(Long id) {
		
		Result result = update(id, PostType.SIMPLE);
		if(null != result) {
			return result;
		}
		
		flash("success", "Post has been updated");
		return redirect("/workouts/" + id);
	}
	
	protected static Result update(Long id, PostType type) {
		Form<Post> form = Form.form(Post.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(edit.render(form));
		}
		
		Post post = form.get();
		post.id = id;
		PostDAO.updateContent(post);

		// Yeah ... null means OK 
		return null;
	}
	
	@Transactional
	public static Result attend() {
		
		Map<String,String[]> values = request().body().asFormUrlEncoded();
		
		String[] data = values.get("postId");
		
		if(null == data || data.length == 0) {
			return badRequest("No post data found");
		}
		
		Long postId = Long.parseLong(data[0]);
			
		if(null == postId) {
			return badRequest("No post data found");
		}
		
		User user = Identity.getAuthenticatedUser();
		
		if(null == user) {
			return badRequest("Please login");
		}
		
		Post post = PostDAO.find(postId);
		
		Attendance attend = new Attendance();
		attend.participant = user;
		attend.subject = post;
		
		AttendanceDAO.save(attend);

		return ok();
	}
	
	public static boolean isAttendee(Post post) {
		return AttendanceDAO.isAttendee(post, Identity.getAuthenticatedUser());
	}

}
