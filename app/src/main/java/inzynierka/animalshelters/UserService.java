package inzynierka.animalshelters;

public class UserService {

    private volatile static UserService instance;
    private int mUserId;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                    instance.mUserId = 0;
                }
            }
        }

        return instance;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getmUserId(){
        return mUserId;
    }

    public void resetUser(){
        instance = null;
    }
}
