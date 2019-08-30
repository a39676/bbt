package demo.tool.pojo.type;

public enum ChartType {
	/** 柱图 */
	barChart("柱图", 1),
	/** 线图 */
	lineChart("线图", 2),
	/** 饼图 */
	pieChart("饼图", 3),
	/** 雷达图 */
	radarChart("雷达图", 4),
	/** 极地图 */
	polarAreaChart("极地图", 5),
	/** 圆环图 */
	doughnutChart("圆环图", 6),
	/** 水平柱 */
	horizontalBars("水平柱", 7),
//	/** 分组图 */
//	groupedBars("分组图", 8),
//	/** 组合图 */
//	mixedCharts("组合图", 9),
//	/** 泡泡图 */
//	bubbleChart("泡泡图", 10),
	;

	private String name;
	private Integer code;

	private ChartType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
	
	public static ChartType getType(String typeName) {
		for(ChartType t : ChartType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ChartType getType(Integer typeCode) {
		for(ChartType t : ChartType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
