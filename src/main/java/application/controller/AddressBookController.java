package application.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import application.constant.CheckResult;
import application.constant.ResEnv;
import application.entity.Contact;
import application.entity.ContactExample;
import application.service.ContactService;
import application.util.JwtUtils;
import application.util.PinYinUtil;

@RestController
public class AddressBookController {
	
	@Autowired
	ContactService contactService;
	
	@GetMapping("/contacts")
	public ResEnv<Map<String, List<Contact>>> get(HttpServletRequest request) {
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		ContactExample example = new ContactExample();
		example.createCriteria().andUserIdEqualTo(userId);
		example.setOrderByClause("letter_group");
		List<Contact> list = contactService.selectByExample(example);
		
		Map<String, List<Contact>> map = list.stream().collect(Collectors.groupingBy(Contact::getLetterGroup, LinkedHashMap::new, Collectors.toList()));
		
		return ResEnv.success(map);
	}
	
	@GetMapping("/contact/{id}")
	public ResEnv<Contact> getDetail(HttpServletRequest request, @PathVariable("id") Integer targetId) {
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		ContactExample example = new ContactExample();
		example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(targetId);
		List<Contact> list = contactService.selectByExample(example);
		
		return ResEnv.success(list.get(0));
	}
	
	@PostMapping("/contact")
	public ResEnv<Void> add(HttpServletRequest request, @RequestBody Contact contact) {
		
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		String pinyin = PinYinUtil.getPinYin(contact.getNickname());
		String letterGroup = String.valueOf(pinyin.charAt(0)).toUpperCase();
		
		String reg = "[A-Z]";
		Pattern compile = Pattern.compile(reg);
		Matcher matcher = compile.matcher(letterGroup);
		if (!matcher.matches()) {
			letterGroup = "#";
		}
		
		contact.setLetterGroup(letterGroup);
		contact.setUserId(userId);
		
		contactService.insert(contact);
		return ResEnv.success();
	}
	
	@PutMapping("/contact")
	public ResEnv<Void> update(HttpServletRequest request, @RequestBody Contact contact) {
		
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		String pinyin = PinYinUtil.getPinYin(contact.getNickname());
		String letterGroup = String.valueOf(pinyin.charAt(0)).toUpperCase();
		
		String reg = "[A-Z]";
		Pattern compile = Pattern.compile(reg);
		Matcher matcher = compile.matcher(letterGroup);
		if (!matcher.matches()) {
			letterGroup = "#";
		}
		
		contact.setLetterGroup(letterGroup);
		contact.setUserId(userId);
		
		contactService.updateByPrimaryKey(contact);
		return ResEnv.success();
	}
	
	@DeleteMapping("/contact/{id}")
	public ResEnv<Contact> delete(HttpServletRequest request, @PathVariable("id") Integer targetId) {
		String token = request.getHeader("token");
		CheckResult checkResult = JwtUtils.validateJWT(token);
		Integer userId = Integer.parseInt(checkResult.getClaims().getId());
		
		ContactExample example = new ContactExample();
		example.createCriteria().andUserIdEqualTo(userId).andIdEqualTo(targetId);
		
		contactService.deleteByExample(example);
		
		return ResEnv.success();
	}
	
}
