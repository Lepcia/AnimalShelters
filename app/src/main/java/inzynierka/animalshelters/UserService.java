package inzynierka.animalshelters;

public class UserService {
    private static UserService userServiceInstance;
    private static int mUserId;

    private UserService(int userId) {
        mUserId = userId;
    }

    public static  UserService setInstance(int userId) {
        if (userServiceInstance == null) {
            userServiceInstance = new UserService(userId);
        }
        return userServiceInstance;
    }

    public static  UserService getInstance() {
        if (userServiceInstance == null) {
            throw new Error("User not specified");
        }
        return userServiceInstance;
    }

    public int getmUserId(){
        return mUserId;
    }

    public static void resetUser(){
        userServiceInstance = null;
    }
}
