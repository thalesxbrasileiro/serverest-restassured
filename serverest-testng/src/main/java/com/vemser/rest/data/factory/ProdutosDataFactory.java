package com.vemser.rest.data.factory;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.model.ProdutosModel;
import io.restassured.response.Response;
import net.datafaker.Faker;

import java.util.Locale;

public class ProdutosDataFactory {

	private static Faker faker = new Faker(new Locale("PT-BR"));
	private static final ProdutosClient produtosClient = new ProdutosClient();

	private static ProdutosModel novoProduto() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome(faker.commerce().productName());
		produto.setPreco(faker.number().numberBetween(1, 5000));
		produto.setDescricao(faker.commerce().material());
		produto.setQuantidade(faker.number().numberBetween(1, 200));

		return produto;
	}

	public static ProdutosModel produtoValido() {
		return novoProduto();
	}

	public static ProdutosModel produtoSemNome() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome("");
		produto.setPreco(faker.number().numberBetween(1, 5000));
		produto.setDescricao(faker.commerce().material());
		produto.setQuantidade(faker.number().numberBetween(1, 200));

		return produto;
	}

	public static ProdutosModel produtoSemPreco() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome(faker.commerce().productName());
		produto.setPreco(null);
		produto.setDescricao(faker.commerce().material());
		produto.setQuantidade(faker.number().numberBetween(1, 200));

		return produto;
	}

	public static ProdutosModel produtoSemDescricao() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome(faker.commerce().productName());
		produto.setPreco(faker.number().numberBetween(1, 5000));
		produto.setDescricao("");
		produto.setQuantidade(faker.number().numberBetween(1, 200));

		return produto;
	}

	public static ProdutosModel produtoSemQuantidade() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome(faker.commerce().productName());
		produto.setPreco(faker.number().numberBetween(1, 5000));
		produto.setDescricao(faker.commerce().material());
		produto.setQuantidade(null);

		return produto;
	}

	public static ProdutosModel produtoJaCadastrado() {

		ProdutosModel produto = new ProdutosModel();
		produto.setNome("Logitech MX Vertical");
		produto.setPreco(faker.number().numberBetween(1, 5000));
		produto.setDescricao(faker.commerce().material());
		produto.setQuantidade(faker.number().numberBetween(1, 200));

		return produto;
	}

	public static Response buscarTodosProdutosERetornarResposta() {
		return produtosClient.buscarTodosProdutos()
				.then()
				.extract()
				.response();
	}

	public static String buscarPrimeiroProdutoId() {
		Response response = buscarTodosProdutosERetornarResposta();
		return response.path("produtos[0]._id");
	}

}
