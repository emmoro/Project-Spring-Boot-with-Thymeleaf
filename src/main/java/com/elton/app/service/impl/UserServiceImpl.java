package com.elton.app.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.app.datatables.Datatables;
import com.elton.app.datatables.DatatablesColumn;
import com.elton.app.domain.Profile;
import com.elton.app.domain.UserSystem;
import com.elton.app.repository.UserRepository;
import com.elton.app.service.UserService;


@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Datatables datatables;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSystem user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
		return new User(
				user.getEmail(),
				user.getPassword(),
			AuthorityUtils.createAuthorityList(getAtuthorities(user.getProfiles()))
		);
	}
	
	private String[] getAtuthorities(List<Profile> profiles) {
		String[] authorities = new String[profiles.size()];
		for (int i = 0; i < profiles.size(); i++) {
			authorities[i] = profiles.get(i).getDesc();
		}
		return authorities;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> findAll(HttpServletRequest request) {
		datatables.setRequest(request);
		datatables.setColunas(DatatablesColumn.USERS);
		Page<UserSystem> page = datatables.getSearch().isEmpty()
				? userRepository.findAll(datatables.getPageable())
				: userRepository.findByEmailOrProfile(datatables.getSearch(), datatables.getPageable());
		return datatables.getResponse(page);
	}

	@Transactional(readOnly = false)
	public void saveUser(UserSystem user) {
		String crypt = new BCryptPasswordEncoder().encode(user.getPassword());
		user.setPassword(crypt);

		userRepository.save(user); 	 	
	}

	@Transactional(readOnly = true)
	public UserSystem findById(Long id) {
		
		return userRepository.findById(id).get();
	}

	@Transactional(readOnly = true)
	public UserSystem findByIdProfiles(Long userId, Long[] profilesId) {
		
		return userRepository.findByIdAndProfiles(userId, profilesId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário inexistente!"));
	}
	
	@Transactional(readOnly = true)
	@Override
	public UserSystem findByEmail(String email) {
		return userRepository.findByEmail(email).get();
	}
	
	@Override
	public boolean isPasswordCorrect(String typedPassword, String storedPassword) {
		return new BCryptPasswordEncoder().matches(typedPassword, storedPassword);
	}

	@Transactional(readOnly = false)
	@Override
	public void updatePassword(UserSystem user, String password) {
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		userRepository.save(user);		
	}
	
}
