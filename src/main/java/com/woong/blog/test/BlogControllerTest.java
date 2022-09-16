package com.woong.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 스프링이 com.woong.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 하는 것이 아닌
//  특정 어노테이션@이 붙은 클래스 파일들을 new해서 스프링 컨테이너가 관리 (IOC)
@RestController
public class BlogControllerTest {
	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}
}
