package demo.chart.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import demo.base.system.pojo.constant.BaseViewConstant;
import demo.baseCommon.pojo.type.ResultType;
import demo.baseCommon.service.CommonService;
import demo.chart.pojo.bo.ChartColorBO;
import demo.chart.pojo.bo.ChartDataSetBO;
import demo.chart.service.ChartService;
import demo.tool.pojo.type.ChartType;

@Service
public class ChartServiceImpl extends CommonService implements ChartService {

	/** 根据所需图片类型, 生成数据页面 */
	@Override
	public ModelAndView dataToViewHandler(Workbook workbook, ChartType ct, String privateKey, boolean needColumnToRow) {
//		TODO
		ModelAndView view = null;

		Sheet sheet = workbook.getSheetAt(0);

		ChartDataSetBO bo = sheetToChartDataSetBO_horizontal(sheet);

//		TODO
//		不再处理行列转换, 另写以列为方式的读取数据
//		if (needColumnToRow) {
//			bo = columnToRow(bo);
//		} 

		if (ct.equals(ChartType.barChart)) {
			view = dataToBarChartView(bo);
		} else if (ct.equals(ChartType.lineChart)) {
			view = dataToLineChartView(bo);
		} else if (ct.equals(ChartType.pieChart)) {
			view = dataToPieChartView(bo);
		} else if (ct.equals(ChartType.radarChart)) {
			view = dataToRadarChartView(bo);
		} else if (ct.equals(ChartType.polarAreaChart)) {
			view = dataToPolarAreaChartView(bo);
		} else if (ct.equals(ChartType.doughnutChart)) {
			view = dataToDoughnutChartView(bo);
		} else if (ct.equals(ChartType.horizontalBars)) {
			view = dataToHorizontalBarChartView(bo);
		} else {
			view = new ModelAndView(BaseViewConstant.viewError);
			view.addObject("exception", ResultType.errorParam.getName());
			return view;
		}

		return view;
	}

	private ChartDataSetBO sheetToChartDataSetBO_horizontal(Sheet sheet) {
		ChartDataSetBO bo = new ChartDataSetBO();

		String sheetName = sheet.getSheetName();
		bo.setChartName(sheetName);

		int firstRowIndex = sheet.getFirstRowNum();
		int lastRowIndex = sheet.getLastRowNum();

		Row firstRow = sheet.getRow(firstRowIndex);
		int columnNameFirstCellIndex = firstRow.getFirstCellNum();

		List<String> columnNameList = getColumnNameListFromRow(columnNameFirstCellIndex, firstRow);
		bo.setLineNameList(columnNameList);

		List<ChartColorBO> randomColorList = setRandomColor(columnNameList.size());
		bo.setRandomColorList(randomColorList);

		if (sheet.getLastRowNum() - firstRowIndex < 1) {
			return bo;
		}

		boolean isSingleDataSet = false;
		if (sheet.getLastRowNum() - firstRowIndex == 1) {
			isSingleDataSet = true;
		}

		Row secondRow = sheet.getRow(firstRowIndex + 1);
		Short tmpCellIndex = null;
		Integer minCellIndex = null;
		Row tmpRow = null;
		for (int tmpRowIndex = firstRowIndex; tmpRowIndex < lastRowIndex; tmpRowIndex++) {
			tmpRow = sheet.getRow(tmpRowIndex);
			if (tmpRow != null && tmpRow.getFirstCellNum() >= 0) {
				tmpCellIndex = tmpRow.getFirstCellNum();
				if (minCellIndex == null || minCellIndex > tmpCellIndex) {
					minCellIndex = tmpCellIndex.intValue();
				}
			}
		}

		if (isSingleDataSet) {
			List<BigDecimal> dataList = getBigDecimalListFromRow(minCellIndex, secondRow);
			bo.setDataList(dataList);
		} else {
			List<List<BigDecimal>> dataLists = getDataListFromSheet(firstRowIndex, minCellIndex + 1, sheet);
			bo.setDataLists(dataLists);
			if (minCellIndex < columnNameFirstCellIndex) {
				List<String> horizontalLabels = getHorizontalLabelsFromSheet(sheet);
				bo.setHorizontalLabels(horizontalLabels);
			}
		}

		return bo;
	}

	
	public HashMap<Integer, String> getColumnNameMapFromRow(Row row) {
//		TODO
//		should be private
		HashMap<Integer, String> columnNameMap = new HashMap<Integer, String>();
		int lastCellNum = row.getLastCellNum();

		for (int i = 0; i < lastCellNum; i++) {
			if (row.getCell(i) != null) {
				columnNameMap.put(i, row.getCell(i).getStringCellValue());
			}
		}
		return columnNameMap;
	}

