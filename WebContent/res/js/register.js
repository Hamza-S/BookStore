/**
 * 
 */


function showPassword(){
	password1=document.getElementById("password1");
	if(password1.type=="password"){
		password1.type="text";
		password2.type="text";
	}
	else{
		password1.type="password";
		password2.type="password";
	}
}


function check() {
	if (document.getElementById('password1').value ==
	    document.getElementById('password2').value) {
	    document.getElementById('message').style.color = 'green';
	    document.getElementById('message').innerHTML = 'matching';
	  } 
	  else {
	    document.getElementById('message').style.color = 'red';
	    document.getElementById('message').innerHTML = 'not matching';
	  }
}