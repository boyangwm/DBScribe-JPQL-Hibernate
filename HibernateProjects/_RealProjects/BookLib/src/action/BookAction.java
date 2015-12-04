package action;

import java.util.Date;
import java.util.List;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanbooklendrecord;
import cfg.hibernate.Beanpublisher;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreadertype;

import com.opensymphony.xwork2.ActionContext;

import dao.BookDAO;
import dao.PublisherDAO;

public class BookAction {
	private String barcode;
	private String bookname;
	private String price;
	private String state;
	private String publisher;
	private Beanbook book = new Beanbook();
	private BookDAO bk = new BookDAO();
	private PublisherDAO pubDAO = new PublisherDAO();
	private String[] select = null;
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String loadAll() {
		List<Beanbook> list = null;
		if (state != null && bookname != null && (!bookname.equals(""))) {
			list = bk.loadByStateAndName(bookname, state);
		} else if (state != null) {
			list = bk.loadByState(state);
		} else {
			list = bk.listAll();
		}
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setPublisher(
					list.get(i).getBeanpublisher().getPublisherName());
		}
		List<Beanpublisher> publisher = pubDAO.listAll();
		ActionContext.getContext().getSession().put("publist", publisher);
		ActionContext.getContext().getSession().put("booklist", list);
		return "loadall";
	}

	public String add() {
		if (isExist() == true) {
			ActionContext.getContext().put("msg", "ID已经存在！");
			return "addfailure";
		} else {
			book.setBarcode(barcode);
			book.setBookname(bookname);
			book.setBeanpublisher(pubDAO.loadbyid(publisher));
			book.setPrice(Double.parseDouble(price));
			book.setState("在库");
			bk.add(book);
			return "addsuccess";
		}
	}

	public String delete() {
		select = json.split(",");
		if (!select[0].equals("")) {
			for (int i = 0; i < select.length; i++) {
				book = bk.loadbyid(select[i]);
				book.setState("已删除");
				bk.update(book);
			}
		}
		return "delete";
	}

	public String editValueSend() {
		List<Beanpublisher> publisher = pubDAO.listAll();
		ActionContext.getContext().getSession().put("publist", publisher);
		ActionContext.getContext().put("editvalue", bk.loadbyid(barcode));
		return "send";
	}

	public String edit() {
		book = bk.loadbyid(barcode);
		book.setBookname(bookname);
		book.setPublisher(publisher);
		book.setPrice(Double.parseDouble(price));
		bk.update(book);
		return "editsuccess";
	}

	public boolean isExist() {
		if (bk.loadbyid(barcode) != null)
			return true;
		else
			return false;
	}

	public String loadByBook() {
		List<Beanbook> list = null;
		List<Long> count = bk.countBook();
		list = bk.listBookRecord();
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setCount(count.get(i));
		}
		ActionContext.getContext().getSession().put("booklist", list);
		return "loadByBook";
	}
}
