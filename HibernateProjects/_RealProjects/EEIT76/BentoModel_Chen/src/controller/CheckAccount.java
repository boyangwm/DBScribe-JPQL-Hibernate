package controller;

import java.util.List;

import com.member.model.MemberService;
import com.owner.model.OwnerService;

public class CheckAccount {
	List<Object> owneracclist ;
	List<Object> memberacclist ;
	MemberService membersvc = new MemberService();
	OwnerService ownersvc = new OwnerService();
	public Boolean CheckAccount(String Account){
		boolean exist = false;
		owneracclist = ownersvc.getAllOwnAcc();
		memberacclist = membersvc.getAllMemberAcc();
		for(Object owneracc : owneracclist){
			if(owneracc.equals(Account)){
				exist = true;
				break;
			}
		}
		if(!exist){
			for(Object memberacc : memberacclist){
				if(memberacc.equals(Account)){
					exist = true;
					break;
				}
			}
		}
		return exist;
	}
}
