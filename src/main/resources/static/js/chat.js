$("#sendmessage").click(function(){
	var message=document.getElementById("messageid").value;
	
	if (message.length ==0) {
		alert("Write Some message");
	}else{
		document.getElementById("sendmessage").disabled=true;
		document.getElementById("messageid").value=null;
		var iDiv=document.createElement("DIV");
		iDiv.id="userbot";
		var image=document.createElement("IMG");
		image.id="avatar2";
		image.alt="Avatar";
		image.src="../../images/img_avatar.png";
		iDiv.appendChild(image);
		var para=document.createElement("P");
		var text=document.createTextNode(message);
		para.appendChild(text);
		iDiv.appendChild(para);
		document.getElementById("box").appendChild(iDiv);		
		$.ajax({
			  url: '/api/v1/botrequest',
			  type: 'POST',
			 // dataType: 'application/json',
			  contentType: 'application/json; charset=utf-8',
			  data: message,
			  success: function(data,status,jqXHR){
				  document.getElementById("sendmessage").disabled=false;
				  response='<div id="systembot">'+data+'</div>';
				  $("#box").append(response);
				
			  },
			  error: function(jqXHR,error,status){
				  document.getElementById("sendmessage").disabled=false;
				  response='<div id="systembot"><p style="color: red;"> There is something wrong with bot please try after some time</p></div>';
				  $("#box").append(response);
			  }
			  
			});
	
	}
	
	
});