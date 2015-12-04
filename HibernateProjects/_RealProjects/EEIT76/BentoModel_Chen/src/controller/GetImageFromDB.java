package controller;

import java.io.*;
import java.sql.*;
import javax.naming.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;

//本類別會依據傳入的書籍編號(BookID)讀取eBook表格內CoverImage欄位內的圖片，
//然後傳回給提出請求的瀏覽器
public class GetImageFromDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = null;
		Connection conn = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			// 讀取瀏覽器傳送來的書籍代號(BookID)
			String TreatID = request.getParameter("TreatID");//RestID
			String RestID = request.getParameter("RestID");
			System.out.println("..");
			System.out.println(TreatID);
			System.out.println(RestID);
			// 分辨讀取哪個表格的圖片欄位
//			String type = request.getParameter("type"); 
			// 取得能夠執行JNDI的Context物件
			Context context = new InitialContext();
			// 透過JNDI取得DataSource物件
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/bento");
			conn = ds.getConnection();
			PreparedStatement pstmt = null;
			pstmt = conn.prepareStatement(
						"SELECT Image from Advertisement where TreatID = ? and RestID=? order by RestID");
			pstmt.setString(1, TreatID);
			pstmt.setString(2, RestID);
			rs = pstmt.executeQuery();
			os = response.getOutputStream();
			if (rs.next()) {
				System.out.println("*****************");
				// Image欄位可以取出InputStream物件
				is = rs.getBinaryStream(1);				
//				String mimeType = getServletContext().getMimeType(fileName);
				//設定輸出資料的型態
//				response.setContentType(mimeType);
				// 取得能寫出非文字資料的OutputStream物件
//				os = response.getOutputStream();				
				if (is == null) {
					is = getServletContext().getResourceAsStream("/Image/1111.jpg");  //在設定
				}

				int count = 0;
				byte[] bytes = new byte[1024];
				while ((count = is.read(bytes)) != -1) {
					os.write(bytes, 0, count);
				}
			}
		} catch (NamingException e) {
			throw new ServletException(e);
		} catch (SQLException e) {
			throw new ServletException(e);
		} finally {
			if (conn != null) {
				try {
					conn.close(); // 一定要註解此行來執行本程式五次
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				os.close();
			}
		}
	}
}