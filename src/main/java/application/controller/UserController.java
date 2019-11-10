package application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.constant.CommonConstant;
import application.constant.ResEnv;
import application.entity.User;
import application.entity.UserExample;
import application.service.UserService;
import application.util.JwtUtils;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	/**
	 * 
	 * JWT（JSON WEB TOKEN）是目前比较流行的跨域身份验证解決方案
	 * JWT的原则是服务器完成身份验证后，生成一個JSON串返回给前台，包括用户信息，令牌超时时间等
	 * 后续请求，客户端携带JSON串，服务器根据这个JSON串识别用户
	 * 为了防止用户篡改信息，服务器生成JSON串时添加签名
	 * 服务器不保存任何会话数据，即服务器变为无状态，使其更容易扩展
	 * 
	 * JWT结构：
	 * 		头部：有效荷载：签名
	 * JWT用法：
	 * 		客户端收到后，存储在cookie或者localStorage中
	 * 		此后双方交互都携带JWT，如果存储在cookie中，就会自动发送，但不会跨域，一般是將它放入HTTP HEAD中的Authorization字段中
	 * 		Authorization:Bearer
	 * 		当跨域时，也可以將JWT置于POST请求体中
	 * JWT特点:
	 * 		1.由于服务器不保存，所以JWT一旦签发，在过期前都有效，在使用期间，服务器不能取消令牌
	 * 		2.JWT本身包含认证信息，因此一旦信息泄露，任何人都可以获得令牌的所有权限，因此JWT应该使用HTTPS来传输
	 * 
	 */
	
	@RequestMapping("/user/login")
	public ResEnv<String> login(@RequestBody User user) {
		UserExample example = new UserExample();
		example.createCriteria().andUsernameEqualTo(user.getUsername());
		List<User> list = userService.selectByExample(example);
		if (list.size() > 0) {
			User targetUser = list.get(0);
			if (targetUser.getPassword().equals(user.getPassword())) {
				String token = JwtUtils.createJWT(String.valueOf(targetUser.getId()), targetUser.getUsername(), CommonConstant.JWT_TTL);
				return ResEnv.success(token);
			} else {
				return ResEnv.fail("用户名/密码错误");
			}
		} else {
			return ResEnv.fail("用户名/密码错误");
		}
		
	}

}
