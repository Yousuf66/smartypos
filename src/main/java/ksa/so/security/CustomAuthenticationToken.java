package ksa.so.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;
	private String phone;
    private String otp;

    public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public CustomAuthenticationToken(String phone, String otp) {
        super(null, null);
        this.phone = phone;
        this.otp = otp;
        super.setAuthenticated(false);
    }

    public CustomAuthenticationToken(Object principal, Object credentials, String phone, String otp, 
        Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.phone = phone;
        this.otp = otp;
        super.setAuthenticated(true); // must use super, as we override
    }
}