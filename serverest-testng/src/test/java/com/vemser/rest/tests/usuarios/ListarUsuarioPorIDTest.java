package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import com.vemser.rest.model.UsuariosResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ListarUsuarioPorIDTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();
	private SoftAssert softAssert;

	private final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	UsuariosModel usuario;
	String usuarioId;

	@BeforeMethod
	public void setUp() {
		softAssert = new SoftAssert();

		usuario = UsuariosDataFactory.usuarioValido();

		usuarioId = usuariosClient.cadastrarUsuarios(usuario)
				.then()
					.statusCode(HttpStatus.SC_CREATED)
					.extract()
						.path("_id");
	}

	@AfterMethod
	public void excluirUsuarioAoFinalDaRequisicao() {
		if (usuarioId != null) {
			usuariosClient.excluirUsuarioDoBanco(usuarioId);
		}
	}

	@Test(groups = "contrato", description = "CT-001: Deve validar contrato de listar usuários por ID com sucesso")
	public void testDeveValidarContratoListarUsuariosPorIDComSucesso() {

		usuariosClient.buscarUsuarioPorID(usuarioId)
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/usuarios/listar_usuarios_por_id.json"));
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-002: Deve retornar 200 e listar usuários por nome com sucesso")
	public void testListarUsuariosPorNomeComSucesso() {

		usuariosClient.buscarUsuarioPorNome(usuario.getNome())
			.then()
				.statusCode(200)
				.body("usuarios[0].nome", equalTo(usuario.getNome()));
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-003: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando Hamcrest")
	public void testBuscarUsuarioPorIDComSucessoHamcrest() {

		usuariosClient.buscarUsuarioPorID(usuarioId)
			.then()
				.statusCode(200)
				.time(lessThan(2000L))
				.body("_id", notNullValue())
				.body("nome", equalTo(usuario.getNome()));

		assertThat("_id", notNullValue());
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-004: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando Assertions")
	public void testBuscarUsuarioPorIDComSucessoAssertions() {

		Response usuarioResponse =
				usuariosClient.buscarUsuarioPorID(usuarioId)
					.then()
						.statusCode(200)
						.extract()
						.response();

		String nomeResponse = usuarioResponse.path("nome");
		String emailResponse = usuarioResponse.path("email");

		Assert.assertEquals(usuario.getNome(), nomeResponse);
		Assert.assertEquals(usuario.getEmail(), emailResponse);
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-005: Deve retornar 200 ao buscar usuário por ID com sucesso utilizando AssertAll")
	public void testBuscarUsuarioPorIDComAssertAll() {

		UsuariosResponse response =
				usuariosClient.buscarUsuarioPorID(usuarioId)
					.then()
						.header("Content-Type", "application/json; charset=utf-8")
						.statusCode(200)
						.extract()
						.as(UsuariosResponse.class);

		softAssert.assertEquals(usuario.getNome(), response.getNome(), "O nome não corresponde ao esperado");
		softAssert.assertEquals(usuario.getEmail(), response.getEmail(), "O e-mail não corresponde ao esperado");
		softAssert.assertNotNull(response.getPassword(), "O password não deve ser nulo");
		softAssert.assertAll();
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-006: Deve retornar 400 ao tentar buscar usuário com ID inválido")
	public void testTentarBuscarUsuarioComIDInvalido() {

		String ID_INVALIDO = "idInvalido";

		Response response =
				usuariosClient.buscarUsuarioPorID(ID_INVALIDO)
					.then()
						.extract()
						.response();

		softAssert.assertEquals(400, response.getStatusCode());
		softAssert.assertEquals(USUARIO_NAO_ENCONTRADO, response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

	@Test(dependsOnGroups = "contrato", groups = "funcional", description = "CT-007: Deve retornar 400 ao tentar buscar usuário com ID com espaço em branco")
	public void testTentarBuscarUsuarioComIDComEspacoEmBranco() {

		String ID_COM_ESPACO_VAZIO = " ";

		Response response =
				usuariosClient.buscarUsuarioPorID(ID_COM_ESPACO_VAZIO)
						.then()
						.extract()
							.response();

		softAssert.assertEquals(400, response.getStatusCode());
		softAssert.assertEquals(USUARIO_NAO_ENCONTRADO, response.jsonPath().getString("message"));
		softAssert.assertEquals("application/json; charset=utf-8", response.contentType());
		softAssert.assertAll();
	}

}
