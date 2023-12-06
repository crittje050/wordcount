/* (C)2023 */
package com.example.restadapter;

import com.example.restadapter.config.ApplicationServerConfig;
import jakarta.ws.rs.core.Application;
import java.util.Objects;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class WordCountRestApplication extends Application {

  private static final Logger logger = Logger.getLogger(WordCountRestApplication.class.getName());

  public static void main(String[] args) throws Exception {

    Server server = null;

    try {
      server = new Server(8080);

      ResourceConfig resourceConfig = new ApplicationServerConfig();
      ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
      handler.setContextPath("/");

      handler.addServlet(new ServletHolder(new ServletContainer(resourceConfig)), "/*");

      var resourceBasePath =
          Objects.requireNonNull(WordCountRestApplication.class.getResource("/webapp"))
              .toExternalForm();
      handler.setWelcomeFiles(new String[] {"index.html"});
      handler.setResourceBase(resourceBasePath);
      handler.addServlet(new ServletHolder(new DefaultServlet()), "/ui/*");

      server.setHandler(handler);

      server.start();
      server.join();

      logger.info("Server started");

    } finally {
      if (server != null) {
        server.stop();
        server.destroy();
        logger.info("Server stopped");
      }
    }
  }
}
