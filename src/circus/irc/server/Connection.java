
package circus.irc.server;

import java.net.Socket;

public class Connection implements Runnable {
  private Socket socket;

  public Connection(Socket socket) {
    this.socket = socket;
  }

  public int getPort() {
    return socket.getPort();
  }

  public void run() {
    System.out.println("New user connected: " + socket.getPort());
  }
}