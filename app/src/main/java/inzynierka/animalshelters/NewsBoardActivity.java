package inzynierka.animalshelters;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import inzynierka.animalshelters.activities.administration.AdminActivity;
import inzynierka.animalshelters.activities.animalShelters.SheltersActivity;
import inzynierka.animalshelters.activities.animals.AnimalsActivity;
import inzynierka.animalshelters.activities.favorites.FavoriteAnimalsActivity;
import inzynierka.animalshelters.activities.basic.BasicActivity;
import inzynierka.animalshelters.activities.newsBoard.NewsBoardNew;
import inzynierka.animalshelters.activities.search.SearchActivity;
import inzynierka.animalshelters.activities.settings.SettingsActivity;

public class NewsBoardActivity extends BasicActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateDrawer();
        onCreateDrawerMenu();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("The newest animals"));
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
                    NewsBoardNew tab1 = new NewsBoardNew();
                    return tab1;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            //na razie może będzie więcej
            return 1;
        }
    }

    @Override
    public void openAdminModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, AdminActivity.class);
        startActivity(intent);
    }

    @Override
    public void openFavAnimalsModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, FavoriteAnimalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void  openSearchModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    public void openAnimalsModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, AnimalsActivity.class);
        startActivity(intent);
    }

    public void openSheltersModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, SheltersActivity.class);
        startActivity(intent);
    }

    @Override
    public void openSettingsModule()
    {
        Intent intent = new Intent(NewsBoardActivity.this, SettingsActivity.class);
        intent.putExtra("ShelterId", 1);
        intent.putExtra("UserId", 1);
        startActivity(intent);
    }
}
