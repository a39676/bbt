package demo.chart.pojo.bo;

import java.math.BigDecimal;
import java.util.List;

public class ChartDataSetBO {

	private String chartName;
	private List<String> lineNameList;
	private List<ChartColorBO> randomColorList;
	private List<List<BigDecimal>> dataLists;
	private List<BigDecimal> dataList;
	private List<String> horizontalLabels;

	public String getChartName() {
		return chartName;
	}

	public void setChartName(String chartName) {
		this.chartName = chartName;
	}

	public List<String> getLineNameList() {
		return lineNameList;
	}

	public void setLineNameList(List<String> lineNameList) {
		this.lineNameList = lineNameList;
	}

	public List<ChartColorBO> getRandomColorList() {
		return randomColorList;
	}

	public void setRandomColorList(List<ChartColorBO> randomColorList) {
		this.randomColorList = randomColorList;
	}

	public List<List<BigDecimal>> getDataLists() {
		return dataLists;
	}

	public void setDataLists(List<List<BigDecimal>> dataLists) {
		this.dataLists = dataLists;
	}

	public List<BigDecimal> getDataList() {
		return dataList;
	}

	public void setDataList(List<BigDecimal> dataList) {
		this.dataList = dataList;
	}

	public List<String> getHorizontalLabels() {
		return horizontalLabels;
	}

	public void setHorizontalLabels(List<String> horizontalLabels) {
		this.horizontalLabels = horizontalLabels;
	}

	@Override
	public String toString() {
		return "ChartDataSetBO [chartName=" + chartName + ", lineNameList=" + lineNameList + ", randomColorList="
				+ randomColorList + ", dataLists=" + dataLists + ", dataList=" + dataList + ", horizontalLabels="
				+ horizontalLabels + "]";
	}

}
