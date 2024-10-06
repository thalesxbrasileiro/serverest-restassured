package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.LoginModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes de realizar login")
public class RealizarLoginTest {

	private final LoginClient loginClient = new LoginClient();

	@Test
	@DisplayName("CT-001: Deve retornar 200 e realizar login com sucesso")
	public void testRealizarLoginComSucesso() {

		LoginModel login = LoginDataFactory.loginValido();

		Response response =
				loginClient.realizarLogin(login)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
							.response();

		String authorization = response.jsonPath().getString("authorization");

		assertEquals("Login realizado com sucesso", response.jsonPath().getString("message"));
		assertNotNull(authorization);
		assertTrue(authorization.startsWith("Bearer "), "O token de autorização deve começar com 'Bearer '");
		assertTrue(authorization.matches("^Bearer [a-zA-Z0-9.\\-_]+$"), "O token de autorização deve estar no formato esperado");
	}

	@Test
	@DisplayName("CT-002: Deve retornar 401 quando tentar realizar login com usuário inválido")
	public void testTentarRealizarLoginComUsuarioInvalido() {

		LoginModel loginInvalido = LoginDataFactory.LoginInvalido();

		loginClient.realizarLogin(loginInvalido)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Email e/ou senha inválidos"));
	}

	@Test
	@DisplayName("CT-003: Deve retornar 401 quando tentar realizar login com senha inválida")
	public void testTentarRealizarLoginComSenhaInvalida() {

		LoginModel loginSenhaInvalida = LoginDataFactory.loginSenhaInvalida();

		loginClient.realizarLogin(loginSenhaInvalida)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Email e/ou senha inválidos"));
	}

	@Test
	@DisplayName("CT-004: Deve retornar 400 quando tentar realizar login sem informar usuário")
	public void testTentarRealizarLoginSemInformarUsuario() {

		LoginModel loginSemInformarUsuario = LoginDataFactory.loginSemInformarUsuario();

		loginClient.realizarLogin(loginSemInformarUsuario)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("email", equalTo("email não pode ficar em branco"));
	}

	@Test
	@DisplayName("CT-005: Deve retornar 400 quando tentar realizar login sem informar senha")
	public void testTentarRealizarLoginSemInformarSenha() {

		LoginModel loginSemInformarSenha = LoginDataFactory.loginSemInformarSenha();

		loginClient.realizarLogin(loginSemInformarSenha)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("password", equalTo("password não pode ficar em branco"));
	}

}
