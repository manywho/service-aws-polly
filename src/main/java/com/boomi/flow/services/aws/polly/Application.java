package com.boomi.flow.services.aws.polly;

import com.manywho.sdk.services.servers.EmbeddedServer;
import com.manywho.sdk.services.servers.Servlet3Server;
import com.manywho.sdk.services.servers.undertow.UndertowServer;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class Application extends Servlet3Server {

    public Application(){
        this.addModule(new ApplicationModule());
        this.setApplication(Application.class);
        this.start();
    }

    public static void main(String[] args) throws Exception {
        EmbeddedServer server = new UndertowServer();
        server.addModule(new ApplicationModule());
        server.setApplication(Application.class);
        server.start();
    }
}
