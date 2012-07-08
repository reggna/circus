
package circus.irc.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.LinkedList;

public class Server {
  private List<Channel> channels;
  private List<User> users;
  private int port = 6667;

  private Server(){
    users = new LinkedList<User>();
    Socket clientSocket;
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      while(true){
        try {
          clientSocket = serverSocket.accept();
          Connection c = new Connection(clientSocket);
          users.add(new User(c));
          c.run();
        } catch(IOException e){
          System.out.println("Unable to create client socket: " + e.getMessage());
        }
      }
    } catch(IOException e){
      System.out.println("Unable to create server socket: " + e.getMessage());
      System.exit(-1);
    }
  }

  public static void main(String args[]) {
    new Server();
  }
}
