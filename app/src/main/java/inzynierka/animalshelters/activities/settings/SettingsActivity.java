package inzynierka.animalshelters.activities.settings;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Set;

import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;

import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.administration.AdminShelters;
import inzynierka.animalshelters.activities.administration.AdminUsers;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.search.SearchActivity;

public class SettingsActivity extends BasicActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private int ShelterId = -1;
    private int UserId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        onCreateDrawer();
        onCreateDrawerMenu();

        Bundle bundle = getIntent().getExtras();
        if(bundle.getInt("ShelterId") > 0)
        {
            ShelterId = bundle.getInt("ShelterId");
        }
        if(bundle.getInt("UserId") > 0)
        {
            UserId = bundle.getInt("UserId");
        }

        mSectionsPagerAdapter = new SettingsActivity.SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("User"));
        tabLayout.addTab(tabLayout.newTab().setText("Animal shelter"));
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Animals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }

    public int GetShelterId()
    {
        return ShelterId;
    }

    public int GetUserId()
    {
        return UserId;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SettingsUser userTab = new SettingsUser();
                    return userTab;
                case 1:
                    SettingsShelter shelterTab = new SettingsShelter();
                    return  shelterTab;
                case 2:
                    SettingsUsers usersTab = new SettingsUsers();
                    return usersTab;
                case 3:
                    SettingsAnimals animalsTab = new SettingsAnimals();
                    return animalsTab;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(SettingsActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(SettingsActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSearchModule()
    {
        Intent intent = new Intent(SettingsActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(SettingsActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(SettingsActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(SettingsActivity.this, AdminActivity.class);
        startActivity(intent);
    }
}
