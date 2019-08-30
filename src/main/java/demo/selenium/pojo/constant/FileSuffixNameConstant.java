package demo.selenium.pojo.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileSuffixNameConstant {

	public static final String jpg = "jpg";
	public static final String jpeg = "jpeg";
	public static final String png = "png";
	public static final String bmp = "bmp";

	public static final String txt = "txt";
	public static final String doc = "doc";
	public static final String docx = "docx";
	public static final String ppt = "ppt";
	public static final String xlsx = "xlsx";
	public static final String xls = "xls";
	public static final String csv = "csv";
	public static final String pdf = "pdf";

	public static final List<String> imageSuffix = new ArrayList<String>();
	public static final List<String> documentSuffix = new ArrayList<String>();

	static {
		imageSuffix.addAll(Arrays.asList(jpeg, jpeg, bmp));
		documentSuffix.addAll(Arrays.asList(txt, doc, docx, ppt, xlsx, xls, csv, pdf));
	}
	
	public static void main(String[] args) {
		System.out.println(documentSuffix);
	}
}
