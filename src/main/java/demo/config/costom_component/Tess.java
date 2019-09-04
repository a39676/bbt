package demo.config.costom_component;

import java.io.File;

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
	
}
