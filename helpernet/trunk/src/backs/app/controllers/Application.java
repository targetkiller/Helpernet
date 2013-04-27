package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

@With(UserSecure.class)
public class Application extends Controller {

	public static void Backpage() {
		render();
	}

	public static void Myinfo() {
		flash.clear();
		User user = User.findById(UserSecure.getUserId());
		UserInfo userInfo = UserInfo.findById(UserSecure.getUserId());
		render(userInfo, user);
	}

	public static void Mypassword() {
		flash.clear();
		render();
	}

	public static void Mytag() {
		List<Tag> tagList = User.getUserTagListByUserId(UserSecure.getUserId());
		render(tagList);
	}

	public static void Checkmylist(int state) {
		List<Event> eventList = Event.find(
				"userId = ? AND state = ? order by time desc",
				UserSecure.getUserId(), state).fetch();
		render(eventList);
	}

	public static void updatePersonalInfo(String nickname, UserInfo userInfo) {
		flash.clear();
		User u = User.findById(UserSecure.getUserId());
		UserInfo ui = UserInfo.find("userId", UserSecure.getUserId()).first();
		if (nickname == null || "".equals(nickname.trim())) {
			flash.error("昵称不能为空");
			renderArgs.put("user", u);
			renderArgs.put("userInfo", ui);
			render("Application/myinfo.html");
		}

		if (u == null) {
			flash.error("用户不存在");
			renderArgs.put("user", u);
			renderArgs.put("userInfo", ui);
			render("Application/myinfo.html");
		}
		u.nickname = nickname;
		u.save();

		if (ui != null) {
			ui.email = userInfo.email;
			ui.msn = userInfo.msn;
			ui.longTel = userInfo.longTel;
			ui.shortTel = userInfo.shortTel;
			ui.qq = userInfo.qq;
			ui.sex = userInfo.sex;
			ui.save();
		}
		flash.success("修改成功");
		renderArgs.put("user", u);
		renderArgs.put("userInfo", ui);
		render("Application/myinfo.html");
	}

	public static void updatePassword(String oldPassword, String newPassword,
			String repeatPassword) {
		flash.clear();
		User u = User.findById(UserSecure.getUserId());
		if (u != null && u.password.equals(oldPassword)) {
			if (newPassword != null && newPassword.length() >= 6
					&& newPassword.equals(repeatPassword)) {
				u.password = newPassword;
				u.save();
				flash.success("密码更改成功！");
			} else {
				flash.error("新密码输入有误");
			}
		} else {
			flash.error("旧密码输入错误");
		}
		render("Application/mypassword.html");
	}

	public static void deleteTag(long tagId) {
		UserToTag utt = UserToTag.find("userId = ? AND tagId = ?",
				UserSecure.getUserId(), tagId).first();
		if (utt != null) {
			utt.delete();
		}
		Mytag();
	}

	public static void addTag(String tagName) {
		if (User.getUserTagListByUserId(UserSecure.getUserId()).size() < 5) {
			if (tagName != null && !"".equals(tagName.trim())) {
				Tag tag = Tag.find("name", tagName).first();
				if (tag!=null) {
                       UserToTag utt=new UserToTag();
                       utt.userId=UserSecure.getUserId();
                       utt.tagId=tag.id;
                       utt.save();
				}else{
					Tag tag1=new Tag();
					tag1.name=tagName;
					tag1.save();
                    UserToTag utt=new UserToTag();
                    utt.userId=UserSecure.getUserId();
                    utt.tagId=tag1.id;
                    utt.save();
				}
			}
			
		}
		Mytag();
	}

}