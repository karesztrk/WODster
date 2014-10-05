package controllers;

import java.util.Date;

import model.blog.Post;
import model.box.Box;
import model.journal.PersonalRecord;
import model.training.Workout;
import model.training.Workout.ResultMeasurementType;
import model.user.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.security.Security;
import dao.BoxDAO;
import dao.PersonalRecordDAO;
import dao.PostDAO;
import dao.UserDAO;
import dao.WorkoutDAO;

public class DataGenerator extends Controller {

	@Transactional
	public static Result saveUsers() {
		
		User user = new User();
		user.email = "torok.karoly.krisztian@gmail.com";
		user.password = Security.getSecurePassword("12345");
		user.name = "Karoly Torok";
		
		UserDAO.save(user);
		
		session().clear();
		return redirect(routes.Application.login());
	}
	
	@Transactional
	public static Result saveBoxes() {
		
		Box box = new Box();
		box.name = "Testakadémia";
		box.city = "Szeged";
		box.country = "HU";
		box.email = "testakademiaszeged@email.hu";
		box.website = "http://www.testakademiaszeged.hu";
		
		BoxDAO.save(box);
		
		session().clear();
		return redirect(routes.Application.login());
	}
	
	@Transactional
	public static Result savePosts() {
		
		saveUsers();
		
		Post post = new Post();
		post.date = new Date();
		post.content = "<p>A. <br/> Every minute, on the minute, for 15 minutes: <br/> Clean <br/> <ul><li>Minutes 1-3 – 55-65%</li> <li>Minutes 4-6 – 65-75%</li> <li>Minutes 7-9 – 75-80%</li> <li>Minutes 10-12 – 80-85%</li> <li>Minutes 13-15 – 85-90%</li></ul> </p>";
		post.image = "";
		post.title = "Hello world";
		post.user = UserDAO.find("torok.karoly.krisztian@gmail.com");
		
		PostDAO.save(post);
		
		post = new Post();
		post.date = new Date();
		post.content = "<p class=\"well\">" + "Hello-bello 2" + "</p>";
		post.image = "";
		post.title = "Hello world2";
		post.user = UserDAO.find("torok.karoly.krisztian@gmail.com");
		
		PostDAO.save(post);
		
		Workout w = new Workout();
		w.date = new Date();
		w.content = "AMRAP in 20 minutes<br><br><ul><li>5 HSPU</li><li>10 Pistols (5 each leg alternating</li><li>15 Pull ups</li></ul>";
		w.image = "";
		w.title = "Mary";
		w.user = UserDAO.find("torok.karoly.krisztian@gmail.com");
		w.resultType = ResultMeasurementType.REPETITION;
		w.girl = true;
		
		PostDAO.save(w);
		
		w = new Workout();
		w.date = new Date();
		w.image = "";
		w.title = "Fran";
		w.content = "21-15-9 reps, for time <br/> Thruster 95 lbs Pull-ups";
		w.user = UserDAO.find("torok.karoly.krisztian@gmail.com");
		w.resultType = ResultMeasurementType.TIME;
		w.girl = true;
		
		PostDAO.save(w);
		
		w = new Workout();
		w.date = new Date();
		w.image = "";
		w.title = "Angie";
		w.content = "Perform for time: 100 Pull-ups 100 Push-ups 100 Sit-ups 100 Squats ";
		w.user = UserDAO.find("torok.karoly.krisztian@gmail.com");
		w.resultType = ResultMeasurementType.TIME;
		w.girl = true;
		
		PostDAO.save(w);
		
		session().clear();
		return redirect(routes.Application.login());
	}
	
	@Transactional
	public static Result saveExercises() {		
		WorkoutDAO.save(new Workout("1000m run"));
		WorkoutDAO.save(new Workout("Pull up"));
		WorkoutDAO.save(new Workout("Push up"));
		WorkoutDAO.save(new Workout("Handstand push-up"));
		WorkoutDAO.save(new Workout("Air squat"));
		WorkoutDAO.save(new Workout("Pistol"));
		WorkoutDAO.save(new Workout("Deadlift")); 
		
		WorkoutDAO.save(new Workout("Mary", "AMRAP in 20 minutes<br><br><ul><li>5 HSPU</li><li>10 Pistols (5 each leg alternating</li><li>15 Pull ups</li></ul>"));
		
		session().clear();
		return redirect(routes.Application.login()); 
	}
	
	@Transactional
	public static Result saveRecords() { 
		
		saveExercises();
		savePosts();
		
		PersonalRecord mary = new PersonalRecord();
		mary.date = new Date();
		mary.workout = WorkoutDAO.find("Mary").get(0);
		mary.result = 300L;
		mary.user = UserDAO.find("torok.karoly.krisztian@gmail.com");

		PersonalRecordDAO.save(mary);
		
		session().clear();
		return redirect(routes.Application.login());
	}
}
