package com.broctagon.webgateway.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.broctagon.webgateway.util.VerifyCodeUtil;

@RestController
public class HttpController {

	@GetMapping("/register/getVerificationCode")
	public void getVerificationCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedImage image = VerifyCodeUtil.getImage();
		request.getSession().setAttribute("verificationCode", VerifyCodeUtil.getText());
		VerifyCodeUtil.output(image, response.getOutputStream());
	}
	
	@GetMapping("/register/verifyCode")
	public boolean verifyCode(HttpServletRequest request, HttpServletResponse response, @RequestParam("vcode") String code) throws IOException {
		boolean flag = false;
		String vCode = String.valueOf(request.getSession().getAttribute("verificationCode"));
		if(vCode.toUpperCase().equals(code.toUpperCase())) {
			flag = true;
		}
		return flag;
	}
	
	@PostMapping("/upload/kyc")
	@ResponseBody
	public String singleFileUpload(HttpServletRequest requset, @RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
		if (file.isEmpty()) {
			return "Empty File";
		}
		try {
			String path = requset.getSession().getServletContext().getRealPath("/") + userId + "_" + file.getOriginalFilename();
			File dest = new File(path);
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			file.transferTo(dest);
			return "/" + userId + "_" + file.getOriginalFilename();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error";
	}
	
}
