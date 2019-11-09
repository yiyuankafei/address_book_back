package application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.entity.User;
import application.entity.UserExample;
import application.entity.base.ResEnv;
import application.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/user/login")
	public ResEnv<String> login(@RequestBody User user) {
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername());
		List<User> list = userService.selectByExample(example);
		if (list.size() > 0) {
			User targetUser = list.get(0);
			if (targetUser.getPassword().equals(user.getPassword())) {
				return ResEnv.success("登錄成功");
			} else {
				return ResEnv.fail("用戶名/密碼錯誤");
			}
		} else {
			return ResEnv.fail("用戶名/密碼錯誤");
		}
		
	}

}
