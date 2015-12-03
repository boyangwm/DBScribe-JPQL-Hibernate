package com.marciani.sample.report.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.marciani.sample.exception.ReportGeneratorException;

@Service("reportBuilder")
public class ReportBuilder {
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	public ByteArrayOutputStream generateReport(ReportType reportType, HashMap<String, Object> entities) throws ReportGeneratorException {
		ModelMap model = new ModelMap();
        for (Map.Entry<String, Object> entity : entities.entrySet()) {
        	model.put(entity.getKey(), entity.getValue());
        }
        String html = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, reportType.getTemplate(), "UTF-8", model);
        
        Document document = new Document();
        PdfWriter writer;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {
			throw new ReportGeneratorException();
		}
        document.open();
        InputStream htmlStream = new ByteArrayInputStream(html.getBytes());
        try {
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlStream);
		} catch (IOException e) {
			throw new ReportGeneratorException();
		} 
        document.close();
        return out;
	}

}
