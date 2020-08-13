package run.ut.app.controller;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import run.ut.app.security.util.JwtOperator;
import run.ut.app.utils.ServletUtils;
import run.ut.app.utils.SpringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * base controller
 *
 * @author wenjie
 */
public class BaseController {
    protected HttpServletRequest request;

    protected HttpServletResponse response;

    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {

        this.request = request;

        this.response = response;

        this.session = request.getSession();

    }

    protected Long getUid() {
        return Long.parseLong(request.getAttribute("uid") + "");
    }

    protected Long getUidFromToken() {
        String token = ServletUtils.getHeaderIgnoreCase("UT-Token");
        if (StringUtils.isBlank(token)) {
            return null;
        }
        try {
            JwtOperator jwtOperator = SpringUtils.getBean(JwtOperator.class);
            Claims claims = jwtOperator.getClaimsFromToken(token);
            return Long.valueOf(claims.get("uid") + "");
        } catch (Exception e) {
            return null;
        }
    }
}
