package com.vemser.rest.client;

import com.vemser.rest.model.LoginModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {

	private final String LOGIN = "/login";

	public Response realizarLogin(LoginModel login) {

		return
				given()
						.spec(super.set())
						.contentType(ContentType.JSON)
						.body(login)
				.when()
						.post(LOGIN)
				;
	}

}
