package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.jpa.GenericModel;
@Entity
@Table(name="user_info")
public class UserInfo extends GenericModel{

	@Id
	@Column(name="id",nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;
	
	@Column(name="user_id")
	public long userId;
	
	@Column(name="long_tel",length=32)
	public String longTel;
	
	@Column(name="short_tel",length=32)
	public String shortTel;
	
	@Column(name="email",length=255)
	public String email;
	
	@Column(name="coin")
	public int coin;
	
	@Column(name="sex",length=8)
	public int sex;
	
	@Column(name="qq",length=255)
	public String qq;
	
	@Column(name="msn",length=255)
	public String msn;
	
	
}
