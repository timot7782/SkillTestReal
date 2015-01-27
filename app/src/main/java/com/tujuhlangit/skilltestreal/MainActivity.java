package com.tujuhlangit.skilltestreal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

    private ListView mDrawerList;
    private String[] initialValue;
    private DrawerLayout drawerLayout;
    private MyAdapter myAdapter;
    private ActionBarDrawerToggle drawerListener;

    private CharSequence mTitle;
    private CharSequence mDrawerTitle;

    private LoginFragment loginFragment = new LoginFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();
        initialValue = getResources().getStringArray(R.array.initial_values);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        //mDrawerList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,initialValue));   //this=this activity itself
        if(this.loginFragment.isLoggedIn()) {
            myAdapter = new MyAdapter(this,"fb");
        }
        else {
            myAdapter = new MyAdapter(this);
        }
        mDrawerList.setAdapter(myAdapter);
        mDrawerList.setOnItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                //super.onDrawerOpened(drawerView);
                //Toast.makeText(MainActivity.this, " Drawer Opened ", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //super.onDrawerClosed(drawerView);
                //Toast.makeText(MainActivity.this," Drawer Closed ", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, initialValue[position] + " was selected", Toast.LENGTH_SHORT).show();
        selectItem(position);
        setTitle(initialValue[position]);
    }

    private void selectItem(int position) {

        switch (position) {
            case 0:
                //this.loginFragment = new LoginFragment();
                FragmentManager loginFragmentManager = getFragmentManager();
                loginFragmentManager.beginTransaction().replace(R.id.content_frame, this.loginFragment).commit();
                break;
            case 1:
                Fragment mainRSSFragment = new MainRSSFragment();
                FragmentManager mainRSSFragmentManager = getFragmentManager();
                mainRSSFragmentManager.beginTransaction().replace(R.id.content_frame, mainRSSFragment).commit();
                break;
            case 3:
                break;
        }

        mDrawerList.setItemChecked(position, true);
        setTitle(mTitle);
        drawerLayout.closeDrawer(mDrawerList);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //Log.i("setTitle", "the title is " + title);
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerListener.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.i("check on resume", this.loginFragment.toString());
        if(this.loginFragment.isLoggedIn()) {
            Log.i("Login@ln135", "Login");
            myAdapter = new MyAdapter(this, "fb");
        }
        else {
            Log.i("Login@ln135", "Logout");
            myAdapter = new MyAdapter(this);
        }

        mDrawerList.setAdapter(myAdapter);
        mDrawerList.setOnItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                //super.onDrawerOpened(drawerView);
                //Toast.makeText(MainActivity.this, " Drawer Opened ", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //super.onDrawerClosed(drawerView);
                //Toast.makeText(MainActivity.this, " Drawer Closed ", Toast.LENGTH_SHORT).show();
                getSupportActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerListener);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends BaseAdapter {

        String[] defaultVal = getResources().getStringArray(R.array.initial_values);
        Context context;
        int imgFacebook = R.drawable.ic_menu_facebook;
        int imgGooglePlus = R.drawable.ic_menu_googleplus;
        int imgRSS = R.drawable.ic_rss;
        int imgTwitter = R.drawable.ic_twitter;
        int[] myImages = {imgFacebook, imgRSS, imgTwitter};
        int[] fbLoginImages = {imgFacebook, imgRSS, imgTwitter};
        int[] gPlusLoginImages = {imgGooglePlus, imgRSS, imgTwitter};
        private static final String TAG = "MyAdapter";
        private boolean loginAsFb = false;
        private boolean loginAsGplus = false;
        String[] loginedValues = {"Profile", defaultVal[1], defaultVal[2]};

        public MyAdapter(Context context) {
            this.context = context;
            this.loginAsFb = false;
            this.loginAsGplus = false;
        }

        public MyAdapter(Context context, String loginAsFacebookOrGooglePlus) {
            this.context = context;
            if(loginAsFacebookOrGooglePlus.equals("fb")) {
                this.loginAsFb = true;
                this.loginAsGplus = false;
            }
            else {
                this.loginAsFb = true;
                this.loginAsGplus = false;
            }
        }

        @Override
        public int getCount() {
            return defaultVal.length;
        }

        @Override
        public Object getItem(int position) {
            return defaultVal[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = null;

            if (convertView==null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.drawer_list, parent, false);
            }
            else {
                row = convertView;
            }

            TextView textView = (TextView) row.findViewById(R.id.drawer_list_text);
            ImageView imgView = (ImageView) row.findViewById(R.id.imageView);

            if(this.loginAsGplus || this.loginAsFb) {
                Log.i("MyAdapter_Login@Ln261", "login as FB/G+");
                textView.setText(loginedValues[position]);
                imgView.setImageResource(myImages[position]);
            }
            else {
                Log.i("MyAdapter_Login@Ln266", "not logged in");
                textView.setText(defaultVal[position]);
                imgView.setImageResource(myImages[position]);
            }
            return row;
        }

    }
}
