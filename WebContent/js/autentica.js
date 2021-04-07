function cod64 (){
	
var senhaembase64 = btoa(document.frmLogin.txtsenha.value);
	
	document.frmLogin.txtsenha.value = senhaembase64;

	return true;
}