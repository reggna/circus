
package circus.irc.server;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Connection implements Runnable {
  private Socket socket;
  private BufferedReader in;
  private PrintWriter out;
  private Server server;
  private User user;

  public Connection(Socket socket, Server server) throws IOException {
    this.socket = socket;
    out = new PrintWriter(socket.getOutputStream());
    this.server = server;
  }

  public int getPort() {
    return socket.getPort();
  }

  public void setUser(User u) {
    user = u;
  }

  public void run() {
    if(!socket.isConnected()) {
      throw new RuntimeException("Socket is not connected");
    }
    try{
      //socket.setSoTimeout(10000);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      String line = "";
      while(socket.isConnected()) {
        line = in.readLine();
        System.out.println(line);

        if(line == null){
          server.userDisconnected(user);
          return;
        }

        String[] array = line.split(" ");
        switch(array[0]) {
        case "NICK":
          user.onNickMessage(array[1]);
          break;
        case "USER":
          user.onUserMessage(array[2], "hostname", array[4].substring(1));
          break;
        case "PING":
          send("PONG");
          break;
        case "WHOIS":
          send(server.onWhois(array[1]));
          send("318");
          break;
        }
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
      System.out.println(e.getStackTrace());
    }
  }

  public void disconnect() throws IOException {
    socket.close();
    in.close();
    out.close();
  }

  public void send(String s) throws IOException {
    System.out.println("-> " + s);
    out.print(s + "\r\n");
    out.flush();
  }
}
