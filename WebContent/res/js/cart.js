/**
 * 
 */

window.onload = (function(){update2()});
function update2() {
	var item2 = document.getElementById('item-2').innerHTML;
	var item2val = document.getElementById('item-2-value').innerHTML;
	var item1quant = document.getElementById('item1quant').value;
	
	
	console.log(item2);
	console.log(item2val);
	console.log(item1quant);
}

function calculate() {
	var item1val = document.getElementById('item-1-value').innerHTML;
	var item2val = document.getElementById('item-2-value').innerHTML;
	var item3val = document.getElementById('item-3-value').innerHTML;
	var item1quant = document.getElementById('item1quant').value;
	var item2quant = document.getElementById('item2quant').value;
	var item3quant = document.getElementById('item3quant').value;
	item1val = item1val.substr(-1);
	item2val = item2val.substr(-1);
	item3val = item3val.substr(-1);
	var total = (item1val * item1quant) + (item2val * item2quant) + (item3val * item3quant);
	return total;
}
function update() {
	alert(calculate());
	document.getElementById('total').innerHTML ="$"+calculate() ;
	
	
	
}

