package net.liuxuan.spring.security;

import com.google.code.kaptcha.Constants;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

public class CaptchaAuthenticationDetails implements Serializable {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String answer;
    private String captcha;


    public CaptchaAuthenticationDetails(HttpServletRequest request) {

        this.answer = request.getParameter("j_captcha");
        this.captcha = (String) WebUtils.getSessionAttribute(request, Constants.KAPTCHA_SESSION_KEY);
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
