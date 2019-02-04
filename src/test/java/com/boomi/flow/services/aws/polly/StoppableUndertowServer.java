package com.boomi.flow.services.aws.polly;

import com.google.inject.Module;
import com.manywho.sdk.services.ServiceApplication;
import com.manywho.sdk.services.servers.BaseServer;
import com.manywho.sdk.services.servers.EmbeddedServer;
import com.manywho.sdk.services.servers.undertow.UndertowServer;
import io.undertow.Undertow;
import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * This class is a copy of UndertowServer.class, but with stop server functionality
 */
public class StoppableUndertowServer extends BaseServer implements EmbeddedServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UndertowServer.class);
    private UndertowJaxrsServer server;

    public StoppableUndertowServer() {
    }

    @Override
    public void start(String s) throws Exception {
        this.start(s, 8080);
    }

    public void start(String path, int port) {
        ServiceApplication serviceApplication = new ServiceApplication();
        Iterator var4 = this.modules.iterator();

        while(var4.hasNext()) {
            Module module = (Module)var4.next();
            serviceApplication.addModule(module);
        }

        serviceApplication.initialize(this.application.getPackage().getName(), true);

        try {
            server = new UndertowJaxrsServer();
            Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(port, "0.0.0.0");
            server.start(serverBuilder);
            server.deploy(serviceApplication, path);
            LOGGER.info("Service started on 0.0.0.0:{}", port);
            LOGGER.info("Stop the service using CTRL+C");
        } catch (Exception var6) {
            LOGGER.error("Unable to start the server", var6);
        }

    }

    // new method
    public void stop() {
        server.stop();
    }

    @Override
    public void start() throws Exception {
        this.start("/");
    }
}
