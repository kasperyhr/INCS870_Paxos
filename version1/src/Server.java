import java.io.*;
import java.net.*;

public class Server {
  private static final int DEFAULT_PORT_START = 50_000;
  private static Server server;
  private int serverId;
  private int port;

  private Server(String[] args) {
    parseArgs(args);
  }

  public static void main(String[] args) {
    server = new Server(args);
    server.listenCommands();
  }

  private void listenCommands() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      System.out.printf("Server %d listening on port %d\n", serverId, port);
      while (true) {
        try (Socket clientSocket = serverSocket.accept();
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
          System.out.printf("Server %d connected.\n", serverId);
          String clientMessage = in.readLine();
          System.out.printf("Server %d received message: %s\n", serverId, clientMessage);
          out.println("Received");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /* The args.length = 1, args[0] = serverId
   * port = DEFAULT_PORT_START + serverId
   */
  private void parseArgs(String[] args) {
    serverId = Integer.parseInt(args[0]);
    port = DEFAULT_PORT_START + serverId;
  }
}
