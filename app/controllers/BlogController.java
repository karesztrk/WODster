package controllers;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.blog.Post;
import model.blog.Post.PostType;
import model.result.Attendance;
import model.user.User;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.asset.AssetUtils;
import util.exception.EmptyParameterException;
import util.permission.Identity;
import views.html.blog.comments;
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
				sortBy, order, filter, Form.form(Attendance.class)));
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
	@BodyParser.Of(util.http.type.parser.SimpleFormParser.class)
	public static Result attend() {
		
		util.http.request.SimpleForm values = request().body().as(util.http.request.SimpleForm.class);
		
		try {
			String note = values.get("note");
			String postId = values.getAsRequired("postId");
			String date = values.getAsRequired("date");
			
			User user = Identity.getAuthenticatedUser();
			
			if(null == user) {
				return badRequest("Please login");
			}
			
			Post post = PostDAO.find(Long.parseLong(postId));
			
			DateFormat format = new SimpleDateFormat(util.Configuration.DATE_PATTERN);
			
			Attendance attend = new Attendance();
			attend.participant = user;
			attend.subject = post;
			attend.note = note;

			attend.date = format.parse(date);
			
			AttendanceDAO.save(attend);
		} catch (EmptyParameterException e) {
			e.printStackTrace();
			return badRequest("Required paramter not found when executing 'attend' request: " + e.getParameterName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return badRequest("Required parameter contain invalid value");
		} catch (ParseException e) {
			e.printStackTrace();
			return badRequest("Required parameter contain invalid value");
		}

		return ok();
	}
	
	public static boolean isAttendee(Post post) {
		return AttendanceDAO.isAttendee(post, Identity.getAuthenticatedUser());
	}

	@Transactional
	@BodyParser.Of(util.http.type.parser.SimpleFormParser.class)
	public static Result comment() throws UnsupportedEncodingException {	
		util.http.request.SimpleForm values = request().body().as(util.http.request.SimpleForm.class);
		
		try {
			String postId = values.getAsRequired("postId");
			String content = values.getAsRequired("content");
			
			User user = Identity.getAuthenticatedUser();
			
			if(null == user) {
				return badRequest("Please login");
			}
			
			Post post = PostDAO.find(Long.parseLong(postId));
			post.addComment(user, content);
			
			PostDAO.save(post);
		
			return ok(comments.render(post));
		} catch (EmptyParameterException e) {
			e.printStackTrace();
			return badRequest("Required paramter not found when executing 'attend' request: " + e.getParameterName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return badRequest("Required parameter contain invalid value");
		}

	}
	
}
