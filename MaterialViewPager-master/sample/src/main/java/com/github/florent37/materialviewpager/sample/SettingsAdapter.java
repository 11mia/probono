package com.github.florent37.materialviewpager.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialviewpager.sample.fragment.SettingsFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import static com.github.florent37.materialviewpager.sample.MainActivity.variable;

public class SettingsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Object> contents;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;
    Variable variable;
    EditText edName;
    NumberPicker np;String sex="여";
    String area;
    OnItemClick onItemClick;
    public SettingsAdapter(List<Object> contents, OnItemClick listener) {
        this.contents = contents;
        this.onItemClick=listener;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_HEADER: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.fragment_settings, parent, false);
                Button button=(Button)view.findViewById(R.id.edit);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.v("test","button clicked!");
                        show(v);    //dialog

                    }
                });
                variable=(Variable) MainActivity.getapplication();

                TextView tv_name = (TextView)view.findViewById(R.id.name);
                tv_name.setText(variable.getName());
                TextView tv_age = (TextView)view.findViewById(R.id.age);
                tv_age.setText(Integer.toString(variable.getAge()));
                TextView tv_sex = (TextView)view.findViewById(R.id.sex);
                tv_sex.setText(variable.getSex());
                TextView tv_area = (TextView)view.findViewById(R.id.area);
                tv_area.setText(variable.getArea());

                return new RecyclerView.ViewHolder(view) {
                };

            }
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_big, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
        }
        return null;
    }

    Context context;
    void show(View v)
    {
        context=v.getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //builder.setTitle("사용자 정보 수정");


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
                        sex = "여";break;
                    case R.id.rdM:sex = "남";break;

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
                        Toast.makeText(context,"수정되었습니다.",Toast.LENGTH_LONG).show();
                        variable.setName(edName.getText().toString());
                        variable.setAge(np.getValue());
                        variable.setSex(sex);
                        variable.setArea(area);
                        onItemClick.Refresh();  //refresh fragment
                        UpdateDB(variable.getUsername(),variable.getName(),variable.getSex(),variable.getAge());//change db
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"수정이 취소되었습니다.",Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                break;
            case TYPE_CELL:
                break;
        }
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
/*
                    if (success) {
                      //  Toast.makeText(context,"테이블이 수정되었습니다.",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context,"실패하였습니다.",Toast.LENGTH_LONG).show();
                    }
*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        UpdateRequest UpdateRequest = new UpdateRequest(username,name,sex,age, responseListener);
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(UpdateRequest);
    }



}