	private List<String> getColumnNameListFromRow(int startIndex, Row row) {
		List<String> columnNameList = new ArrayList<String>();
		int lastCellNum = row.getLastCellNum();

		for (int i = startIndex; i < lastCellNum; i++) {
			if (row.getCell(i) != null) {
				columnNameList.add(row.getCell(i).getStringCellValue());
			} else {
				columnNameList.add("");
			}
		}
		return columnNameList;
	}

	private List<BigDecimal> getBigDecimalListFromRow(int startIndex, Row row) {
		List<BigDecimal> bigDecimalList = new ArrayList<BigDecimal>();
		if (row == null) {
			return bigDecimalList;
		}

		int lastCellNum = row.getLastCellNum();
		if (lastCellNum - startIndex < 1) {
			return bigDecimalList;
		}

		BigDecimal tmpNum = null;
		Cell tmpCell = null;

		for (int i = startIndex; i < lastCellNum; i++) {
			tmpCell = row.getCell(i);
			if (tmpCell != null) {
				try {
					tmpNum = new BigDecimal(tmpCell.getNumericCellValue());
					bigDecimalList.add(tmpNum);
				} catch (Exception e) {
					bigDecimalList.add(BigDecimal.ZERO);
				}
			} else {
				bigDecimalList.add(BigDecimal.ZERO);
			}
		}
		return bigDecimalList;
	}

	private List<List<BigDecimal>> getDataListFromSheet(int rowStartIndex, int columnStartIndex, Sheet sheet) {
		List<List<BigDecimal>> dataList = new ArrayList<List<BigDecimal>>();
		if (sheet == null || sheet.getLastRowNum() < 2) {
			return dataList;
		}

		int columnCount = sheet.getLastRowNum();
		if (columnCount < 1) {
			return dataList;
		}

		for (int rowIndex = rowStartIndex; rowIndex < columnCount; rowIndex++) {
			dataList.add(new ArrayList<BigDecimal>());
		}

		Row tmpRow = null;
		int tmpCellCount = 0;
		BigDecimal tmpNum = null;
		Cell tmpCell = null;

		for (int rowIndex = rowStartIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			tmpRow = sheet.getRow(rowIndex);
			tmpCellCount = tmpRow.getLastCellNum();
			for (int columnIndex = columnStartIndex; columnIndex < tmpCellCount; columnIndex++) {
				tmpCell = tmpRow.getCell(columnIndex);
				if (tmpCell != null) {
					try {
						tmpNum = new BigDecimal(tmpCell.getNumericCellValue());
						dataList.get(columnIndex - 1).add(tmpNum);
					} catch (Exception e) {
						dataList.get(columnIndex - 1).add(BigDecimal.ZERO);
					}
				} else {
					dataList.get(columnIndex - 1).add(BigDecimal.ZERO);
				}
			}
		}

		return dataList;
	}

