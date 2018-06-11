package com.broctagon.webgateway.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class QrCodeUtil {

	private static final int width = 500;
	
	private static final int height = 500;
	
	private static final int BLACK = 0xFF000000;
	
	private static final int WHITE = 0xFFFFFFFF;
	
	private static final String PIC_TYPE = "jpg";
	
	public static void generateQrCode(String userId, String contents, String path) throws WriterException, IOException {
		File file = new File(path);
		if(!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		HashMap<EncodeHintType, String> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
		BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
			}
		}
		ImageIO.write(image, PIC_TYPE, file);
	}

}
