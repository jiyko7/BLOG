package com.woong.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //User 클래스가 MySQL에 테이블이 생성
//ORM기술임 -> Java(다른 언어) Object -> 테이블로 매핑해주는 기술
// @DynamicInsert // insert시 null인 필드를 제외
public class User {
	
	@Id // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라감
	// DB의 넘버링 전략 (오라클 = sequence, MySQL = auto_increment)
	private int id; //시퀀스, auto_increment
	
	@Column(nullable=false, length = 30)
	private String username; //아이디
	
	@Column(nullable=false, length = 100) // 패스워드를 해쉬로 변경해 암호화하기 때문
	private String password;
	
	@Column(nullable=false, length = 50)
	private String email;
	
	//@ColumnDefault("'user'") //" " 사이에 ' '를 넣어줘야 문자로 인식함
	@Enumerated(EnumType.STRING) // DB는 RoleType을 모르므로 String Enum임을 알려줌
	private RoleType role; //Enum을 쓰는게 좋음 - 도메인을 만들어 줄 수 있음
	
	
	@CreationTimestamp // 시간이 자동입력
	private Timestamp createDate;
}
