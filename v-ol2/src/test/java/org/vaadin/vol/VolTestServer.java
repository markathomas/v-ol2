package org.vaadin.vol;

import com.vaadin.server.VaadinServlet;

import java.io.File;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.vaadin.vol.demo.VolApplication;

public class VolTestServer {

    private static final int PORT = 9998;

    /**
     *
     * Test server for the addon that does not fuck with you like m2eclipse and
     * its WTP integration.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Server server = new Server();

        final Connector connector = new SelectChannelConnector();

        connector.setPort(PORT);
        server.setConnectors(new Connector[] { connector });

        WebAppContext context = new WebAppContext();
        ServletHolder servletHolder = new ServletHolder(VaadinServlet.class);
        servletHolder.setInitParameter("widgetset",
                "org.vaadin.vol.demo.VolExampleAppWidgetset");
        servletHolder.setInitParameter("ui",
                VolApplication.class.getName());

        File file = new File("./target/testwebapp");
        context.setWar(file.getPath());
        context.setContextPath("/");

        ServletHolder servletHolder2 = new ServletHolder(WFSProxyServlet.class);
        context.addServlet(servletHolder2, "/WFSPROXY/*");
        context.addServlet(servletHolder, "/*");

        server.setHandler(context);

        server.start();
        server.join();
    }

}
