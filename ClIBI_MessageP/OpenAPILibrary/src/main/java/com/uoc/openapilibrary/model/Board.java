package com.uoc.openapilibrary.model;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;

public class Board {
	private String id;
	private String subtype;
	private String title;
	private String code;
	private String domainId;
    private String unreadMessages;
    private String totalMessages;
	
	public Board() {
		//Default constructor
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubtype() {
		return subtype;
	}
	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
    public String getUnreadMessages() {
        return unreadMessages;
    }

    public void setUnreadMessages(String unreadMessages) {
        this.unreadMessages = unreadMessages;
    }

    public String getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(String totalMessages) {
        this.totalMessages = totalMessages;
    }
	
	public static Board JSONToBoard(String boardJSON) {
		Gson gson = new Gson();
		Board obj = gson.fromJson(boardJSON, Board.class);
		return obj;
	}
	
	public static Board getBoardfromClassRoomWS(String token, String domain_id, String board_id) {
		String b = RESTMethod.Get(
				Constants.BASEURI +"classrooms/"+domain_id+"/boards/"+board_id,
				token);

		return JSONToBoard(b);
	}
	
	public static Board getBoardfromSubjectWS(String token, String subject_id, String board_id) {
		String b = RESTMethod.Get(
				Constants.BASEURI +"subjects/"+subject_id+"/boards/"+board_id,
				token);

		return JSONToBoard(b);
	}



}
