//alert("chegou produto.js");

LAPADOCCA.produto = new Object();

$(document)
		.ready(
				function() {

					// Carrega as categorias registradas no BD no select do
					// formulário
					// de inserir
					LAPADOCCA.produto.carregarCategorias = function(idcategoria) {
						if (idcategoria != undefined) {
							select = "#selCategoriaEdicao";
						} else {
							select = "#selCategoria";
						}

						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH + "categoria/buscar",
									success : function(categorias) {

										if (categorias != "") {

											$(select).html("");

											var option = document
													.createElement("option");
											option.setAttribute("value", "");
											option.innerHTML = ("Escolha");
											$(select).append(option);

											for (var i = 0; i < categorias.length; i++) {

												var option = document
														.createElement("option");
												option
														.setAttribute(
																"value",
																categorias[i].idcategoria);

												if ((idcategoria!=undefined)&&(idcategoria==categorias[i].idcategoria)) 
													option.setAttribute("selected", "selected");
												
												option.innerHTML = (categorias[i].descricao);
												$(select).append(option);

											}

										} else {

											$(select).html("");

											var option = document
													.createElement("option");
											option.setAttribute("value", "");
											option.innerHTML = ("Cadastre uma categoria primeiro!");
											$(select).append(option);
											$(select).addClass("aviso");
										}
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar as categorias: "
														+ info.status
														+ " - "
														+ info.statusText);

										$(select).html("");
										var option = document
												.createElement("option");
										option.setAttribute("value", "");
										option.innerHTML = ("Erro ao carregar categorias!");
										$(select).append(option);
										$(select).addClass("aviso");
									}
								});

					}

					LAPADOCCA.produto.carregarCategorias();

					// Cadastra no BD o produto informado
					LAPADOCCA.produto.cadastrar = function() {

						var produto = new Object();

						produto.idcategoria = document.frmAddProduto.idcategoria.value;
						produto.descricao = document.frmAddProduto.descricao.value;
						produto.quantidade = document.frmAddProduto.quantidade.value;
						produto.preco = document.frmAddProduto.preco.value;

						if ((produto.descricao == "")
								|| (produto.quantidade == "")
								|| (produto.preco == "")) {
							LAPADOCCA.exibirAviso("Preencha todos os campos!");
						} else {

							$
									.ajax({
										type : "POST",
										url : LAPADOCCA.PATH
												+ "produto/inserir",
										data : JSON.stringify(produto),// trasformando
										// objeto
										// produto
										// em
										// String
										// formato
										// JSON
										success : function(msg) {
											LAPADOCCA.exibirAviso(msg);
											$("#addProduto").trigger("reset");// utilizando
											// comando
											// jQuery
											// trigger
											// p/
											// executar
											// o
											// reset
											LAPADOCCA.produto.buscar();
										},
										error : function(info) {
											LAPADOCCA
													.exibirAviso("Erro ao cadastrar um novo produto: "
															+ info.status
															+ " - "
															+ info.statusText);
										}
									});

						}
					}

					LAPADOCCA.produto.buscar = function() {

						var valorBusca = $("#campoBuscaProduto").val();

						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH + "produto/buscar",
									data : "valorBusca=" + valorBusca,// chave
									// chamada
									// valorBusca
									// tendo
									// como
									// valor
									// a
									// variável
									// valorBusca;
									success : function(dados) {

										dados = JSON.parse(dados);
										console.log(dados)
										$("#listaProdutos")
												.html(
														LAPADOCCA.produto
																.exibir(dados));

									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao consultar os produtos: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});
					};

					LAPADOCCA.produto.exibir = function(listaProdutos) {

						var tabela = "<table><tr><th>Descrição</th><th>Quantidade</th><th>Preço</th><th class='acoes'>Ações</th></tr>";

						if (listaProdutos != undefined
								&& listaProdutos.length > 0) {
							for (var i = 0; i < listaProdutos.length; i++) {
								tabela += "<tr>"
										+ "<td>"
										+ listaProdutos[i].descricao
										+ "</td>"
										+ "<td>"
										+ listaProdutos[i].quantidade
										+ "</td>"
										+ "<td>"
										+ LAPADOCCA
												.formatarDinheiro(listaProdutos[i].preco)
										+ "</td>"
										+ "<td>"
										+ "<a onclick=\"LAPADOCCA.produto.exibirEdicao('"
										+ listaProdutos[i].idproduto
										+ "')\" ><img src='../imgs/edit.png' alt='Edita'>  </a> "
										+ "<a onclick=\"LAPADOCCA.produto.excluir('"
										+ listaProdutos[i].idproduto
										+ "')\" ><img src='../imgs/delete.png' alt='Exclui'> </a> "
										+ "</td>" + "</tr>"
							}
						} else if (listaProdutos == "") {
							tabela += "<tr><td colspan='2'>Nenhum registro encontrado</td></tr>";
						}
						tabela += "</table>";

						return tabela;
					};

					
					LAPADOCCA.produto.buscar();

					LAPADOCCA.produto.excluir = function(idproduto) {
					
						var mensagem = "Deseja realmente excluir esse produto?";
						
						LAPADOCCA.exibirConfirmacao(mensagem, function(){
							
						$.ajax({
									type : "DELETE",
									url : LAPADOCCA.PATH + "produto/excluir/"
											+ idproduto,
									success : function(msg) {
										LAPADOCCA.exibirAviso(msg);
										LAPADOCCA.produto.buscar();
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao excluir o produto: "
														+ info.status
														+ " - "
														+ info.statusText);
									}

								});
						});
					};

					LAPADOCCA.produto.exibirEdicao = function(idproduto) {

						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH
											+ "produto/buscarPorId",
									data : "idproduto=" + idproduto,
									success : function(produto) {

										//console.log(produto);

										document.frmEditaProduto.idproduto.value = produto.idproduto;
										document.frmEditaProduto.idcategoria.value = produto.idcategoria;
										document.frmEditaProduto.descricao.value = produto.descricao;
										document.frmEditaProduto.quantidade.value = produto.quantidade;
										document.frmEditaProduto.preco.value = produto.preco;

										LAPADOCCA.produto
												.carregarCategorias(produto.idcategoria);

										var modalEditaProduto = {
											title : "Editar Produto",
											height : 300,
											width : 450,
											modal : true,
											buttons : {
												"Salvar" : function() {
													LAPADOCCA.produto.editar();
												},
												"Cancelar" : function() {
													$(this).dialog("close");
												}
											},
											close : function() {

											}
										};

										$("#modalEditaProduto").dialog(
												modalEditaProduto);

									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar produto para edição: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});

					};
					
					LAPADOCCA.produto.editar = function(){
						
						var produto = new Object();
						
						produto.idproduto = document.frmEditaProduto.idproduto.value;
						produto.idcategoria = document.frmEditaProduto.idcategoria.value;
						produto.descricao = document.frmEditaProduto.descricao.value;
						produto.quantidade = document.frmEditaProduto.quantidade.value;
						produto.preco = document.frmEditaProduto.preco.value;
						
						$.ajax({
							type:"PUT",
							url: LAPADOCCA.PATH + "produto/alterar",
							data: JSON.stringify(produto),
							success: function(msg){
								LAPADOCCA.exibirAviso(msg);
								LAPADOCCA.produto.buscar();
								$("#modalEditaProduto").dialog("close");
							},
							error:function(info) {
								LAPADOCCA
								.exibirAviso("Erro ao editar produto: "
										+ info.status
										+ " - "
										+ info.statusText);
					}
						});
					};
				});// fim
