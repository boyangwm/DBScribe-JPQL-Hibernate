package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.*;
import com.owner.model.*;
import com.restaurant.model.*;
import com.randompwd.model.RandomPwd;

@WebServlet("/InquiryPassword")
public class InquiryPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		this.doPost(request,response);
	}
	public boolean OwnAccExists(String Account){
		boolean exist = false;
		List<Object> ownerlist ;
		OwnerService ownerSvc = new OwnerService();
		ownerlist = ownerSvc.getAllOwnAcc();
		for(Object ownerAcc : ownerlist){
			if(ownerAcc.equals(Account)){
				exist = true;
				break;
			}
		}
		return exist;
	}
	public boolean MemberAccExists(String Account){
		boolean exist = false;
		List<Object> memberlist;
		MemberService memberSvc = new MemberService();
		memberlist = memberSvc.getAllMemberAcc();
		for(Object memberAcc : memberlist){
			if(memberAcc.equals(Account)){
				exist = true;
				break;
			}
		}
		return exist;
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		InquiryPasswordServlet IPS= new InquiryPasswordServlet();
		Map<String,String> errorMsgs = new HashMap<String,String>();
		request.setAttribute("errorMsgs", errorMsgs);
		
		String who = request.getParameter("who");
		String Email = request.getParameter("Email");
		if(Email == null || Email.trim().length() == 0){
			errorMsgs.put("Email","請輸入Email!");
		}
		
		if("Owner".equals(who)){
			String Account = request.getParameter("Account");
			if(Account == null || Account.trim().length() == 0){
				errorMsgs.put("Account","請輸入帳號!");
			}
			if(!(Account == null || Account.trim().length() == 0) && !IPS.OwnAccExists(Account)){
				errorMsgs.put("Account","帳號不存在!");
			}
			if(!errorMsgs.isEmpty()){
				RequestDispatcher failureView = request.getRequestDispatcher("/register/forgotPwd_Owner.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			RandomPwd newpwd = new RandomPwd();
			String Password = newpwd.getRandomPassword();
			OwnerService ownerSvc = new OwnerService();
			OwnerVO newownervo = ownerSvc.getByOwnAcc(Account);
			OwnerVO temp = new OwnerVO();
			temp.setOwnAcc(newownervo.getOwnAcc());
			temp.setOwnPwd(Password);
			temp.setOwnEmail(newownervo.getOwnEmail());
			temp.setOwnLastName(newownervo.getOwnLastName());
			temp.setOwnFirstName(newownervo.getOwnFirstName());
			temp.setOwnGender(newownervo.isOwnGender());
			temp.setOwnOpen(newownervo.isOwnOpen());
			OwnerVO ownervo = ownerSvc.updateOpen(temp);
			
			request.setAttribute("ownervo", ownervo);
			RequestDispatcher Success = request.getRequestDispatcher("/register/NewPwd.jsp");
			Success.forward(request, response);
		}
		if("Member".equals(who)){
			String Account = request.getParameter("Account");
			if(Account == null || Account.trim().length() == 0){
				errorMsgs.put("Account","請輸入帳號!");
			}
			if(!(Account == null || Account.trim().length() == 0) && !IPS.MemberAccExists(Account)){
				errorMsgs.put("Account","帳號不存在!");
			}
			if(!errorMsgs.isEmpty()){
				RequestDispatcher failureView = request.getRequestDispatcher("/register/forgotPwd_Member.jsp");
				failureView.forward(request, response);
				return;//程式中斷
			}
			RandomPwd newpwd = new RandomPwd();
			String Password = newpwd.getRandomPassword();
			MemberService memberSvc = new MemberService();
			MemberVO newmembervo = memberSvc.getByMemAcc(Account);
			MemberVO temp = new MemberVO();
			temp.setMemberAcc(newmembervo.getMemberAcc());
			temp.setMemberPwd(Password);
			temp.setMemberLastName(newmembervo.getMemberLastName());
			temp.setMemberFirstName(newmembervo.getMemberFirstName());
			temp.setMemberPhone(newmembervo.getMemberPhone());
			temp.setMemberCel(newmembervo.getMemberCel());
			temp.setMemberEmail(newmembervo.getMemberEmail());
			temp.setMemberGender(newmembervo.isMemberGender());
			temp.setMemberGender(newmembervo.isMemberOpen());
			MemberVO membervo = memberSvc.updateOpen(temp);
			
			request.setAttribute("membervo", membervo);
			RequestDispatcher Success = request.getRequestDispatcher("/register/NewPwd.jsp");
			Success.forward(request, response);
		}
 	}
}

