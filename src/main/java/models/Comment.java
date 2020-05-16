package models;

import java.io.Serializable;

public class Comment implements Serializable{
	private static final long serialVersionUID = 7683046650585196592L;
	private String content;
	private Person author;
	public Comment(Person author, String content) {
		this.content = content;
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Person getAuthor() {
		return this.author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	};
	public String toString() {
		return this.author.getUsername() + ": [" + this.content + "]";
	}
}

