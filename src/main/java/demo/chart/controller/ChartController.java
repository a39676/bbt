package demo.chart.controller;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.controller.CommonController;
import demo.chart.pojo.constant.ChartUrl;
import demo.chart.service.ChartService;
import demo.tool.pojo.type.ChartType;

@Controller
@RequestMapping(value = ChartUrl.root)
public class ChartController extends CommonController {

	
	@Autowired
	private ChartService chartService;
	
	public ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey, boolean needColumnToRow) {
		return chartService.dataToViewHandler(workbook, ct, privateKey, needColumnToRow);
	}
	
	public ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey) {
		return chartService.dataToViewHandler(workbook, ct, privateKey, false);
	}
}
