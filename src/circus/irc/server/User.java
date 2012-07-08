
package circus.irc.server;

import java.util.LinkedList;

public class User {
  private String nickname;
  private String hostname;
  private String realname;
  private Connection connection;
  private LinkedList<Channel> channels;
  
  public boolean hasSecureConnection(){
    return connection instanceof Connection;
  }

  public User(Connection connection) {
    this.connection = connection;
  }
}
