package managers.user;

import main.Main;
import managers.file.CSVSavable;
import common.IDAccess;
import managers.file.DBSavable;
import result.Result;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class User implements Comparable<User>, IDAccess, CSVSavable, DBSavable {
    private static long idCounter = 0;
    long id;
    String login;
    String password;
    String salt;
    InetAddress host;
    int port;
    LocalDateTime lastActivity;

    public User(String login, String password, String salt, InetAddress host, int port) {
        this.id = idCounter++;
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.host = host;
        this.port = port;
        lastActivity = LocalDateTime.now();
    }

    public User(String login, String password, InetAddress host, int port) {
        this.id = idCounter++;
        this.login = login;
        this.password = password;
        this.host = host;
        this.port = port;
        this.salt = getRandomString();
        lastActivity = LocalDateTime.now();
    }

    private String getRandomString() {
        String res = "";
        for (int i = 0; i < new Random().nextInt(10) + 4; i++) {
            char a = (char) (new Random().nextInt(94) + 33);
            res += a;
        }
        return res;
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
        return Result.success(id + "," + login + "," + password);
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    private String getHashedPassword() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return new String(
                    md.digest(
                            ("h*3nP(P*ueF32" + password + salt).getBytes(StandardCharsets.UTF_8)
                    ), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            Main.logger.error("Password coding error", e);
        }
        return "";
    }

    public String getHashedPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            return new String(
                    md.digest(
                            ("h*3nP(P*ueF32" + password + salt).getBytes(StandardCharsets.UTF_8)
                    ), StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            Main.logger.error("Password coding error", e);
        }
        return "";
    }

    public InetAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    public void refreshLastActivity() {
        this.lastActivity = LocalDateTime.now();
    }

    @Override
    public Result<List<String>> toFields() {
        try {
            ArrayList<String> res = new ArrayList<>();
            res.add(this.getID() + "");
            res.add("'" + this.getLogin() + "'");
            res.add("'" + this.getHashedPassword() + "'");
            res.add("'" + this.salt + "'");
            return Result.success(res, null);
        }catch(Exception e){
            return Result.failure(e, "Error with parsing DataBase format");
        }
    }
}