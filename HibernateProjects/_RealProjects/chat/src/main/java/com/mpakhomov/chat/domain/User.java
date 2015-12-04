package com.mpakhomov.chat.domain;


import com.mpakhomov.chat.domain.ChatMessage;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NamedQueries({
    @NamedQuery(
        name = "findUserByNick",
        query = "from User u where u.nick = :nick"
    )
})
@Entity
@Table(name="user")
public class User {
    @Id @GeneratedValue
    @Column(name = "id" )
    private Long id;

    @Size(min=2, max=15, message=
            "First name must be between 2 and 15 characters long")
    @Pattern(regexp="^[a-zA-Z]+$",
            message="Invalid characters")
    private String firstName;

    @Size(max=15, message=
            "Middle name must be less than 15 characters long")
    @Pattern(regexp="^([a-zA-Z \\.])*$",
            message="Invalid characters")
    private String middleName;

    @Size(max=15, message=
            "Last name must be between 2 and 15 characters long")
    @Pattern(regexp="^[a-zA-Z]+$",
            message="Invalid characters")
    private String lastName;

    @Size(min=3, max=15, message=
            "Nick must be between 3 and 15 characters long")
    @Pattern(regexp="^[a-zA-Z0-9]+$",
            message="Nick must be alphanumeric with no spaces")
    private String nick;

    @Size(min=5, max=20,
            message="The password must be at least 5 characters long")
    private String password;


    @Pattern(regexp="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
            message="Invalid email address")
    private String email;

    public User() {

    }

    public User(String firstName, String middleName, String lastName, String nick,
                String email, String password) {
        this.id = null;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name="nick", unique=true, nullable=false)
    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Column(name="firstName", nullable=false)
    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name="middleName", nullable=true)
    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Column(name="lastName", nullable=false)
    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
	
	@Column(name="password", nullable=false)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name="email", nullable=false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return String.format("id:%d nick:%s firstName:%s middleName:%s lastName:%s email:%s password:%s",
                    id, nick, firstName, middleName, lastName, email, password);
    }
	
	public String getFullName() {
		return getFirstName() + " " + getMiddleName() + " " + getLastName();
	}
	
	public void setFullName(String firstName, String middleName, String lastName) {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
	}
}
