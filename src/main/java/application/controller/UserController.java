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
	 * JWT（JSON WEB TOKEN）是目前比較流行的跨域身份驗證解決方案
	 * JWT的原則是服務器完成身份驗證後，生成一個JSON串返回給前台，包括用戶信息，令牌超時時間等
	 * 後續請求，客戶端攜帶JSON串，服務器根據這個JSON串識別用戶
	 * 為了防止用戶篡改信息，服務器生成JSON串時添加簽名
	 * 服務器不保存任何會話數據，即服務器變為無狀態，使其更容易擴展
	 * 
	 * JWT結構：
	 * 		頭部：有效荷載：簽名
	 * JWT用法：
	 * 		客戶端收到後，存儲在cookie或者localStorage中
	 * 		此後雙方交互都攜帶JWT，如果存儲在cookie中，就會自動發送，但不會跨域，一般是將它放入HTTP HEAD中的Authorization字段中
	 * 		Authorization:Bearer
	 * 		當跨域時，也可以將JWT放置於POST請求體中
	 * JWT特點:
	 * 		1.由於服務器不保存，所以JWT一旦簽發，在過期前都有效，在試用期間，服務器不能取消令牌
	 * 		2.JWT本身包含認證信息，因此一旦信息洩露，任何人都可以獲得令牌的所有權限，因此JWT應該使用HTTPS來傳輸
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
				return ResEnv.fail("用戶名/密碼錯誤");
			}
		} else {
			return ResEnv.fail("用戶名/密碼錯誤");
		}
		
	}

}
