package com.woong.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woong.blog.model.RoleType;
import com.woong.blog.model.User;
import com.woong.blog.repository.UserRepository;

@RestController //html파일이 아닌 Data를 리턴
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
	
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}") //{id} 주소로 파라미터를 전달 받음
	public User detail(@PathVariable int id) {
		//데이터베이스에서 못 찾아오면 user가 null - return 오류가 생김 - Optional로 User를 감싸서 보내줄테니 null인지 알아서 판단
		
		//람다식 사용방법
		//		User user = userRepository.findById(id).orElseThrow(()->{
		//				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);	
		//		});
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		// 요청 - 웹브라우저 , user 객체 - 자바 오브젝트
		// 웹브라우저는 자바 오브젝트를 이해 못함 - JSON으로 변환하여 사용 - Gson 라이브러리 사용 등
		// 하지만 스프링부트는 MessageConverter가 자동 작동 - 자바 오브젝트를 Jackson 라이브러리를 통해 JSON으로 자동 변환 - 브라우저가 인식
		return user;
	}
}
