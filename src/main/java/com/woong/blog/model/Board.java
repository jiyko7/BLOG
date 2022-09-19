package com.woong.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터
	private String content; //섬머노트 라이브러리 사용하면 <html> 태그가 섞여서 디자인됨
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, One = User //유저 하나당 여러개의 게시물  
	//Eager - 하나이므로 바로 불러옴
	@JoinColumn(name="userId") //DB에는 오브젝트가 못 들어가므로 userId 필드로 들어감 (Primarykey 따라감)
	private User user; //DB는 오브젝트를 저장불가능 - 따라서 외래키를 사용하는데 자바는 오브젝트 저장이 가능
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //연관관계의 주인이 아님 (외래키가 아님을 표시 - 필드를 만들지 말라)
	//lazy - 나중에 불러옴
	//@JoinColumn(name="replyId") - 외래키 필요없으므로 작성하면 안됨
	private List<Reply> reply; //board만으로 reply 데이터베이스를 join하여 불러오기 위함임
	
	@CreationTimestamp
	private Timestamp createDate;

}
