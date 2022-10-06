let index = {
		init: function(){
			$("#btn-save").on("click", ()=>{ //function(){} 대신 ()=>{}사용 - this를 바인딩하기 위해
				this.save();
			}); //on은 첫번째 파라미터에 이벤트, 두번째 파라미터에 기능
		},
		
		save: function(){
			//alert("user의 save함수");
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val()
			};
			
			//console.log(data);
			//ajax 통신을 이용해 3개의 데이터를 json으로 변경하여 insert 요청
			//ajax 호출시 default가 비동기 호출
			$.ajax({
				type:"POST",
				url: "/blog/api/user",
				data: JSON.stringify(data), //http body 데이터
				contentType: "application/json; charset = utf-8", //body데이터가 어떤 타입(MIME)인지
				dataType : "json" //요청을 서버로 해서 응답이 왔을때 기본적으로 모든것이 String 문자열 - 생긴게 JSON이면 javascript 오브젝트로 변경 
				//안 적어도 ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환을 해줌
			}).done(function(resp){
				alert("회원가입이 완료되었습니다");
				//console.log(resp);
				location.href = "/blog";
			}).fail(function(error){
				alert(JSON.stringify(error));
			}); 
		}
}

index.init();