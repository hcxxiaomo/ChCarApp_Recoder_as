package org.yanzi.bean;

public class MessageInfo {
	private int id;
	private String title;
	private String content;
	private String position;
	private String author;
	private String depart;
	private String time;
	
	public MessageInfo(){
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public MessageInfo(int id, String title, String time) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
	}

	@Override
	public String toString() {
		return "MessageInfo [id=" + id + ", title=" + title + ", time=" + time
				+ "]";
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepart() {
		return depart;
	}

	public void setDepart(String depart) {
		this.depart = depart;
	}

	public MessageInfo(int id, String title, String content, String position,
			String depart,String author , String time) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.position = position;
		this.depart = depart;
		this.author = author;
		this.time = time;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	
}
