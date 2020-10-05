/**
 *
 * 项目中常用的验证方法和验证正则表达式，以及自定义alert和confirm框
 * 【注意：此js需要放在jquery-confirm.js之下，引用了confirm的弹出框】
 *  
 * @author tianpengw
 * Date: 2017.04.10
 * Time: 17:06:30
 * 
 */
var regPhone = /^(((13[0-9]{1})|(14[5|7|9]{1})|(15[0|1|2|3|5|6|7|8|9]{1})|(17[0|1|2|3|5|6|7|8]{1})|(18[0-9]{1}))+\d{8})$/;
var regMail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
var regIdCard = /^[1-9]\d{5}(((18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3})|(\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}))[0-9Xx]$/;
var regPassword = /^[a-zA-Z0-9_]{6,22}$/;
var regEnglish = /^[A-Za-z]+$/;
var regChinese = /^[\u0391-\uFFE5]+$/;
var regUrl = /^(http|https):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/;
var regDate = /^\d{4}-\d{1,2}-\d{1,2}$/;
var regTime = /^\d{4}-\d{1,2}-\d{1,2}\s\d{1,2}:\d{1,2}:\d{1,2}$/;
var regMoney = /^\d+(\.\d+)?$/;
var regInt = /^[-\+]?[1-9]\d*$/;
var regZhengInt = /^[1-9]\d*$/;
var regTelephone = /^0\d{2,3}-\d{7,8}(-\d{1,6})?$/;


/**
 * 
 * 自定义弹出框
 * @param con 弹出框内容；
 * @param act 弹出框事件（function）此值可不输入，如果不输入将不执行事件，如果弹出框标题不为空，此处必须填写（可填写null）；
 * @param t 弹出框标题；
 * @returns
 */
function jQAlert(con,act,t){
	if(!t){
		t = "";
	}
	if(!act){
		$.alert({
			title: t,
			content: con,
			buttons: {
				okay: {
					text: '确认'
				}
			}
		});
	}else{
		$.alert({
			title: t,
			content: con,
			buttons: {
				okay: {
					text: '确认',
		        	action: function(){
		        		eval(act);
		        	}
				}
			}
		});
	}
	
  }

/**
 * 
 * 自定义确认框
 * @param con 确认框内容；
 * @param act 确认框事件（function）
 * @param t 确认框标题，不输不显示；
 */
function jQConfirm(con,act,t){
	if(!t){
		t = "";
	}
	
	$.confirm({
	    title: t,
	    content: con,
	    buttons: {
	        confirm:{
	        	text:"确认",
	        	action: function(){
	        		eval(act);
	        	}
	        },
	        cancel: {
	        	text:"取消"
	        }
	     }
	});
}

/**
 * 
 * 去空格
 * @param str 
 * @returns
 */
function trimStr(str){
	if(!str){
		return str;
	}else{
		return str.replace(/(^\s*)|(\s*$)/g, "");
	}
}

/**
 * 验证手机号码合法性
 * @param p 手机号码/固定电话
 * @param t 弹出框关键字
 * @param isNull 布尔值是否可以为空，此值为空时限制非空
 */
function v_phoneAll(p,t,isNull){
	if(!t){
		t = "电话号码";
	}
	if(!isNull){
		if (trimStr(p).length == 0){
			jQAlert(t+"不能为空！");
			return true;
		}else if(!regPhone.test(p) && !regTelephone.test(p)){
			jQAlert("请输入有效"+t+"！");
			return true;
		}
	}else{
		if(trimStr(p).length > 0 && !regPhone.test(p) && !regTelephone.test(p)){
			jQAlert("请输入有效"+t+"！");
			return true;
		}
	}
	return false;
}

/**
 * 验证手机号码合法性
 * @param p 手机号码
 * @param isNull 布尔值是否可以为空，此值为空时限制非空
 */
function v_phone(p,isNull){
	if(!isNull){
		if (trimStr(p).length == 0){
			jQAlert("手机号码不能为空！");
			return true;
		}else if(!regPhone.test(p)){
			jQAlert("请输入有效手机号码！");
			return true;
		}
	}else{
		if(trimStr(p).length > 0 && !regPhone.test(p)){
			jQAlert("请输入有效手机号码！");
			return true;
		}
	}
	return false;
}


/**
 * 验证网址合法性
 * @param u 网址
 * @param isNull 布尔值是否可以为空，此值为空时限制非空
 */
function v_url(u,isNull){
	if(!isNull){
		if (trimStr(u).length == 0){
			jQAlert("网址不能为空！");
			return true;
		}else if(!regUrl.test(u)){
			jQAlert("请输入合法网址！（请以http://或https://开头）");
			return true;
		}
	}else{
		if(trimStr(u).length > 0 && !regUrl.test(u)){
			jQAlert("请输入合法网址！（请以http://或https://开头）");
			return true;
		}
	}
	return false;
}

/**
 * 验证电子邮箱合法性，验证失败返回true，验证成功返回fasle
 * @param m
 * @param isNull
 * @returns {Boolean}
 */
