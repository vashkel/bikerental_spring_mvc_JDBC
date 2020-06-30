
function isPasswordEqual(){
	var passw1 = document.getElementById('passw1').value;
	var passw2 = document.getElementById('passw2').value;
	if(passw1!==passw2){
		errorMessageShow('passwordNotMatchLabel');
		return false;
	}else{
		return true;
	}	
}