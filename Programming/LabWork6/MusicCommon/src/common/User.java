package common;

public class User implements Comparable<User>, IDAccess{
    @Override
    public long getID() {
        return 0;
    }

    @Override
    public void setID(long id) {

    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
