package controllers;

import play.*;
import play.mvc.*;
import play.data.validation.*;

import java.util.*;

import models.*;

public class DataHandle extends Controller {

	public static List tosearch(int stype,String keyword) {
		//0为按标题和内容检索，1为按标签检索
		switch(stype)
		{
			case 0:
				return Event.find("title like ?  or content like ?","%"+keyword+"%","%"+keyword+"%").fetch();
			case 1:
				//return Event.find("from Event e where e.title like '%"+keyword+"%' or e.content like '%"+keyword+"%'").fetch();
				break;
		}
		return null;
	}
	public static void search(String keyword) {
		List eventList = tosearch(0,keyword);
		render(eventList);
	}

	
}