package demo.captcha.service.impl;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.captcha.service.CaptchaService;

@Service
public class CaptchaServiceImpl extends CommonService implements CaptchaService {

	/**
	 * 
	 * @param sfile   需要去噪的图像
	 * @param outputFolerPath 去噪后的图像保存地址
	 * @return 
	 * @throws IOException
	 */
	@Override
	public boolean cleanImage(File sfile, String outputFolerPath) {
		File destF = new File(outputFolerPath);
		if (!destF.exists()) {
			destF.mkdirs();
		}

		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(sfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		int h = bufferedImage.getHeight();
		int w = bufferedImage.getWidth();

		// 灰度化
		int[][] gray = new int[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int argb = bufferedImage.getRGB(x, y);
				// 图像加亮（调整亮度识别率非常高）
				int r = (int) (((argb >> 16) & 0xFF) * 1.1 + 30);
				int g = (int) (((argb >> 8) & 0xFF) * 1.1 + 30);
				int b = (int) (((argb >> 0) & 0xFF) * 1.1 + 30);
				if (r >= 255) {
					r = 255;
				}
				if (g >= 255) {
					g = 255;
				}
				if (b >= 255) {
					b = 255;
				}
				gray[x][y] = (int) Math.pow(
						(Math.pow(r, 2.2) * 0.2973 + Math.pow(g, 2.2) * 0.6274 + Math.pow(b, 2.2) * 0.0753), 1 / 2.2);
			}
		}

		// 二值化
		int threshold = ostu(gray, w, h);
		BufferedImage binaryBufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				if (gray[x][y] > threshold) {
					gray[x][y] |= 0x00FFFF;
				} else {
					gray[x][y] &= 0xFF0000;
				}
				binaryBufferedImage.setRGB(x, y, gray[x][y]);
			}
		}

		// 矩阵打印
//		for (int y = 0; y < h; y++) {
//			for (int x = 0; x < w; x++) {
//				if (isBlack(binaryBufferedImage.getRGB(x, y))) {
//					System.out.print("*");
//				} else {
//					System.out.print(" ");
//				}
//			}
//			System.out.println();
//		}
		
		String suffix = getSuffixName(sfile.getAbsolutePath());
		String outputPath = outputFolerPath + File.separator + sfile.getName();
		System.out.println(outputPath);
		try {
			boolean flag = ImageIO.write(binaryBufferedImage, suffix, new File(outputPath));
			System.out.println(flag);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

//	private boolean isBlack(int colorInt) {
//		Color color = new Color(colorInt);
//		if (color.getRed() + color.getGreen() + color.getBlue() <= 300) {
//			return true;
//		}
//		return false;
//	}
//
//	private boolean isWhite(int colorInt) {
//		Color color = new Color(colorInt);
//		if (color.getRed() + color.getGreen() + color.getBlue() > 300) {
//			return true;
//		}
//		return false;
//	}
//
//	private int isBlackOrWhite(int colorInt) {
//		if (getColorBright(colorInt) < 30 || getColorBright(colorInt) > 730) {
//			return 1;
//		}
//		return 0;
//	}
//
//	private int getColorBright(int colorInt) {
//		Color color = new Color(colorInt);
//		return color.getRed() + color.getGreen() + color.getBlue();
//	}

	private int ostu(int[][] gray, int w, int h) {
		int[] histData = new int[w * h];
		// Calculate histogram
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int red = 0xFF & gray[x][y];
				histData[red]++;
			}
		}

		// Total number of pixels
		int total = w * h;

		float sum = 0;
		for (int t = 0; t < 256; t++)
			sum += t * histData[t];

		float sumB = 0;
		int wB = 0;
		int wF = 0;

		float varMax = 0;
		int threshold = 0;

		for (int t = 0; t < 256; t++) {
			wB += histData[t]; // Weight Background
			if (wB == 0)
				continue;

			wF = total - wB; // Weight Foreground
			if (wF == 0)
				break;

			sumB += (float) (t * histData[t]);

			float mB = sumB / wB; // Mean Background
			float mF = (sum - sumB) / wF; // Mean Foreground

			// Calculate Between Class Variance
			float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

			// Check if new maximum found
			if (varBetween > varMax) {
				varMax = varBetween;
				threshold = t;
			}
		}

		return threshold;
	}

	// 图片灰度，黑白
	@Override
	public boolean gray(File srcImageFile, String outputPath) {
		try {
			BufferedImage src = ImageIO.read(srcImageFile);
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			String suffix = getSuffixName(srcImageFile.getAbsolutePath());
			ImageIO.write(src, suffix, new File(outputPath));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		/*
		 * File testDataDir = new File("testdata"); final String destDir =
		 * testDataDir.getAbsolutePath()+"/tmp"; for (File file :
		 * testDataDir.listFiles()){ cleanImage(file, destDir); }
		 */
		CaptchaServiceImpl t = new CaptchaServiceImpl();
		String sourceFilePath = "D:\\auxiliary\\captchas";
//		File testDataDir = new File(sourceFilePath);
//		String destDir = "D:\\auxiliary\\captcha";
//		t.cleanImage(testDataDir, destDir);
		
		File testDataDir = new File(sourceFilePath + File.separator + "45.png");
		String destDir ="D:\\auxiliary\\tmp";
		t.cleanImage(testDataDir, destDir);
	}
}
