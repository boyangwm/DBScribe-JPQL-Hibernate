package login.model;

import com.owner.model.OwnerDAO;
import com.owner.model.OwnerVO;



public class OwnerLoginService {
	
	public OwnerVO login(String userID,String password){
		System.out.println("123");
		OwnerVO mvo = new OwnerVO();
		OwnerDAO dao = new OwnerDAO();
		OwnerVO temp = dao.getByPrimaryKey(userID);
		
		if(temp!=null){
			if(temp.getOwnPwd().equals(password)){
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
		
		OwnerLoginService ls = new OwnerLoginService();
		OwnerVO mvo  = ls.login("money123","money123");
		//System.out.println(mvo.getMemberFirstName());
		System.out.println(mvo.getOwnAcc());
		
	}

}
