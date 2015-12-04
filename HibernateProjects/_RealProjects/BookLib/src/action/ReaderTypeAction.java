package action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import com.opensymphony.xwork2.ActionContext;

import cfg.hibernate.Beanreadertype;
import dao.ReaderTypeDAO;

public class ReaderTypeAction {
	private ReaderTypeDAO rtDAO = new ReaderTypeDAO();
	private Beanreadertype readertype = new Beanreadertype();
	private String json;
	private String[] select = null;
	private int transnum;
	private String readerTypeId;
	private String readerTypeName;
	private String lendBookLimitted;

	public String getReaderTypeId() {
		return readerTypeId;
	}

	public void setReaderTypeId(String readerTypeId) {
		this.readerTypeId = readerTypeId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getReaderTypeName() {
		return readerTypeName;
	}

	public void setReaderTypeName(String readerTypeName) {
		this.readerTypeName = readerTypeName;
	}

	public String getLendBookLimitted() {
		return lendBookLimitted;
	}

	public void setLendBookLimitted(String lendBookLimitted) {
		this.lendBookLimitted = lendBookLimitted;
	}

	public String loadAll() {
		ActionContext.getContext().getSession()
				.put("typelist", rtDAO.listAll());
		return "loadall";
	}

	public String add() {
		if (rtDAO.loadbyname(readerTypeName) != null) {
			ActionContext.getContext().put("msg", "该类型已经存在！");
			return "addfailure";
		}
		transnum = Integer.parseInt(lendBookLimitted);
		readertype.setReaderTypeName(readerTypeName);
		readertype.setLendBookLimitted(transnum);
		rtDAO.add(readertype);
		return "addsuccess";
	}

	public String delete() throws UnsupportedEncodingException {
		URLDecoder.decode(json, "utf-8");
		select = json.split(",");
		if (!select[0].equals("")) {
			for (int i = 0; i < select.length; i++) {
				rtDAO.delete(select[i]);
			}
		}
		return "delete";
	}

	public String edit() {
		transnum = Integer.parseInt(lendBookLimitted);
		readertype = rtDAO.loadbyid(Integer.parseInt(readerTypeId));
		readertype.setReaderTypeName(readerTypeName);
		readertype.setLendBookLimitted(transnum);
		rtDAO.update(readertype);
		return "editsuccess";
	}

	public String editValueSend() {
		ActionContext.getContext().put("editvalue",
				rtDAO.loadbyid(Integer.parseInt(readerTypeId)));
		return "send";
	}
}
