package com.razikallayi.gecf;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.razikallayi.gecf.utils.FontUtils;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ImageView searchBtn = (ImageView) findViewById(R.id.home_search);
        ImageView menuBtn = (ImageView) findViewById(R.id.home_menu);
        TextView gecfTitle = (TextView) findViewById(R.id.home_gecf_title);
        TextView gecfDesc = (TextView) findViewById(R.id.home_gecf_desc);
        Button btnCategory = (Button) findViewById(R.id.home_btn_category);
        Button btnList = (Button) findViewById(R.id.home_btn_list);
        Button btnPrice = (Button) findViewById(R.id.home_btn_price);

        ImageView cat_1 = (ImageView) findViewById(R.id.cat_1);
        ImageView cat_2 = (ImageView) findViewById(R.id.cat_2);
        ImageView cat_3 = (ImageView) findViewById(R.id.cat_3);
        ImageView cat_4 = (ImageView) findViewById(R.id.cat_4);
        ImageView cat_5 = (ImageView) findViewById(R.id.cat_5);
        ImageView cat_7 = (ImageView) findViewById(R.id.cat_7);
        ImageView cat_8 = (ImageView) findViewById(R.id.cat_8);
        ImageView cat_9 = (ImageView) findViewById(R.id.cat_9);
        ImageView cat_10 = (ImageView) findViewById(R.id.cat_10);
        ImageView cat_11 = (ImageView) findViewById(R.id.cat_11);

        cat_1.setOnClickListener(this);

        Typeface fontMuseoBold = FontUtils.getTypeface(getApplicationContext(), FontUtils.MUSEO_BOLD);
        Typeface fontMuseoLight = FontUtils.getTypeface(getApplicationContext(), FontUtils.MUSEO_LIGHT);
        gecfTitle.setTypeface(fontMuseoBold);
        gecfDesc.setTypeface(fontMuseoLight);
        btnCategory.setTypeface(fontMuseoBold);
        btnList.setTypeface(fontMuseoBold);
        btnPrice.setTypeface(fontMuseoBold);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.RIGHT);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);


            //for aapplying a font to subMenu ...
//            SubMenu subMenu = mi.getSubMenu();
//            if (subMenu!=null && subMenu.size() >0 ) {
//                for (int j=0; j <subMenu.size();j++) {
//                    MenuItem subMenuItem = subMenu.getItem(j);
//                    applyFontToMenuItem(subMenuItem);
//                }
//            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = FontUtils.getTypeface(getApplicationContext(), FontUtils.MUSEO_BOLD);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


    @Override
    public void onClick(View view) {
        Log.d("IZRA", "onClick: ");
        int id = view.getId();
        switch (id) {
            case R.id.cat_1:
                Log.d("IZRA", "onClick: cat_1");
                startActivity(new Intent(getApplicationContext(), PriceActivity.class));
                break;
            default:
                startActivity(new Intent(getApplicationContext(), PriceActivity.class));
                Log.d("IZRA", "onClick: default");
        }
    }
}
