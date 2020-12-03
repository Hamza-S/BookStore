function handler(request) {

	if ((request.readyState == 4) && (request.status == 200)) {
		console.log(request)
		var target = document.getElementById("library_category");
		console.log(request.responseText);
		target.innerHTML = request.responseText;
	}

}

function getBooksByCategory(address, category) {
	var request = new XMLHttpRequest();
	var data = '';
	/* add your code here to grab all parameters from form */
	data += "category=" + document.getElementById(category).value + "&";
	data += "fetch=true";

	request.open("GET", (address + "?" + data), true);
	request.onreadystatechange = function() {
		handler(request);
	};
	request.send(null);

}