package com.broctagon.webgateway.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCodeUtil {

	private static int weight = 100;
	private static int height = 50;
	private static int lineNum = 10;
	private static String code;
	private static Random random = new Random();
	private static String[] fontNames= {"宋体","华文楷体", "黑体", "微软雅黑", "楷体_GB2312"};
	private static String codes = "23456789QWERTYUPASDFGHJKZXCVBNM";
	
	private static Color randomColor() {
		int r = random.nextInt(150);
		int g = random.nextInt(150);
		int b = random.nextInt(150);
		return new Color(r, g, b);
	}
	
	private static Font randomFont() {
		String fontName = fontNames[random.nextInt(fontNames.length)];
		int style = random.nextInt(4);
		int size = random.nextInt(5) + 24;
		return new Font(fontName, style, size);
	}
	
	private static char randomChar() {
		int index = random.nextInt(codes.length());
		return codes.charAt(index);
	}
	
	private static void drawLine(BufferedImage image) {
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		for(int i = 0; i < lineNum; i++) {
			int x1 = random.nextInt(weight);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(weight);
			int y2 = random.nextInt(height);
			graphics.setColor(randomColor());
			graphics.drawLine(x1, y1, x2, y2);
		}
	}
	
	private static BufferedImage createImage() {
		BufferedImage image = new BufferedImage(weight, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(Color.LIGHT_GRAY);
		graphics.fillRect(0, 0, weight, height);
		return image;
	}
	
	public static BufferedImage getImage() {
		BufferedImage image = createImage();
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 4; i++) {
			String code = String.valueOf(randomChar());
			sb.append(code);
			float x = i * 1.0F * weight /4;
			graphics.setFont(randomFont());
			graphics.setColor(randomColor());
			graphics.drawString(code, x, height - 5);
		}
		code = sb.toString();
		drawLine(image);
		return image;
	}
	
	public static String getText() {
		return code;
	}
	
	public static void output(BufferedImage image, OutputStream out) throws IOException {
		ImageIO.write(image, "JPEG", out);
	}
	
}
