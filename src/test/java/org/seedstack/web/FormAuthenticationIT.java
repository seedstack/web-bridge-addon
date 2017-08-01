/**
 * Copyright (c) 2013-2016, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.web;


import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.seedstack.seed.it.AbstractSeedWebIT;

import java.net.URL;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


public class FormAuthenticationIT extends AbstractSeedWebIT {
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addAsResource("form-auth.yaml", "META-INF/configuration/form-auth.yaml");
    }

    @Test
    @RunAsClient
    public void loginValidUser(@ArquillianResource URL baseURL) throws Exception {
        String location = given()
                .formParam("username", "ThePoltergeist")
                .formParam("password", "bouh")
                .expect().statusCode(302)
                .when().post(baseURL.toString() + "web-bridge/security/authentication")
                .header("Location");
        assertThat(location).endsWith("web-bridge/security/authorizations");
    }


    @Test
    @RunAsClient
    public void loginInvalidUser(@ArquillianResource URL baseURL) throws Exception {
        given()
                .formParam("username", "ThePoltergeist")
                .formParam("password", "invalid")
                .expect().statusCode(401)
                .when().post(baseURL.toString() + "web-bridge/security/authentication");
    }
}
