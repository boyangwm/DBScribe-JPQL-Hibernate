package com.ordersum.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;
import com.ordercond.model.OrderCondVO;
import com.orderdetail.model.OrderDetailVO;
import com.restaurant.model.RestaurantHibernateDAO;
import com.restaurant.model.RestaurantVO;
import com.useraddr.model.UserAddrDAO;
import com.useraddr.model.UserAddrDAO_Interface;
import com.useraddr.model.UserAddrVO;

public class MemberOrderService {
	
	private OrderSumDAO_Interface dao;

	public MemberOrderService() {
		dao = new OrderSumDAO();
	}

	// 客戶下訂單
	public OrderSumVO addOrder(Integer RestID, String MemberAcc,
			String MemberPhone, String City, String Area, String Addr,
			Integer TotalPrice, Date ExpectDate, Time ExpectTime, String Memo,
			String json) {
		Long now = new java.util.Date().getTime();
		Date today = new Date(now);
		RestaurantVO restaurantVO = new RestaurantVO();
		MemberVO memberVO = new MemberVO();
		OrderCondVO orderCondVO = new OrderCondVO();
		UserAddrDAO_Interface userAddrDAO = new UserAddrDAO();
		Set<OrderDetailVO> set = null;
		OrderSumVO orderSumVO = new OrderSumVO();
		restaurantVO.setRestID(RestID);
		memberVO.setMemberAcc(MemberAcc);
		memberVO.setMemberPhone(MemberPhone);
		orderCondVO.setOrderCondID(1);
		String orderSumID = this.generateOrderSumID(RestID, today);
		orderSumVO.setOrderSumID(orderSumID);
		orderSumVO.setRestaurantVO(restaurantVO);
		orderSumVO.setMemberVO(memberVO);
		orderSumVO.setMemberPhone(memberVO.getMemberPhone());
		orderSumVO.setCity(City);
		orderSumVO.setArea(Area);
		orderSumVO.setAddr(Addr);

		// 這裡要根據得到的地址去取得經緯度(要去檢查地址是否有寫在常用地址,有的話直接從那裡取)
		List<UserAddrVO> list = userAddrDAO.getAllByMemberAcc(MemberAcc);
		for (UserAddrVO userAddrVO : list) {
			if (City.equals(userAddrVO.getCity())
					&& Area.equals(userAddrVO.getArea())
					&& Addr.equals(userAddrVO.getAddr())) {
				orderSumVO.setLatitude(userAddrVO.getLatitude());
				orderSumVO.setLongitude(userAddrVO.getLongitude());
			}
		}
		if (orderSumVO.getLatitude() == null
				&& orderSumVO.getLongitude() == null) {
			String[] LatLng = getLatLngByAddress(City, Area, Addr).split(",");
			orderSumVO.setLatitude(Double.valueOf(LatLng[0]));
			orderSumVO.setLongitude(Double.valueOf(LatLng[1]));
		}

		orderSumVO.setTotalPrice(TotalPrice);
		orderSumVO.setOrderDate(today);
		orderSumVO.setOrderTime(new Time(now));
		orderSumVO.setExpectDate(ExpectDate);
		orderSumVO.setExpectTime(ExpectTime);
		orderSumVO.setMemo(Memo);
		orderSumVO.setOrderCondVO(orderCondVO);
		dao.insert(orderSumVO);

		// 以下要將得到的json字串轉成一個個orderDetail塞進去
		JSONObject obj = new JSONObject(json);
		if (obj != null) {
			set = new LinkedHashSet<OrderDetailVO>();
			JSONArray array = obj.getJSONArray("orderDetail");
			for (int i = 0; i < array.length(); i++) {
				OrderDetailVO orderDetailVO = new OrderDetailVO();
				JSONObject oDobj = (JSONObject) array.get(i);
				orderDetailVO.setOrderSumID(orderSumID);
				orderDetailVO.setDishID(oDobj.getInt("id"));
				orderDetailVO.setPrice(oDobj.getInt("price"));
				orderDetailVO.setQuantity(oDobj.getInt("quantity"));
				orderDetailVO.setSubtotal(oDobj.getInt("subtotal"));
				set.add(orderDetailVO);
			}
		}
		orderSumVO.setOrderDetails(set);
		dao.insert(orderSumVO);
		orderSumVO = dao.getByPrimaryKey(orderSumVO.getOrderSumID());
		return orderSumVO;
	}

	// Member這裡的update只會在想申請取消訂單時用到,會動到的欄位為memo
	public void updateOrder(OrderSumVO orderSumVO) {
		dao.update(orderSumVO);
	}
	
	

	// 在客戶的帳戶秀出所有訂單(最新的訂單會排在最上面)
	public List<OrderSumVO> getAll(String MemberAcc) {
		return dao.getAllByMemberAcc(MemberAcc);
	}
	
