package com.dish.model;
import java.sql.Date;
import java.util.List;

//�S�� (SpecialPrice , StartDate , EndDate) 
public class DishService {
	private DishDAO_interface Dishdao;
	private List<DishVO> dishVOlist;
	public DishService(){
		Dishdao = new DishHibernateDAO();
	}
	//�s�W���    
	public DishVO addDish(String DishName,Integer Price,
			Integer SpecialPrice,Date StartDate,Date EndDate,Integer RestID){
		
		DishVO dishvo = new DishVO();
		//dishvo.setDishID(DishID);
		dishvo.setDishName(DishName);
		dishvo.setPrice(Price);
		dishvo.setSpecialPrice(SpecialPrice);	
		dishvo.setStartDate(StartDate);
		dishvo.setEndDate(EndDate);
		dishvo.setRestID(RestID);
		Dishdao.insert(dishvo);
		return dishvo;
	}
	//�ק���
	public DishVO updateDish(Integer DishID,String DishName,Integer Price,
			Integer SpecialPrice,Date StartDate,Date EndDate){
		
		DishVO dishvo = Dishdao.getByPrimaryKey(DishID);
		dishvo.setDishID(DishID);
		dishvo.setDishName(DishName);
		dishvo.setPrice(Price);
		dishvo.setSpecialPrice(SpecialPrice);
		dishvo.setStartDate(StartDate);
		dishvo.setEndDate(EndDate);
		Dishdao.insert(dishvo);
		return Dishdao.getByPrimaryKey(DishID);
	}
	
	public DishVO getByPrimaryKey(Integer dishID) {
		return Dishdao.getByPrimaryKey(dishID);
	}
	//�R�����
	public void deleteDish(Integer DishID) {
		Dishdao.delete(DishID);
	}
	public List<DishVO> getAll() {
		return Dishdao.getAll();
	}
	public List<DishVO> getAllByRestID(Integer RestID) {
		return Dishdao.getAllByRestID(RestID);
	}
	public boolean dishNameExists(String dishName , Integer RestID){
		boolean exist = false;
		DishService dishSvc = new DishService();
		dishVOlist = dishSvc.getAllByRestID(RestID);
		for(DishVO dishVO : dishVOlist){
			if(dishVO.getDishName().equals(dishName)){
				exist = true;
				break;
			}
		}
		return exist;
	}
}
