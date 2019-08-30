package demo.chart.service;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import demo.tool.pojo.type.ChartType;

public interface ChartService {

	ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey, boolean needColumnToRow);

}
