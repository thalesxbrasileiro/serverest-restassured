package com.vemser.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoCarrinhoRequestModel {

	private String idProduto;
	private int quantidade;
}
