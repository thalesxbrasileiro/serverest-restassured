package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.model.LoginModel;
import com.vemser.rest.model.ProdutosModel;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class AtualizarProdutosTest {

	private final LoginClient loginClient = new LoginClient();

	private final ProdutosClient produtosClient = new ProdutosClient();

	private String token;

	@BeforeMethod
	public void setUp() {
		LoginModel login = LoginDataFactory.loginValido();

		token =
				loginClient.realizarLogin(login)
					.then()
						.extract()
						.path("authorization");
	}

	@Test(description = "CT-001: Deve retornar 200 ao atualizar produto com sucesso")
	public void testAtualizarProdutoComSucesso() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();
		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		produtosClient.atualizarProduto(produto, idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(200)
				.body("message", equalTo("Registro alterado com sucesso"));
	}

	@Test(description = "CT-002: Deve retornar 400 ao tentar atualizar produto sem informar descrição")
	public void testTentarAtualizarProdutoSemInformarDescricao() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();
		ProdutosModel produto = ProdutosDataFactory.produtoSemDescricao();

		produtosClient.atualizarProduto(produto, idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("descricao", equalTo("descricao não pode ficar em branco"));
	}

	@Test(description = "CT-003: Deve retornar 401 ao tentar atualizar produto sem estar autenticado")
	public void testTentarAtualizarProdutoSemEstarAutenticado() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();
		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		produtosClient.atualizarProduto(produto, idProduto, "")
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
	}

	@Test(description = "CT-005: Deve retornar 400 ao tentar atualizar produto já existente")
	public void testTentarAtualizarProdutoJaExistente() {

		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();
		ProdutosModel produto = ProdutosDataFactory.produtoJaCadastrado();

		produtosClient.atualizarProduto(produto, idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Já existe produto com esse nome"));
	}

	@Test(description = "CT-002: Deve retornar 403 ao tentar atualizar produto sem ser administrador")
	public void testTentarAtualizarProdutoSemSerAdministrador() {

		LoginModel login = LoginDataFactory.loginUsuarioNaoAdmin();

		String token =
				loginClient.realizarLogin(login)
					.then()
						.extract()
						.path("authorization");

		ProdutosModel produto = ProdutosDataFactory.produtoValido();
		String idProduto = ProdutosDataFactory.buscarPrimeiroProdutoId();

		produtosClient.atualizarProduto(produto, idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(403)
				.body("message", equalTo("Rota exclusiva para administradores"));
	}

}
