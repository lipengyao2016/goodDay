package com.tpw.goo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @ClassName: CommonController 
 * @Description: 通用控制类
 * @author tianpengw 
 * @date 2017年12月7日 下午4:47:12 
 *
 */
@Controller
@RequestMapping("/admin/")
public class AdminController {

	
	/**
	 * 
	 * @Description: 后台管理登陆页面
	 * @author tianpengw 
	 * @return ModelAndView
	 */
	@RequestMapping("home.do")
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("/admin/home");
		
		return mv;
	}
}
