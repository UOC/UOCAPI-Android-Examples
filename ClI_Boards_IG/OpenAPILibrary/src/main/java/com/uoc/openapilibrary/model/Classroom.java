package com.uoc.openapilibrary.model;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;

public class Classroom {
	private String id;
	private String title;
	private String color;
	private String fatherId;
	private String[] assignments;
    private String code;
    private String shortTitle;

	public Classroom() {
		//Default constructor
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String[] getAssignments() {
		return assignments;
	}

	public void setAssignments(String[] assignments) {
		this.assignments = assignments;
	}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShorttitle() {
        return shortTitle;
    }

    public void setShorttitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public static Classroom JSONToClassRoom(String classRoomJSON) {
		Gson gson = new Gson();
		Classroom obj = gson.fromJson(classRoomJSON, Classroom.class);
		return obj;
	}
	
	public static Classroom getClassroomWS(String token, String id) {
		return JSONToClassRoom(RESTMethod
				.Get(Constants.BASEURI +"classrooms/"+id,
						token));
	}
	
	public static Classroom getClassroomfromSubjectWS(String token, String subject_id) {
		return JSONToClassRoom(RESTMethod
				.Get(Constants.BASEURI +"subjects/"+subject_id,
						token));
	}
}
