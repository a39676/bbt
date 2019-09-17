package demo.captcha.service;

import java.io.File;

public interface CaptchaService {

	boolean cleanImage(File sfile, String outputPath);

	boolean gray(File srcImageFile, String outputPath);

	String ocr(String imgPath, boolean numberAndLetterOnly);

}
