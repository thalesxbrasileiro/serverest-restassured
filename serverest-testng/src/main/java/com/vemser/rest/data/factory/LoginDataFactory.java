package com.vemser.rest.data.factory;

import com.vemser.rest.model.LoginModel;
import com.vemser.rest.utils.Manipulation;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class LoginDataFactory {

	private static final String INVALIDO = "invalido";

	private static final Properties PROPS = Manipulation.getProp();

	public static String getEmailValidoProp() {
		return PROPS.getProperty("EMAIL_USUARIO");
	}
	public static String getSenhaValidaProp() {
		return PROPS.getProperty("SENHA_USUARIO");
	}

	private static LoginModel novoLogin() {

		LoginModel login = new LoginModel();
		login.setEmail(getEmailValidoProp());
		login.setPassword(getSenhaValidaProp());

		return login;
	}

	public static LoginModel loginValido() {
		return novoLogin();
	}

	public static LoginModel LoginInvalido() {

		LoginModel login = new LoginModel();
		login.setEmail(INVALIDO + getEmailValidoProp());
		login.setPassword(getSenhaValidaProp());

		return login;
	}

	public static LoginModel loginSenhaInvalida() {

		LoginModel login = new LoginModel();
		login.setEmail(getEmailValidoProp());
		login.setPassword(getSenhaValidaProp() + INVALIDO);

		return login;
	}

	public static LoginModel loginSemInformarUsuario() {

		LoginModel login = new LoginModel();
		login.setEmail(StringUtils.EMPTY);
		login.setPassword(getSenhaValidaProp());

		return login;
	}

	public static LoginModel loginSemInformarSenha() {

		LoginModel login = new LoginModel();
		login.setEmail(getEmailValidoProp());
		login.setPassword(StringUtils.EMPTY);

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
