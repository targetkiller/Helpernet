package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;
import java.util.*;
import models.*;

public class UserSecure extends Controller {

	@Before(unless = { "login", "authenticate", "signout", "register",
			"checkRegisterInfo" })
	static void checkAccess() {
		// Authent
		if (!session.contains(SESSION_KEY)) {
			String fromUrl = "GET".equals(request.method) ? request.url
					: Play.configuration.getProperty("http.path", "/");
			flash.put("url", fromUrl);
			login();
		}
	}

	static final String SESSION_KEY = "userId";
	static final String NAME_SESSION_KEY = "userName";

	/**
	 * 跳登录页面
	 */
	public static void login() {
		render();
	}

	/**
	 * 登录信息检测
	 * 
	 * @param account
	 * @param password
	 * @throws Throwable
	 */
	public static void authenticate(@Required String account,@Required String password) throws Throwable {
		
		Boolean allowed = false;
		User user = User.getByAccountAndPassword(account, password);
		if (null != user) {
			allowed = true;
		}
		if (validation.hasErrors() || !allowed) {
			flash.keep("url");
			flash.error("账号不存在或者密码不正确.");
			params.flash();
			login();
		}
		session.put(SESSION_KEY, user.id);
		session.put(NAME_SESSION_KEY, user.nickname);
		// Redirect to the original URL (or WebRoot)
		redirectToOriginalURL();
	}

	/**
	 * 跳注册页面
	 */
	public static void register() {
		render();
	}

	/**
	 * 检查注册信息
	 * 
	 * @param account
	 * @param nickname
	 * @param password
	 * @param repeatpassword
	 * @throws Throwable
	 */
	public static void checkRegisterInfo(String account, String nickname,String password, String repeatpassword) throws Throwable {
		System.out.println(Scope.Params.current().get("authenticityToken"));
		
		boolean flag=true;
		flash.clear();
		if (account == null || account.length() < 6 || account.length() > 16||account.contains(" ")) {
			flag=false;
			flash.put("account_error","账号必须是6-16位非空格字符");
		}else{
			if (User.isExistAccount(account)) {
				flag=false;
				flash.put("account_error","该账号已存在");
			}
		}
		
		if (nickname == null || nickname.length() < 3 || nickname.length() > 16||nickname.contains(" ")) {
			flag=false;
			flash.put("nickname_error","昵称必须是3-16位非空格字符");
		}
		
		if (password == null || password.length() < 6 || password.length() > 16) {
			flag=false;
			flash.put("password_error","密码输入不合法");
		}else{
			if (!repeatpassword.equals(password)) {
				flag=false;
				flash.put("repeatpassword_error","两次密码输入不一致");
			}
		}
		
		if(flag==false){
			params.flash();
			render("UserSecure/register.html");
		}
		User u = new User(account, repeatpassword, nickname);
		u.save();
		UserInfo uf=new UserInfo();
		uf.email="@";
		uf.userId=u.id;	
		uf.coin=5;
		uf.save();
		session.put(SESSION_KEY, u.id);
		session.put(NAME_SESSION_KEY, u.nickname);
		redirectToOriginalURL();
	}

	static void redirectToOriginalURL() throws Throwable {
		String url = flash.get("url");
		if (url == null) {
			url = "GET".equals(request.method) ? request.url
					: Play.configuration.getProperty("http.path", "/");
		}
		redirect(url);
	}

	static boolean isConnected() {
		return session.contains(SESSION_KEY);
	}

	static String connected() {
		return session.get(SESSION_KEY);
	}

	static String getUserName() {
		return session.get(NAME_SESSION_KEY);
	}

	public static long getUserId() {
		if(session.get(SESSION_KEY)==null){
			return -1;
		}
		return Long.valueOf(session.get(SESSION_KEY));
	}

	public static void signout() {
		session.clear();
		flash.success("您已经退出系统.");
		// String fromUrl = Play.configuration.getProperty("http.path",
		// "/UserSecure/signin");
		String toUrl = "http://helper.scauhci.org:9303";
		redirect(toUrl);
	}

}
