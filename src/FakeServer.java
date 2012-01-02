import java.util.ArrayList;
import java.util.List;

import org.mortbay.jetty.AbstractConnector;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.NCSARequestLog;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerList;
import org.mortbay.jetty.handler.RequestLogHandler;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.Context;

@SuppressWarnings({"FieldCanBeLocal", "UseOfSystemOutOrSystemErr"})
public class FakeServer {
    public static final int SERVER_PORT = 7005;

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: FakeServer /home/chi/dev/webbycraft");
            System.exit(1);
        }

        new FakeServer(args[0]).start();
    }

    private final String _projectBaseDir;
    private final int _serverPort;

    public FakeServer(String projectBaseDir) {
        _projectBaseDir = projectBaseDir;
        _serverPort = SERVER_PORT;
    }

    private void start() throws Exception {
        Server server = new Server();
        server.addConnector(getConnector());

        //static files
        List<Handler> handlerList = new ArrayList<Handler>();
        handlerList.add(getContext());

        //start
        HandlerList handlers = new HandlerList();
        handlers.setHandlers(handlerList.toArray(new Handler[handlerList.size()]));

        RequestLogHandler logHandler = new RequestLogHandler();
        logHandler.setRequestLog(new NCSARequestLog());
        logHandler.setHandler(handlers);
        server.setHandler(logHandler);
        server.start();

        System.out.println();
        System.out.println("Startup complete, go to: ");
        System.out.println("  http://127.0.0.1:" + SERVER_PORT + "/Minicraft.html");
    }

    private Context getContext() {
        ResourceHandler contentHandler = new ResourceHandler();
        contentHandler.setResourceBase(_projectBaseDir + "/out/gwt-js-war");

        HandlerList contentHandlers = new HandlerList();
        contentHandlers.setHandlers(new Handler[]{contentHandler});

        final Context context = new Context();
        context.setContextPath("/");
        context.setHandler(contentHandlers);
        return context;
    }


    private AbstractConnector getConnector() {
        AbstractConnector connector = new SelectChannelConnector();
        connector.setPort(_serverPort);

        // Don't share ports with an existing process.
        connector.setReuseAddress(false);

        // Linux keeps the port blocked after shutdown if we don't disable this.
        connector.setSoLingerTime(0);
        return connector;
    }
}
