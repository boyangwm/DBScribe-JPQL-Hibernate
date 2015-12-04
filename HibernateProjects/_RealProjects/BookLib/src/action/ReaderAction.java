package action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreadertype;
import dao.LendDAO;
import dao.ReaderDAO;
import dao.ReaderTypeDAO;

public class ReaderAction {
	private String readerid;
	private String readerName;
	private String readerTypeId;
	private String readerType;
	private String lendBookLimitted;
	private ReaderDAO rdDAO = new ReaderDAO();
	private ReaderTypeDAO rtDAO = new ReaderTypeDAO();
	private LendDAO ldDAO = new LendDAO();
	private Beanreader reader = new Beanreader();
	private String[] select = null;
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getReaderType() {
		return readerType;
	}

	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getReaderTypeId() {
		return readerTypeId;
	}

	public void setReaderTypeId(String readerTypeId) {
		this.readerTypeId = readerTypeId;
	}

	public String getLendBookLimitted() {
		return lendBookLimitted;
	}

	public void setLendBookLimitted(String lendBookLimitted) {
		this.lendBookLimitted = lendBookLimitted;
	}

	public String loadAll() {
		List<Beanreader> list = null;
		if (readerName != null && readerType != null
				&& (!readerName.equals(""))) {
			list = rdDAO.loadByTypeAndName(Integer.parseInt(readerType),
					readerName);

		} else if (readerType != null) {
			list = rdDAO.loadByType(Integer.parseInt(readerType));

		} else {
			list = rdDAO.listAll();
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setReaderType(
					rtDAO.loadbyid(list.get(i).getReaderTypeId())
							.getReaderTypeName());
			if (list.get(i).getStopDate() == null
					&& list.get(i).getRemoveDate() == null)
				list.get(i).setState("正常");
			else if (list.get(i).getStopDate() != null)
				list.get(i).setState("挂失");
		}
		List<Beanreadertype> type = rtDAO.listAll();
		ActionContext.getContext().getSession().put("typelist", type);
		ActionContext.getContext().getSession().put("readerlist", list);
		return "loadall";
	}

	public String add() throws Exception {
		if (isExist() == true) {
			ActionContext.getContext().put("msg", "ID或名称已经存在！");
			return "addfailure";
		} else {
			reader.setReaderid(readerid);
			reader.setReaderName(readerName);
			reader.setReaderTypeId(Integer.parseInt(readerType));
			reader.setLendBookLimitted(rtDAO.loadbyid(
					Integer.parseInt(readerType)).getLendBookLimitted());
			reader.setCreateDate(new Date());
			reader.setBeansystemuserByCreatorUserId(UserAction.currentuser);
			rdDAO.add(reader);
			return "addsuccess";
		}
	}

	public String delete() throws Exception {
		select = json.split(",");
		if (!select[0].equals("")) {
			for (int i = 0; i < select.length; i++)
				rdDAO.delete(select[i]);
		}
		return "delete";
	}

	public String editValueSend() {
		List<Beanreadertype> type = rtDAO.listAll();
		ActionContext.getContext().getSession().put("typelist", type);
		ActionContext.getContext().put("editvalue", rdDAO.loadbyid(readerid));
		return "send";
	}

	public String edit() {
		reader = rdDAO.loadbyid(readerid);
		reader.setReaderName(readerName);
		reader.setReaderTypeId(Integer.parseInt(readerType));
		reader.setLendBookLimitted(rtDAO.loadbyid(Integer.parseInt(readerType))
				.getLendBookLimitted());
		rdDAO.update(reader);
		readerType = null;
		readerName = null;
		return "editsuccess";
	}

	public String lose() {
		select = json.split(",");
		for (int i = 0; i < select.length; i++) {
			reader = rdDAO.loadbyid(select[i]);
			reader.setStopDate(new Date());
			reader.setBeansystemuserByStopUserId(UserAction.currentuser);
			rdDAO.update(reader);
		}
		return "lose";
	}

	public String removeLose() {
		select = json.split(",");
		for (int i = 0; i < select.length; i++) {
			reader = rdDAO.loadbyid(select[i]);
			reader.setStopDate(null);
			reader.setBeansystemuserByStopUserId(null);
			rdDAO.update(reader);
		}
		return "removelose";
	}

	public boolean isExist() {
		if (rdDAO.loadbyid(readerid) != null)
			return true;
		else
			return false;
	}

	public String loadByReader() {
		List<Beanreader> list = null;
		List<Long> count = rdDAO.countReader();
		list = rdDAO.listReaderRecord();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCount(count.get(i));
			list.get(i).setPenalSum(ldDAO.countPenalSum(list.get(i)));
		}
		ActionContext.getContext().getSession().put("readerlist", list);
		return "loadByReader";
	}
}
