package hello;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {

	private static final String TEMPLATE = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final CapitalService service;
	private final DrinkRepository repository;


	@Autowired
	public GreetingController(final CapitalService service,final DrinkRepository repository) {
		this.service = service;
		this.repository = repository;
	}

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, service.uppercase(name)));
		}
		else {throw new NoLetterException();}
	}

	@PostMapping("/drink/create")
	public String createDrink(@RequestBody Drink drink) {
		repository.save(drink);
		return "Drink was saved!";
	}
	
	@RequestMapping("/greeting/{name}")
	public Greeting pathGreeting(@PathVariable("name") String name) throws NoLetterException {
		if(name.matches("[a-zA-Z]+")) {
			return new Greeting(counter.incrementAndGet(), String.format(TEMPLATE, service.uppercase(name)));
		}
		else {throw new NoLetterException(); }
	}

	@RequestMapping("/drink/{id}")
	public Optional<Drink> showDrink(@PathVariable("id") int id){
		return repository.findById(id);
	}


	@RequestMapping("/drink/all")
	public Iterable<Drink> showAllDrink(){
		return repository.findAll();
	}


	@ExceptionHandler(NoLetterException.class)
	public String handle() {
		return "Numbers and special characters are not allowed!";
	}
}