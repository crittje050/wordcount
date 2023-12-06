/* (C)2023 */
package com.example.restadapter.config;

import org.glassfish.jersey.server.ResourceConfig;

public class ApplicationServerConfig extends ResourceConfig {
  public ApplicationServerConfig() {
    register(new ServiceBindings());
    packages(
        true, "com.example.restadapter.controller", "io.swagger.v3.jaxrs2.integration.resources");
  }
}
