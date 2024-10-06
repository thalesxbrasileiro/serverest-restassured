package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ExcluirUsuarioTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();
	private String usuarioId;
	private SoftAssert softAssert;

	@BeforeMethod
	public void setUp() {
		softAssert = new SoftAssert();

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		usuarioId = usuariosClient.cadastrarUsuarios(usuario)
			.then()
				.extract()
					.path("_id");
	}

	@Test(description = "CT-001: Deve retornar 200 e excluir usuário com sucesso")
	public void testExcluirUsuarioComSucesso() {

		Response response =
				usuariosClient.excluirUsuario(usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
							.response();

		softAssert.assertEquals(200, response.getStatusCode());
		softAssert.assertEquals("Registro excluído com sucesso", response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(description = "CT-002: Deve retornar 405 ao tentar excluir usuário sem informar ID")
	public void testTentarDeletarUsuarioSemInformarID() {

		String ID_VAZIO = "";

		Response response =
				usuariosClient.excluirUsuario(ID_VAZIO)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(405)
						.extract()
							.response();

		softAssert.assertEquals(405, response.getStatusCode());
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(description = "CT-003: Deve retornar 200 e não excluir usuário informando ID inválido")
	public void testTentarDeletarUsuarioInformandoIDInvalido() {

		String ID_INVALIDO = "idInvalido";

		Response response =
				usuariosClient.excluirUsuario(ID_INVALIDO)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
							.response();

		softAssert.assertEquals(200, response.getStatusCode());
		softAssert.assertEquals("Nenhum registro excluído", response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(description = "CT-004: Deve retornar 400 ao tentar excluir usuário com carrinho cadastrado")
	public void testTentarDeletarUsuarioComCarrinhoCadastrado() {

		String ID_USUARIO_COM_CARRINHO_CADASTRADO = "0uxuPY0cbmQhpEz1";

		Response response =
				usuariosClient.excluirUsuario(ID_USUARIO_COM_CARRINHO_CADASTRADO)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(400)
						.extract()
							.response();

		softAssert.assertEquals(400, response.getStatusCode());
		softAssert.assertEquals("Não é permitido excluir usuário com carrinho cadastrado", response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

}