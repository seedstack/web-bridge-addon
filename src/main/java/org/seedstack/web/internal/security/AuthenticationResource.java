/*
 * Copyright © 2013-2020, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.web.internal.security;

import static javax.ws.rs.core.Response.Status.FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.seedstack.seed.security.SecuritySupport;

@Path("/web-bridge/security/authentication")
public class AuthenticationResource {
    @Inject
    private SecuritySupport securitySupport;

    @GET
    public Response authenticateGet() {
        if (!securitySupport.isAuthenticated()) {
            return Response.status(UNAUTHORIZED).build();
        }
        return Response.noContent().build();
    }

    @POST
    public Response authenticatePost() {
        if (!securitySupport.isAuthenticated()) {
            return Response.status(UNAUTHORIZED).build();
        }
        return Response.status(FOUND)
                .location(UriBuilder.fromResource(AuthorizationsResource.class).build())
                .build();
    }

    @DELETE
    public Response deauthenticate() {
        securitySupport.logout();
        return Response.noContent().build();
    }
}
