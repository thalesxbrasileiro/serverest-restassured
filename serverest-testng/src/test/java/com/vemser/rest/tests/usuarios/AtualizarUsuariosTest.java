package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class AtualizarUsuariosTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();
	private String usuarioId;
	private SoftAssert softAssert;

	@BeforeMethod
	public void setUp() {
		softAssert = new SoftAssert();

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		usuarioId =
				usuariosClient.cadastrarUsuarios(usuario)
					.then()
						.extract()
							.path("_id");
	}

	@AfterMethod
	public void tearDown() {
		if (usuarioId != null) {
			usuariosClient.excluirUsuarioDoBanco(usuarioId);
		}
	}

	@Test(description = "CT-001: Deve retornar 200 e atualizar usuário com sucesso")
	public void testAtualizarUsuariosComSucesso() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		Response response =
				usuariosClient.atualizarUsuario(usuario, usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
							.response();

		softAssert.assertEquals(200, response.getStatusCode());
		softAssert.assertEquals("Registro alterado com sucesso", response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(description = "CT-002: Deve retornar 400 ao tentar alterar usuário sem todos os campos obrigatórios")
	public void testTentarAlterarUsuarioSemTodosOsCamposObrigatorios() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioComTodosOsDadosEmBranco();

		Response response =
				usuariosClient.atualizarUsuario(usuario, usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(400)
						.extract()
							.response();

		softAssert.assertEquals(400, response.getStatusCode());
		softAssert.assertEquals("nome não pode ficar em branco", response.jsonPath().getString("nome"));
		softAssert.assertEquals("email não pode ficar em branco", response.jsonPath().getString("email"));
		softAssert.assertEquals("password não pode ficar em branco", response.jsonPath().getString("password"));
		softAssert.assertEquals("administrador deve ser 'true' ou 'false'", response.jsonPath().getString("administrador"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(description = "CT-003: Deve retornar 400 ao tentar alterar usuário com ID inválido")
	public void testTentarAlterarUsuarioComIDInvalido() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		String idInvalido = "idInvalido";

		Response response =
				usuariosClient.atualizarUsuario(usuario, idInvalido)
					.then()
						.log().all()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(400)
						.extract()
							.response();

		softAssert.assertEquals(400, response.getStatusCode(), "Status code inválido");
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType(), "Content-Type inválido");
		softAssert.assertEquals("ID inválido", response.jsonPath().getString("message"), "Mensagem de erro inválida");
		softAssert.assertAll();
	}

	@Test(description = "CT-004: Deve retornar 405 ao tentar alterar usuário sem informar ID")
	public void testTentarAlterarUsuarioSemInformarID() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		String idVazio = "";

		Response response =
				usuariosClient.atualizarUsuario(usuario, idVazio)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(405)
						.extract()
							.response();

		softAssert.assertEquals(405, response.getStatusCode());
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
	}
}