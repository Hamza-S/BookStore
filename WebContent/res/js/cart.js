/**
 * 
 */

function validate() {
	if (!ok)
		return false;
	
	p = document.getElementById("itemquant").value;

	if (isNaN(p) || p <= 0 || p >= 100) {
		alert("Invalid. Must be in (0,100).");
		//document.getElementById("interest-error").style.display = "inline";
		//document.getElementById("interest-error").style.color = "red";
		ok = false;
	} 
	else {
		//document.getElementById("interest-error").style.display = "none";
	}
	return ok;
}

