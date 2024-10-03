package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class AtualizarUsuariosTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();
	private String usuarioId;

	@BeforeEach
	public void criarUsuarioAntesDeCadaTeste() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		usuarioId = usuariosClient.cadastrarUsuarios(usuario)
				.then()
				.extract()
				.path("_id");
	}

	@AfterEach
	public void excluirUsuarioAoFinalDaRequisicao() {
		if (usuarioId != null) {
			usuariosClient.excluirUsuarioDoBanco(usuarioId);
		}
	}

	@Test
	@DisplayName("Cenário 01: Deve retornar 200 e atualizar usuário com sucesso")
	public void testAtualizarUsuariosComSucesso() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		Response response = usuariosClient.atualizarUsuario(usuario, usuarioId)
				.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(200)
				.extract()
				.response();

		Assertions.assertAll(
				() -> Assertions.assertEquals(200, response.getStatusCode()),
				() -> Assertions.assertEquals("Registro alterado com sucesso", response.jsonPath().getString("message")),
				() -> Assertions.assertEquals("application/json; charset=utf-8", response.contentType())
		);
	}

	@Test
	@DisplayName("Cenário 02: Deve retornar 400 ao tentar alterar usuário sem todos os campos obrigatórios")
	public void testTentarAlterarUsuarioSemTodosOsCamposObrigatorios() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioComTodosOsDadosEmBranco();

		Response response =

				usuariosClient.atualizarUsuario(usuario, usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(400)
						.extract()
							.response();

		Assertions.assertAll(
				() -> Assertions.assertEquals(400, response.getStatusCode()),
				() -> Assertions.assertEquals("nome não pode ficar em branco", response.jsonPath().getString("nome")),
				() -> Assertions.assertEquals("email não pode ficar em branco", response.jsonPath().getString("email")),
				() -> Assertions.assertEquals("password não pode ficar em branco", response.jsonPath().getString("password")),
				() -> Assertions.assertEquals("administrador deve ser 'true' ou 'false'", response.jsonPath().getString("administrador")),
				() -> Assertions.assertEquals("application/json; charset=utf-8", response.contentType())
		);
	}

	@Test
	@DisplayName("Cenário 03: Deve retornar 400 ao tentar alterar usuário com ID inválido")
	public void testTentarAlterarUsuarioComIDInvalido() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		String idInvalido = "idInvalido";

		Response response =

				usuariosClient.atualizarUsuario(usuario, idInvalido)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(400)
						.extract()
							.response();

		Assertions.assertAll(
				() -> Assertions.assertEquals(400, response.getStatusCode(), "Status code inválido"),
				() -> Assertions.assertEquals("application/json; charset=utf-8", response.contentType(), "Content-Type inválido"),
				() -> Assertions.assertEquals("ID inválido", response.jsonPath().getString("message"), "Mensagem de erro inválida")
		);
	}

	@Test
	@DisplayName("Cenário 04: Deve retornar 405 ao tentar alterar usuário sem informar ID")
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

		Assertions.assertAll(
				() -> Assertions.assertEquals(405, response.getStatusCode()),
				() -> Assertions.assertEquals("application/json; charset=utf-8", response.contentType())
		);
	}
}