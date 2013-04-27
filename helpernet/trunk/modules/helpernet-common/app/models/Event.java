package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import controllers.UserSecure;

import play.db.jpa.GenericModel;
@Entity
@Table(name="event")
public class Event extends GenericModel{
	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="title",nullable=false,length=32)
	public String title;
	
	@Column(name="content",nullable=false,length=1024)
	public String content;
	
	@Column(name="extra",length=255)
	public String extra;//附加信息
	
	@Column(name="time",nullable=false)
	public long time;
	
	@Column(name="user_id",nullable=false,length=255)
	public long userId;
	
	@Column(name="state",length=8)
	public int state;//事件状态,0表示申请中,1表示进行中,2表示已结束
	
	public static List<Event> getEventOrderByTime(int state,int page,int length){
		return Event.find("state = ? order by time desc",state).fetch(page,length);
	}
	public static long eventCount(int state){
		return Event.count("state", state);
	}
	public String getStateName(){
		String s ="";
		switch(state){
		case 0:s="申请中";break;
		case 1:s="进行中";break;
		case 2:s="已结束";break;
		default:break;
		}
		return s;
	}
	public String getUserName(){
		User user=User.findById(userId);
		return user==null?"":user.nickname;
	}
	public List<Tag> getTagList(){
		List<EventToTag> ettlist=EventToTag.find("eventId", id).fetch();
		List<Tag> tagList=new ArrayList<Tag>();
		for(EventToTag ett:ettlist){
			Tag tag=Tag.findById(ett.tagId);
			tagList.add(tag);
		}
		return tagList;
	}
	public boolean isJoin(){
		if(EventToUser.find("userId = ? AND eventId = ?", UserSecure.getUserId(),id).first()==null){
			return false;
		}
		return true;
	}
	public List<User> getHelperList(){
		List<User> userList=new ArrayList<User>();
		List<EventToUser> etuList=EventToUser.find("eventId", id).fetch();
		for(EventToUser etu:etuList){
			User user =User.findById(etu.userId);
			userList.add(user);
		}
		return userList;
	}
}
