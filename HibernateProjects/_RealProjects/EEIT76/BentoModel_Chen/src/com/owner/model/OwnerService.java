package com.owner.model;
import java.util.List;

public class OwnerService {
	private OwnerDAO_interface Ownerdao;
	private List<Object> owneracclist;
	public OwnerService(){
		Ownerdao = new OwnerDAO();
	}
	public OwnerVO getByOwnAcc(String OwnAcc){
		return Ownerdao.getByPrimaryKey(OwnAcc);
	}
	public OwnerVO addOwner(String OwnAcc,String OwnPwd,String OwnEmail,
			String OwnLastName,String OwnFirstName,boolean OwnGender ,boolean OwnOpen){
		OwnerVO ownervo = new OwnerVO();
		ownervo.setOwnAcc(OwnAcc);
		ownervo.setOwnPwd(OwnPwd);
		ownervo.setOwnEmail(OwnEmail);
		ownervo.setOwnLastName(OwnLastName);
		ownervo.setOwnFirstName(OwnFirstName);
		ownervo.setOwnGender(OwnGender);
		ownervo.setOwnGender(OwnOpen);
		Ownerdao.insert(ownervo);
		return ownervo;
	}
	public OwnerVO addOwner(OwnerVO ownerVO){  //用這個性別不會有問題
		Ownerdao.insert(ownerVO);
		return ownerVO;
	}
	public OwnerVO updateOwner(String OwnAcc,String OwnPwd,String OwnEmail,
			String OwnLastName,String OwnFirstName,boolean OwnGender){
		OwnerVO ownervo = new OwnerVO();
		ownervo.setOwnAcc(OwnAcc);
		ownervo.setOwnPwd(OwnPwd);
		ownervo.setOwnEmail(OwnEmail);
		ownervo.setOwnLastName(OwnLastName);
		ownervo.setOwnFirstName(OwnFirstName);
		ownervo.setOwnGender(OwnGender);
		Ownerdao.insert(ownervo);
		return Ownerdao.getByPrimaryKey(OwnAcc);
	}
	public OwnerVO updateOpen(OwnerVO ownerVO){
		OwnerVO ownerVonew = new OwnerVO();
		ownerVonew.setOwnAcc(ownerVO.getOwnAcc());
		ownerVonew.setOwnPwd(ownerVO.getOwnPwd());
		ownerVonew.setOwnEmail(ownerVO.getOwnEmail());
		ownerVonew.setOwnLastName(ownerVO.getOwnLastName());
		ownerVonew.setOwnFirstName(ownerVO.getOwnFirstName());
		ownerVonew.setOwnGender(ownerVO.isOwnGender());
		ownerVonew.setOwnOpen(true);
		Ownerdao.insert(ownerVonew);
		return ownerVonew ;
	}
	public void deleteOwner(String OwnAcc) {
		Ownerdao.delete(OwnAcc);
	}
	public List<OwnerVO> getAll() {
		return Ownerdao.getAll();
	}
	public List<Object> getAllOwnAcc() {
		return Ownerdao.getAllOwnAcc();
	}
//	public boolean OwnerAccExists(String OwnerAcc){
//		boolean exist = false;
//		owneracclist = Ownerdao.getAllOwnAcc();
//		for(Object owneracc : owneracclist){
//			if(owneracc.equals(OwnerAcc)){
//				exist = true;
//				break;
//			}
//		}
//		return exist;
//	}
}
