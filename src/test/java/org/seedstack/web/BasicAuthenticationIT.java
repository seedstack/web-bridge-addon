/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.web;

import static io.restassured.RestAssured.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.ConfigurationProfiles;
import org.seedstack.seed.testing.junit4.internal.JUnit4Runner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

@RunWith(JUnit4Runner.class)
@LaunchWithUndertow
@ConfigurationProfiles("basic")
public class BasicAuthenticationIT {
    @Configuration("runtime.web.baseUrl")
    private String url;

    @Test
    public void loginValidUser() throws Exception {
        given()
                .auth().basic("ThePoltergeist", "bouh")
                .expect().statusCode(204)
                .when().get(url + "/web-bridge/security/authentication");
    }

    @Test
    public void logoutValidUser() throws Exception {
        // With basic authentication the user is logged in and out in one client call
        given()
                .auth().basic("ThePoltergeist", "bouh")
                .expect().statusCode(204)
                .when().delete(url + "/web-bridge/security/authentication");
    }

    @Test
    public void loginInvalidUser() throws Exception {
        given()
                .auth().basic("InvalidUser", "invalidPassword")
                .expect().statusCode(401)
                .when().get(url + "/web-bridge/security/authentication");
    }
}
