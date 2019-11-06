package ksa.so.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ksa.so.domain.User;
import ksa.so.domain.UserApp;

public interface CustomUserDetailsService {

	UserDetails loadUserByPhoneAndOtp(String phone, String otp) throws UsernameNotFoundException;

	UserApp loadUserByPhone(String phone) throws UsernameNotFoundException;

	User loadUserByUsername(String username) throws UsernameNotFoundException;

}