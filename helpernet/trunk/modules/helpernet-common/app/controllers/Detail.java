package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class Detail extends Controller{
	public static void detail(long eventId){
		int exist=0;
		
		Event event=Event.findById(eventId);
		List<EventToUser> etulist=new ArrayList<EventToUser>();
		List<User> helperList=new ArrayList<User>();
		List<Tag> tagList=new ArrayList<Tag>();
		if(event!=null){
			exist=1;
			etulist=EventToUser.find("eventId",eventId).fetch();
			for(EventToUser etu:etulist){
				User u=User.findById(etu.userId);
				if(u!=null){
					helperList.add(u);
				}
			}
			tagList=event.getTagList();
		}else{
			exist=2;
		}
		render(event,tagList,exist,helperList);
	}
	
}






