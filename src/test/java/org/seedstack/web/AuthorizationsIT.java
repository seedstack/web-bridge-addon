/*
 * Copyright © 2013-2018, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package org.seedstack.web;

import static io.restassured.RestAssured.given;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import io.restassured.response.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.ConfigurationProfiles;
import org.seedstack.seed.testing.junit4.internal.JUnit4Runner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

@RunWith(JUnit4Runner.class)
@LaunchWithUndertow
@ConfigurationProfiles("basic")
public class AuthorizationsIT {
    @Configuration("runtime.web.baseUrl")
    private String url;

    @Test
    public void checkValidUserAuthorizations() throws Exception {
        ResponseBody r = given()
                .auth().basic("ThePoltergeist", "bouh")
                .expect().statusCode(200)
                .when().get(url + "/web-bridge/security/authorizations")
                .body();
        String expected = "{\"id\":\"ThePoltergeist\",\"type\":\"user\"," +
                "\"principals\":{\"userId\":\"ThePoltergeist\"},\"roles\":[{\"name\":\"jedi\",\"attributes\":{}," +
                "\"permissions\":[[\"academy\",\"*\"],[\"lightSaber\",\"*\"]]},{\"name\":\"ghost\"," +
                "\"attributes\":{\"scope\":[\"MU\",\"PY\"]},\"permissions\":[[\"site\",\"haunt\"]]}]," +
                "\"permissions\":[]}";
        assertEquals(expected, r.asString(), false);
    }
}
