/**
 * 
 */

function showAddReview(){
	alert("wow");
	console.log("initial:"+document.getElementById('register-div').style.display);

	//Show Review Form
	if(document.getElementById('register-div').style.display == "none"){
		document.getElementById('register-div').style.display = "block";
		document.getElementById("review").value = "";
		console.log(document.getElementById("review").value);
		document.getElementById('addReview').value="Cancel Review";
//		document.getElementById("review").value = "Yes";

//		console.log("in if");
	}
	//Cancel Review Form
	else{
		document.getElementById('register-div').style.display = "none";
		document.getElementById('addReview').value="Add Review"
//		console.log("in else"+ document.getElementById('register-div').style.display);
	}
}