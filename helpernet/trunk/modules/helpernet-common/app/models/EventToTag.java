package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
@Entity
@Table(name = "event_to_tag")
public class EventToTag extends GenericModel {
	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="event_id",nullable=false)
	public long eventId;
	
	@Column(name="tag_id",nullable=false)
	public long tagId;
}
