package com.marciani.sample.service;

import java.io.ByteArrayOutputStream;

import com.marciani.sample.entity.user.model.User;
import com.marciani.sample.exception.ReportGeneratorException;

public interface ReportService {
	
	public ByteArrayOutputStream generateSubscriptionReport(User user) throws ReportGeneratorException;
	
}
