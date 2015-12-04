package action;

import java.util.ArrayList;
import java.util.Date;

import dao.*;
import antlr.collections.List;
import cfg.hibernate.Beansystemuser;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {
	private String uid;
	private String uname;
	private String pwd;
	private String usertype;
	private UserDAO userDAO = new UserDAO();
	private String newPwd = "123456";
	private String json;
	private String[] select = null;
	private Beansystemuser user = new Beansystemuser();
	public static Beansystemuser currentuser = new Beansystemuser();

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String login() throws Exception {
		user = userDAO.checkLogin(uid, pwd);
		if (user == null) {
			ActionContext.getContext().put("msg", "用户名或密码错误！");
			return "loginfaliure";
		} else if (user.getUsertype().equals("读者")) {
			currentuser = user;
			ActionContext.getContext().getSession().put("user", user);
			return "loginreader";
		} else {
			currentuser = user;
			ActionContext.getContext().getSession().put("user", user);
			return "login";
		}
	}

	public String loadAllUser() throws Exception {
		ActionContext.getContext().getSession()
				.put("userlist", userDAO.listAll());
		return "loadall";
	}

	public String register() throws Exception {
		if (isUserExist(uid) == true) {
			ActionContext.getContext().put("msg", "账号已经存在！");
			return "registerfailure";
		} else {
			user.setUserid(uid);
			user.setUsername(uname);
			user.setPwd(pwd);
			user.setUsertype("读者");
			user.setCreateDate(new Date());
			userDAO.add(user);
			return "register";
		}
	}

	public String addUser() throws Exception {
		if (isUserExist(uid) == true) {
			ActionContext.getContext().put("msg", "账号已经存在！");
			return "addfailure";
		} else {
			user.setUserid(uid);
			user.setUsername(uname);
			user.setPwd(pwd);
			user.setUsertype(usertype);
			user.setCreateDate(new Date());
			userDAO.add(user);
			return "addsuccess";
		}
	}

	public boolean isUserExist(String uid) {
		if (userDAO.loadbyuserid(uid) != null)
			return true;
		else
			return false;
	}

	public String delete() {
		select = json.split(",");
		if (!select[0].equals("")) {
			for (int i = 0; i < select.length; i++) {
				userDAO.delete(select[i]);
			}
		}
		return "delete";
	}

	public String resetPwd() {
		select = json.split(",");
		for (int i = 0; i < select.length; i++) {
			userDAO.resetPwd(select[i], newPwd);
		}
		return "resetpwd";
	}
}
