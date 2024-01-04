
//const stompClient = new StompJs.Client({
//    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
//});

//const login = ()=>{
//	stompClient.onConnect = (frame) => {
//	    setConnected(true);
//	    console.log('Connected: ' + frame);
//	    stompClient.subscribe('/chat/contact/${document.getElementById('uid').value}', (message) => {
//	    	showMessage(JSON.parse(message.body).content);
//	    });
//	};
//
//	stompClient.onWebSocketError = (error) => {
//	    console.error('Error with websocket', error);
//	};
//
//	stompClient.onStompError = (frame) => {
//	    console.error('Broker reported error: ' + frame.headers['message']);
//	    console.error('Additional details: ' + frame.body);
//	};
//	
//	stompClient.activate();
//    
//}

//stompClient.onConnect = (frame) => {
//    setConnected(true);
//    console.log('Connected: ' + frame);
//    var header = {
//            "user" : "Kate"
//    }
//
//    stompClient.subscribe('/topic/greetings', (message) => {
//    	showMessage(JSON.parse(message.body).content);
//    });
//    stompClient.subscribe('/topic/contact/kate', (message) => {
//    	  console.log('Connected: ' + '/topic/contact/kate');
//    	showMessage(JSON.parse(message.body).message);
//    });
//};
//var header ;
//
//stompClient.onConnect = (header,(frame) => {
//setConnected(true);
//console.log('Connected: ' + frame);
//
//
//
//stompClient.subscribe('/topic/greetings', (message) => {
//	showMessage(JSON.parse(message.body).content);
//});
//stompClient.subscribe('/topic/contact/kate', (message) => {
//	  console.log('Connected: ' + '/topic/contact/kate');
//	showMessage(JSON.parse(message.body).message);
//});
//});
//
//stompClient.onWebSocketError = (error) => {
//    console.error('Error with websocket', error);
//};
//
//stompClient.onStompError = (frame) => {
//    console.error('Broker reported error: ' + frame.headers['message']);
//    console.error('Additional details: ' + frame.body);
//};
 var stompClient;
 let socket;
 var genUid;
 var uid;

 
function login(userName) {
	genUid = crypto.randomUUID();
	uid = genUid.replaceAll("-", "");
	console.log("GEN UUID:"+uid);
    socket = new WebSocket('ws://localhost:8080/gs-guide-websocket')
//	let socket = new SockJS('ws://localhost:8080/gs-guide-websocket')
    stompClient = Stomp.over(socket);
    stompClient.connect({user:userName,uid:uid, gender:gender}, function () {
    	 setConnected(true);
    	 stompClient.subscribe('/topic/crcontact', function(respnose) {///topic/crcontact
    		 displayContacts(JSON.parse(respnose.body));
         });
    	 stompClient.subscribe('/topic/public', (message) => {
    		 showPublicMessage(JSON.parse(message.body));
  	     });
    	 stompClient.subscribe('/topic/alert', (message) => {
    		 showPublicMessage(JSON.parse(message.body));
  	     });
    	 stompClient.subscribe('/user/subscribe/'+uid, (message) => {
    	 	showPrivateMessage(JSON.parse(message.body));
    	 });
    	 
    	 connectWs();
    });

}
/* 顯示在線人員列表 */
function displayContacts(contacts) {
  console.log("displayContacts ...");
  var contactlist = $("#friend");
  contactlist.children().remove();
  console.log(contacts);
  if (contacts) {
	  $("#friend").append("<li class=\"friend\"  id=\"liall\"><input type=\"hidden\" value=\"all\"><img class=\"contactUserSrc\" id=\"imgall\"><div class=\"name\">All</div></li>");
	  $("#imgall").attr('src', getUserImg("all"));
	  var alljs = getOnclickPrivateJs("all","All","#sendPublicBtn");
	  console.log("alljs=====>"+alljs);
	  var allclickfun = eval("(function (){"+alljs+"});");
	  console.log("===allclickfun==========>"+allclickfun);
	  $("#liall").attr('onclick','').click(allclickfun);
	  contacts.forEach(function(contact) {
		console.log(getUserImg(contact.gender));
		var uid3 = contact.uid;
		$("#friend").append("<li class=\"friend\"  id=\"li"+uid3+"\"><input type=\"hidden\" value=\""+uid3+"\"><img class=\"contactUserSrc\" id=\"img"+uid3+"\"><div class=\"name\">"+contact.name+"</div></li>");
		$("#img"+uid3).attr('src', getUserImg(contact.gender));
		console.log("================>"+uid3);
		var js = getOnclickPrivateJs(uid3,contact.name,"#sendPrivateBtn");
		var clickfun = eval("(function (){"+js+"});");
		console.log("===clickfun==========>"+clickfun);
		$("#li"+contact.uid).attr('onclick','').click(clickfun);
    
    });
  }
}

