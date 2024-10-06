package com.vemser.rest.config;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.model.LoginModel;
import com.vemser.rest.utils.Manipulation;
import org.junit.jupiter.api.BeforeAll;


public class BaseAuth {

    public static String tokenValido;

    @BeforeAll
    public static void login() {
        LoginClient loginClient = new LoginClient();

//        LoginModel loginUsuarioModel = LoginDataFactory.loginValido();

		LoginModel loginUsuarioModel = new LoginModel();
		loginUsuarioModel.setEmail(Manipulation.getProp().getProperty("email"));
		loginUsuarioModel.setPassword(Manipulation.getProp().getProperty("senha"));

        tokenValido =
				loginClient.realizarLogin(loginUsuarioModel)
						.then()
							.extract()
								.path("authorization");
    }
}
