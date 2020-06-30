function initLogin(){
	isLogin = document.getElementById('isLoginElement').value;
	if(isLogin){
		document.getElementById('authorization').style.display = 'block';
	}else{
		document.getElementById('registration').style.display = 'block';
		document.getElementById('choose_type').text='${authorizationLabel}';
	}

	return isLogin;

}
function showFunc() {
	if(isLogin) {
		document.getElementById('authorization').style.display = 'none';
		document.getElementById('registration').style.display = 'block';
		document.getElementById('choose_type').text='${authorizationLabel}';
		isLogin = false;
	} else {
		document.getElementById('authorization').style.display = 'block';
		document.getElementById('registration').style.display = 'none';
		document.getElementById('choose_type').text='${registrationLabel}';
		isLogin = true;
		}
}

function checkUserData() {
    if (isPasswordEqual()) {
        document.getElementById("userDataForm").submit();
    }
}

