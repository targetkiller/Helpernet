package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.scauhci.EventStatus;
import org.scauhci.Selected;

import models.Event;
import models.EventToTag;
import models.EventToUser;
import models.Tag;
import models.User;
import play.data.validation.Required;
import play.mvc.Controller;
import play.mvc.With;
@With(UserSecure.class)
public class HelpInfo extends Controller{
      public static void deleteEvent(long eventId){
    	  Map map=new HashMap();
    	  Event e=Event.findById(eventId);
    	  if(e==null){
    		  map.put("status", 401);
    		  map.put("info", "该帮帮不存在");
    		  renderJSON(map);
    	  }
    	  if(e.userId!=UserSecure.getUserId()){//确保用户删的帮帮是自己发表的
    		 map.put("status", 402);
    		 map.put("info", "该帮帮不是你发表的，删除失败!");
    		 renderJSON(map);
    	  }
    	  Event.delete("id", eventId);
    	  EventToTag.delete("eventId", eventId);
    	  EventToUser.delete("eventId", eventId);
    	  map.put("status", 200);
    	  map.put("info","删除成功!");
    	  renderJSON(map);
    	  
      }
      public static void cancelEvent(long eventId){
    	  Map map=new HashMap();
    	  EventToUser etu=EventToUser.find("eventId = ? AND userId = ?", eventId,UserSecure.getUserId()).first();
    	  Event e=Event.findById(eventId);
    	  if(etu==null&&e==null){
    		  map.put("status", 401);
    		  map.put("info", "该帮帮已不存在");
    		  renderJSON(map);
    	  }
    	  if(e==null){
    		  etu.delete();
    		  map.put("status", 401);
    		  map.put("info", "该帮帮已不存在");
    		  renderJSON(map);
    	  }
    	  if(etu!=null){
    		  etu.delete();
    	  }
    	  map.put("status", 200);
    	  map.put("info", "取消成功!");
    	  renderJSON(map);
    	  
      }
      public static void joinEvent(long eventId){
    	  Map map=new HashMap();
    	  Event e=Event.findById(eventId);
    	  if(e==null){
    		  map.put("status", 401);
    		  map.put("info","该帮帮不存在");
    		  renderJSON(map);
    	  }
    	  EventToUser etu =EventToUser.find("userId = ? AND eventId = ?", UserSecure.getUserId(),eventId).first();
    	  if(etu==null){
    		  etu=new EventToUser();
    		  etu.eventId=eventId;
    		  etu.userId=UserSecure.getUserId();
    		  etu.state=Selected.no;
    		  etu.save();
    	  }
    	  map.put("status", 200);
    	  map.put("info", "参与成功!");
    	  renderJSON(map);
      }
      
      public static void selectHelper(long eventId,long helperId){
    	  Event e=Event.findById(eventId);
    	  EventToUser etu=EventToUser.find("eventId = ? AND userId = ?", eventId,helperId).first();
    	  if(e!=null&&e.userId==UserSecure.getUserId()&&etu!=null&&etu.state==Selected.no){
    		  etu.state=Selected.yes;
    		  e.state=EventStatus.handing;
    	      etu.save();
    		  e.save();
    		  User.addCoin(helperId, 1);
    	  }
    	  Detail.detail(eventId);
      }
      
      
}
