package com.github.florent37.materialviewpager.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.github.florent37.materialviewpager.MaterialViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import io.fabric.sdk.android.Fabric;


/**
 * Created by florentchampigny on 27/05/2016.
 */
public class DrawerActivity extends AppCompatActivity {

    @BindView(R.id.materialViewPager)
    MaterialViewPager mView;

    Variable variable;
    private Handler mHandler;
    public static Context mContext;

    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private TextView nav_header_tv;
    private View navHeader;

    // dialog
    EditText edName;
    NumberPicker np;
    String sex="여";
    String area;
    OnItemClick onItemClick;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    // tags used to attach the fragments
    private static final String TAG_INFO = "info";
    private static final String TAG_PURSE = "purse";
    private static final String TAG_CO = "co";
    private static final String TAG_XIRO = "xiro";
    public static String CURRENT_TAG = TAG_INFO;

    // Index to identify current nav menu item
    public static int navIndex = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, 0, 0);
        mDrawer.setDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = mNavigationView.getHeaderView(0);
        nav_header_tv = (TextView) navHeader.findViewById(R.id.nav_header_textView);
        variable = (Variable) MainActivity.getapplication();
        nav_header_tv.setText(variable.getName());
        mHandler = new Handler();

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
        }

        // initializing navigation menu
        setUpNavigationView();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) ||
            super.onOptionsItemSelected(item);
    }
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            mDrawer.closeDrawers();

            return;
        }


        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                mView.getViewPager().setCurrentItem(navIndex);
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        mDrawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private void selectNavMenu() {
        mNavigationView.getMenu().getItem(navIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_info:
                        navIndex = 0;
                        CURRENT_TAG = TAG_INFO;
                        break;
                    case R.id.nav_purse:
                        navIndex = 1;
                        CURRENT_TAG = TAG_PURSE;
                        break;
                    case R.id.nav_co:
                        navIndex = 2;
                        CURRENT_TAG = TAG_CO;
                        break;
                    case R.id.nav_xiro:
                        navIndex = 3;
                        CURRENT_TAG = TAG_XIRO;
                        break;
                    case R.id.info_modify:
                        // launch new intent instead of loading fragment
                        show();
                        return true;
                    case R.id.logout:
                        // launch new intent instead of loading fragment
                        LogoutFunc();
                        mDrawer.closeDrawers();
                        return true;
                    default:
                        navIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });
    }

    Context context;
    void show()
    {
        context = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View infoFormView = inflater.inflate(R.layout.activity_alert_dialog_information, null);

        //NumberPicker for Age 0~150
        np = (NumberPicker) infoFormView.findViewById(R.id.picker);
        np.setMinValue(1);
        np.setMaxValue(150);
        np.setValue(variable.getAge());
        np.setWrapSelectorWheel(false);
        edName = (EditText)infoFormView.findViewById(R.id.infoName);
        edName.setText(variable.getName());

        RadioGroup rg   = (RadioGroup)infoFormView.findViewById(R.id.rgGender);
        RadioButton button1 = infoFormView.findViewById(R.id.rdWm);
        RadioButton button2 = infoFormView.findViewById(R.id.rdM);

        switch(variable.getSex()){  //default check
            case "여": default:
                button1.setChecked(true);
                button2.setChecked(false);
                break;
            case "남":
                button1.setChecked(false);
                button2.setChecked(true);
                break;
        }
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //체크된 라디오버튼id가 checkedId에 자동으로 들어가있음
                switch(checkedId){
                    //선택된 라디오버튼에 해당하는 색상으로 글자색상바꾸기
                    case R.id.rdWm:
                        sex = "여";
                        break;
                    case R.id.rdM:
                        sex = "남";
                        break;

                }
            }
        });

        Spinner Main_spinner = (Spinner)infoFormView.findViewById(R.id.spinner);

        //스피너 어댑터 설정
        ArrayAdapter adapter = ArrayAdapter.createFromResource(context,R.array.location,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Main_spinner.setAdapter(adapter);

        switch(variable.getArea()){ //set default selection
            case "서울":
                Main_spinner.setSelection(0); break;
            case "인천":
                Main_spinner.setSelection(1); break;
            case "대전":
                Main_spinner.setSelection(2); break;
            case "부산":
                Main_spinner.setSelection(3); break;
            case "제주":
                Main_spinner.setSelection(4); break;

        }


        //스피너 이벤트 발생
        Main_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                /// Toast.makeText(context, Integer.toString(position), Toast.LENGTH_SHORT).show();
                switch(position){
                    case 0: area ="서울";break;
                    case 1: area = "인천";break;
                    case 2: area = "대전";break;
                    case 3: area = "부산";break;
                    case 4: area = "제주";break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        builder.setView(infoFormView);

        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //mDrawer.closeDrawers();
                        Toast.makeText(context,"수정되었습니다.",Toast.LENGTH_LONG).show();
                        variable.setName(edName.getText().toString());
                        variable.setAge(np.getValue());
                        variable.setSex(sex);
                        variable.setArea(area);
                        //onItemClick.Refresh();  //refresh fragment
                        ((MainActivity)MainActivity.mContext).refresh();
                        UpdateDB(variable.getUsername(),variable.getName(),variable.getSex(),variable.getAge());//change db
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface d, int arg1) {
                                d.cancel();
                            };
                        };
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       // mDrawer.closeDrawers();
                        Toast.makeText(context,"수정이 취소되었습니다.",Toast.LENGTH_LONG).show();
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface d, int arg1) {
                                d.cancel();
                            };
                        };
                    }
                });
        builder.show();
    }

    public void UpdateDB(String username, String name,String sex, int age) {
        variable=(Variable) MainActivity.getapplication();

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UpdateRequest UpdateRequest = new UpdateRequest(username,name,sex,age, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(UpdateRequest);
    }

    Context context1;
    public void LogoutFunc() {
        context1 = this;
        pref = context1.getSharedPreferences("Login", Activity.MODE_PRIVATE);
        editor = pref.edit();

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context1);
        builder.setMessage("어플리케이션이 종료됩니다.")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        editor.clear();
                        editor.commit();
                        //SettingsFragment.logout();
                        System.exit(0);
                    }
                })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context1,"로그아웃이 취소되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        })
                .create()
                .show();

    }

}
