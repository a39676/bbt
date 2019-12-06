package demo.autoTestBase.captcha.service;

import java.io.File;

import demo.autoTestBase.captcha.pojo.dto.CleanImageResult;

public interface CaptchaService {

	CleanImageResult cleanImage(File sfile, String outputPath);

	boolean gray(File srcImageFile, String outputPath);

	String ocr(String imgPath, boolean numberAndLetterOnly);

	String getCaptchaSaveFolder();

}
