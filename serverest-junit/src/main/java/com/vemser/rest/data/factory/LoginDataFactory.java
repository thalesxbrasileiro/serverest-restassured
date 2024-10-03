package com.vemser.rest.data.factory;

import com.vemser.rest.model.LoginModel;

public class LoginDataFactory {

	private static LoginModel novoLogin() {

		LoginModel login = new LoginModel();
		login.setEmail("fulano@qa.com");
		login.setPassword("teste");

		return login;
	}

	public static LoginModel loginValido() {
		return novoLogin();
	}

	public static LoginModel LoginInvalido() {

		LoginModel login = new LoginModel();
		login.setEmail("usuario@invalido.com");
		login.setPassword("teste");

		return login;
	}

	public static LoginModel loginSenhaInvalida() {

		LoginModel login = new LoginModel();
		login.setEmail("fulano@qa.com");
		login.setPassword("senhaInvalida");

		return login;
	}

	public static LoginModel loginSemInformarUsuario() {

		LoginModel login = new LoginModel();
		login.setEmail("");
		login.setPassword("teste");

		return login;
	}

	public static LoginModel loginSemInformarSenha() {

		LoginModel login = new LoginModel();
		login.setEmail("fulano@qa.com");
		login.setPassword("");

		return login;
	}

	public static LoginModel loginUsuarioNaoAdmin() {

		String emailUsuarioNaoAdmin = UsuariosDataFactory.buscarOemailDoPrimeiroUsuarioNaoAdminId();
		String passwordUsuarioNaoAdmin = UsuariosDataFactory.buscarOpasswordDoPrimeiroUsuarioNaoAdminId();

		LoginModel login = new LoginModel();
		login.setEmail(emailUsuarioNaoAdmin);
		login.setPassword(passwordUsuarioNaoAdmin);

		return login;
	}


}
