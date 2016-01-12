package org.vaadin.vol;


import com.vaadin.annotations.Widgetset;
import org.vaadin.addonhelpers.TServer;

@Widgetset("org.vaadin.vol.VolWidgetset")
public class VolTestServer extends TServer {

    /**
     *
     * Test server for the addon that does not fuck with you like m2eclipse and
     * its WTP integration.
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new VolTestServer().startServer();
    }

}
