package ksa.so.web;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ksa.so.domain.Menu;
import ksa.so.domain.MenuRights;
import ksa.so.domain.User;
import ksa.so.repositories.UserRepository;
import ksa.so.service.MenuService;


@RestController
public class MenuController {
	@Autowired
	MenuService menuService;
	
	@Autowired
	UserRepository userRepository;

	@GetMapping("/api/config/menu")

	@ResponseBody
	public List<MenuRights> getMainMenu(Authentication authentication) {
		System.out.println(authentication.getName());
		List<MenuRights> list = new ArrayList<MenuRights>();
		Optional<User> useropt = userRepository.findByUsername(authentication.getName());
		System.out.println(useropt.get().getId());
		return menuService.getMainMenu(useropt.get().getId());

	}

	
}
