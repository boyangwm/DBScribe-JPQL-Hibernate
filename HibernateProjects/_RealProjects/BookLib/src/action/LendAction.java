package action;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionContext;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanbooklendrecord;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreserve;
import cfg.hibernate.Beansystemuser;
import dao.BookDAO;
import dao.LendDAO;
import dao.ReaderDAO;
import dao.ReaderTypeDAO;
import dao.ReserveDAO;

public class LendAction {
	private String id;
	private Beansystemuser beansystemuserByReturnOperUserid;
	private Beanreader beanreader;
	private Beansystemuser beansystemuserByLendOperUserid;
	private Beanbook beanbook;
	private String readerid;
	private String bookid;
	private Date lendDate;
	private Date returnDate;
	private Double penalSum;
	private Beanbooklendrecord lendrecord = new Beanbooklendrecord();
	private ReaderDAO rdDAO = new ReaderDAO();
	private LendDAO ld = new LendDAO();
	private BookDAO bkDAO = new BookDAO();
	private ReserveDAO rsDAO = new ReserveDAO();
	private Beanreserve reserve = new Beanreserve();
	private ReaderTypeDAO rtDAO = new ReaderTypeDAO();

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getReaderid() {
		return readerid;
	}

	public void setReaderid(String readerid) {
		this.readerid = readerid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Beansystemuser getBeansystemuserByReturnOperUserid() {
		return beansystemuserByReturnOperUserid;
	}

	public void setBeansystemuserByReturnOperUserid(
			Beansystemuser beansystemuserByReturnOperUserid) {
		this.beansystemuserByReturnOperUserid = beansystemuserByReturnOperUserid;
	}

	public Beanreader getBeanreader() {
		return beanreader;
	}

	public void setBeanreader(Beanreader beanreader) {
		this.beanreader = beanreader;
	}

	public Beansystemuser getBeansystemuserByLendOperUserid() {
		return beansystemuserByLendOperUserid;
	}

	public void setBeansystemuserByLendOperUserid(
			Beansystemuser beansystemuserByLendOperUserid) {
		this.beansystemuserByLendOperUserid = beansystemuserByLendOperUserid;
	}

	public Beanbook getBeanbook() {
		return beanbook;
	}

	public void setBeanbook(Beanbook beanbook) {
		this.beanbook = beanbook;
	}

	public Date getLendDate() {
		return lendDate;
	}

	public void setLendDate(Date lendDate) {
		this.lendDate = lendDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public Double getPenalSum() {
		return penalSum;
	}

	public void setPenalSum(Double penalSum) {
		this.penalSum = penalSum;
	}

	public String loadAll() {
		List<Beanbooklendrecord> list = null;
		list = ld.listAll();
		ActionContext.getContext().getSession().put("lendlist", list);
		return "loadall";
	}

	public String loadByReader() {
		List<Beanbooklendrecord> list = null;
		beanreader = rdDAO.loadbyid(readerid);
		if (bookid != null && (!bookid.equals(""))) {
			beanbook = bkDAO.loadbyid(bookid);
			if (beanbook != null) {
				ActionContext.getContext().put("state", beanbook.getState());
				ActionContext.getContext().put("book", beanbook.getBookname());
			}
		}
		if (beanreader != null) {
			list = ld.listByReader(beanreader);
			ActionContext.getContext()
					.put("reader", beanreader.getReaderName());
			ActionContext.getContext().put("lendlist", list);
		}
		ActionContext.getContext().put("readeridvalue", readerid);
		ActionContext.getContext().put("bookidvalue", bookid);
		return "loadbyreader";
	}

	public String loadByReaderid() {
		List<Beanbooklendrecord> list = null;
		beanreader = rdDAO.loadbyid(readerid);
		if (beanreader != null) {
			list = ld.listByReaderid(beanreader);
			ActionContext.getContext()
					.put("reader", beanreader.getReaderName());
			ActionContext.getContext().put("lendlist", list);
		}
		ActionContext.getContext().put("readeridvalue", readerid);
		return "loadbyreaderid";
	}

	public String loadByBook() {
		beanbook = bkDAO.loadbyid(bookid);
		List<Beanbooklendrecord> list = null;
		ActionContext.getContext().put("bookidvalue", bookid);
		if (beanbook != null) {
			list = ld.listByBook2(beanbook);
			ActionContext.getContext().put("book", beanbook.getBookname());
			ActionContext.getContext().put("state", beanbook.getState());
			ActionContext.getContext().put("lendlist", list);
		}
		return "loadbybook";
	}

	public String loadByBookid() {
		beanbook = bkDAO.loadbyid(bookid);
		List<Beanbooklendrecord> list = null;
		ActionContext.getContext().put("bookidvalue", bookid);
		if (beanbook != null) {
			list = ld.listByBookid(beanbook);
			ActionContext.getContext().put("book", beanbook.getBookname());
			ActionContext.getContext().put("state", beanbook.getState());
			ActionContext.getContext().put("lendlist", list);
		}
		return "loadbybookid";
	}

	public String add() {
		beanbook = bkDAO.loadbyid(bookid);
		if (beanbook != null) {
			if (rsDAO.listByBook(beanbook).isEmpty()) {
				if (bkDAO.loadbyid(bookid).getState().equals("在库")) {
					beanreader = rdDAO.loadbyid(readerid);
					Long sum = rdDAO.countLimit(beanreader);
					int limitted = rtDAO.loadbyid(
							rdDAO.loadbyid(readerid).getReaderTypeId())
							.getLendBookLimitted();
					if (sum < limitted) {
						lendrecord.setBeanbook(bkDAO.loadbyid(bookid));
						lendrecord.setBeanreader(rdDAO.loadbyid(readerid));
						lendrecord
								.setBeansystemuserByLendOperUserid(UserAction.currentuser);
						lendrecord.setLendDate(new Date());
						lendrecord.setPenalSum(0.0);
						beanbook = bkDAO.loadbyid(bookid);
						beanbook.setState("已借出");
						bkDAO.update(beanbook);
						ld.add(lendrecord);
					} else {
						ActionContext.getContext().put("state", "借阅数已达到上限");
					}
				} else {
					ActionContext.getContext().put("state",
							bkDAO.loadbyid(bookid).getState());
				}
			} else if (rsDAO.listByBook(beanbook).get(0).getBeanreader()
					.getReaderid().equals(readerid)) {
				reserve=rsDAO.listByBook(beanbook).get(0);
				if (bkDAO.loadbyid(bookid).getState().equals("在库")) {
					beanreader = rdDAO.loadbyid(readerid);
					Long sum = rdDAO.countLimit(beanreader);
					int limitted = rtDAO.loadbyid(
							rdDAO.loadbyid(readerid).getReaderTypeId())
							.getLendBookLimitted();
					if (sum < limitted) {
						lendrecord.setBeanbook(bkDAO.loadbyid(bookid));
						lendrecord.setBeanreader(rdDAO.loadbyid(readerid));
						lendrecord
								.setBeansystemuserByLendOperUserid(UserAction.currentuser);
						lendrecord.setLendDate(new Date());
						lendrecord.setPenalSum(0.0);
						beanbook = bkDAO.loadbyid(bookid);
						beanbook.setState("已借出");
						bkDAO.update(beanbook);
						ld.add(lendrecord);
						reserve.setCancelDate(new Date());
						rsDAO.update(reserve);
					} else {
						ActionContext.getContext().put("state", "借阅数已达到上限");
					}
				} else {
					ActionContext.getContext().put("state",
							bkDAO.loadbyid(bookid).getState());
				}
			} else {
				ActionContext.getContext().put("state", "书本已被预约！");
			}
		} else {
			ActionContext.getContext().put("state", "书本不存在！");
		}
		return "addsuccess";
	}

	public String delete() {
		if (bkDAO.loadbyid(bookid).getState().equals("已借出")) {
			beanbook = bkDAO.loadbyid(bookid);
			if (beanbook != null) {
				List<Beanbooklendrecord> list = ld.listByBook(beanbook);
				lendrecord = list.get(0);
				lendrecord.setReturnDate(new Date());
				lendrecord
						.setBeansystemuserByReturnOperUserid(UserAction.currentuser);
				Date lendDate = lendrecord.getLendDate();
				long x = (System.currentTimeMillis() - lendDate.getTime())
						/ (1000 * 60 * 60 * 24);
				double penalSum = 0;
				if (x > 60) {// 超过60天需要处罚
					penalSum = (x - 60) * 0.1;
				}
				lendrecord.setPenalSum(penalSum);
				beanbook = bkDAO.loadbyid(bookid);
				beanbook.setState("在库");
				bkDAO.update(beanbook);
				ld.update(lendrecord);
			}
		} else
			ActionContext.getContext().put("state",
					bkDAO.loadbyid(bookid).getState());
		return "return";
	}
	
	public String listMyLend(){
		beanreader=rdDAO.loadbyname(UserAction.currentuser.getUsername());
		List<Beanbooklendrecord> list=ld.listByReaderid(beanreader);
		if(!list.isEmpty()){
			ActionContext.getContext().getSession().put("lendlist",list);
		}
		return "listMyLend";
	}
	
}
