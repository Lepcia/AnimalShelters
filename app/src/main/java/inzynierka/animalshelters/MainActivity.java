package inzynierka.animalshelters;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import inzynierka.animalshelters.activities.administration.UserListActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;

public class MainActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();
        onCreateDrawerMenu();

        Button usersBtn = (Button)findViewById(R.id.userBtn);
        usersBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }
}
