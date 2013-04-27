package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class UserZone extends Controller{
	public static void UserInfo(long userId){
		User user=User.findById(userId);
		UserInfo userInfo=UserInfo.find("userId", userId).first();
		List<Event> eventList=Event.find("userId", userId).fetch();
		render(user,userInfo,eventList);
	}
}






