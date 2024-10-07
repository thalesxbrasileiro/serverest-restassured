package com.vemser.rest.tests.carrinhos;

import com.vemser.rest.client.CarrinhosClient;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@DisplayName("Testes de listar todos os carrinhos")
public class ListarTodosCarrinhosTest {

	private final CarrinhosClient carrinhosClient = new CarrinhosClient();

	@Test
	@DisplayName("CT-001: Deve validar contrato de listar todos os carrinhos com sucesso")
	public void testDeveValidarContratoListarTodosCarrinhosComSucesso() {

		carrinhosClient.BuscarTodosCarrinhos()
				.then()
					.header("Content-Type", "application/json; charset=utf-8")
					.statusCode(HttpStatus.SC_OK)
					.body(matchesJsonSchemaInClasspath("schemas/carrinhos/listar_todos_carrinhos.json"));
		;
	}

	@Test
	@DisplayName("CT-002: Deve retornar 200 quando listar todos os carrinhos com sucesso")
	public void testListarTodosCarrinhosComSucesso() {

		carrinhosClient.BuscarTodosCarrinhos()
				.then()
					.header("Content-Type", "application/json; charset=utf-8")
					.statusCode(HttpStatus.SC_OK);
	}
}
