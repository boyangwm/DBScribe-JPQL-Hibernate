package com.mpakhomov.chat.domain;

import javax.persistence.*;
import java.util.Date;

@NamedQueries({
    @NamedQuery(
        name = "fetchRecentMessages",
        query = "from ChatMessage m order by m.timestamp desc"
    )
})
@Entity
@Table(name="messages")
public class ChatMessage {
    @Id @GeneratedValue
    @Column(name = "id" )
    private Long id;

    @Column(name="message", nullable=false)
    private String message;

    @Column(name="timestamp", nullable = false)
    private Date timestamp;

    @ManyToOne
    @JoinColumn(name="author_id")
    private User author;


    public ChatMessage() {

    }

    public ChatMessage(String message, User author) {
        this.message = message;
        this.timestamp = new Date();
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getAuthor() {
        return this.author;
    }

    @Override
    public String toString() {
        return String.format("id:%d message:%s timestamp:%s author:%s",
                id, message, timestamp, author);
    }
	
	public String getAuthorFullName() {
		return getAuthor().getFullName();
	}
}