	private List<ChartColorBO> setRandomColor(int colorListSize) {
		List<ChartColorBO> chartColorBOList = new ArrayList<ChartColorBO>();

		if (colorListSize < 1) {
			return chartColorBOList;
		}
		ChartColorBO tmpBO = null;
		String tmpHexStr = "";

		int randomNum = 0;
		for (int i = 0; i <= colorListSize * 3; i++) {
			randomNum = ThreadLocalRandom.current().nextInt(17, 256 + 1);
			if (i == 0) {
				tmpBO = new ChartColorBO();
				tmpHexStr = tmpHexStr + Integer.toHexString(randomNum);
				tmpBO.setRed(randomNum);
			} else if (i > 0 && i % 3 == 1) {
				tmpBO.setGreen(randomNum);
				tmpHexStr = tmpHexStr + Integer.toHexString(randomNum);
			} else if (i > 0 && i % 3 == 2) {
				tmpBO.setBlue(randomNum);
				tmpHexStr = tmpHexStr + Integer.toHexString(randomNum);
				tmpBO.setHexString(tmpHexStr);
				tmpHexStr = "";
				chartColorBOList.add(tmpBO);
			} else if (i > 0 && i % 3 == 0) {
				tmpBO = new ChartColorBO();
				tmpBO.setRed(randomNum);
				tmpHexStr = tmpHexStr + Integer.toHexString(randomNum);
			}

		}
		return chartColorBOList;
	}

	/**
	 * -取首行之下 每一行 首单元格
	 * 
	 * @param sheet
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<String> getHorizontalLabelsFromSheet(Sheet sheet) {
		List<String> labels = new ArrayList<String>();
		if (sheet == null || sheet.getLastRowNum() < 1) {
			return labels;
		}

		int firstRowIndwx = sheet.getFirstRowNum();
		int lastRowIndex = sheet.getLastRowNum();
		Row tmpRow = null;
		Cell tmpCell = null;
		Date tmpDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int tmpCellType = 0;
		int firstCellIndex = 0;

		for (int i = firstRowIndwx + 1; i < lastRowIndex + 1; i++) {
			tmpRow = sheet.getRow(i);
			firstCellIndex = tmpRow.getFirstCellNum();
			tmpCell = tmpRow.getCell(firstCellIndex);
			if (tmpCell != null) {
				tmpCellType = tmpCell.getCellType();
				if (tmpCellType == Cell.CELL_TYPE_NUMERIC) {
					if (DateUtil.isCellDateFormatted(tmpCell)) {
						tmpDate = new Date(tmpCell.getDateCellValue().getTime());
						labels.add(sdf.format(tmpDate));
					} else {
						labels.add(String.valueOf(tmpCell.getNumericCellValue()));
					}
				} else if (tmpCellType == Cell.CELL_TYPE_STRING) {
					labels.add(tmpCell.getStringCellValue());
				} else {
					labels.add("");
				}
			}
		}

		return labels;
	}

	private ModelAndView dataToLineChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/lineChart");

		view.addObject("lineNames", bo.getLineNameList());
		view.addObject("dataLists", bo.getDataLists());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("horizontalLabels", bo.getHorizontalLabels());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToRadarChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/radarChart");

		view.addObject("lineNames", bo.getLineNameList());
		view.addObject("dataLists", bo.getDataLists());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("horizontalLabels", bo.getHorizontalLabels());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToPieChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/pieChart");

		view.addObject("labels", bo.getLineNameList());
		view.addObject("dataList", bo.getDataList());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToBarChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/barChart");

		view.addObject("labels", bo.getLineNameList());
		view.addObject("dataList", bo.getDataList());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToPolarAreaChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/polarAreaChart");

		view.addObject("labels", bo.getLineNameList());
		view.addObject("dataList", bo.getDataList());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToDoughnutChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/doughnutChart");

		view.addObject("labels", bo.getLineNameList());
		view.addObject("dataList", bo.getDataList());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

	private ModelAndView dataToHorizontalBarChartView(ChartDataSetBO bo) {
		ModelAndView view = new ModelAndView("chartJSP/horizontalBarChart");

		view.addObject("labels", bo.getLineNameList());
		view.addObject("dataList", bo.getDataList());
		view.addObject("randomColorList", bo.getRandomColorList());
		view.addObject("chartTitle", bo.getChartName());

		return view;
	}

}
