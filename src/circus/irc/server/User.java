
package circus.irc.server;

import java.util.LinkedList;
import java.util.Map;
import java.io.IOException;

public class User {
  private String nickname;
  private String username;
  private String hostname;
  private String realname;
  private Connection connection;
  private LinkedList<Channel> channels;

  public boolean hasSecureConnection() {
    return connection instanceof Connection;
  }

  public User(Connection connection) {
    this.connection = connection;
  }

  public void disconnect() throws IOException {
    connection.disconnect();
    System.out.println("User <" + nickname + "> has disconnected");
  }

  public void sendTopic(Channel c, String topic) throws IOException {
    connection.send("RPL_TOPIC: 332" + c.toString() + " :" + topic);
  }

  public void onNickMessage(String nick) throws IOException {
    this.nickname = nick;
  }
  
  public void onUserMessage(String username, String hostname, String realname) throws IOException {
    this.username = username;
    this.hostname = hostname;
    this.realname = realname;
    connection.send("001\r\n002\r\n003\r\n004");
  }

  public String getNickname() {
    return nickname;
  }

  public String getHostname() {
    return hostname;
  }

  public String getUsername() {
    return username;
  }

  public String getRealname() {
    return realname;
  }
}
