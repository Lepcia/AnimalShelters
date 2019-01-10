package inzynierka.animalshelters;

import java.util.List;

import inzynierka.animalshelters.models.RightsModel;
import inzynierka.animalshelters.models.RoleModel;

public class UserService {

    private volatile static UserService instance;
    private int mUserId;
    private int mShelterId;
    private RoleModel mUserRole;
    private List<RightsModel> mUserRights;
    private String token;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                    instance.mUserId = 0;
                    instance.mShelterId = 0;
                    instance.mUserRole = null;
                    instance.mUserRights = null;
                    instance.token = "";
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

    public void setmShelterId(int shelterId) {mShelterId = shelterId;}

    public int getmShelterId() {
        return mShelterId;
    }

    public RoleModel getmUserRole() {
        return mUserRole;
    }

    public void setmUserRole(RoleModel mUserRole) {
        this.mUserRole = mUserRole;
    }

    public List<RightsModel> getmUserRights() {
        return mUserRights;
    }

    public void setmUserRights(List<RightsModel> mUserRights) {
        this.mUserRights = mUserRights;
    }

    public void resetUser(){
        instance = null;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
