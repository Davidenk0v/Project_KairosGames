package com.kairosgames.kairos_games;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class KairosGamesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KairosGamesApplication.class, args);

	}

// 	@Bean
// 	CommandLineRunner init(UserRepository userRepository){
// 		return args -> {
// 			PermissionEntity allPermission = PermissionEntity.builder()
// 			.name("ALL")
// 			.build();

// 			PermissionEntity readPermission = PermissionEntity.builder()
// 			.name("READ")
// 			.build();


// 	RoleEntity roleAdmin = RoleEntity.builder()
// 			.roleEnum(ERole.ADMIN)
// 			.permissionEntitySet(Set.of(allPermission))
// 			.build();	

// 	RoleEntity roleUser = RoleEntity.builder()
// 			.roleEnum(ERole.USER)
// 			.permissionEntitySet(Set.of(readPermission))
// 			.build();

// 	UserEntity userAdmin = UserEntity.builder()
// 		.username("admin")
// 		.password("1234")
// 		.edad(77)
// 		.email("test2@mail.com")
// 		.isEnabled(true)
// 		.isAccountNonExpired(true)
// 		.isAccountNonLocked(true)
// 		.isCredentialsNonExpired(true)
// 		.roles(Set.of(roleAdmin))
// 		.build();

// 	UserEntity userUser = UserEntity.builder()
// 		.username("user")
// 		.password("1234")
// 		.edad(77)
// 		.email("test@mail.com")
// 		.isEnabled(true)
// 		.isAccountNonExpired(true)
// 		.isAccountNonLocked(true)
// 		.isCredentialsNonExpired(true)
// 		.roles(Set.of(roleUser))
// 		.build();


// 	userRepository.save(userAdmin);
// 	userRepository.save(userUser);
// 	};
// }

}
