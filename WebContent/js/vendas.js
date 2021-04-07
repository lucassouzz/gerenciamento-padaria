LAPADOCCA.vendas = new Object();

$(document)
		.ready(
				function() {

					LAPADOCCA.vendas.exibirPopup = function() {

						var modalNewRegistro = {
							title : "Novo item",
							// height : 200,
							width : 550,
							modal : true,
							buttons : {
								"Salvar" : function() {
									
									LAPADOCCA.vendas.salvarVenda();

								},
								"Cancelar" : function() {
									$(this).dialog("close");
								}
							},
							close : function() {

							}
						};

						$("#modalNewRegistro").dialog(modalNewRegistro);
						LAPADOCCA.vendas.inserirRegVenda();
						LAPADOCCA.vendas.produtoBuscar();

					}
					// -----------------------------------------------
					LAPADOCCA.vendas.inserirRegVenda = function() {

						var dadosVenda = new Object();

						dadosVenda.data = document.getElementById("hora").value;

						$
								.ajax({
									type : "POST",
									url : LAPADOCCA.PATH
											+ "vendas/inserirRegVenda",
									data : JSON.stringify(dadosVenda),

									success : function(jsonIdInserido) {
										var idVenda = jsonIdInserido.idReg;
										localStorage.setItem("idvenda", idVenda);
										
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao iniciar o registro da venda, tente novamente.");
									}
								});

					}
					// -----------------------------------------------
					LAPADOCCA.vendas.retornoHora = function() {

						var horario = new Date()

						var hora = horario.getHours();
						var minuto = horario.getMinutes();
						var segundos = horario.getSeconds();

						var dia = horario.getDate();
						var mes = horario.getMonth();
						var ano = horario.getFullYear();

						if (dia < 10) {
							dia = "0" + dia;
						}
						if (mes < 10) {
							mes = "0" + mes;
						}
						if (hora < 10) {
							hora = "0" + hora;
						}
						if (minuto < 10) {
							minuto = "0" + minuto;
						}
						if (segundos < 10) {
							segundos = "0" + segundos
						}

						formatoData = dia + " / " + mes + " / " + ano + " - ";
						formatoHora = hora + " : " + minuto + " : " + segundos;

						document.getElementById("data").innerHTML = formatoData;
						document.getElementById("hora").innerHTML = formatoHora;

						setTimeout("LAPADOCCA.vendas.retornoHora()", 1000);
					}
					// -----------------------------------------------
					window.onload = LAPADOCCA.vendas.retornoHora();
					// -----------------------------------------------
					LAPADOCCA.vendas.popularSelect = function(select, dados) {

						var select = select;
						var dados = dados;

						// console.log(select);
						// console.log(dados);

						if (dados != "") {
							for (var i = 0; i < 3; i++) {

								$(select).html("");
								var option = document.createElement("option");
								option.setAttribute("value", "");
								option.innerHTML = ("Escolha o produto a ser vendido");

								$(select).append(option);

								for (var i = 0; i < dados.length; i++) {

									var option = document
											.createElement("option");
									option.setAttribute("value",
											dados[i].idproduto);
									option.innerHTML = (dados[i].descricao);
									$(select).append(option);

								}

							}

						}
					}
					// -----------------------------------------------
					LAPADOCCA.vendas.newTest = function() {

						var idprod = document.getElementById("selProduto").value;
						
						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH + "vendas/dadosProd",
									data : "idprod=" + idprod, // chave
																// recebendo o
																// valor do id
																// da minha
																// escolha no
																// select
									success : function(dados) {
										dados = JSON.parse(dados);
										//console.log(dados);
										document.getElementById("precoProd").value = "R$ "
												+ dados.preco;
										document
												.getElementById("qntDisponivel").value = dados.quantidade;
									},
									error : function(info) {
										LAPADOCCA.exibirAviso("Erro: "
												+ info.status + " - "
												+ info.statusText);
									}
								})
					}
					// -----------------------------------------------
					LAPADOCCA.vendas.produtoBuscar = function() {

						$.ajax({
							type : "GET",
							url : LAPADOCCA.PATH + "vendas/buscar",

							success : function(dados) {

								var dados = JSON.parse(dados);

								select = "#selProduto";
								LAPADOCCA.vendas.popularSelect(select, dados);

							},
							error : function(info) {
								LAPADOCCA.exibirAviso("Erro ao buscar produtos: " + info.status
										+ " - " + info.statusText);
							}

						});
					};
					// -----------------------------------------------
					LAPADOCCA.vendas.salvarVenda = function() {
						
						var dadosSalvarVenda = new Object();
						
						dadosSalvarVenda.idvenda = localStorage.getItem("idvenda");
						dadosSalvarVenda.idprod= document.getElementById("selProduto").value;
						dadosSalvarVenda.qnt = document.getElementById("qnt").value;
						//console.log(dadosSalvarVenda)
						$.ajax({
							type: "POST",
							url: LAPADOCCA.PATH + "vendas/salvarVenda",
							data: JSON.stringify(dadosSalvarVenda),
							
							success: function(msg){
								LAPADOCCA.exibirAviso(msg);
								
								$("#modalNewRegistro").dialog("close");
							},
							error: function(){
								LAPADOCCA.exibirAviso("Erro ao salvar venda: " + info.status
										+ " - " + info.statusText);
							}
							
						});
						
					};
					
					LAPADOCCA.vendas.buscarListaVHP = function(){
						
						$.ajax({
							type:"GET",
							url: LAPADOCCA.PATH + "vendas/buscarLista",
							success: function (dados){
								
								dados = JSON.parse(dados);
								console.log(dados);
								$("#VHP").html(LAPADOCCA.vendas.exibirListaVHP(dados));
							},
							error: function(){
								LAPADOCCA.exibirAviso("Erro ao buscar produtos")
							}
							
							
						});
					};
					LAPADOCCA.vendas.buscarListaVHP();
					
					LAPADOCCA.vendas.exibirListaVHP = function(json){
						var tabela = "<table><tr><th>Descrição</th><th>Quantidade</th><th>Preço</th><th class='acoes'>Ações</th></tr>";
						
						var quantidade;
						var preco;
						
						var total = Number(0);
						if (json != undefined
								&& json.length > 0) {
							for (var i = 0; i < json.length; i++) {
								tabela += "<tr>"
										+ "<td>"
										+ json[i].descricao
										+ "</td>"
										+ "<td>"
										+ json[i].quantidade
										+ "</td>"
										+ "<td>"
										+ json[i].preco
										+ "</td>"
										+ "<td>"
										+ "<a onclick=\"LAPADOCCA.produto.exibirEdicao('"
										+ json[i].idproduto
										+ "')\" ><img src='../imgs/edit.png' alt='Edita'>  </a> "
										+ "<a onclick=\"LAPADOCCA.produto.excluir('"
										+ json[i].idproduto
										+ "')\" ><img src='../imgs/delete.png' alt='Exclui'> </a> "
										+ "</td>" + "</tr>"
										
//										quantidade= parseFloat(json[i].quantidade);
//										preco = parseFloat(json[i].preco);
//										console.log(quantidade);
//										console.log(preco);
//										console.log(preco*quantidade);
										total+= parseFloat(json[i].preco)*parseFloat(json[i].quantidade);
										//console.log(total.toFixed(2));
										
							}
						} else if (json == "") {
							tabela += "<tr><td colspan='2'>Nenhum registro encontrado</td></tr>";
						}
						tabela += "<tr><td>Valor total</td><td>R$"+total.toFixed(2)+"</td></tr></table>";
						
						return tabela;
					};
					
					
					
					
					
					
					
					
					
					
				});//end
