package com.randompwd.model;

public class RandomPwdSercvice {
	private RandomPwd_interfce Randompwddao;
	public RandomPwdSercvice(){
		Randompwddao = new RandomPwdHibernateDAO();
	}
	public RandomPwdVO addRandomPwdVO(String Acc,String RandomPwd){
		RandomPwdVO randompwdvo = new RandomPwdVO();
		randompwdvo.setAcc(Acc);
		randompwdvo.setRandomPwd(RandomPwd);
		Randompwddao.insert(randompwdvo);
		return randompwdvo;
	}
	public RandomPwdVO SelectOne(String randompwd) {
		return Randompwddao.SelectOne(randompwd);
	}
}
