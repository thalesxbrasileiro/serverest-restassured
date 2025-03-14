package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import com.vemser.rest.model.UsuariosResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.DisplayName.class)
@DisplayName("Testes de listar usuário por ID")
public class ListarUsuarioPorIDTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();

	private final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	UsuariosModel usuario;
	String usuarioId;

	@BeforeEach
	public void setUp() {
		usuario = UsuariosDataFactory.usuarioValido();

		usuarioId =
				usuariosClient.cadastrarUsuarios(usuario)
					.then()
						.statusCode(HttpStatus.SC_CREATED)
						.extract()
						.path("_id");
	}

	@AfterEach
	public void tearDown() {
		if (usuarioId != null) {
			usuariosClient.excluirUsuarioDoBanco(usuarioId);
		}
	}

	@Test
	@DisplayName("CT-001: Deve validar contrato de listar usuários por ID com sucesso")
	public void testDeveValidarContratoListarUsuariosPorIDComSucesso() {

		usuariosClient.buscarUsuarioPorID(usuarioId)
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/usuarios/listar_usuarios_por_id.json"));
	}

	@Test
	@DisplayName("CT-002: Deve retornar 200 e listar usuários por nome com sucesso")
	public void testListarUsuariosPorNomeComSucesso() {

		usuariosClient.buscarUsuarioPorNome(usuario.getNome())
			.then()
				.statusCode(200)
				.body("usuarios[0].nome", equalTo(usuario.getNome()));
	}

	@Test
	@DisplayName("CT-003: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando Hamcrest")
	public void testBuscarUsuarioPorIDComSucessoHamcrest() {

		usuariosClient.buscarUsuarioPorID(usuarioId)
			.then()
				.statusCode(200)
				.time(lessThan(2000L))
				.body("_id", notNullValue())
				.body("nome", equalTo(usuario.getNome()));
	}

	@Test
	@DisplayName("CT-004: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando Assertions")
	public void testBuscarUsuarioPorIDComSucessoAssertions() {

		Response usuarioResponse =
				usuariosClient.buscarUsuarioPorID(usuarioId)
					.then()
						.statusCode(200)
						.extract()
						.response();

		String nomeResponse = usuarioResponse.path("nome");
		String emailResponse = usuarioResponse.path("email");

		assertAll(
				() -> assertEquals(usuario.getNome(), nomeResponse),
				() -> assertEquals(usuario.getEmail(), emailResponse)
		);
	}

	@Test
	@DisplayName("CT-005: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando AssertAll")
	public void testBuscarUsuarioPorIDComAssertAll() {

		UsuariosResponse response =
				usuariosClient.buscarUsuarioPorID(usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
						.as(UsuariosResponse.class);

		assertAll("response",
				() -> assertEquals(usuario.getNome(), response.getNome(), "O nome não corresponde ao esperado"),
				() -> assertEquals(usuario.getEmail(), response.getEmail(), "O e-mail não corresponde ao esperado"),
				() -> assertNotNull(response.getPassword(), "O password não deve ser nulo")
		);
	}

	@Test
	@DisplayName("CT-006: Deve retornar 400 ao tentar buscar usuário com ID inválido")
	public void testTentarBuscarUsuarioComIDInvalido() {

		String ID_INVALIDO = "idInvalido";

		Response response =
				usuariosClient.buscarUsuarioPorID(ID_INVALIDO)
					.then()
						.extract()
						.response();

		assertAll(
				() -> assertEquals(400, response.getStatusCode()),
				() -> assertEquals(USUARIO_NAO_ENCONTRADO, response.jsonPath().getString("message")),
				() -> assertEquals("application/json; charset=utf-8", response.contentType())
		);
	}

	@Test
	@DisplayName("CT-007: Deve retornar 400 ao tentar buscar usuário com ID com espaço em branco")
	public void testTentarBuscarUsuarioComIDComEspacoEmBranco() {

		String ID_COM_ESPACO_VAZIO = " ";

		Response response =
				usuariosClient.buscarUsuarioPorID(ID_COM_ESPACO_VAZIO)
					.then()
						.extract()
						.response();

		assertAll(
				() -> assertEquals(400, response.getStatusCode()),
				() -> assertEquals(USUARIO_NAO_ENCONTRADO, response.jsonPath().getString("message")),
				() -> assertEquals("application/json; charset=utf-8", response.contentType())
		);
	}

}
