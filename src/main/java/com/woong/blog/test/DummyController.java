package com.woong.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woong.blog.model.RoleType;
import com.woong.blog.model.User;
import com.woong.blog.repository.UserRepository;

@RestController
public class DummyController {

	@Autowired //의존성 주입 (DI)
	private UserRepository userRepository;
	
	//http://localhost:8000/blog/dummy/join
	//http의 body에 username, password, email 데이터를 가지고 요청
	//x-www-form-urlencoded 를 사용하면 key=value로 와 변수에 값을 자동 대입
	@PostMapping("/dummy/join")
	public String join(User user) { 
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("Date : " + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료";
	}
}
