package com.vemser.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhosModel {

	private String id;
	private List<ProdutosModel> produtos;
	private Integer total;
	private String dataCriacao;

	public void calcularTotal() {
		this.total = produtos.stream()
				.mapToInt(produto -> produto.getPreco() * produto.getQuantidade())
				.sum();
	}

}