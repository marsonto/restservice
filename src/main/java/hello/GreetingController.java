package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final CapitalService service;
	
	@Autowired
	public GreetingController(final CapitalService service) {
		this.service = service;
	}

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(template, service.uppercase(name)));
		}
		else {throw new NoLetterException();}
	}
	
	@RequestMapping("/greeting/{name}")
	public Greeting pathGreeting(@PathVariable("name") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(template, service.uppercase(name)));
		}
		else {throw new NoLetterException(); }
	}
	
	@ExceptionHandler(NoLetterException.class)
	public String handle() {
		return "Numbers and special characters are not allowed!";
	}
}