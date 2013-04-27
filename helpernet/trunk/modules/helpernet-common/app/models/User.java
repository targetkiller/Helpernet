package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.scauhci.Selected;

import play.db.jpa.GenericModel;


@Entity
@Table(name="user")
public class User extends GenericModel {
	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="account",nullable=false,length=32)
	public String account;
	
	@Column(name="nickname")
	public String nickname;
	
	@Column(name="password",nullable=false,length=32)
	public String password;
	
	public User(String account,String password,String nickname)
	{
		this.account = account;
		this.password = password;
		this.nickname=nickname;
	}
	
	public static User getByAccountAndPassword(String account,String password){
		return User.find("account = ? AND password = ?", account,password).first();
	}
	public static boolean isExistAccount(String account){
		User u=User.find("account", account).first();
		if(u==null){
			return false;
		}
		return true;
	}
	
	public static int getCoinByUserId(long userId){
		UserInfo ui=UserInfo.find("userId = ?", userId).first();
		return ui==null?0:ui.coin;
	}
	public static void deleteCoin(long userId,int coin){
		UserInfo ui=UserInfo.find("userId = ?", userId).first();
		if(ui!=null){
			ui.coin-=coin;
			ui.save();
		}
	}
	public static void addCoin(long userId,int coin){
		UserInfo ui=UserInfo.find("userId = ?", userId).first();
		if(ui!=null){
			ui.coin+=coin;
			ui.save();
		}
	}
	public  boolean isSelected(long eventId){
		EventToUser etu=EventToUser.find("eventId = ? AND userId = ?", eventId,id).first();
		if(etu!=null&&etu.state==Selected.yes){
			return true;
		}
		return false;
	}
	public static List<Tag> getUserTagListByUserId(long userId){
		List<Tag> tagList=new ArrayList<Tag>();
		List<UserToTag> uttList=UserToTag.find("userId", userId).fetch();
		for(UserToTag utt:uttList){
			Tag tag=Tag.findById(utt.tagId);
			tagList.add(tag);
		}
		return tagList;
	}
}
