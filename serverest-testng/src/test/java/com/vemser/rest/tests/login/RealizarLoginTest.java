package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.model.LoginModel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static org.hamcrest.Matchers.equalTo;

public class RealizarLoginTest {

	private final LoginClient loginClient = new LoginClient();
	private SoftAssert softAssert;

	@BeforeMethod
	public void setUp() {
		softAssert = new SoftAssert();
	}

//	@DisplayName("Cenário 01: Deve retornar 200 e realizar login com sucesso")
	@Test
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

		softAssert.assertEquals("Login realizado com sucesso", response.jsonPath().getString("message"));
		softAssert.assertNotNull(authorization);
		softAssert.assertTrue(authorization.startsWith("Bearer "), "O token de autorização deve começar com 'Bearer '");
		softAssert.assertTrue(authorization.matches("^Bearer [a-zA-Z0-9.\\-_]+$"), "O token de autorização deve estar no formato esperado");
		softAssert.assertAll();
	}

//	@DisplayName("Cenário 02: Deve retornar 401 quando tentar realizar login com usuário inválido")
	@Test
	public void testTentarRealizarLoginComUsuarioInvalido() {

		LoginModel loginInvalido = LoginDataFactory.LoginInvalido();

		loginClient.realizarLogin(loginInvalido)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Email e/ou senha inválidos"));
	}

//	@DisplayName("Cenário 03: Deve retornar 401 quando tentar realizar login com senha inválida")
	@Test
	public void testTentarRealizarLoginComSenhaInvalida() {

		LoginModel loginSenhaInvalida = LoginDataFactory.loginSenhaInvalida();

		loginClient.realizarLogin(loginSenhaInvalida)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Email e/ou senha inválidos"));
	}

//	@DisplayName("Cenário 04: Deve retornar 400 quando tentar realizar login sem informar usuário")
	@Test
	public void testTentarRealizarLoginSemInformarUsuario() {

		LoginModel loginSemInformarUsuario = LoginDataFactory.loginSemInformarUsuario();

		loginClient.realizarLogin(loginSemInformarUsuario)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("email", equalTo("email não pode ficar em branco"));
	}

//	@DisplayName("Cenário 05: Deve retornar 400 quando tentar realizar login sem informar senha")
	@Test
	public void testTentarRealizarLoginSemInformarSenha() {

		LoginModel loginSemInformarSenha = LoginDataFactory.loginSemInformarSenha();

		loginClient.realizarLogin(loginSemInformarSenha)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("password", equalTo("password não pode ficar em branco"));
	}

}
