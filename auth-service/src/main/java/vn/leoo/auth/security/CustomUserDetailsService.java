package vn.leoo.auth.security;

import vn.leoo.auth.entity.UserEntity;
import vn.leoo.auth.exception.ResourceNotFoundException;
import vn.leoo.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email).map(UserPrincipal::create)
				.orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email));
	}

	@Transactional
	public UserDetails loadUserById(String id) {
		UserEntity user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(user);
	}
}