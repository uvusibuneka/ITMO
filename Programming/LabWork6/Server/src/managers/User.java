package managers;


import collection_file_managers.decorators.CSV.CSV_savable;
import common.IDAccess;
import result.Result;

import java.net.InetAddress;

public class User implements Comparable<User>, IDAccess, CSV_savable {
    long id;
    String login;
    String password;
    InetAddress host;
    int port;

    public User(long id, String login, String password, InetAddress host, int port){
        this.id = id;
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
