package demo.tool.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import demo.chart.pojo.constant.ChartUrl;
import demo.tool.ToolViewConstant;
import demo.tool.pojo.constant.UploadUrlConstant;
import demo.tool.pojo.param.controllerParam.ExcelAnalysisByPkParam;
import demo.tool.pojo.result.UploadExcelResult;
import demo.tool.pojo.type.ChartType;
import demo.tool.service.ExcelAnalysisService;
import demo.tool.service.UploadService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = {UploadUrlConstant.uploadRoot})
public class ExcelAnalysisController extends ComplexToolController {
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private ExcelAnalysisService excleAnalysisService;
	
	@GetMapping(value = { UploadUrlConstant.uploadExcel })
	public ModelAndView uploadTestView(
			@RequestParam(value = "pk", defaultValue = "" ) String pk,
			@RequestParam(value = "chartType", defaultValue = "1") int chartType,
			@RequestParam(value = "columnToRow", defaultValue = "0") String columnToRow,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		ModelAndView view = new ModelAndView(ToolViewConstant.uploadExcel);
		
		view.addObject("pk", pk);
		view.addObject("chartType", chartType);
		view.addObject("columnToRow", columnToRow);
		view.addObject("uploadUrl", UploadUrlConstant.uploadExcel);
		view.addObject("chartViewUri", ChartUrl.root + ChartUrl.simpleChart);
		view.addObject("chartViewUrl", foundHostNameFromRequst(request) + ChartUrl.root + ChartUrl.simpleChart);
		List<ChartType> chartTypeList = new ArrayList<ChartType>();
		for(ChartType subChartType : ChartType.values()) {
			chartTypeList.add(subChartType);
		}
		view.addObject("chartTypeList", chartTypeList);
		return view;
	}
	
	@PostMapping(value = UploadUrlConstant.uploadExcel)
	public void uploadExcel(MultipartHttpServletRequest request, HttpServletResponse response) {
		Map<String, MultipartFile> fileMap = uploadService.getFiles(request);
		UploadExcelResult result = excleAnalysisService.uploadExcel(fileMap, foundHostNameFromRequst(request));
		outputJson(response, JSONObject.fromObject(result));
	}
	
	public ModelAndView excelAnalysisByPk(
			String pk,
			int chartType,
			boolean columnToRow
			) {
		ModelAndView view = null;
		ExcelAnalysisByPkParam param = new ExcelAnalysisByPkParam();
		param.setPk(pk);
		param.setChartType(chartType);
		param.setNeedColumnToRow(columnToRow);
		
		view = excleAnalysisService.excelAnalysisByPk(param);
		
		return view;
	}
}
