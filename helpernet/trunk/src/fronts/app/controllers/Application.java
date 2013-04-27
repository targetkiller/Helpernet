package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void mainpage(int state,int page) {
    	int length=25;
    	long recordNum=Event.eventCount(state);
  	  int pageNum=(int)recordNum/length;
  	  if(recordNum%length!=0){
  		  pageNum++;
  	  }
  	  if(page<1){
  		  page=1;
  	  }
  	  if(page>pageNum){
  		  page=pageNum;
  	  }
  	  List pageList =new ArrayList();
  	  for(int i=1;i<=pageNum;i++){
  		  pageList.add(i);
  	  }
  	  List<Tag> tagList=Tag.findAll();
  	  List<Event> eventList=Event.getEventOrderByTime(state, page, length);
  	  render(eventList,tagList,pageList,state);
    }
    public static void myhobby(int state,int page) {
      int length=25;
      long recordNum=Event.eventCount(state);
  	  int pageNum=(int)recordNum/length;
  	  if(recordNum%length!=0){
  		  pageNum++;
  	  }
  	  if(page<1){
  		  page=1;
  	  }
  	  if(page>pageNum){
  		  page=pageNum;
  	  }
  	  List pageList =new ArrayList();
  	  for(int i=1;i<=pageNum;i++){
  		  pageList.add(i);
  	  }
  	  List<Tag> tagList=Tag.findAll();
  	  List<Event> eventList=Event.getEventOrderByTime(state, page, length);
  	  render(eventList,tagList,pageList,state);
       
    }
    public static void myhelperlist() {
    	
    	if(UserSecure.isConnected()){
    		List<Event> eventList=Event.find("userId",UserSecure.getUserId()).fetch();
    		render(eventList);
    	}else{
    		UserSecure.login();
    	}
        
    }
    public static void aboutus() {
        render();
    }
}