let index = {
		init: function(){
			$("#btn-save").on("click", ()=>{
				this.save();
			}); //on은 첫번째 파라미터에 이벤트, 두번째 파라미터에 기능
		},
		
		save: function(){
			//alert("user의 save함수");
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val()
			}
			
			//console.log(data);
			//ajax 통신을 이용해 3개의 데이터를 json으로 변경하여 insert 요청
			$.ajax().done().fail(); 
}

index.init();