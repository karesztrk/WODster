package controllers;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.journal.PersonalRecord;
import model.training.Workout;
import model.training.Workout.ResultMeasurementType;
import model.training.result.Attendance;
import model.user.User;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import play.Logger;
import play.data.Form;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import util.asset.AssetUtils;
import util.exception.EmptyParameterException;
import util.permission.Identity;
import views.html.wod.comments;
import views.html.wod.create;
import views.html.wod.edit;
import views.html.wod.list;
import views.html.wod.view;
import dao.AttendanceDAO;
import dao.PersonalRecordDAO;
import dao.WorkoutDAO;

public class WODController extends BlogController {

	@Transactional
	public static Result save() {
		
		Form<Workout> form = Form.form(Workout.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(create.render(form));
		}
		
		Workout post = form.get();
		post.createdAt = new Date();

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
		
		WorkoutDAO.save(post);

		flash("success", "Workout has been created");
		return redirect("/workouts");
	}
	
	@Transactional
	public static Result update(Long id) {
		Form<Workout> form = Form.form(Workout.class).bindFromRequest();
		if (form.hasErrors()) {
			return badRequest(edit.render(id, form));
		}
		
		Workout post = form.get();
		post.id = id;
		WorkoutDAO.updateContent(post);
		
		flash("success", "Workout has been updated");
		return redirect("/workouts/" + id);
	}
	
	@Transactional
	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(list.render(WorkoutDAO.page(page, 10, sortBy, order, filter),
				sortBy, order, filter, Form.form(Attendance.class)));
	}
	
	public static Result create() {
		Form<Workout> form = Form.form(Workout.class);
		return ok(create.render(form));
	}
	
	@Transactional
	public static Result view(Long id) {
		Workout post = WorkoutDAO.find(id);
		return ok(view.render(post));
	}
	
	@Transactional
	public static Result edit(Long id) {
		Form<Workout> form = Form.form(Workout.class).fill(WorkoutDAO.find(id));
		Logger.info(form.toString());
		return ok(edit.render(id, form));
	}
	
	@Transactional
	@BodyParser.Of(util.http.type.parser.SimpleFormParser.class)
	public static Result attend() {
		
		util.http.request.SimpleForm values = request().body().as(util.http.request.SimpleForm.class);
		
		try {
			String note = values.get("note");
			String postId = values.getAsRequired("wodId");
			String date = values.getAsRequired("date");
			String result = values.get("result");
			String timeResult = values.get("timeResult");

			User user = Identity.getAuthenticatedUser();
			
			if(null == user) {
				return badRequest("Please login");
			}
			
			if(!StringUtils.isEmpty(result) && StringUtils.isEmpty(timeResult)) {
				return badRequest("Didn't receive any result");
			}
			
			Workout workout = WorkoutDAO.find(Long.parseLong(postId));

			Attendance attend = new Attendance();
			attend.user = user;
			attend.workout = workout;
			attend.note = note;
			
			if(ResultMeasurementType.TIME == workout.resultType) {
				DateFormat timeFormat = new SimpleDateFormat(util.Configuration.TIME_PATTERN);
				Date time = timeFormat.parse(timeResult);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(time);
				attend.result = DateUtils.getFragmentInSeconds(cal, Calendar.DATE);
			} else {
				attend.result = Long.valueOf(result);
			}

			DateFormat dateFormat = new SimpleDateFormat(util.Configuration.DATE_PATTERN);
			attend.date = dateFormat.parse(date);
			
			AttendanceDAO.save(attend);
			
			// Yet automatically add as a new record if can
			if(workout.hero || workout.girl) {
				PersonalRecord record = PersonalRecordDAO.findRecord(attend);

				if(null == record) {
					play.Logger.info("Saving new record...");
					PersonalRecordDAO.save(attend.toPersonalRecord());
				} else {
					play.Logger.info("Updating new record...");
					record.result = attend.result;
					PersonalRecordDAO.update(record);
				}
				
			}
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
	
	
	public static boolean isAttendee(Workout post) {
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
			
			Workout post = WorkoutDAO.find(Long.parseLong(postId));
			post.addComment(user, content);
			
			WorkoutDAO.save(post);
		
			return ok(comments.render(post));
		} catch (EmptyParameterException e) {
			e.printStackTrace();
			return badRequest("Required paramter not found when executing 'attend' request: " + e.getParameterName());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return badRequest("Required parameter contain invalid value");
		}

	}
	
	@Transactional(readOnly = true)
	public static Result fetch() {
		
		User user = Identity.getAuthenticatedUser();
		
		List<Workout> workouts = WorkoutDAO.list(user);

		return ok(Json.toJson(workouts));
	}
}
