package ksa.so.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ksa.so.domain.User;
import ksa.so.domain.UserApp;
import ksa.so.repositories.UserAppRepository;
import ksa.so.repositories.UserRepository;

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Autowired
	private UserAppRepository userAppRepository;

	@Autowired
	private UserRepository userRepository;

	public CustomUserDetailsServiceImpl(UserAppRepository userAppRepository) {
		this.userAppRepository = userAppRepository;
	}

	@Override
	public UserDetails loadUserByPhoneAndOtp(String phone, String otp) throws UsernameNotFoundException {
		if (StringUtils.isAnyBlank(phone, otp)) {
			throw new UsernameNotFoundException("phone and otp must be provided");
		}
		UserApp userApp = userAppRepository.findByPhoneAndOtp(phone, otp);
		if (userApp == null) {
			throw new UsernameNotFoundException(
					String.format("User not found with phone and otp, phone=%s, otp=%s", phone, otp));
		}
		return userApp;
	}

	@Override
	public UserApp loadUserByPhone(String phone) throws UsernameNotFoundException {
		if (StringUtils.isAnyBlank(phone)) {
			throw new UsernameNotFoundException("phone must be provided");
		}
		UserApp userApp = userAppRepository.findByPhone(phone);
		if (userApp == null) {
			throw new UsernameNotFoundException(String.format("User not found with phone, phone=%s", phone));
		}
		return userApp;
	}

	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		if (StringUtils.isAnyBlank(username)) {
			throw new UsernameNotFoundException("phone must be provided");
		}
		Optional<User> user = userRepository.findByUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(String.format("User not found with username, username=%s", username));
		}
		return user.get();
	}
}