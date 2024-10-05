package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class ListarTodosUsuariosTest {

	private final UsuariosClient usuariosClient = new UsuariosClient();

	@Test(description = "Cenário 01: Deve validar contrato de listar todos os usuários com sucesso")
	public void testDeveValidarContratoListarTodosUsuariosComSucesso() {

		usuariosClient.buscarTodosUsuarios()
			.then()
				.statusCode(HttpStatus.SC_OK)
				.body(matchesJsonSchemaInClasspath("schemas/usuarios/listar_todos_usuarios.json"));
	}

	@Test(description = "Cenário 02: Deve retornar 200 e listar todos os usuários com sucesso")
	public void testListarTodosUsuariosComSucesso() {

		usuariosClient.buscarTodosUsuarios()
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(200)
				.time(lessThan(3000L))
				.body("usuarios._id", notNullValue())
				.body("usuarios.email", everyItem(containsString("@")));
	}

}
