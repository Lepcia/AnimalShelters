package inzynierka.animalshelters.activities.search;

import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import inzynierka.animalshelters.NewsBoardActivity;
import inzynierka.animalshelters.R;
import inzynierka.animalshelters.UserService;
import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.photos.PhotosActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;

public class SearchActivity extends BasicActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        onCreateDrawer();
        onCreateDrawerMenu();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Animals"));
        tabLayout.addTab(tabLayout.newTab().setText("Animal shelters"));
        tabLayout.addTab(tabLayout.newTab().setText("Map"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    SearchAnimals tab1 = new SearchAnimals();
                    return tab1;
                case 1:
                    SearchShelters tab2 = new SearchShelters();
                    return tab2;
                case 2:
                    SearchMap tab3 = new SearchMap();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }

    @Override
    public void openMainModule()
    {
        Intent intent = new Intent(SearchActivity.this, NewsBoardActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(SearchActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(SearchActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openAnimalsModule()
    {
        Intent intent = new Intent(SearchActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSheltersModule()
    {
        Intent intent = new Intent(SearchActivity.this, SheltersActivity.class);
        startActivity(intent);
    }


    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(SearchActivity.this, SettingsActivity.class);
        int userId = UserService.getInstance().getmUserId();
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        intent.putExtra("UserId", userId);
        startActivity(intent);
    }

    @Override
    public void openPhotosModule()
    {
        Intent intent = new Intent(SearchActivity.this, PhotosActivity.class);
        int shelterId = UserService.getInstance().getmShelterId();
        intent.putExtra("ShelterId", shelterId);
        startActivity(intent);
    }
}
