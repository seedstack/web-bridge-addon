/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.web;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.ConfigurationProfiles;
import org.seedstack.seed.testing.junit4.internal.JUnit4Runner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

@RunWith(JUnit4Runner.class)
@LaunchWithUndertow
@ConfigurationProfiles("form")
public class FormAuthenticationIT {
    @Configuration("runtime.web.baseUrl")
    private String url;

    @Test
    public void loginValidUser() throws Exception {
        String location = given()
                .formParam("username", "ThePoltergeist")
                .formParam("password", "bouh")
                .expect().statusCode(302)
                .when().post(url + "/web-bridge/security/authentication")
                .header("Location");
        assertThat(location).endsWith("/web-bridge/security/authorizations");
    }

    @Test
    public void loginInvalidUser() throws Exception {
        given()
                .formParam("username", "ThePoltergeist")
                .formParam("password", "invalid")
                .expect().statusCode(401)
                .when().post(url + "/web-bridge/security/authentication");
    }
}
