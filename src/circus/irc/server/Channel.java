
package circus.irc.server;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public abstract class Channel {
  private String name;
  private String topic;

  private Map<User, UserLevel> users = new HashMap<User, UserLevel>();

  public enum UserLevel {
    OP;
  }

  public Channel(String name) {
    this.name = name;
  }

  public void gotJoinRequest(User u) throws IOException {
    if (!users.containsKey(u)) {
      users.put(u, null);
      u.sendTopic(this, topic);
      u.sendNames(this, users);
    } else {
      // TODO: what if user is already is in channel?
    }
  }
}
