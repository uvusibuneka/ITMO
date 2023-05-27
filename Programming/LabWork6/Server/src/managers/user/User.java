package managers.user;


import managers.file.CSV_savable;
import common.IDAccess;
import result.Result;

import java.net.InetAddress;

public class User implements Comparable<User>, IDAccess, CSV_savable {
    private static long idCounter = 0;
    long id;
    String login;
    String password;
    InetAddress host;
    int port;

    public User(String login, String password, InetAddress host, int port){
        this.id = idCounter++;
        this.login = login;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    @Override
    public long getID() {
        return id;
    }

    @Override
    public void setID(long id) {
        this.id = id;
        idCounter = Math.max(idCounter, id + 1);
    }

    @Override
    public int compareTo(User o) {
        return Long.compare(id, o.id);
    }

    @Override
    public Result<String> toCSV() {
        return Result.success(id+","+login+","+password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public InetAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
