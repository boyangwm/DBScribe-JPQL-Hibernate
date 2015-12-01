package com.marciani.sample.report.service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.exception.ReportGeneratorException;
import com.marciani.sample.report.model.ReportBuilder;
import com.marciani.sample.report.model.ReportType;
import com.marciani.sample.service.ReportService;

@Service("reportService")
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportBuilder reportBuilder;

	@Override
	public ByteArrayOutputStream generateSubscriptionReport(User user) throws ReportGeneratorException {
		HashMap<String, Object> entities = new HashMap<String, Object>();
		entities.put("user", user);
		return reportBuilder.generateReport(ReportType.SUBSCRIPTION, entities);
	}

}
