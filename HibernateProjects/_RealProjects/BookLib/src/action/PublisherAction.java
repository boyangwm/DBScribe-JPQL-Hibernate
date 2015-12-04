package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cfg.hibernate.Beanpublisher;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import dao.BookDAO;
import dao.PublisherDAO;

public class PublisherAction extends ActionSupport {
	private PublisherDAO pubDAO = new PublisherDAO();
	private BookDAO bkDAO = new BookDAO();
	private Beanpublisher publisher = new Beanpublisher();
	private String[] select = null;
	private String json;
	private String pubid;
	private String publisherName;
	private String address;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getPubid() {
		return pubid;
	}

	public void setPubid(String pubid) {
		this.pubid = pubid;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String loadAll() throws Exception {
		ActionContext.getContext().getSession()
				.put("publisherlist", pubDAO.listAll());
		return "loadall";
	}

	public String add() throws Exception {
		if (isExist() == true) {
			ActionContext.getContext().put("msg", "ID或名称已经存在！");
			return "addfailure";
		} else {
			publisher.setPubid(pubid);
			publisher.setPublisherName(publisherName);
			publisher.setAddress(address);
			pubDAO.add(publisher);
			return "addsuccess";
		}
	}

	public String delete() throws Exception {
		select = json.split(",");
		List<String> list = new ArrayList<String>();
		Long count = null;
		if (select[0]!=null&&(!select[0].equals(""))) {
			for (int i = 0; i < select.length; i++) {
				publisher = pubDAO.loadbyid(select[i]);
				count = bkDAO.count(publisher);
				if (count > 0) {
					list.add(publisher.getPublisherName());
				} else
					pubDAO.delete(select[i]);
			}
			if(list.size()!=0)
				ActionContext.getContext().put("count", list.get(0)+"已有相关书籍，无法删除！");
		}
		return "delete";
	}

	public String editValueSend() {
		ActionContext.getContext().put("editvalue", pubDAO.loadbyid(pubid));
		return "send";
	}

	public String edit() {
		if (pubDAO.loadbyname(publisherName) != null) {
			ActionContext.getContext().put("msg", "名称已经存在！");
			return "editfailure";
		}
		publisher.setPubid(pubid);
		publisher.setPublisherName(publisherName);
		publisher.setAddress(address);
		pubDAO.update(publisher);
		return "editsuccess";
	}

	public boolean isExist() {
		if (pubDAO.loadbyid(pubid) != null)
			return true;
		if (pubDAO.loadbyname(publisherName) != null)
			return true;
		else
			return false;
	}

}
