package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.LoginDataFactory;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.LoginModel;
import com.vemser.rest.model.ProdutosModel;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class ExcluirProdutoTest {

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

	@Test(description = "CT-001: Deve retornar 200 ao excluir produto com sucesso")
	public void testExcluirProdutoComSucesso() {

		ProdutosModel produto = ProdutosDataFactory.produtoValido();

		String idProduto =
				produtosClient.cadastrarProduto(produto, token)
					.then()
						.statusCode(201)
						.extract()
							.path("_id");

		produtosClient.excluirProduto(idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(200)
				.body("message", equalTo("Registro excluído com sucesso"));
	}

	@Test(description = "CT-002: Deve retornar 401 ao tentar excluir produto com token expirado")
	public void testTentarExcluirProdutoComTokenExpirado() {

		String id = "BeeJh5lz3k6kSIzA";
		String tokenExpirado = "BeeJh5lz3k6kSIzA";

		produtosClient.excluirProduto(id, tokenExpirado)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(401)
				.body("message", equalTo("Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"));
	}

	@Test(description = "CT-003: Deve retornar 405 ao tentar excluir produto sem fornecer ID")
	public void testTentarExcluirProdutoSemFornecerID() {

		String idProduto = "";

		produtosClient.excluirProduto(idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(405);
	}

	@Test(description = "CT-004: Deve retornar 401 ao tentar excluir produto com ID em branco")
	public void testTentarExcluirProdutoComIDEmBranco() {

		String idProduto = StringUtils.EMPTY;

		produtosClient.excluirProduto(idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(405)
				.body("message", equalTo("Não é possível realizar DELETE em /produtos/. Acesse https://serverest.dev para ver as rotas disponíveis e como utilizá-las."));
	}

	@Test(description = "CT-005: Deve retornar 400 ao tentar excluir produto que faz parte de carrinho")
	public void testTentarExcluirProdutoQueFazParteDoCarrinho() {

		String idProduto = "K6leHdftCeOJj8BJ";

		produtosClient.excluirProduto(idProduto, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(400)
				.body("message", equalTo("Não é permitido excluir produto que faz parte de carrinho"));
	}

	@Test(description = "CT-006: Deve retornar 403 ao tentar excluir produto sem ser administrador")
	public void testTentarExcluirProdutoSemSerAdministrador() {

		String token =
				loginClient.realizarLogin(LoginDataFactory.loginUsuarioNaoAdmin())
					.then()
						.extract()
							.path("authorization");

		String idUsuarioNaoAdmin = UsuariosDataFactory.buscarPrimeiroUsuarioNaoAdminId();

		produtosClient.excluirProduto(idUsuarioNaoAdmin, token)
			.then()
				.header("Content-Type", "application/json; charset=utf-8")
				.statusCode(403)
				.body("message", equalTo("Rota exclusiva para administradores"));
	}

}
