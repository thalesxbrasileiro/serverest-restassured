package com.vemser.rest.data.factory;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.model.UsuariosModel;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class UsuariosDataFactory {

	private static Faker faker = new Faker(new Locale("PT-BR"));
	private static Random geradorBoolean = new Random();
	private static final UsuariosClient usuariosClient = new UsuariosClient();

	public static UsuariosModel usuarioValido() {
		return  novoUsuario();
	}

	private static UsuariosModel novoUsuario() {
		UsuariosModel usuario = new UsuariosModel();

		usuario.setNome(faker.name().firstName() + " " + faker.name().lastName());
		usuario.setEmail(faker.internet().emailAddress());
		usuario.setPassword(faker.internet().password());
		usuario.setAdministrador(String.valueOf(geradorBoolean.nextBoolean()));

		return usuario;
	}

	public static UsuariosModel usuarioComNomeEmBranco() {
		UsuariosModel usuario = novoUsuario();
		usuario.setNome(StringUtils.EMPTY);

		return usuario;
	}

	public static UsuariosModel usuarioComEmailEmBranco() {
		UsuariosModel usuario = novoUsuario();
		usuario.setEmail(StringUtils.EMPTY);

		return usuario;
	}

	public static UsuariosModel usuarioComPasswordEmBranco() {
		UsuariosModel usuario = novoUsuario();
		usuario.setPassword(StringUtils.EMPTY);

		return usuario;
	}

	public static UsuariosModel usuarioComIsAdminEmBranco() {
		UsuariosModel usuario = novoUsuario();
		usuario.setAdministrador(StringUtils.EMPTY);

		return usuario;
	}

	public static UsuariosModel usuarioComEmailJaCadastrado(String email) {
		UsuariosModel usuario = novoUsuario();
		usuario.setEmail(email);

		return usuario;
	}

	public static UsuariosModel usuarioComTodosOsDadosEmBranco() {
		UsuariosModel usuario = novoUsuario();

		usuario.setNome(StringUtils.EMPTY);
		usuario.setEmail(StringUtils.EMPTY);
		usuario.setPassword(StringUtils.EMPTY);
		usuario.setAdministrador(StringUtils.EMPTY);

		return usuario;
	}

	public static Response buscarTodosUsuariosERetornarResposta() {
		return
				usuariosClient.buscarTodosUsuarios()
						.then()
						.extract()
						.response();
	}

	public static String buscarOemailDoPrimeiroUsuarioNaoAdminId() {
		Response response = buscarTodosUsuariosERetornarResposta();
		List<Object> listaUsuarios = response.path("usuarios");

		for (Object usuario : listaUsuarios) {
			String administrador = response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "].administrador");
			if (!Boolean.parseBoolean(administrador)) {
				return response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "].email");
			}
		}
		return null;
	}

	public static String buscarOpasswordDoPrimeiroUsuarioNaoAdminId() {
		Response response = buscarTodosUsuariosERetornarResposta();
		List<Object> listaUsuarios = response.path("usuarios");

		for (Object usuario : listaUsuarios) {
			String administrador = response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "].administrador");
			if (!Boolean.parseBoolean(administrador)) {
				return response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "].password");
			}
		}
		return null;
	}

	public static String buscarPrimeiroUsuarioNaoAdminId() {
		Response response = buscarTodosUsuariosERetornarResposta();
		List<Object> listaUsuarios = response.path("usuarios");

		for (Object usuario : listaUsuarios) {
			String administrador = response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "].administrador");
			if (!Boolean.parseBoolean(administrador)) {
				return response.path("usuarios[" + listaUsuarios.indexOf(usuario) + "]._id");
			}
		}
		return null;
	}

}
