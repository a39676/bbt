package demo.selenium.pojo.constant;

public class RegexConstant {

	public static final String fileNameRegex = "(?:[^/][\\d\\w\\.]+)$(?<=\\.\\w{3,4})";
	public static final String fileNameSuffixRegex = "\\.\\w{3,4}($|\\?)";
}
