package login.model;

import com.member.model.MemberDAO;
import com.member.model.MemberVO;

public class LoginService {
	
	public MemberVO login(String userID,String password){
		MemberVO mvo = new MemberVO();
		MemberDAO dao = new MemberDAO();
		MemberVO temp = dao.getByPrimaryKey(userID);
		
		if(temp!=null){
			if(temp.getMemberPwd().equals(password)){
				System.out.println("登入成功");
				return temp;
			}
			else{
//				�K�X���~
				System.out.println("密碼錯誤");
				return null;
			}
		}
		else{
	//		�b�����~
			System.out.println("查無帳號");
			return null;
		}
	}

	public static void main(String args[]){
		
		LoginService ls = new LoginService();
		MemberVO mvo  = ls.login("Bad5566","Bad5566");
		//System.out.println(mvo.getMemberFirstName());
		System.out.println(mvo.getMemberAcc());
		
	}

}
