package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.ad.model.ADservice;
import com.ad.model.AdVO;
import com.owner.model.OwnerVO;
import com.restaurant.model.RestaurantService;
import com.restaurant.model.RestaurantVO;
import com.treatcase.model.TreatCaseVO;

@WebServlet("/AddADServlet")
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024,maxFileSize = 1024 * 1024 * 500,maxRequestSize = 1024 * 1024 * 500 * 5)
public class AddADServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Map<String,String> errorMsgs = new HashMap<String,String>();
		request.setAttribute("errorMsgs", errorMsgs);
		
		Integer RestID = null;
		HttpSession session = request.getSession();
		OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
		Set<RestaurantVO> restaurantVOs = ownerVO.getRestaurants();
		for(RestaurantVO rvo: restaurantVOs ){
			RestID = rvo.getRestID();
		}
		
		
		
		ADservice ADSvc = new ADservice();
		
		String Context = "";
		String StartDate_temp  = "" ;
		String EndDate_temp  = "";
		String fileName  = "";
		
		
		long sizeInBytes = 0;
		InputStream is = null;
		Collection<Part> parts = request.getParts(); // 取出HTTP multipart request內所有的parts
		GlobalService.exploreParts(parts, request);
		if (parts != null) {
			for (Part p : parts) {  
				String fldName = p.getName();
				String value = request.getParameter(fldName);
				if (p.getContentType() == null) {
					if (fldName.equals("Context")) {
						Context = value;
					}else if (fldName.equals("StartDate")) {
						StartDate_temp = value;
					}else if (fldName.equals("EndDate")) {
						EndDate_temp = value;
					}
				}else {
					fileName = GlobalService.getFileName(p); // 此為圖片檔的檔名
					if (fileName != null && fileName.trim().length() > 0) {
						sizeInBytes = p.getSize();
						is = p.getInputStream();
					}else {
						errorMsgs.put("fileName", "必須挑選圖片檔");
					}
				}
			}
		}
		if(Context == null || Context.trim().length() == 0){
			errorMsgs.put("Context","請勿空白!");
		}
		java.sql.Date StartDate = null;
		System.out.println("StartDate_temp : "+StartDate_temp);
		System.out.println("StartDate_tempYear : "+StartDate_temp.substring(0,4));
		System.out.println("StartDate_tempMonth : "+StartDate_temp.substring(5,7));
		System.out.println("StartDate_tempDate : "+StartDate_temp.substring(8,10));
		String StartDate_temp2 = StartDate_temp.substring(0,4)+"-"+StartDate_temp.substring(5,7)+"-"+StartDate_temp.substring(8,10);
		System.out.println("StartDate_temp2 : "+StartDate_temp2);
		if(StartDate_temp == null || StartDate_temp.trim().length() == 0){
			errorMsgs.put("StarDate","請選擇!");
		}else {
			StartDate = java.sql.Date.valueOf(StartDate_temp2.trim());
		}
		java.sql.Date EndDate = null;
		System.out.println("EndDate_temp : "+EndDate_temp);
		String EndDate_temp2 = EndDate_temp.substring(0,4)+"-"+EndDate_temp.substring(5,7)+"-"+EndDate_temp.substring(8,10);
		System.out.println("EndDate_temp2 : "+EndDate_temp2);
		if(EndDate_temp == null || EndDate_temp.trim().length() == 0){
			errorMsgs.put("EndDate","請選擇!");
		}else {
			EndDate = java.sql.Date.valueOf(EndDate_temp2.trim());
		}
//		if(fileName == null || fileName.trim().length() == 0){
//			errorMsgs.put("fileName","請勿空白!");
//		}

		int startIndex = fileName.lastIndexOf(46) + 1;
	    int endIndex = fileName.length();
	    System.out.println(fileName.substring(startIndex, endIndex));
	    if(fileName != null && fileName.trim().length() > 0){
	    	if(!(fileName.substring(startIndex, endIndex).equalsIgnoreCase("jpg") 
	    		|| fileName.substring(startIndex, endIndex).equalsIgnoreCase("png"))) {
	    	errorMsgs.put("fileNameerr","請選擇jpg或是png檔");
	    	}
	    }
	    if(!errorMsgs.isEmpty()){
//			request.setAttribute("advo", advo);
			RequestDispatcher failureView = request.getRequestDispatcher("/Advertisement/addAD.jsp");
			failureView.forward(request, response);
			return;//程式中斷
		}
		System.out.println(Context);
		System.out.println(StartDate);
		System.out.println(EndDate);
		System.out.println(fileName);
		System.out.println(RestID);
		System.out.println(is);
		System.out.println(sizeInBytes);
		AdVO advo = new AdVO();
		TreatCaseVO tcvo = new TreatCaseVO();
		tcvo.setTreatID(1);
		advo.setContext(Context);
		advo.setStartDate(StartDate);
		advo.setEndDate(EndDate);
		advo.setTreatCaseVO(tcvo);
		advo.setRestID(RestID);

		System.out.println(".....");
		System.out.println(".....");
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[1024];
		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
			buffer.flush();
		}
		data = buffer.toByteArray();
		is.close();
//		advo.setImage(data);
		RestaurantVO restaurantVO = new RestaurantVO();
		RestaurantService restsvc = new RestaurantService();
		String RestName = restsvc.getOneRestaurantName(RestID);
		restaurantVO.setRestID(RestID);
		restaurantVO.setRestName(RestName);
		advo = ADSvc.addAD(Context, StartDate, EndDate, data, tcvo, RestID,restaurantVO,1);
		if(!errorMsgs.isEmpty()){
			RequestDispatcher failureView = request.getRequestDispatcher("/Advertisement/addAD.jsp");
			failureView.forward(request, response);
			return;//程式中斷
		}
		//新增成功，轉至展示層
		request.setAttribute("advo", advo);
		RequestDispatcher Success = request.getRequestDispatcher("/owner/owner.jsp");
		Success.forward(request, response);
		

	}
}
