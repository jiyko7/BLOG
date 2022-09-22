package com.woong.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
		//http://localhost:8000/blog/dummy/users
		@GetMapping("/dummy/users")
		public List<User> list() {
			return userRepository.findAll();
		}
		
		//http://localhost:8000/blog/dummy/user
		//http://localhost:8000/blog/dummy/user?page=페이지번호
		//페이지번호 0,1.2,3 순으로 올라가면 페이지가 넘어감
		@GetMapping("/dummy/user") 
		public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Direction.DESC) Pageable pageable) {
			Page<User> pagingUser = userRepository.findAll(pageable);
			List<User> users = pagingUser.getContent();
			return users;
		}
		
		//http://localhost:8000/blog/dummy/user/3
		@Transactional	//@Tracsactional은 함수가 종료되면 자동 커밋됨 - 변경이 감지되면 DB를 수정함 - 더티체킹
		@PutMapping("/dummy/user/{id}")
		public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //JSON 데이터를 요청 -> ㅡMessageConverter의 Jackson 라이브러리가 자바 오브젝트로 변환해줌
			System.out.println("id : " +id);
			System.out.println("password : " + requestUser.getPassword());
			System.out.println("email : " + requestUser.getEmail());
			
			User user = userRepository.findById(id).orElseThrow(()->{
				return new IllegalArgumentException("수정에 실패하였습니다");	
			});
			user.setPassword(requestUser.getPassword());
			user.setEmail(requestUser.getEmail());
			
			//userRepository.save(requestUser); //save는 id를 전달하지 않으면 insert인데 id를 전달하면 update해줌
			return user;
		}
		
		@DeleteMapping("/dummy/user/{id}")
		public String delete(@PathVariable int id) {
			try {
				userRepository.deleteById(id);
			} catch (EmptyResultDataAccessException e) {
				return "해당 id는 존재하지 않아 삭제할 수 없습니다";
			}
			
			return "삭제되었습니다 id : "+ id;
		}
	
}
