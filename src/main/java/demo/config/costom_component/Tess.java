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

	public static void main(String[] args) {
		Tess ob = new Tess();
		String fileSourceFormat = "gif";
//		String fileTargetFormat = "png";
//		t.batchRename(fileSourceFormat, fileTargetFormat, 43, 49);
		ob.batchOcr(fileSourceFormat, 10, 23);
	}
}
