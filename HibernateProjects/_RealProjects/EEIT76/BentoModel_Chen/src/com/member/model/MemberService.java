package com.member.model;

import java.util.List;

import org.hibernate.Query;

import com.favorite.model.FavoriteDAO;
import com.favorite.model.FavoriteDAO_interface;
import com.favorite.model.FavoriteVO;
import com.owner.model.OwnerVO;
import com.useraddr.model.UserAddrDAO;
import com.useraddr.model.UserAddrDAO_Interface;
import com.useraddr.model.UserAddrVO;

public class MemberService {

	private List<Object> memberacclist;
	private MemberDAO_Interface Memberdao;
	private UserAddrDAO_Interface AddrDao;
	private FavoriteDAO_interface FavDao;

	public MemberService(){
		Memberdao = new MemberDAO();
		AddrDao = new UserAddrDAO();
		FavDao = new FavoriteDAO();
	}
	//�d�߷|��
		public MemberVO getByMemAcc(String MemAcc){
			return Memberdao.getByPrimaryKey(MemAcc);
		}
	//�s�W�|��
	public MemberVO addMember(String MemberAcc,String MemberPwd,String MemberLastName,String MemberFirstName,
			String MemberPhone,String MemberCel,String MemberEmail, boolean MemberGender ,boolean MemberOpen){
		
		MemberVO mvo = new MemberVO();
		mvo.setMemberAcc(MemberAcc);
		mvo.setMemberPwd(MemberPwd);
		mvo.setMemberLastName(MemberLastName);
		mvo.setMemberFirstName(MemberFirstName);
		mvo.setMemberPhone(MemberPhone);
		mvo.setMemberCel(MemberCel);
		mvo.setMemberEmail(MemberEmail);
		mvo.setMemberGender(MemberGender);
		mvo.setMemberGender(MemberOpen);
		Memberdao.insert(mvo);
		
		
		return mvo;
		
	}
	public MemberVO addMember(MemberVO memberVO){  //用這個性別不會有問題
		Memberdao.insert(memberVO);
		return memberVO;
	}
	//��s�|����
	public MemberVO updateMember(String MemberAcc,String MemberPwd,String MemberLastName,String MemberFirstName,
                                 String MemberPhone,String MemberCel,String MemberEmail,   boolean MemberGender){

		MemberVO mvo = new MemberVO();
		mvo.setMemberAcc(MemberAcc);
		mvo.setMemberPwd(MemberPwd);
		mvo.setMemberLastName(MemberLastName);
		mvo.setMemberFirstName(MemberFirstName);
		mvo.setMemberPhone(MemberPhone);
		mvo.setMemberCel(MemberCel);
		mvo.setMemberEmail(MemberEmail);
		Memberdao.insert(mvo);
		
		return Memberdao.getByPrimaryKey(MemberAcc);
	}
	public MemberVO updateOpen(MemberVO memberVO){     //更改開通欄位用
		MemberVO memberVOnew = new MemberVO();
		memberVOnew.setMemberAcc(memberVO.getMemberAcc());
		memberVOnew.setMemberPwd(memberVO.getMemberPwd());
		memberVOnew.setMemberEmail(memberVO.getMemberEmail());
		memberVOnew.setMemberLastName(memberVO.getMemberLastName());
		memberVOnew.setMemberFirstName(memberVO.getMemberFirstName());
		memberVOnew.setMemberGender(memberVO.isMemberGender());
		memberVOnew.setMemberPhone(memberVO.getMemberPhone());
		memberVOnew.setMemberCel(memberVO.getMemberCel());
		memberVOnew.setMemberOpen(true);
		Memberdao.insert(memberVOnew);
		return memberVOnew ;
	}
	
	//�R�|��
	public void deleteMember(String MemberAcc){
		
		Memberdao.delete(MemberAcc);
	}
	
	//�s�W�ק�`�Φa�}
	
	public UserAddrVO insertAddr(Integer UserAddrID,String City,String Area,String Addr,
            String MemberAcc,Double Latitude,Double Longitude){

			UserAddrVO userAddr = new UserAddrVO();

			userAddr.setUserAddrID(UserAddrID);
			userAddr.setCity(City);
			userAddr.setArea(Area);
			userAddr.setAddr(Addr);
			userAddr.setMemberAcc(MemberAcc);
			userAddr.setLatitude(Latitude);
			userAddr.setLongitude(Longitude);
			AddrDao.update(userAddr);

			return userAddr;
	}
	
	public UserAddrVO updateAddr(Integer UserAddrID,String City,String Area,String Addr,
			                     String MemberAcc,Double Latitude,Double Longitude){
		
		//�p�G�a�}�C���ťաA�h�R���ӱ`�Φa�}
		if(City.equals(null) && Area.equals(null) && Addr.equals(null)){
			AddrDao.delete(UserAddrID);
			return null;
		}
		
		UserAddrVO userAddr = new UserAddrVO();
		
		userAddr.setUserAddrID(UserAddrID);
		userAddr.setCity(City);
		userAddr.setArea(Area);
		userAddr.setAddr(Addr);
		userAddr.setMemberAcc(MemberAcc);
		userAddr.setLatitude(Latitude);
		userAddr.setLongitude(Longitude);
		AddrDao.update(userAddr);
		
		return AddrDao.getByPrimaryKey(UserAddrID);
	}
	
	//�s�W�ߦn���a
	public FavoriteVO addNewFav(String MemberAcc,Integer RestID){
		
		FavoriteVO favVO = new FavoriteVO();
		favVO.setMemberAcc(MemberAcc);
		favVO.setRestID(RestID);
		FavDao.insert(favVO);
		return favVO;
	}
	//�R���ߦn���a
	public void deleteFav(String MemberAcc,Integer RestID){
		
		FavoriteVO favVO = new FavoriteVO();
		favVO.setMemberAcc(MemberAcc);
		favVO.setRestID(RestID);
	}
	public List<Object> getAllMemberAcc() {
		return Memberdao.getAllMemberAcc();
	}	
//	public boolean MemberAccExists(String MemberAcc){
//		boolean exist = false;
//		memberacclist = Memberdao.getAllMemberAcc();
//		for(Object memberacc : memberacclist){
//			if(memberacc.equals(MemberAcc)){
//				exist = true;
//				break;
//			}
//		}
//		return exist;
//	}
}
