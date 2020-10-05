package com.tpw.goo.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tpw.goo.bean.MyConstants;
import com.tpw.goo.bean.User;
import com.tpw.goo.service.IUserService;
import com.tpw.goo.util.MyStringUtils;
/**
 * 
 * @ClassName: CommonController 
 * @Description: 登陆控制类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:47:12 
 *
 */
@Controller
@RequestMapping("/common/")
public class LoginController {

	private static Logger log = LogManager.getLogger(LoginController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * 
	 * @Description: 后台管理登陆页面
	 * @author tianpengw 
	 * @return ModelAndView
	 */
	@RequestMapping("login.do")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/login");
		return mv;
	}
	
	/**
	 * 
	 * @Description: 登陆异步操作
	 * @author tianpengw 
	 * @return ModelAndView
	 */
	@RequestMapping("login.json")
	@ResponseBody
	public ModelAndView login(@RequestBody User user,HttpServletRequest req) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("errCode", MyConstants.result_fail);
		mv.addObject("errMsg", "登陆失败，请稍后再试！");
		String userName = user.getUserLoginName();
		
		try {
			if(!MyStringUtils.isEmpty(userName)){
				User u = userService.findUserByLoginName(userName);
				if(null != u){
					if(u.getUserPassword().equals(user.getUserPassword())){
						mv.addObject("errCode", MyConstants.result_success);
						log.info("用户"+user.getUserLoginName()+"登陆成功！");
						req.getSession().setAttribute("user", u);
					}else{
						mv.addObject("errMsg", "账户名或密码有误，登陆失败！");
						log.warn("用户"+user.getUserLoginName()+"登陆失败，登陆密码有误！");
					}
				}else{
					mv.addObject("errMsg", "账户名或密码有误，登陆失败！");
					log.warn("用户"+user.getUserLoginName()+"登陆失败，登陆用户名不存在！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("登陆异常：" + MyStringUtils.getStackTrace(e));
		}
		return mv;
	}
	
}
