package inzynierka.animalshelters.rest;

public final class Api {

    private static final String BASE_URL = "http://192.168.1.14:49268/api/";
    public static final String ADMINISTRATION_URL = "administration";
    public static final String ADMIN_USER_MODULE = ADMINISTRATION_URL + "/userModules/{id}";
    public static final String ADMIN_RIGHTS_TO_MODULE = ADMINISTRATION_URL + "/rightsToModule";
    public static final String USERS_URL = "users";
    public static final String USER_ID_URL = "users/{id}";
    public static final String USERS_ALL_URT = "users/all";
    public static final String ANIMALS_URL = "animals";
    public static final String ANIMAL_ID_URL = "animals/{id}";
    public static final String ANIMALS_ALL_URL = "animals/all";
    public static final String ANIMALS_NEWEST_URL = "animals/newest";
    public static final String ANIMAL_SHELTERS_URL = "animalshelters";
    public static final String ANIMAL_SHELTER_ID_URL = "animalsshelters/{id}";
    public static final String ANIMAL_SHELTERS_ALL_URL = "animalShelters/all";
    public static final String RIGHTS_URL = "rights";
    public static final String RIGHTS_TO_USER_URL = "rightToUser";
    public static final String MODULE_URL = "module";
    public static final String FAVORITE_ANIMALS_URL = USERS_URL + "/{id}" + "/favoriteAnimals";

    public static String getBaseUrl()
    {
        return BASE_URL;
    }
}
