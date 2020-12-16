LAPADOCCA.categoria = new Object();

$(document)
		.ready(
				function() {

					LAPADOCCA.categoria.cadastrar = function() {

						var categoria = new Object();

						categoria.descricao = document.frmAddCategoria.descricao.value;

						// console.log(categoria.descricao);

						if (categoria.descricao == "") {
							LAPADOCCA.exibirAviso("Preencha o campo nome!");
						} else {
							$
									.ajax({
										type : "POST",
										url : LAPADOCCA.PATH
												+ "categoria/inserir",
										data : JSON.stringify(categoria),
										success : function(msg) {
											LAPADOCCA.exibirAviso(msg);
											$("#addCategoria").trigger("reset");
											LAPADOCCA.categoria.buscar();
										},
										error : function(info) {
											LAPADOCCA
													.exibirAviso("Erro ao cadastrar uma nova categoria: "
															+ info.status
															+ " - "
															+ info.statusText);
										}
									})
						}

					}

					LAPADOCCA.categoria.buscar = function() {

						var valorBusca = $("#campoBuscaCategoria").val();

						$
								.ajax({

									type : "GET",
									url : LAPADOCCA.PATH
											+ "categoria/buscarCategoria",
									data : "valorBusca=" + valorBusca,
									success : function(dados) {

										dados = JSON.parse(dados);

										$("#listaCategorias").html(
												LAPADOCCA.categoria
														.exibir(dados));
									},

									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao consultar as categorias: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});
					};

					LAPADOCCA.categoria.exibir = function(listaDeCategorias) {

						var tabela = "<table><tr><th>Descrição</th><th class='acoes'>Ações</th></tr>";

						if (listaDeCategorias != undefined
								&& listaDeCategorias.length > 0) {
							for (var i = 0; i < listaDeCategorias.length; i++) {
								tabela += "<tr>"
										+ "<td>"
										+ listaDeCategorias[i].descricao
										+ "</td>"
										+ "<td>"
										+ "<a onclick=\"LAPADOCCA.categoria.exibirEdicao('"
										+ listaDeCategorias[i].idcategoria
										+ "')\" ><img src='../imgs/edit.png' alt='Edita'>  </a> "
										+ "<a onclick=\"LAPADOCCA.categoria.excluir('"
										+ listaDeCategorias[i].idcategoria
										+ "')\" ><img src='../imgs/delete.png' alt='Exclui'> </a> "
										+ "</td>" + "</tr>"

							}

						} else if (listaDeCategorias == "") {
							tabela += "<tr><td colspan='2'>Nenhum registro encontrado</td></tr>";
						}
						tabela += "</table>";

						return tabela;
					};
					// Busca as categorias ao iniciar a página
					LAPADOCCA.categoria.buscar();

					// exclui o produto selecionado
					LAPADOCCA.categoria.excluir = function(idcategoria) {

						
						/*if (confirm("Deseja realmente excluir a categoria?")) {
							
							$.ajax({
								type : "DELETE",
								url : LAPADOCCA.PATH
										+ "categoria/excluir/"
										+ idcategoria,
								success : function(msg) {
									LAPADOCCA.exibirAviso(msg);
									LAPADOCCA.categoria.buscar();
								},
								error : function(info) {
									LAPADOCCA
											.exibirAviso("Erro ao excluir a categoria: "
													+ info.status
													+ " - "
													+ info.statusText);
								}
							});
						}*/
						
						
						
						
						var mensagem = "Deseja realmente excluir essa categoria?";

						LAPADOCCA.exibirConfirmacao(mensagem, function(){

								$.ajax({
											type : "DELETE",
											url : LAPADOCCA.PATH
													+ "categoria/excluir/"
													+ idcategoria,
											success : function(msg) {
												LAPADOCCA.exibirAviso(msg);
												LAPADOCCA.categoria.buscar();
											},
											error : function(info) {
												LAPADOCCA
														.exibirAviso("Erro ao excluir a categoria: "
																+ info.status
																+ " - "
																+ info.statusText);
											}
										});

							
						});
				

					};

					LAPADOCCA.categoria.exibirEdicao = function(idcategoria) {

						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH
											+ "categoria/buscarPorId",
									data : "idcategoria=" + idcategoria, // enviando
									// no
									// data
									// uma
									// chave
									// chamada
									// idcategoria
									// tendo
									// como
									// valor
									// = a
									// variável
									// idcategoria;

									success : function(categoria) {
										// coloco 2 campos do formulario os seus
										// valores retornados
										document.frmEditaCategoria.idcategoria.value = categoria.idcategoria;
										document.frmEditaCategoria.descricao.value = categoria.descricao;

										var modalEditaCategoria = {
											tittle : "Editar Categoria",
											height : 200,
											width : 550,
											modal : true,
											buttons : {
												"Salvar" : function() {
													LAPADOCCA.categoria
															.editar();
												},
												"Cancelar" : function() {
													$(this).dialog("close");
												}
											},
											close : function() {

											}
										};

										$("#modalEditaCategoria").dialog(
												modalEditaCategoria);

									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar categoria para edição: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});
					};

					// Realiza a edição dos dados no BD
					LAPADOCCA.categoria.editar = function() {

						var categoria = new Object();// criação do objeto
						// categoria e
						// adicionamos nele os
						// dados do formulário.
						categoria.idcategoria = document.frmEditaCategoria.idcategoria.value;
						categoria.descricao = document.frmEditaCategoria.descricao.value;

						$
								.ajax({
									type : "PUT",
									url : LAPADOCCA.PATH + "categoria/alterar",
									data : JSON.stringify(categoria),
									success : function(msg) {

										LAPADOCCA.exibirAviso(msg);
										LAPADOCCA.categoria.buscar();
										$("#modalEditaCategoria").dialog(
												"close");
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar categoria para edição: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});

					};

				});// FIM
