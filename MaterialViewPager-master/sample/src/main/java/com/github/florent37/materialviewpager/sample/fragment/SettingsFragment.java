package com.github.florent37.materialviewpager.sample.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.github.florent37.materialviewpager.sample.LoginActivity;
import com.github.florent37.materialviewpager.sample.MainActivity;
import com.github.florent37.materialviewpager.sample.OnItemClick;
import com.github.florent37.materialviewpager.sample.R;
import com.github.florent37.materialviewpager.sample.SettingsAdapter;
import com.github.florent37.materialviewpager.sample.TestRecyclerViewAdapter;
import com.github.florent37.materialviewpager.sample.UpdateRequest;
import com.github.florent37.materialviewpager.sample.UserAreaActivity;
import com.github.florent37.materialviewpager.sample.Variable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment implements OnItemClick {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 1;
    Button editbutton;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    Variable variable;
    Context context;
    static Activity activity;
    public static SettingsFragment newInstance() { return new SettingsFragment(); }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

       // return inflater.inflate(R.layout.fragment_recyclerview, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<Object> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new Object());
        }

        //setup materialviewpager
        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(new SettingsAdapter(items,this));
        context = getActivity();

    }

    @Override
    public void Refresh(){
        ((MainActivity)getActivity()).refresh();//refresh fragment
    }
    /*
    public void logout(){
        activity = getActivity();
        activity.finishAffinity();

        // getActivity().finish();
    }
*/
}
