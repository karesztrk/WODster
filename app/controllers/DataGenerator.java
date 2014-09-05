package controllers;

import java.util.Date;

import model.blog.Post;
import model.box.Box;
import model.user.User;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import util.security.Security;
import dao.BoxDAO;
import dao.PostDAO;
import dao.UserDAO;

public class DataGenerator extends Controller {

	@Transactional
	public static Result saveUsers() {
		
		User user = new User();
		user.email = "torok.karoly.krisztian@gmail.com";
		user.password = Security.getSecurePassword("12345");
		user.name = "Karoly Torok";
		
		user.save();
		
		return redirect(routes.Application.index());
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
		
		return redirect(routes.Application.index());
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
		
		return redirect(routes.Application.index());
	}
}
