
package circus.irc.server;

import java.util.Map;

public abstract class Channel {
  private String name;
  private String topic;

  private Map<User, UserLevel> users;

  public enum UserLevel{
    OP;
  }
}
