package inzynierka.animalshelters.activities.administration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import inzynierka.animalshelters.R;
import inzynierka.animalshelters.activities.basic.BasicActivity;

public class AdminEditUser extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_user);
        onCreateDrawer();
        onCreateDrawerMenu();
    }
}
