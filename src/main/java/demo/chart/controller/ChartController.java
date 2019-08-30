package demo.chart.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.chart.pojo.constant.ChartUrl;
import demo.chart.service.ChartService;
import demo.tool.controller.ExcelAnalysisController;
import demo.tool.pojo.type.ChartType;

@Controller
@RequestMapping(value = ChartUrl.root)
public class ChartController extends CommonController {

	@Autowired
	private ExcelAnalysisController excelAnalysisController;
	
	@Autowired
	private ChartService chartService;
	

	@GetMapping(value = ChartUrl.simpleChart)
	public ModelAndView simpleChart(
			@RequestParam(value = "pk", defaultValue = "" ) String pk,
			@RequestParam(value = "chartType", defaultValue = "1") int chartType,
			@RequestParam(value = "columnToRow", defaultValue = "0") String columnToRow,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		ModelAndView view = null;
		boolean flag = false;
		if("1".equals(columnToRow) || "true".equals(columnToRow)) {
			flag = true;
		}
		
		view = excelAnalysisController.excelAnalysisByPk(pk, chartType, flag);
		
		return view;
	}
	
	public ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey, boolean needColumnToRow) {
		return chartService.dataToViewHandler(workbook, ct, privateKey, needColumnToRow);
	}
	
	public ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey) {
		return chartService.dataToViewHandler(workbook, ct, privateKey, false);
	}
}
