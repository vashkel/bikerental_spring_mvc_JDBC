function showProfile() {
	if(document.getElementById('profile').style.display === 'block'){
		document.getElementById('profile').style.display = 'none';
        document.getElementById('addBike').style.display = 'block';
        document.getElementById('createOrder').style.display = 'block';
        document.getElementById('password').style.display = 'block';

    }else{
		document.getElementById('profile').style.display = 'block';
		document.getElementById('password').style.display = 'none';
        document.getElementById('addBike').style.display = 'none';
        document.getElementById('createOrder').style.display = 'none';

    }

}
function showPassword() {
    if(document.getElementById('password').style.display === 'block'){
        document.getElementById('password').style.display = 'none';
        document.getElementById('addBike').style.display = 'block';
        document.getElementById('createOrder').style.display = 'block';
    }else{
        document.getElementById('password').style.display = 'block';
        document.getElementById('currentPassword').style.display = 'block';
        document.getElementById('profile').style.display = 'none';
        document.getElementById('addBike').style.display = 'none';
        document.getElementById('createOrder').style.display = 'none';
    }
}

function checkPassword(){
	if(isPasswordEqual()){
		document.getElementById("passwUpdateForm").submit();
	}	
}
