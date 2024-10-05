package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de cadastrar usuários")
public class CadastrarUsuariosTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();
	private final String NOME_EM_BRANCO = "nome não pode ficar em branco";
	private final String EMAIL_EM_BRANCO = "email não pode ficar em branco";
	private final String PASSWORD_EM_BRANCO = "password não pode ficar em branco";
	private final String ADMINISTRADOR_EM_BRANCO = "administrador deve ser 'true' ou 'false'";
	private final String EMAIL_JA_CADASTRADO = "Este email já está sendo usado";
	private String usuarioId;

	@AfterEach
	public void tearDown() {
		if (usuarioId != null) {
			usuariosClient.excluirUsuarioDoBanco(usuarioId);
		}
	}

	@Test
	@DisplayName("Cenário 01: Deve validar contrato de cadastro de usuários com sucesso")
	public void testDeveValidarContratoCadastrarUsuariosComSucesso() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		usuarioId =
				usuariosClient.cadastrarUsuarios(usuario)
					.then()
						.statusCode(HttpStatus.SC_CREATED)
						.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/usuarios/cadastrar_usuarios.json"))
						.extract()
							.path("_id");
	}

	@Test
	@DisplayName("Cenário 02: Deve retornar 200 quando cadastrar usuário com sucesso")
	public void testDeveCadastrarUsuarioComSucesso() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

		usuarioId =
				usuariosClient.cadastrarUsuarios(usuario)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(201)
						.body("message", equalTo("Cadastro realizado com sucesso"))
						.body("_id", notNullValue())
						.extract()
							.path("_id");
	}

	@Test
	@DisplayName("Cenário 03: Deve retornar 400 quando tentar cadastrar usuário sem nome")
	public void testTentarCadastrarUsuarioSemNome() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioComNomeEmBranco();

		UsuariosModel response =
				usuariosClient.cadastrarUsuarios(usuario)
					.then()
						.statusCode(400)
						.extract()
							.as(UsuariosModel.class);

		assertEquals(NOME_EM_BRANCO, response.getNome());
	}


	@Test
	@DisplayName("Cenário 04: Deve retornar 400 quando tentar cadastrar usuário com email já cadastrado")
	public void testTentarCadastrarUsuarioComEmailCadastrado() {

		UsuariosModel usuarioValido = UsuariosDataFactory.usuarioValido();

		usuarioId = usuariosClient.cadastrarUsuarios(usuarioValido)
			.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract()
					.path("_id");

		UsuariosModel usuarioComEmailJaCadastrado = UsuariosDataFactory.usuarioComEmailJaCadastrado(usuarioValido.getEmail());

		String response = usuariosClient.cadastrarUsuarios(usuarioComEmailJaCadastrado)
			.then()
				.statusCode(400)
				.extract()
					.path("message");

		assertEquals(EMAIL_JA_CADASTRADO, response);
	}

	@Test
	@DisplayName("Cenário 05: Deve retornar 400 quando tentar cadastrar usuário com email em branco")
	public void testTentarCadastrarUsuarioComTodosDadosEmBranco() {

		UsuariosModel usuario = UsuariosDataFactory.usuarioComTodosOsDadosEmBranco();

		UsuariosModel response =
				usuariosClient.cadastrarUsuarios(usuario)
						.then()
						.extract()
						.as(UsuariosModel.class);

		Assertions.assertAll("response",
				() -> assertEquals(NOME_EM_BRANCO, response.getNome()),
				() -> assertEquals(EMAIL_EM_BRANCO, response.getEmail()),
				() -> assertEquals(PASSWORD_EM_BRANCO, response.getPassword()),
				() -> assertEquals(ADMINISTRADOR_EM_BRANCO, response.getAdministrador())
		);
	}

	@ParameterizedTest(name = "Run: {index} - {0} - KEY: {1} - VALUE: {2}")
	@MethodSource("com.vemser.rest.data.provider.UsuariosDataProvider#usuarioDataProvider")
	@DisplayName("Cenário 06: Deve retornar 400 quando tentar cadastrar usuário com dados inválidos")
	public void testTentarCadastrarUsuarioComTodosDadosEmBrancoDataProvider(UsuariosModel usuario, String key, String value) {

		usuariosClient.cadastrarUsuarios(usuario)
				.then()
				.statusCode(400)
				.body(key, Matchers.equalTo(value));
	}

}
