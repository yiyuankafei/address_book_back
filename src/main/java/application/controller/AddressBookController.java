package application.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import application.constant.CheckResult;
import application.entity.Contact;
import application.entity.ContactExample;
import application.service.ContactService;
import application.util.JwtUtils;

@RestController
public class AddressBookController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/contacts")
	public Map<String, List<Contact>> get(HttpServletRequest request) {
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		ContactExample example = new ContactExample();
		example.createCriteria().andUserIdEqualTo(userId);
		example.setOrderByClause("letter_group");
		
		List<Contact> list = contactService.selectByExample(example);
		
		Map<String, List<Contact>> map = list.stream().collect(Collectors.groupingBy(Contact::getLetterGroup, LinkedHashMap::new, Collectors.toList()));
		
		return map;
	}
}
