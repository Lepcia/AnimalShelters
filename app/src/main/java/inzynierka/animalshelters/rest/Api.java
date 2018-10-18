package inzynierka.animalshelters.rest;

public final class Api {

    private static final String BASE_URL = "http://192.168.10.129:49268/api/";
    public static final String ADMINISTRATION_URL = "administration";
    public static final String ADMIN_USER_MODULE = ADMINISTRATION_URL + "/userModules";
    public static final String ADMIN_RIGHTS_TO_MODULE = ADMINISTRATION_URL + "/rightsToModule";
    public static final String USERS_URL = "users";
    public static final String ANIMALS_URL = "animals";
    public static final String ANIMAL_SHELTERS_URL = "animalshelters";
    public static final String RIGHTS_URL = "rights";
    public static final String RIGHTS_TO_USER_URL = "rightToUser";
    public static final String MODULE_URL = "module";

    public static String getBaseUrl()
    {
        return BASE_URL;
    }
}
