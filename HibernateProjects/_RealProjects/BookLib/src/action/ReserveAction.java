package action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreserve;
import dao.BookDAO;
import dao.ReaderDAO;
import dao.ReserveDAO;

public class ReserveAction {
	private String bookid;
	private String reserveid;
	private Beanreserve reserve = new Beanreserve();
	private Beanbook book = new Beanbook();
	private Beanreader reader = new Beanreader();
	private ReaderDAO rdDAO = new ReaderDAO();
	private ReserveDAO rsDAO = new ReserveDAO();
	private BookDAO bkDAO = new BookDAO();
	private String[] select = null;
	private String json;
	

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getReserveid() {
		return reserveid;
	}

	public void setReserveid(String reserveid) {
		this.reserveid = reserveid;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String loadByBook() {
		book = bkDAO.loadbyid(bookid);
		if (book != null) {
			List<Beanreserve> list = rsDAO.listByBook(book);
			if (!list.isEmpty()) {
				ActionContext.getContext().put("reservelist", list);
			}
			ActionContext.getContext().put("book", book.getBookname());
			ActionContext.getContext().put("state", book.getState());
		}
		ActionContext.getContext().put("bookidvalue", bookid);
		return "loadbybook";
	}
	
	public String loadByReader(){
		reader=rdDAO.loadbyname(UserAction.currentuser.getUsername());
		List<Beanreserve> list=rsDAO.listByReader(reader);
		if(!list.isEmpty()){
			ActionContext.getContext().getSession().put("reservelist", list);
		}
		return "loadbyreader";
	}

	public String add() {
		book = bkDAO.loadbyid(bookid);
		if (!rsDAO.listByBook(book).isEmpty()) {
			reserve = rsDAO.listByBook(book).get(0);
		}
		if (book != null) {
			if (book.getState().equals("已借出")) {
				if (reserve.getReserveid() == null) {
					reserve.setBeanbook(book);
					reader = rdDAO.loadbyname(UserAction.currentuser
							.getUsername());
					reserve.setBeanreader(reader);
					reserve.setReserveDate(new Date());
					rsDAO.add(reserve);
				} else {
					ActionContext.getContext().put("state", "该书本已被预约！");
				}
			} else if (book.getState().equals("已删除")) {
				ActionContext.getContext().put("state", "该书本已删除！");
			} else {
				ActionContext.getContext().put("state", "该书本在库，可直接借阅！");
			}
		}
		return "addsuccess";
	}
	
	public String cancelReserve(){
		select = json.split(",");
		if(!select[0].equals("")){
			for (int i = 0; i < select.length; i++) {
				reserve=rsDAO.listById(select[i]);
				reserve.setCancelDate(new Date());
				rsDAO.update(reserve);
			}
		}
		return "cancel";
	}
}
