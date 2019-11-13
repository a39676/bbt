package demo.captcha.service;

import java.io.File;

import demo.captcha.pojo.dto.CleanImageResult;

public interface CaptchaService {

	CleanImageResult cleanImage(File sfile, String outputPath);

	boolean gray(File srcImageFile, String outputPath);

	String ocr(String imgPath, boolean numberAndLetterOnly);

}
