
package circus.irc.server;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.LinkedList;

public class Server {
  private Map<String, Channel> channels;
  private List<User> users;
  private int port = 6667;

  private Server() {
    users = new LinkedList<User>();
    channels = new HashMap<String, Channel>();
    Socket clientSocket;
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      while(true) {
        try {
          clientSocket = serverSocket.accept();
          Connection c = new Connection(clientSocket, this);
          User u = new User(c);
          c.setUser(u);
          users.add(u);
          c.run();
        } catch(IOException e) {
          System.out.println("Unable to create client socket: " + e.getMessage());
        }
      }
    } catch(IOException e) {
      System.out.println("Unable to create server socket: " + e.getMessage());
      System.exit(-1);
    }
  }
  
  public void userDisconnected(User u) throws IOException {
    u.disconnect();
    users.remove(u);
  }

  public void gotJoinRequest(User u, String channel) throws IOException {
    channels.get(channel).gotJoinRequest(u);
  }

  public String onWhois(String query) {
    for(User u: users) {
      if(query.equals(u.getNickname())) {
        return "331 " + u.getNickname() + " " + u.getUsername() + " " + u.getHostname() + " * :" + u.getRealname();
      }
    }
    return "401 " + query;
  }

  public static void main(String args[]) {
    new Server();
  }
}
