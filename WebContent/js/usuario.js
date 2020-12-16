LAPADOCCA.usuario = new Object();

$(document)
		.ready(
				function() {

					LAPADOCCA.usuario.buscar = function() {

						var valorBusca = $("#campoBuscaUsuario").val();

						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH + "usuario/buscar",
									data : "valorBusca=" + valorBusca,
									success : function(dados) {

										dados = JSON.parse(dados);
										// console.log(dados);

										$("#listaUsuarios")
												.html(
														LAPADOCCA.usuario
																.exibir(dados));

									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar usuários cadastrados: "
														+ info.status
														+ " - "
														+ info.statusText);

									}

								});

					}

					LAPADOCCA.usuario.exibir = function(listaUsuarios) {

						var tabela = "<table>" + "<tr>" + "<th>E-mail</th>"
								+ "<th>Senha</th>"
								+ "<th class='acoes'>Ações</th>" + "</tr>";

						if (listaUsuarios != undefined
								&& listaUsuarios.length > 0) {
							// console.log(listaUsuarios);
							for (var i = 0; i < listaUsuarios.length; i++) {
								tabela += "<tr>"
										+ "<td>"
										+ listaUsuarios[i].email
										+ "</td>"
										+ "<td>"
										+ listaUsuarios[i].senha
										+ "</td>"
										+ "<td>"
										+ "<a onclick=\"LAPADOCCA.usuario.exibirEdicao('"
										+ listaUsuarios[i].idusuario
										+ "')\" ><img src='../imgs/edit.png' alt='Edita'>  </a> "
										+ "<a onclick=\"LAPADOCCA.usuario.excluir('"
										+ listaUsuarios[i].idusuario
										+ "')\" ><img src='../imgs/delete.png' alt='Exclui'> </a> "
										+ "</td>" + "</tr>"
							}

						} else if (listaUsuarios == "") {
							tabela += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
						}
						tabela += "</tabela>";

						return tabela;
					}

					LAPADOCCA.usuario.buscar()

					LAPADOCCA.usuario.cadastrar = function() {

						var usuario = new Object();

						usuario.email = document.frmAddUsuario.email.value;
						usuario.senha = document.frmAddUsuario.senha.value;
						usuario.repetirSenha = document.frmAddUsuario.repetirSenha.value;

						var verificaCondicao = false;

						if (LAPADOCCA.usuario.validaEmail() == false) {

							LAPADOCCA
									.exibirAviso("Email inserido fora do padrão");
							document.frmAddUsuario.email.focus();

							verificaCondicao = true;
						}
						if (usuario.senha != usuario.repetirSenha) {
							LAPADOCCA
									.exibirAviso("Senhas inseridas não conferem");
							document.frmAddUsuario.senha.focus();

							verificaCondicao = true;
						}
						if (verificaCondicao == false) {
							$
									.ajax({

										type : "POST",
										url : LAPADOCCA.PATH
												+ "usuario/inserir",
										data : JSON.stringify(usuario),

										success : function(msg) {
											LAPADOCCA.exibirAviso(msg);
											$("#addUsuario").trigger("reset");
											LAPADOCCA.usuario.buscar();
										},
										error : function(info) {
											LAPADOCCA
													.exibirAviso("Erro ao cadastrar um novo usuário: "
															+ info.status
															+ " - "
															+ info.statusText);
										}

									});
						}

					}

					LAPADOCCA.usuario.excluir = function(idusuario) {
						
						var mensagem = "Deseja realmente excluir esse usuário?";

						LAPADOCCA.exibirConfirmacao(mensagem, function() {
							
											$.ajax({
														type : "DELETE",
														url : LAPADOCCA.PATH
																+ "usuario/excluir/"
																+ idusuario,
														success : function(msg) {
															LAPADOCCA.exibirAviso(msg);
															LAPADOCCA.usuario.buscar();
														},
														error : function(info) {
															LAPADOCCA
																	.exibirAviso("Erro ao excluir o usuário: "
																			+ info.status
																			+ " - "
																			+ info.statusText);
														}
													});
										});
					};

					LAPADOCCA.usuario.exibirEdicao = function(idusuario) {
						$
								.ajax({
									type : "GET",
									url : LAPADOCCA.PATH
											+ "usuario/buscarPorId",
									data : "idusuario=" + idusuario,
									success : function(usuario) {

										document.frmEditaUsuario.idusuario.value = usuario.idusuario;
										document.frmEditaUsuario.email.value = usuario.email;
										document.frmEditaUsuario.senha.value = usuario.senha;

										var modalEditaUsuario = {
											title : "Editar Usuário",
											height : 300,
											width : 450,
											modal : true,
											buttons : {
												"Salvar" : function() {
													LAPADOCCA.usuario.editar()
												},
												"Cancelar" : function() {
													$(this).dialog("close");
												}
											},
											close : function() {

											}
										};

										$("#modalEditaUsuario").dialog(
												modalEditaUsuario);
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao buscar usuário para edição: "
														+ info.status
														+ " - "
														+ info.statusText);
									}

								});
					}

					LAPADOCCA.usuario.editar = function() {

						var usuario = new Object();

						usuario.idusuario = document.frmEditaUsuario.idusuario.value;
						usuario.email = document.frmEditaUsuario.email.value;
						usuario.senha = document.frmEditaUsuario.senha.value;

						$
								.ajax({
									type : "PUT",
									url : LAPADOCCA.PATH + "usuario/alterar",
									data : JSON.stringify(usuario),
									success : function(msg) {
										LAPADOCCA.exibirAviso(msg);
										LAPADOCCA.usuario.buscar();
										$("#modalEditaUsuario").dialog("close");
									},
									error : function(info) {
										LAPADOCCA
												.exibirAviso("Erro ao editar dados do usuário: "
														+ info.status
														+ " - "
														+ info.statusText);
									}
								});
					}

					LAPADOCCA.usuario.validaEmail = function() {

						var email = document.frmAddUsuario.email.value;
						var expRegEmail = new RegExp(
								"^([0-9a-zA-Z]+([_.-]?[0-9a-zA-Z]+)*@[0-9a-zA-Z]+[0-9,a-z,A-Z,.,-]*(.){1}[a-zA-Z]{2,4})+$");
						if (expRegEmail.test(email)) {
							return true;
						}
						return false;
					}
				});// FIM
