package action;

import cfg.hibernate.Beanbook;
import cfg.hibernate.Beanreader;
import cfg.hibernate.Beanreserve;
import dao.BookDAO;
import dao.ReaderDAO;
import dao.ReserveDAO;

public class test {
	private String bookid;
	private static Beanreserve reserve = new Beanreserve();
	private static Beanbook book;
	private static Beanreader reader=new Beanreader();
	private static ReaderDAO rdDAO = new ReaderDAO();
	private static ReserveDAO rsDAO = new ReserveDAO();
	private static BookDAO bkDAO = new BookDAO();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		reader=rdDAO.loadbyid("1");
		Long sum=rdDAO.countLimit(reader);
		System.out.println(sum==3);
	}

}
