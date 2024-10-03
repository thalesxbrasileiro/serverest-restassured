package com.vemser.rest.client;

import com.vemser.rest.model.UsuariosModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UsuariosClient extends BaseClient {

	private final String USUARIOS = "/usuarios";

	public Response cadastrarUsuarios(UsuariosModel usuario) {

		return
				given()
						.spec(super.set())
						.contentType(ContentType.JSON)
						.body(usuario)
				.when()
						.post(USUARIOS)
				;
	}

	public Response buscarTodosUsuarios() {

		return
				given()
						.spec(super.set())
				.when()
						.get(USUARIOS)
				;
	}

	public Response buscarUsuarioPorID(String id) {

		return
				given()
						.spec(super.set())
						.pathParam("id", id)
				.when()
						.get(USUARIOS + "/{id}")
				;
	}

	public Response buscarUsuarioPorNome(String nome) {

		return
				given()
						.spec(super.set())
						.queryParam("nome", nome)
				.when()
						.get(USUARIOS)
				;
	}

	public Response excluirUsuario(String id) {

		return
				given()
						.spec(super.set())
						.pathParam("id", id)
				.when()
						.delete(USUARIOS + "/{id}");
	}

	public void excluirUsuarioDoBanco(String id) {

		given()
				.spec(super.set())
				.pathParam("id", id)
		.when()
				.delete(USUARIOS + "/{id}");
	}

}
