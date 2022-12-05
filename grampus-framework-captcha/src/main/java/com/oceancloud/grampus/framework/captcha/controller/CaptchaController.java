package com.oceancloud.grampus.framework.captcha.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.oceancloud.grampus.framework.captcha.utils.BrowserInfoUtil;
import com.oceancloud.grampus.framework.core.utils.WebUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

	private final CaptchaService captchaService;

	@PostMapping("/get")
	public ResponseModel get(@RequestBody CaptchaVO data) {
		HttpServletRequest request = WebUtil.getRequest();
		data.setBrowserInfo(BrowserInfoUtil.getRemoteId(request));
		return captchaService.get(data);
	}

	@PostMapping("/check")
	public ResponseModel check(@RequestBody CaptchaVO data) {
		HttpServletRequest request = WebUtil.getRequest();
		data.setBrowserInfo(BrowserInfoUtil.getRemoteId(request));
		return captchaService.check(data);
	}

//	//@PostMapping("/verify")
//	public ResponseModel verify(@RequestBody CaptchaVO data) {
//		return captchaService.verification(data);
//	}

}