function v_mail(m, isNull){
	if(!isNull){
		if (trimStr(m).length == 0){
			jQAlert("电子邮箱不能为空！");
			return true;
		}else if(!regMail.test(m)){
			jQAlert("请输入有效电子邮箱！");
			return true;
		}
		
	}else{
		if(trimStr(m).length > 0 && !regMail.test(m)){
			jQAlert("请输入有效电子邮箱！");
			return true;
		}
	}
	return false;
}

/**
 * 验证身份证号合法性，验证失败返回true，验证成功返回fasle
 * @param c
 * @param isNull
 * @returns {Boolean}
 */
function v_idCard(c,isNull){
	if(!isNull){
		if (trimStr(c).length == 0){
			jQAlert("身份证号不能为空！");
			return true;
		}else if(!regIdCard.test(c)){
			jQAlert("请输入有效身份证号！");
			return true;
		}
		
	}else{
		if(trimStr(c).length > 0 && !regIdCard.test(c)){
			jQAlert("请输入有效身份证号！");
			return true;
		}
	}
	return false;
}

/**
 * 只可以输入正数和小数点
 * @param n
 * @param isNull
 * @returns {Boolean} 
 */
function v_number(n, msg, isNull){
	if(!msg){
		msg = "字段 ";
	}
	if(!isNull){
		if (trimStr(n).length == 0){
			jQAlert(msg+"不能为空！");
			return true;
		}else if(!regMoney.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
		
	}else{
		if(trimStr(n).length > 0 && !regMoney.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
	}
	return false;
}

/**
 * 只可以输入正整数数
 * @param n
 * @param isNull
 * @returns {Boolean} 
 */
function v_ZhengInt(n, msg, isNull){
	if(!msg){
		msg = "字段 ";
	}
	if(!isNull){
		if (trimStr(n).length == 0){
			jQAlert(msg+"不能为空！");
			return true;
		}else if(!regZhengInt.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
		
	}else{
		if(trimStr(n).length > 0 && !regZhengInt.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
	}
	return false;
}

/**
 * 只可以输入正整数
 * @param n
 * @param isNull
 * @returns {Boolean} 
 */
function v_zhengInt(n, msg, isNull){
	if(!msg){
		msg = "字段 ";
	}
	if(!isNull){
		if (trimStr(n).length == 0){
			jQAlert(msg+"不能为空！");
			return true;
		}else if(!regZhengInt.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
		
	}else{
		if(trimStr(n).length > 0 && !regZhengInt.test(n)){
			jQAlert("请输入有效"+msg+"！");
			return true;
		}
	}
	return false;
}
/**
 * 验证密码合法性，验证失败返回true，验证成功返回fasle
 * @param p
 * @param isNull
 * @returns {Boolean}
 */
function v_password(p,isNull){
	if(!isNull){
		if (trimStr(p).length == 0){
			jQAlert("密码不能为空！");
			return true;
		}else if(!regPassword.test(p)){
			jQAlert("密码必须为6-22位字母数字下划线组合！");
			return true;
		}
		
	}else{
		if(trimStr(p).length > 0 && !regPassword.test(p)){
			jQAlert("密码必须为6-22位字母数字下划线组合！");
			return true;
		}
	}
	return false;
}
/**
 * 
 * 判断值是否为空 为空时返回true，非空时返回fasle 
 * @param str 值
 * @param msg 为空时的弹出框关键字
 * @returns {Boolean}
 */
function v_isNull(str, msg){
	if(!msg){
		msg = "字段 ";
	}
	
	if(trimStr(str).length == 0){
		jQAlert(msg+"不能为空！");
		return true;
	}
	return false;
}

/**
 * 
 * 根据身份证限制最大年龄
 * @param idCard 身份证号码
 * @param maxAge 最大年龄 如果不输入 默认35岁(这里的判断后期改成了出生年份不能小于1982，如需改动解开注释即可)
 * @returns {Boolean}
 */
function v_limitBirth(idCard,maxAge){
	if(!maxAge){
		maxAge = 35;
	}
	
	var now = new Date();
	var month = now.getMonth()+1;
	var day = now.getDate();
	var age = 0;
	var birthYear, birthMonth, birthDate;
	
	if(idCard.length==18){
		birthYear = idCard.substring(6,10);
		/*birthMonth = idCard.substring(10,12);
		birthDate = idCard.substring(12,14);
		
		var age = now.getFullYear() - birthYear - 1;
		
		if(birthMonth < month || (birthMonth == month && birthDate <= day)){
			age++;
		}*/
	}else if (idCard.length==15){
		birthYear = parseInt("19" + idCard.substring(6,8));
		/*birthMonth = idCard.substring(8,10);
		birthDate = idCard.substring(10,12);*/
	}else {
		jQAlert("请输入15位或18位有效身份证！");
		return true;
	}
	
	/*var age = now.getFullYear() - birthYear - 1;
	if(birthMonth < month || (birthMonth == month && birthDate <= day)){
		age++;
	}*/
	/*if(age > maxAge){
		jQAlert("参赛年龄不能超35岁！");
		return true;
	}*/
	if(birthYear < 1982){
		jQAlert("参赛年龄不能超35岁！");
		return true;
	}else{
		return false;
	}
}
