package demo.tool.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;
import numericHandel.NumericUtilCustom;

public class ExcelAnalysisByPkParam implements CommonControllerParam {

	private Integer chartType;

	private String pk;

	/**
	 * 需要行列转换
	 */
	private boolean needColumnToRow = false;

	@Override
	public ExcelAnalysisByPkParam fromJson(JSONObject j) {
		ExcelAnalysisByPkParam p = new ExcelAnalysisByPkParam();

		if (j.containsKey("chartType") && NumericUtilCustom.matchInteger(j.getString("chartType"))) {
			p.setChartType(j.getInt("chartType"));
		}
		if (j.containsKey("pk")) {
			p.setPk(j.getString("pk"));
		}
		if (j.containsKey("needColumnToRow")
				&& (j.getString("needColumnToRow").equals("1") || j.getString("needColumnToRow").equals("true"))) {
			p.setNeedColumnToRow(true);
		}
		return p;
	}

	public Integer getChartType() {
		return chartType;
	}

	public void setChartType(Integer chartType) {
		this.chartType = chartType;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public boolean getNeedColumnToRow() {
		return needColumnToRow;
	}

	public void setNeedColumnToRow(boolean needColumnToRow) {
		this.needColumnToRow = needColumnToRow;
	}

	@Override
	public String toString() {
		return "ExcelAnalysisByPkParam [chartType=" + chartType + ", pk=" + pk + ", needColumnToRow=" + needColumnToRow
				+ "]";
	}

}
