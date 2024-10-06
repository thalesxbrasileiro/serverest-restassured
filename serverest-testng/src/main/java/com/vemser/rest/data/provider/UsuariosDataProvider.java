package com.vemser.rest.data.provider;

import com.vemser.rest.data.factory.UsuariosDataFactory;
import org.testng.annotations.DataProvider;

public class UsuariosDataProvider {

	// CHAVES
	private static final String KEY_NOME = "nome";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_ISADMIN = "administrador";

	// VALORES
	private static final String VALUE_NOME_EM_BRANCO = "nome não pode ficar em branco";
	private static final String VALUE_EMAIL_EM_BRANCO = "email não pode ficar em branco";
	private static final String VALUE_PASSWORD_EM_BRANCO = "password não pode ficar em branco";
	private static final String VALUE_ISADMIN_EM_BRANCO = "administrador deve ser 'true' ou 'false'";

	@DataProvider
	public static Object[][] usuarioDataProvider() {
		return new Object[][] {
				{ UsuariosDataFactory.usuarioComNomeEmBranco(), KEY_NOME, VALUE_NOME_EM_BRANCO },
				{ UsuariosDataFactory.usuarioComEmailEmBranco(), KEY_EMAIL, VALUE_EMAIL_EM_BRANCO},
				{ UsuariosDataFactory.usuarioComPasswordEmBranco(), KEY_PASSWORD, VALUE_PASSWORD_EM_BRANCO },
				{ UsuariosDataFactory.usuarioComIsAdminEmBranco(), KEY_ISADMIN, VALUE_ISADMIN_EM_BRANCO }
		};
	}
}