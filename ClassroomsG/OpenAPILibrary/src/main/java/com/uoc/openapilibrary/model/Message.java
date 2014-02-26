package com.uoc.openapilibrary.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.style.ClickableSpan;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;

public class Message implements Parcelable {
	private String id;
	private String subject;
	private String snippet;
	private String date;
	private long color;
	private long status;
	private String from;
	private String to;
    private String cc;
    private String body;

    public Message()
    {
        super();
    }
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getColor() {
		return color;
	}
	public void setColor(long color) {
		this.color = color;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
	
	public static Message JSONToMessage(String messageJSON) {
		Gson gson = new Gson();
		Message obj = gson.fromJson(messageJSON, Message.class);
		return obj;
	}
	
	private static String toJSON(Message newMessage) {
		return new Gson().toJson(newMessage);
	}
	
	public static Message postMessageinClassRoomBoardWS(String token, String domain_id, String board_id, Message newMessage) {
		String m = RESTMethod.Post(
				Constants.BASEURI +"classrooms/"+domain_id+"/boards/"+board_id+"/messages",toJSON(newMessage),
				token);

		return JSONToMessage(m);
	}
	
	public static Message getMessagefromClassRoomBoardWS(String token, String domain_id, String board_id, String message_id) {
		String m = RESTMethod.Get(
				Constants.BASEURI +"classrooms/"+domain_id+"/boards/"+board_id+"/messages/"+message_id,
				token);

		return JSONToMessage(m);
	}
	
	public static Message getMessagefromClassRoomBoardFolderWS(String token, String domain_id, String board_id, String folder_id, String message_id) {
		String m = RESTMethod.Get(
				Constants.BASEURI +"classrooms/"+domain_id+"/boards/"+board_id+"/folders/"+folder_id+"/messages/"+message_id,
				token);

		return JSONToMessage(m);
	}
	
	public static Message postSendMessagetoMailWS(String token, Message newMessage) {
		String m = RESTMethod.Post(
				Constants.BASEURI +"mail/messages",toJSON(newMessage),
				token);

		return JSONToMessage(m);
	}
	
	public static Message getMessagefromMailWS(String token, String message_id) {
		String m = RESTMethod.Get(
				Constants.BASEURI +"mail/messages/"+message_id,
				token);

		return JSONToMessage(m);
	}
	
	public static Message postMessageinSubjectBoardWS(String token, String subject_id, String board_id, Message newMessage) {
		String m = RESTMethod.Post(
				Constants.BASEURI +"subjects/"+subject_id+"/boards/"+board_id+"/messages",toJSON(newMessage),
				token);

		return JSONToMessage(m);
	}
	
	public static Message getMessagefromSubjectBoardWS(String token, String subject_id, String board_id, String message_id) {
		String m = RESTMethod.Get(
				Constants.BASEURI +"subjects/"+subject_id+"/boards/"+board_id+"/messages/"+message_id,
				token);

		return JSONToMessage(m);
	}
	
	public static Message getMessagefromSubjectBoardFolderWS(String token, String subject_id, String board_id, String folder_id, String message_id) {
		String m = RESTMethod.Get(
				Constants.BASEURI +"subjects/"+subject_id+"/boards/"+board_id+"/folders/"+folder_id+"/messages/"+message_id,
				token);

		return JSONToMessage(m);
	}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(subject);
        parcel.writeString(snippet);
        parcel.writeString(date);
        parcel.writeLong(color);
        parcel.writeLong(status);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(cc);
        parcel.writeString(body);
    }

    public static final Parcelable.Creator<Message> CREATOR =
            new Parcelable.Creator<Message>()
            {
                @Override
                public Message createFromParcel(Parcel parcel)
                {
                    return new Message(parcel);
                }

                @Override
                public Message[] newArray(int size)
                {
                    return new Message[size];
                }
            };

    public Message(Parcel parcel)
    {
        //seguir el mismo orden que el usado en el método writeToParcel
        id = parcel.readString();
        subject = parcel.readString();
        snippet = parcel.readString();
        date = parcel.readString();
        color = parcel.readLong();
        status = parcel.readLong();
        from = parcel.readString();
        to = parcel.readString();
        cc = parcel.readString();
        body = parcel.readString();
    }
}
