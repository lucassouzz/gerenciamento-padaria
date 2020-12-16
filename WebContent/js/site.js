LAPADOCCA = new Object();

$(document)
		.ready(
				function() {

					LAPADOCCA.PATH = "/laPadocca-PI/rest/";

					$("header").load("/laPadocca-PI/pages/geral/header.html");
					$("footer").load("/laPadocca-PI/pages/geral/footer.html");

					LAPADOCCA.carregaPagina = function(pagename) {
						// Removeo conteúdo criado na abertura de uma janela
						// modal pelo JQUERYui
						if ($(".ui-dialog"))
							$(".ui-dialog").remove();
						// Limpa a tag serction, excluindo todo conteudo dentro
						// dela

						$("section").empty();
						
						$("section")
								.load(
										pagename + "/",
										function(response, status, info) {
											if (status == "error") {
												var msg = "Erro ao encontrar a página: "
														+ info.status
														+ " - "
														+ info.statusText;
												$("section").html(msg);
											}
										});
					}

					// Define as configurações base de uma modal de aviso
					LAPADOCCA.exibirAviso = function(aviso) {
						var modal = {
							title : "Mensagem",
							height : 200,
							width : 400,
							modal : true,
							buttons : {
								"OK" : function() {
									$(this).dialog("close");
								}
							}
						};
						$("#modalAviso").html(aviso);
						$("#modalAviso").dialog(modal);
					}

					LAPADOCCA.formatarDinheiro = function(preco) {
						return preco.toFixed(2).replace('.', ',').replace(
								/(\d)(?=(\d{3})+\,)/g, "$1.");
					}

					LAPADOCCA.exibirConfirmacao = function(mensagem, aoConfirmar) {
						var modal = {
							title : "Confirmar exclusão:",
							height : 200,
							width : 400,
							modal : true,
							buttons : {
								"Confirmar" : function() {
									$(this).dialog("close");
									aoConfirmar();
								},
								"Cancelar": function(){
									$(this).dialog("close");
									
								}
							}
						};
						$("#modalConfirmacao").html(mensagem);
						$("#modalConfirmacao").dialog(modal);
					}
			
				});// FIM
