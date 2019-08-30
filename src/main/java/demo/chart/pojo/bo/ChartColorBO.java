package demo.chart.pojo.bo;

public class ChartColorBO {

	private String hexString;
	private Integer red;
	private Integer green;
	private Integer blue;

	public String getHexString() {
		return hexString;
	}

	public void setHexString(String hexString) {
		this.hexString = hexString;
	}

	public Integer getRed() {
		return red;
	}

	public void setRed(Integer red) {
		this.red = red;
	}

	public Integer getGreen() {
		return green;
	}

	public void setGreen(Integer green) {
		this.green = green;
	}

	public Integer getBlue() {
		return blue;
	}

	public void setBlue(Integer blue) {
		this.blue = blue;
	}

	@Override
	public String toString() {
		return "ChartColorBO [hexString=" + hexString + ", red=" + red + ", green=" + green + ", blue=" + blue + "]";
	}

}
