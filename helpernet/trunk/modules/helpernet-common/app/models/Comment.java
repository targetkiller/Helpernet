package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
@Entity
@Table(name="comment")
public class Comment extends GenericModel{
	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="content",nullable=false,length=1024)
	public String content;

	@Column(name="time",nullable=false)
	public long time;
	
	@Column(name="user_id",nullable=false,length=255)
	public long UserId;
	
	@Column(name="event_id",nullable=false,length=255)
	public long EventId;

}
