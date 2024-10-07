package com.vemser.rest.data.factory;

import com.vemser.rest.model.CarrinhosModel;
import com.vemser.rest.model.ProdutosModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarrinhosDataFactory {

	public static CarrinhosModel criarCarrinhoComProdutos(int quantidadeProdutos) {

		CarrinhosModel carrinho = new CarrinhosModel();
		List<ProdutosModel> produtos = new ArrayList<>();

		for (int i = 0; i < quantidadeProdutos; i++) {
			produtos.add(ProdutosDataFactory.produtoValido());
		}

		carrinho.setProdutos(produtos);
		carrinho.calcularTotal();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String dataAtual = sdf.format(new Date());
		carrinho.setDataCriacao(dataAtual);

		return carrinho;
	}
}
