package hello;

import org.springframework.stereotype.Service;

@Service
public class CapitalService {
	
	public String uppercase(String name) {
		String capital = name.substring(0,1).toUpperCase() + name.substring(1);
		return capital;
	}
}
