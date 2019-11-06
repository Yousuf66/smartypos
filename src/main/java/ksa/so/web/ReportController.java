package ksa.so.web;

import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ksa.so.service.ProductService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

@Controller
@RequestMapping("report")
public class ReportController {

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap) {
		return "product/index";
	}

	@RequestMapping(value = "report", method = RequestMethod.GET)
	public void report(HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productService.report());
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/report.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("abc.pdf"));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setMetadataAuthor("Bisma"); // why not set some config as we like
		exporter.setConfiguration(configuration);
		exporter.exportReport();

	}

	@RequestMapping(value = "reportTopSoldItemByCategory", method = RequestMethod.POST, consumes = "text/plain", produces = {
			"application/json; charset=UTF-8" })
	public void reportTopSoldItemByCategory(@RequestBody String param, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
				productService.topSoldItemByCategory(param));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/topsolditembycategory.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("abc.pdf"));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setMetadataAuthor("Bisma"); // why not set some config as we like
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}

	@RequestMapping(value = "solditems", method = RequestMethod.POST, consumes = "text/plain", produces = {
			"application/json; charset=UTF-8" })
	public void solditems(@RequestBody String param, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productService.solditems(param));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/solditems.jrxml");

		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("abc.pdf"));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setMetadataAuthor("Ariba"); // why not set some config as we like
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}

	@RequestMapping(value = "stackedreporttime", method = RequestMethod.POST, consumes = "text/plain", produces = {
			"application/json; charset=UTF-8" })
	public void reportstacked(@RequestBody String param, HttpServletResponse response) throws Exception {
		response.setContentType("text/html");
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productService.reportStacked(param));
		InputStream inputStream = this.getClass().getResourceAsStream("/reports/stackedGraph.jrxml");
		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("abc.pdf"));
		SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
		configuration.setMetadataAuthor("Bisma"); // why not set some config as we like
		exporter.setConfiguration(configuration);
		exporter.exportReport();
	}
}