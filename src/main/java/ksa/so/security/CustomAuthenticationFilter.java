package ksa.so.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_PHONE = "phone";
    public static final String SPRING_SECURITY_OTP = "otp";
	/* public static final String SPRING_SECURITY_FORM_DOMAIN_KEY = "domain"; */

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
        throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " 
              + request.getMethod());
        }

        CustomAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
		/*
		 * String username = obtainUsername(request); String password =
		 * obtainPassword(request);
		 */
        String phone = obtainPhone(request);
        String otp = obtainOtp(request);

        if (phone == null) {
        	phone = "";
        }
        if (otp == null) {
            otp = "";
        }

        return new CustomAuthenticationToken(phone, otp);
    }

    private String obtainPhone(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_PHONE);
    }
    
    private String obtainOtp(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_OTP);
    }
    
	/*
	 * private String obtainDomain(HttpServletRequest request) { return
	 * request.getParameter(SPRING_SECURITY_FORM_DOMAIN_KEY); }
	 */
}