function getOnclickPrivateJs(liId,name,btnId){
	         //改變li顏色
	var onclickJs="$(\'.friend\').removeClass(\'selected\'); " +
			"$(\'.friend\').removeClass(\'notify\'); " +
			" var currentli =$(\'#li"+liId+"\');" +
			"currentli.addClass(\'selected\');"+
			//改變name
			"$(\'#dialogueName\').html(\'"+name+"\');"+
			//改變div
			"$(\'.dialogueContentdtl\').removeClass(\'show\');"+
			" var currentdi=$(\'#di"+liId+"\');"+
			" if(document.getElementById(\"di"+liId+"\")){" +
			" var currentdi=$(\'#di"+liId+"\');"+
			" currentdi.addClass(\'show\');}"+
			" else{ $(\"#dialogueContent\").append(\'<div id=\"di"+liId+"\" class=\"dialogueContentdtl show\"></div>\')};"+
			//改變sendbtn
			" $(\'.sendBtn\').removeClass(\'show\'); " +
			" var currentBtn=$(\'"+btnId+"\'); currentBtn.addClass(\'show\'); " +
			//改變hidden value
			" $(\".sendHidden\").val(\'"+liId+"\');"
	return onclickJs;
	
	
}
/* 依據性別顯示頭像 */
function getUserImg(userGender){
	  if ('female'===userGender){
		  return imgFemaleSrc;
	  }else if('male'===userGender){
		  return imgMaleSrc;
	  }else{
		  return imgSysSrc;
	  }
}


function createConnectObject(){
	 var crMessage = {
		"toId" : uid, 
		"toName" : $("#userName").html()
		}
	return crMessage;
}


function showPublicMessage(message) {
	var className =".img"+message.fromId;
	console.log("================>showPublicMessage");
	console.log(className);
	if(uid===message.fromId){
	$("#diall")
	.append("<div class=\"user local\"><div class=\"avatar\"><div class=\"pic\"><img class=\"img"+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")		
	}else{
	$("#diall")
	.append("<div class=\"user remote\"><div class=\"avatar\"><div class=\"pic\"><img class=\"img"+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")
	}
	$(className).attr('src',getUserImg(message.gender));
	$("#liall").addClass('notify');
	alert("You got a message from "+message.fromName);
}

function showPrivateMessage(message) {
//	debugger;
	var className =".img"+message.fromId;
	var fromId ="#di"+message.fromId;
	var toId ="#di"+message.toId;
	console.log(message);
	if(uid===message.fromId){
		$("#di"+message.toId)
		.append("<div class=\"user local\"><div class=\"avatar\"><div class=\"pic\"><img class=\"img"+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")		
		alert("message sent to "+message.toName+"!!");
	}else{
	
		if(!(document.getElementById("di"+message.fromId))){
			$("#dialogueContent").append("<div id=\"di"+message.fromId+"\" class=\"dialogueContentdtl notify\"></div>")
		}
		
		$("#di"+message.fromId)
		.append("<div class=\"user remote\"><div class=\"avatar\"><div class=\"pic\"><img class=\"img"+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")
		$("#li"+message.fromId).addClass('notify');
		alert("You got a message from "+message.fromName);
	
	}
	$(className).attr('src',getUserImg(message.gender));
}

/*未修改的方法*/
function showMessage(message) {
	var className ="."+message.fromId;
	console.log(className);
	if(uid===message.fromId){
	$("#all")
	.append("<div class=\"user local\"><div class=\"avatar\"><div class=\"pic\"><img class=\""+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")		
	}else{
	$("#all")
	.append("<div class=\"user remote\"><div class=\"avatar\"><div class=\"pic\"><img class=\""+message.fromId+"\" /></div><div class=\"name\">"+message.fromName+"</div></div><div class=\"text\">"+message.message+"</div></div>")
	}
	$(className).attr('src',getUserImg(message.gender));
}


function connectWs() {
	  console.log("connectWs...");
//	  debugger;
	  var crMessage = createConnectObject();
	  stompClient.send("/app/connect", {}, 
			  JSON.stringify(crMessage));
//	  stompClient.send("/app/announce", {}, 
//			  JSON.stringify({'fromName':'System','toName':userName,'message':'in'}));
	  stompClient.send("/app/announce", {}, 
			  JSON.stringify({'fromName':'System','toName':userName,'message':"User: "+userName+"  join talk!!!"}));
}


function sendContent() {
    stompClient.send(
       '/app/message',{},
       JSON.stringify({'fromId':uid,'fromName': userName,"message":$("#content").val(),"gender":gender})
    );
    $("#content").val('');

}


function sendPrivateContent() {
	console.log('=========>sendPrivateContent');
	const msg = $("#content").val();
	//console.log($(".sendHidden").val());
    stompClient.send(
        "/app/sendMsg",{},
        JSON.stringify({
        	'fromId':uid,'fromName': $("#userName").html(),'toName':$('#dialogueName').html(),'toId':$(".sendHidden").val(),'message':$("#content").val(),'gender':gender})
    );
    $("#content").val('');
	
}


function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#logoutBtn").prop("disabled", !connected);
    if (connected) {
        $("#dialogueContent").show();
    }
    else {
    	location.href="/chat/entry";
    }
    //$("#dialogueContent").html("");
}

function disconnect() {
	stompClient.send("/app/announce", {}, 
			  JSON.stringify({'fromName':'System','toName':userName,'message':"User: "+userName+" exit talk!!!"}));
	stompClient.disconnect();
	console.log("Disconnected");
	setConnected(false);
	
}

$(function () {
//  $("#logoutBtn").click(() => disconnect());
//  $("#sendPublicBtn").click(() => sendContent());
//  $("#sendPrivateBtn").click(() => sendPrivateContent());
  //debugger;
  login(userName);
  alert(userName);
  $("#logoutBtn").click(() => disconnect());
  $("#sendPublicBtn").click(() => sendContent());
  $("#sendPrivateBtn").click(() => sendPrivateContent());

}); 

