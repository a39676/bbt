package demo.config.costom_component;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Component
@Scope("singleton")
public class Tess {

	private static final String dataPath = "d:/soft/tessdataInUse";
	private static final ITesseract instance;
	
	static {
		instance = new Tesseract();
		instance.setDatapath(dataPath);
	}

	public String ocr(String imgPath) {
		File imageFile = new File(imgPath);
		String result = null;
		try {
			result = instance.doOCR(imageFile);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	public String ocr(String imgPath, boolean numberAndLetterOnly) {
		String result = ocr(imgPath);
		if (numberAndLetterOnly && StringUtils.isNotBlank(result)) {
			result = result.replaceAll("[^\\dA-Za-z]", "");
		}
		return result;
	}

	public void batchOcr(String fileFormat, int start, int end) {
		String filePath = null;
		for (Integer i = start; i <= end; i++) {
			filePath = buildFilePath(i.toString(), fileFormat);
			System.out.println(ocr(filePath, true));
		}
	}

	public String buildFilePath(String name, String fileFormat) {
		return String.format("D:\\auxiliary\\captcha/%s.%s", name, fileFormat);
	}

	public void batchRename(String oldSuffixName, String newSuffixName, int start, int end) {
		String filePath = null;
		for (Integer i = start; i <= end; i++) {
			filePath = buildFilePath(i.toString(), oldSuffixName);
			File oldF = new File(filePath);
			File newF = new File(filePath.replaceAll(oldSuffixName, newSuffixName));
			if (oldF.renameTo(newF)) {
				oldF.delete();
			}
		}
	}

//	public String filterPic(String fileName) {
//		// 图片去噪
//		Mat src = Imgcodecs.imread(sourceFolderPath + File.separator + fileName);
//		Mat dst = new Mat(src.width(), src.height(), CvType.CV_8UC1);
//
//		Imgproc.boxFilter(src, dst, src.depth(), new Size(3.2, 3.2));
//		Imgcodecs.imwrite(filterFolderPath + File.separator + fileName, dst);
//
//		// 图片阈值处理，二值化
//		Mat src1 = Imgcodecs.imread(filterFolderPath + File.separator + fileName, Imgcodecs.IMREAD_UNCHANGED);
//		Mat dst1 = new Mat(src1.width(), src1.height(), CvType.CV_8UC1);
//
//		Imgproc.threshold(src1, dst1, 165, 200, Imgproc.THRESH_TRUNC);
//		Imgcodecs.imwrite(processFolderPath + File.separator + fileName, dst1);
//
//		// 图片截取
//		Mat src2 = Imgcodecs.imread(processFolderPath + File.separator + fileName, Imgcodecs.IMREAD_UNCHANGED);
//		Rect roi = new Rect(4, 2, src2.cols() - 7, src2.rows() - 4); // 参数：x坐标，y坐标，截取的长度，截取的宽度
//		Mat dst2 = new Mat(src2, roi);
//
//		String resultFilePath = resultFolderPath + File.separator + fileName;
//		Imgcodecs.imwrite(resultFilePath, dst2);
//
//		return resultFilePath;
//	}
	
	public static void main(String[] args) {
		Tess ob = new Tess();
		String fileSourceFormat = "gif";
//		String fileTargetFormat = "png";
//		t.batchRename(fileSourceFormat, fileTargetFormat, 43, 49);
		ob.batchOcr(fileSourceFormat, 59, 60);
	}
}
