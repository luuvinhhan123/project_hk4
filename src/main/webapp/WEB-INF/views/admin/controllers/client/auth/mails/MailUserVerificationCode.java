package com.aptech.eProject.controllers.client.auth.mails;

import com.aptech.eProject.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailUserVerificationCode {
	final String subject = "Send mail";

	final String templateMail = "Your confirmation code is: %s";

	@Autowired
	MailUtil mailUtil;

	/**
	 * Send mail notification
	 *
	 * @param mailTo
	 * @param code
	 */
	public void sendMail(String mailTo, String code) {
//		Context context = new Context();
//		context.setVariable("message", emailRequest.getBody());
//		mailUtil.sendEmailWithHtmlTemplate(mailTo, subject,  templateMail, context);
		mailUtil.sendEmail(mailTo, subject, mailTo);
	}
}
