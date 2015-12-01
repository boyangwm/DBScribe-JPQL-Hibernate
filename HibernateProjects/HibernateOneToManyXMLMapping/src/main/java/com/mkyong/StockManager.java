package com.mkyong;

import java.util.Date;

import org.hibernate.Session;

import com.mkyong.stock.Stock;
import com.mkyong.stock.StockDailyRecord;
import com.mkyong.util.HibernateUtil;

public class StockManager {
	private static SessionFactory factory; 
	
	public static void main(String[] args) {
		System.out.println("Hibernate one to many (XML Mapping) example");
		try{
			factory = new Configuration().configure().buildSessionFactory();
		}catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex); 
		}
		StockManager SM = new StockManager();
		
		Integer stockId1 = SM.addStock("7052", "PADINI");
		Integer stockId2 = SM.addStock("6003", "SUNSHE");
        
        SM.addStockDailyRecord(stockId1, "1.2", "1.1", "10.0", 3000000L, new Date());
		
		SM.addStockAndDailyRecord();
        
		nonDatabaseMethod();
		
		System.out.println("Done");
	}
	
	public Integer addStockAndDailyRecord()
	{
		Integer newStockId = addStock("1234", "ABCDEF");
		
		addStockDailyRecord(newStockId, "1.3", "1.2", "10.0", 20000000L, new Date());
		addStockDailyRecord(newStockId, "1.2", "1.3", "20.0", 10000000L, new Date());
		addStockDailyRecord(newStcokId, "1.1", "1.4", "10.0", 30000000L, new Date());
		
		return newStcokId;
	}
	
	public Integer addStockDailyRecord(Integer stockId, String priceOpenStr, String priceCloseStr, String priceChangeStr, Long volume, Date date) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer recordId = null;
		try{
			tx = session.beginTransaction();
			StockDailyRecord stockDailyRecords = new StockDailyRecord();
			stockDailyRecords.setPriceOpen(new Float(priceOpenStr));
			stockDailyRecords.setPriceClose(new Float(priceCloseStr));
			stockDailyRecords.setPriceChange(new Float(priceChangeStr));
			stockDailyRecords.setVolume(3000000L);
			stockDailyRecords.setDate(new Date());
			Stock stock = (Stock)session.get(Stock.class, stockId); 
			stockDailyRecords.setStock(stock);        
			stock.getStockDailyRecords().add(stockDailyRecords);
			session.save(stockDailyRecords);
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return stockId;
	}
	
	public Integer addStock(String stockCode, String stockName){
		Session session = factory.openSession();
		Transaction tx = null;
		Integer stockId = null;
		try{
			tx = session.beginTransaction();
			Stock stock = new Stock();
			stock.setStockCode(stockCode);
			stock.setStockName(stockName);
			stockId = (Integer) session.save(stock); 
			tx.commit();
		}catch (HibernateException e) {
			if (tx!=null) tx.rollback();
			e.printStackTrace(); 
		}finally {
			session.close(); 
		}
		return stockId;
	}
	
	public int nonDatabaseMethod(){
		int sum = 0;
		for(int i = 0; i < 10; i++) {
			sum += i;
		}
		return sum;
	}
}
