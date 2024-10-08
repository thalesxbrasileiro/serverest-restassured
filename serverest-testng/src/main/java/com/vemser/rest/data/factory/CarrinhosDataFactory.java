package com.vemser.rest.data.factory;

import com.vemser.rest.model.CarrinhosModel;
import com.vemser.rest.model.ProdutoCarrinhoRequestModel;
import net.datafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarrinhosDataFactory {

	private static Faker faker = new Faker(new Locale("PT-BR"));

	public static CarrinhosModel carrinhoComProdutosValido() {
		return criarCarrinhoComProdutos(
				List.of("BeeJh5lz3k6kSIzA"),
				List.of(1)
		);
	}

	public static CarrinhosModel carrinhoComProdutosInvalido() {
		return criarCarrinhoComProdutos(
				List.of("IdProdutoInvalido"),
				List.of(faker.number().numberBetween(1, 50))
		);
	}

	public static CarrinhosModel carrinhoComProdutosDuplicado() {
		return criarCarrinhoComProdutos(
				List.of("BeeJh5lz3k6kSIzA", "BeeJh5lz3k6kSIzA"),
				List.of(1, 1)
		);
	}

	public static CarrinhosModel carrinhoComProdutoComQtdInsuficiente() {
		return criarCarrinhoComProdutos(
				List.of("BeeJh5lz3k6kSIzA"),
				List.of(faker.number().numberBetween(10000, 15000))
		);
	}

	public static CarrinhosModel criarCarrinhoComProdutos(List<String> idsProdutos, List<Integer> quantidades) {

		CarrinhosModel carrinho = new CarrinhosModel();

		List<ProdutoCarrinhoRequestModel> produtos = new ArrayList<>();

		for (int i = 0; i < idsProdutos.size(); i++) {

			ProdutoCarrinhoRequestModel produto = new ProdutoCarrinhoRequestModel();

			produto.setIdProduto(idsProdutos.get(i));
			produto.setQuantidade(quantidades.get(i));
			produtos.add(produto);
		}

		carrinho.setProdutos(produtos);

		return carrinho;
	}

}
