package application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressBookController {
	
	@RequestMapping("/addressBook/get")
	public String get() {
		return "ok";
	}
	

}
