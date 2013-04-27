package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
@Entity
@Table(name="event_to_user")
public class EventToUser extends GenericModel{
	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="user_id",nullable=false)
	public long userId;//帮助者ID
	
	@Column(name="event_id",nullable=false)
	public long eventId;//事件ID
	
	@Column(name="state",nullable=false,length=8)
	public int state;//帮助者是否被选定为该事件的帮助者
	
	@Column(name="read_state",nullable=false,length=8)
	public int readState;//是否已被发起人读
}
