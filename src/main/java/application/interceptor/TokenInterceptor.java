package application.interceptor;


import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import application.constant.CheckResult;
import application.constant.CommonConstant;
import application.constant.ResEnv;
import application.util.JwtUtils;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String uri = request.getRequestURI();
    	System.out.println("请求路径：" + uri);
    	String token = request.getHeader("token");
    	if (StringUtils.isEmpty(token)) {
    		print(response, ResEnv.fail(CommonConstant.RES_CODE_TOKEN_NOT_EXISTS, "token不存在"));
    		return false;
    	} else {
    		CheckResult result = JwtUtils.validateJWT(token);
    		if (result.isSuccess()) {
    			return true;
    		} else {
    			if (result.getErrCode() == CommonConstant.RES_CODE_TOKEN_EXPIRE) {
    				print(response, ResEnv.fail(CommonConstant.RES_CODE_TOKEN_EXPIRE, "token过期"));
    				return false;
    			} else {
    				print(response, ResEnv.fail(CommonConstant.RES_CODE_TOKEN_AUTH_FAIL, "token认证不通过"));
    				return false;
    			}
    		}
    	}
    }
    
    private void print(HttpServletResponse response, Object message) {
    	try {
    		response.setStatus(HttpStatus.OK.value());
    		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    		response.setHeader("Cache-Control", "no-cache, must-revalidate");
    		PrintWriter writer = response.getWriter();
    		writer.write(JSON.toJSONString(message));
    		writer.flush();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
