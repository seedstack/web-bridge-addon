---
title: "Web bridge"
addon: "Web bridge"
addonCategory: "web"
repo: "https://github.com/seedstack/web-bridge-addon"
author: Adrien LAUER
description: "REST API to integrate any Web frontend to SeedStack backend."
tags:
    - interfaces
    - security
    - web
zones:
    - Addons
noMenu: true    
---

The SeedStack Web bridge is an API that enables to quickly integrate your Web frontend to your SeedStack backend.
<!--more-->

## Dependency

You can add it with the following dependency:

{{< dependency g="org.seedstack.addons.web" a="web-bridge" >}}

## Usage

### Security

#### Authentication resource

The authentication resource is available at `/web-bridge/security/authentication` and supports:

* `GET`. Checks that a subject is authenticated. If true, returns a `204` (no content). If not, returns a `403` (unauthorized).
* `POST`. Checks that a subject is authenticated. If true, returns a `302` (found) with a redirection location to the authorization
resource. If not, returns a `403` (unauthorized).
* `DELETE`. Logs the subject out.

{{% callout info %}}
This resource is meant to be used in conjunction with authentication security filters like `authcBasic` or `authc` to 
trigger user authentication. Learn more about security filters in the [web security documentation]({{< ref "docs/web/security.md" >}}).
{{% /callout %}}

#### Authorization resource

The authorization resource is available at `/web-bridge/security/authorizations` and supports:

* `GET`. Checks that a subject is authenticated. If true, returns a JSON representation of the subject and its authorizations. 
If not, returns a `403` (unauthorized).
 
Example of subject representation:

```json
{
  "id": "userId",
  "type": "user",
  "principals": {
    "userId": "userId",
    "locale": "fr-FR"
  },
  "roles": [{
    "name": "manager",
    "attributes": {
      "scope": ["FR", "UK"]
    },
    "permissions": [
      ["products", "*"],
      ["categories", "*"]
    ]
  }, {
    "name": "admin",
    "attributes": {},
    "permissions": [
      ["users", "*"]
    ]
  }],
  "permissions": []
}
```

A few things to note:

* All string-based subject principals are provided under the `principals` section.
* Roles can have attributes attached to them, which can be used as a way of limiting their scope.
* Permissions are often given through roles but can also be affected individually to subjects.  

{{% callout ref %}}
Refer to the [security documentation]({{< ref "docs/core/security.md" >}}) to learn more about roles and permissions.
{{% /callout %}}