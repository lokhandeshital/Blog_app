package com.bikkadit.blogapp.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bikkadit.blogapp.helper.AppConstant;
import com.bikkadit.blogapp.model.Role;
import com.bikkadit.blogapp.repository.RoleRepository;

@Configuration
public class MyConfiguration implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepo;

	@Bean
	ModelMapper mapper() {
		return new ModelMapper();

	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("Shital423"));

		try {

			Role role = new Role();
			role.setId(AppConstant.ADMIN_ROLE);
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setId(AppConstant.NORMAL_ROLE);
			role1.setName("NORMAL_ROLE");

			List<Role> roles = List.of(role, role1);

			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> System.out.println(r.getName()));

		} catch (Exception e) {

			e.printStackTrace();

		}
	}

}
