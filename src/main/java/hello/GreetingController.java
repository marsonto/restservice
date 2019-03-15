package hello;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String TEMPLATE = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final CapitalService service;
	private final DataAccessObjectService dao;

	@Autowired
	public GreetingController(final CapitalService service, final DataAccessObjectService dao) {
		this.service = service;
		this.dao = dao;
	}

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, service.uppercase(name)));
		}
		else {throw new NoLetterException();}
	}
	
	@RequestMapping("/greeting/{name}")
	public Greeting pathGreeting(@PathVariable("name") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, service.uppercase(name)));
		}
		else {throw new NoLetterException(); }
	}

	//catch out of bounds and no int
	@RequestMapping("/greeting/drink/{id}")
	public Drink showDrink(@PathVariable("id") int id) throws SQLException{
		return dao.accessDrink(id);
	}

	@RequestMapping("/greeting/drink/all")
	public List<Drink> showAllDrink() throws SQLException{
		return dao.accessAllDrinks();
	}

	@ExceptionHandler(NoLetterException.class)
	public String handle() {
		return "Numbers and special characters are not allowed!";
	}
}