package com.woong.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  //파일을 리턴해줌
public class TempController {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome");
		// 파일리턴 기본경로 : src/main/resources/static
		//static 이하에는 정적파일만 가능 (브라우저가 인식가능한 파일) - JSP 안됨
		return "/home.html";
	}
	
	//http://localhost:8000/blog/temp/jsp
		@GetMapping("/temp/jsp")
		public String tempJsp() {
			System.out.println("tempJsp");
			 // prefix: /WEB-INF/views/
		     // suffix: .jsp
			return "test";
		}
}
