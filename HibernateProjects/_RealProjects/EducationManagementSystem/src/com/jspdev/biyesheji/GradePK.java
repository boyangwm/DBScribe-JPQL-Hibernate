package com.jspdev.biyesheji;

import com.jspdev.biyesheji.base.BaseGradePK;

public class GradePK extends BaseGradePK {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public GradePK () {}
	
	public GradePK (
		com.jspdev.biyesheji.Student snumber,
		com.jspdev.biyesheji.Course cnumber) {

		super (
			snumber,
			cnumber);
	}
/*[CONSTRUCTOR MARKER END]*/


}