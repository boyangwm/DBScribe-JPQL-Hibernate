package com.jspdev.biyesheji;
import java.util.*;
public class Page {
	public int curPage;//当前页
	public int maxPage;//最大页数
	public int maxRowCount;//最大行数
	public int rowsPerPage;//每页行数
	public java.util.Vector data;//本页中要显示的数据
	
	public Page(){}	
	public Page(Message message)throws Exception{
		this.rowsPerPage=10;
		this.maxRowCount=message.getMessageCount();
		this.data=message.getResult();
        this.countMaxPage();
	}
	public void countMaxPage(){
		if(this.maxRowCount%this.rowsPerPage==0){
			this.maxPage=this.maxRowCount/this.rowsPerPage;
		}
		else{
			this.maxPage=this.maxRowCount/this.rowsPerPage+1;
		}
	}
	public Vector getResult(){
		return this.data;
	}
}
