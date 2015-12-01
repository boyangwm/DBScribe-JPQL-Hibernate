package com.marciani.sample.report.model;

public enum ReportType {
	
	SUBSCRIPTION ("Subscription", "report_user-registration.vm"),
	ORDER ("Order", "report_order"),
	INVOICE ("Invoice", "report_invoice");
	
	private final String name;
	private final String template;
	
	ReportType(String name, String template) {
		this.name = name;
		this.template = template;
	}
	
	public String getName() {
		return name;
	}

	public String getTemplate() {
		return template;
	}

}