	public List<OrderSumVO> getByMemberExpectDate(String MemberAcc,Date ExpectDate){
		return dao.getByMemberExpectDate(MemberAcc, ExpectDate);
	}
	
	public List<OrderSumVO> getByMemberOrderCondID(String MemberAcc,Integer OrderCondID){
		return dao.getByMemberOrderCondID(MemberAcc, OrderCondID);
	}
	
	public List<OrderSumVO> getByRestName(String MemberAcc, String RestName){
		List<OrderSumVO> result = new ArrayList<OrderSumVO>();
		RestaurantHibernateDAO rdao = new RestaurantHibernateDAO();
		List<Integer> list = rdao.GetRestID(RestName);
		for (Integer id : list) {
			List<OrderSumVO> temp = dao.getByRestMemberAcc(id, MemberAcc);
			for (OrderSumVO orderSumVO : temp) {
				result.add(orderSumVO);
			}
		}
		return result;
	}
	
	

	// 產生OrderSumID的method
	private String generateOrderSumID(Integer RestID, Date today) {
		Long identity = dao.getCountByRestIDAndOrderDate(RestID, today);
		String[] temp = today.toString().split("-");
		return (new StringBuilder())
				.append(RestID)
				.append(temp[0])
				.append(temp[1])
				.append(temp[2])
				.append((((identity + 1) < 10) ? "00" + (identity + 1)
						: ((identity + 1) < 100) ? "0" + (identity + 1)
								: (identity + 1))).toString();
	}

	// 把地址轉成經緯度的method
	public static String getLatLngByAddress(String City, String Area,
			String Addr) {
		String latLng = "";
		BufferedReader in = null;
		try {
			URL url = new URL(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ URLEncoder.encode(
									(new StringBuilder()).append(City)
											.append(Area).append(Addr)
											.toString(), "UTF-8")
							+ "&language=zh-TW&sensor=true");
			HttpURLConnection httpConn = (HttpURLConnection) url
					.openConnection();
			httpConn.setDoInput(true);
			in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream(), "UTF-8"));
			String line;
			String result = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			JSONObject obj = new JSONObject(result);
			if (obj != null) {
				JSONObject temp = ((JSONObject) obj.getJSONArray("results")
						.get(0)).getJSONObject("geometry").getJSONObject(
						"location");
				latLng = temp.getDouble("lat") + "," + temp.getDouble("lng");
			}
			System.out.println(latLng);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return latLng;
	}

	public static void main(String[] args) {
		MemberOrderService service = new MemberOrderService();
		
		List<OrderSumVO> test = service.getByRestName("Bad5566", "晶美自助餐");
		
		for(OrderSumVO aa:test){
			
			System.out.println("總價"+aa.getTotalPrice());
			
		}
		
		
		
		// 新增
//		String json = "{\"orderDetail\":[{\"id\":1,\"price\":85,\"quantity\":1,\"subtotal\":85},{\"id\":2,\"price\":75,\"quantity\":1,\"subtotal\":75}]}";
//		OrderSumVO vo = service.addOrder(101, "Bad5566", "02-222-2222", "台北巿",
//				"大安區", "復興南路390號15樓", 160, null, null, null, json);
//		System.out.println("\n-------總表--------");
//		System.out.print(vo.getOrderSumID() + ",");
//		System.out.print(vo.getRestaurantVO().getRestName() + ",");
//		System.out.print(vo.getMemberVO().getMemberAcc() + ",");
//		System.out.print(vo.getMemberPhone() + ",");
//		System.out.print(vo.getCity() + ",");
//		System.out.print(vo.getArea() + ",");
//		System.out.print(vo.getAddr() + ",");
//		System.out.print(vo.getLatitude() + ",");
//		System.out.print(vo.getLongitude() + ",");
//		System.out.print(vo.getTotalPrice() + ",");
//		System.out.print(vo.getOrderDate() + ",");
//		System.out.print(vo.getOrderTime() + ",");
//		System.out.print(vo.getExpectDate() + ",");
//		System.out.print(vo.getExpectTime() + ",");
//		System.out.print(vo.getMemo() + ",");
//		System.out.print(vo.getOrderCondVO().getOrderCond());
//		System.out.println("\n-------明細--------");
//		Set<OrderDetailVO> set1 = vo.getOrderDetails();
//		for (OrderDetailVO orderDetailVO : set1) {
//			System.out.print(orderDetailVO.getOrderSumID() + ",");
//			System.out.print(orderDetailVO.getDishVO().getDishName() + ",");
//			System.out.print(orderDetailVO.getPrice() + ",");
//			System.out.print(orderDetailVO.getQuantity() + ",");
//			System.out.println(orderDetailVO.getSubtotal());
//
//		}

	}

}
