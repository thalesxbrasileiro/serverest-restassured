package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.ProdutosDataFactory;
import org.testng.annotations.DataProvider;

public class ProdutosDataProvider {

	// CHAVES
	private static final String KEY_NOME = "nome";
	private static final String KEY_PRECO = "preco";
	private static final String KEY_DESCRICAO = "descricao";
	private static final String KEY_QUANTIDADE = "quantidade";

	// VALORES
	private static final String VALUE_NOME_EM_BRANCO = "nome não pode ficar em branco";
	private static final String VALUE_PRECO_EM_BRANCO = "preco deve ser um número";
	private static final String VALUE_DESCRICAO_EM_BRANCO = "descricao não pode ficar em branco";
	private static final String VALUE_QUANTIDADE_EM_BRANCO = "quantidade deve ser um número";

	@DataProvider(name = "produtoDataProvider")
	public static Object[][] produtoDataProvider() {
		return new Object[][] {
				{ ProdutosDataFactory.produtoSemNome(), KEY_NOME, VALUE_NOME_EM_BRANCO },
				{ ProdutosDataFactory.produtoSemPreco(), KEY_PRECO, VALUE_PRECO_EM_BRANCO },
				{ ProdutosDataFactory.produtoSemDescricao(), KEY_DESCRICAO, VALUE_DESCRICAO_EM_BRANCO },
				{ ProdutosDataFactory.produtoSemQuantidade(), KEY_QUANTIDADE, VALUE_QUANTIDADE_EM_BRANCO }
		};
	}

}

