package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.dish.model.DishService;
import com.dish.model.DishVO;
import com.owner.model.OwnerVO;
import com.restaurant.model.RestaurantVO;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.compound.io.ReadException;

public class UploadExcelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UploadExcelServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	HttpSession session;
	File tmpDir = null;//初始化上傳文件的臨時存放目錄
    File saveDir = null;//初始化上傳文件後的保存目錄
    PrintWriter outPrint;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    session = request.getSession();
	    response.setContentType("text/html;charset=UTF-8");
	    outPrint = response.getWriter();
	    File savedPath = null;
	    JSONObject jsonObject = new JSONObject();
        try{  
            if(ServletFileUpload.isMultipartContent(request)){
	            DiskFileItemFactory dff = new DiskFileItemFactory(); 
	            dff.setRepository(tmpDir);//指定上傳文件的臨時目錄 
	            dff.setSizeThreshold(1024000);//指定在記憶體中暫存數據大小,單位為byte
	            ServletFileUpload sfu = new ServletFileUpload(dff);
	            sfu.setFileSizeMax(5000000);//指定單個上傳文件的最大尺寸
	            sfu.setSizeMax(10000000);// 指定一次上傳多個文件的總尺寸
	            FileItemIterator fii = sfu.getItemIterator(request);// 解析request,並返回FileItemIterator集合
	            while(fii.hasNext()){  
		            FileItemStream fis = fii.next();//從集合中獲得一個文件流
		            if(!fis.isFormField() && fis.getName().length()>0){//過濾掉表單中非文件域
		            //String fileName = fis.getName().substring(fis.getName().lastIndexOf("\\"));//獲得上傳文件的文件名
		            String fileName = fis.getName();
		            BufferedInputStream in = new BufferedInputStream(fis.openStream());//獲得文件輸入流
		            savedPath = new File(saveDir+"\\"+fileName);
		            BufferedOutputStream out = new BufferedOutputStream(new 
		            FileOutputStream(savedPath));//獲得文件輸出流
		            Streams.copy(in, out, true);//開始把文件寫到指定的上傳文件夾  
		            }  
		            
	            }
	            JSONArray jsonArray = this.readExcel(savedPath);
	            if(jsonArray==null){
	            	jsonObject.put("error", "新增菜單失敗");
	       		 	outPrint.println(jsonObject.toString());
	       		 	savedPath.delete();//將文件刪除
	       		 	return;	
	            }
	            JSONObject exam = (JSONObject)jsonArray.get(0);
            	if(exam.has("error")){
            		outPrint.println(exam.toString());
            		savedPath.delete();//將文件刪除
		            return;
            	}else{
            		jsonObject.put("msg", jsonArray);
		            outPrint.println(jsonObject.toString());
		            savedPath.delete();//將文件刪除
		            return;
            	}
	        }  
	 }catch(Exception e){  
		 jsonObject.put("error", "新增菜單失敗:"+ e.getMessage());
		 outPrint.println(jsonObject.toString());
		 return;
     } 
	}
	 @Override  
    public void init() throws ServletException {  
        super.init();
        //對上傳文件夾和臨時文件夾進行初始化
        String tmpPath = this.getServletContext().getRealPath("/tmpdir");  
        String savePath = this.getServletContext().getRealPath("/updir");  
        tmpDir = new File(tmpPath);  
        saveDir = new File(savePath);  
        if(!tmpDir.isDirectory())  
        tmpDir.mkdir();  
        if(!saveDir.isDirectory())  
        saveDir.mkdir();
    }
	 //解析Excel檔並將資料放進JSONArray
	 private JSONArray readExcel(File file) throws ParserException, ReadException {
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		JSONArray jsonArray = null;
		try {
			workBook.open();
			Sheet s;
			s = workBook.getSheet(0);
			jsonArray = new JSONArray();
			for (int i = s.getFirstRow(); i <= s.getLastRow()-1; i++) {
				StringBuilder temp = new StringBuilder();
				for (int j = s.getFirstColumn(); j <=s.getLastColumn()-1; j++) {
					temp.append(s.getCell(i, j));
					if(j<s.getLastColumn()-1){
						temp.append(",");
					}
				}System.out.println(temp);
				JSONObject obj = this.addDishToDB(temp.toString());
				if(obj.has("error")){
					JSONArray array = new JSONArray();
					array.put(obj);
					return array;
				}else{
					jsonArray.put(obj);
				}
			}
		} catch (ExcelException e) {
			System.out.println("ExcelException");
			e.printStackTrace();
		}finally{
			if(workBook!=null){
				try {
					workBook.close();
				} catch (ExcelException e) {
					System.out.println("closeExcelException");
					e.printStackTrace();
				}
			}
		}
		return jsonArray;
	}
	 //解析每筆Excel資料並將其存至資料庫
	 private JSONObject addDishToDB(String dishNameAndPrice){
		 String[] temp  = dishNameAndPrice.split(",");
		 String dishName = temp[0];
		 String temp1 = temp[1];
		 DishService DS = new DishService();
		 JSONObject jsonObject = new JSONObject();
		 JSONArray errorMsgs = new JSONArray();
			try {
				if (dishName == null || dishName.trim().length() == 0) {
					errorMsgs.put("品名請勿空白!");
				}
				Integer price = null;
				if (temp1.equals(0) || (temp1.trim()).length() == 0) {
					errorMsgs.put("請輸入價錢!");
				} else {
					try {
						price = Integer.valueOf(temp1);
					} catch (Exception e) {
						errorMsgs.put("價錢格式不正確!");
					}
				}
				Integer RestID = 0;
				OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
				Set<RestaurantVO> restaurantVOs = ownerVO.getRestaurants();
				for(RestaurantVO rvo: restaurantVOs ){
					RestID = rvo.getRestID();
				}
				if (DS.dishNameExists(dishName, RestID)) { // 判斷品名是否重複
					errorMsgs.put("品名重複");
				}
				if (errorMsgs.length() != 0) {
					jsonObject.put("error", errorMsgs);
					return jsonObject;// 程式中斷
				}
				DishVO dishVO = DS.addDish(dishName, price, null,
						null, null, RestID);
				jsonObject.put("dishName", dishVO.getDishName());
				jsonObject.put("price", dishVO.getPrice());
				jsonObject.put("specialPrice", dishVO.getSpecialPrice());
				jsonObject.put("startDate", dishVO.getStartDate());
				jsonObject.put("endDate", dishVO.getEndDate());
				jsonObject.put("dishID", dishVO.getDishID());
				jsonObject.put("restID", dishVO.getRestID());
				return jsonObject;
			} catch (Exception e) {
				errorMsgs.put("無法成功新增:" + e.getMessage());
				jsonObject.put("error", errorMsgs);
				return jsonObject;
			}
	 }
}
