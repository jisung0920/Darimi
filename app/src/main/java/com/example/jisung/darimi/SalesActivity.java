package com.example.jisung.darimi;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SalesActivity extends AppCompatActivity {
    Intent intent;
    TextView time_N;
    String time;

    TextView start_tv, finish_tv;
    TextView year, month;
    GridView calendar_gridview;
    ArrayList<String> day_list = new ArrayList<String>();
    CalendarAdapter adapter;
    Calendar my_calendar;

        ExpandableListView sales_listview;
    private ArrayList<Sales> mGroupList = new ArrayList<Sales>();
//    private ArrayList<ArrayList<Sales>> mChildList = new ArrayList<ArrayList<Sales>>();
//    private ArrayList<Sales> mChildListContent = new ArrayList<Sales>();
    SalesAdpater sales_adpater;
//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        init();
        open_calendar();
    }

    void init() {
        Intent gintent = getIntent();
        time = gintent.getStringExtra("time");
        time_N = (TextView) findViewById(R.id.time);
        time_N.setText(time);

        start_tv = (TextView) findViewById(R.id.sales_start_tv);
        finish_tv = (TextView) findViewById(R.id.sales_finish_tv);
        year = (TextView) findViewById(R.id.sales_year_tv);
        month = (TextView) findViewById(R.id.sales_month_tv);
        calendar_gridview = (GridView) findViewById(R.id.sales_calendar);
        adapter = new CalendarAdapter(day_list, this);


        long now = System.currentTimeMillis();
        final Date date = new Date(now);
        final SimpleDateFormat curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        final SimpleDateFormat curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        final SimpleDateFormat curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        start_tv.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date) + "." + curDayFormat.format(date));
        finish_tv.setText(curYearFormat.format(date) + "." + curMonthFormat.format(date) + "." + curDayFormat.format(date));

        year.setText(curYearFormat.format(date));
        month.setText(curMonthFormat.format(date));

        my_calendar = Calendar.getInstance();
        my_calendar.set(Integer.parseInt(curYearFormat.format(date)), Integer.parseInt(curMonthFormat.format(date)) - 1, 1);
//        int dayNum = my_calendar.get(Calendar.DAY_OF_WEEK);
//        for (int i = 1; i < dayNum; i++) {
//            day_list.add("");
//        }
        setCalendarDate(my_calendar.get(Calendar.YEAR), my_calendar.get(Calendar.MONTH) + 1);
        adapter = new CalendarAdapter(day_list, getApplicationContext());
        calendar_gridview.setAdapter(adapter);
        adapter.getTitleYear(year.getText().toString());
        adapter.getTitleMonth(month.getText().toString());
        adapter.getStartYear(start_tv.getText().toString().substring(0, 4));
        adapter.getStartMonth(start_tv.getText().toString().substring(5, 7));
        adapter.getStartDay(start_tv.getText().toString().substring(8));
        adapter.getFinishYear(finish_tv.getText().toString().substring(0, 4));
        adapter.getFinishMonth(finish_tv.getText().toString().substring(5, 7));
        adapter.getFinishDay(finish_tv.getText().toString().substring(8));
        adapter.notifyDataSetChanged();

        sales_listview = (ExpandableListView) findViewById(R.id.sales_list);
        Sales s1 = new Sales("main",1);
        Sales s2 = new Sales("main2",2);
        s1.sublist.add(new Sales("sub1",1));
        s2.sublist.add(new Sales("sub2",2));
        mGroupList.add(s1);
        mGroupList.add(s2);
//        mGroupList.add(new Sales("main",1));
//        mGroupList.add(new Sales("main2",2));
        sales_adpater = new SalesAdpater(mGroupList, this);
        sales_listview.setAdapter(sales_adpater);
//        LayoutInflater inflater = getLayoutInflater();
//        View v = inflater.inflate(R.layout.sublist, null);
//        sub_sales_listview = (ExpandableListView)v.findViewById(R.id.sales_subsublist);
//        //test
//        mGroupList.add(new Sales("1", 1));
//        mGroupList.add(new Sales("2", 2));
//        mChildListContent.add(new Sales("main", 1));
////        mChildListContent.add(new Sales("ed", 2));
//        mChildList.add(mChildListContent);
//        mChildList.add(mChildListContent);
//
//        sub_mChildListContent.add(new Sales("sub", 1));
//        sub_mChildList.add(sub_mChildListContent);
//        //
//        sales_adpater = new SalesAdpater(mGroupList, mChildList, this);
//        sub_sales_adpater = new SalesSubAdapter(mChildListContent, sub_mChildList, this);
//        sales_listview.setAdapter(sales_adpater);
//        sub_sales_listview.setAdapter(sub_sales_adpater);
//        sales_listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
//                return false;
//            }
//        });


    }

    private void setCalendarDate(int year, int month) {
        day_list.add("일");
        day_list.add("월");
        day_list.add("화");
        day_list.add("수");
        day_list.add("목");
        day_list.add("금");
        day_list.add("토");
        my_calendar.set(year, month - 1, 1);
        int dayNum = my_calendar.get(Calendar.DAY_OF_WEEK);
        for (int i = 1; i < dayNum; i++) {
            day_list.add("");
        }
        my_calendar.set(Calendar.MONTH, month - 1);
        for (int i = 0; i < my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            day_list.add("" + (i + 1));
        }
    }

    void open_calendar() {
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SalesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener,
                        Integer.parseInt(start_tv.getText().toString().substring(0, 4)), Integer.parseInt(start_tv.getText().toString().substring(5, 7)) - 1, Integer.parseInt(start_tv.getText().toString().substring(8)));
                datePickerDialog.show();
            }
        });
        finish_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SalesActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, listener2,
                        Integer.parseInt(finish_tv.getText().toString().substring(0, 4)), Integer.parseInt(finish_tv.getText().toString().substring(5, 7)) - 1, Integer.parseInt(finish_tv.getText().toString().substring(8)));
                datePickerDialog.show();
            }
        });
    }

    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            if (i > Integer.parseInt(finish_tv.getText().toString().substring(0, 4)) || i1 + 1 > Integer.parseInt(finish_tv.getText().toString().substring(5, 7))) {

                Toast.makeText(SalesActivity.this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
            } else if (i == Integer.parseInt(finish_tv.getText().toString().substring(0, 4)) && i1 + 1 == Integer.parseInt(finish_tv.getText().toString().substring(5, 7))
                    && i2 > Integer.parseInt(finish_tv.getText().toString().substring(8))) {

                Toast.makeText(SalesActivity.this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
            } else {
                if (i1 + 1 < 10 && i2 < 10) {
                    start_tv.setText(i + ".0" + (i1 + 1) + ".0" + i2);

                } else if (i1 - 1 < 10) {
                    start_tv.setText(i + ".0" + (i1 + 1) + "." + i2);

                } else if (i2 < 10) {
                    start_tv.setText(i + "." + (i1 + 1) + ".0" + i2);

                } else {
                    start_tv.setText(i + "." + (i1 + 1) + "." + i2);
                }
            }
            adapter.getStartYear(String.valueOf(i));
            adapter.getStartMonth(String.valueOf(i1 + 1));
            adapter.getStartDay(String.valueOf(i2));
            adapter.notifyDataSetChanged();
        }
    };
    DatePickerDialog.OnDateSetListener listener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            if (i < Integer.parseInt(start_tv.getText().toString().substring(0, 4)) || i1 + 1 > Integer.parseInt(start_tv.getText().toString().substring(5, 7))) {

                Toast.makeText(SalesActivity.this, "끝나는 날짜가 시작 날짜보다 작을 수는 없습니다", Toast.LENGTH_LONG).show();
            } else if (i == Integer.parseInt(start_tv.getText().toString().substring(0, 4)) && i1 + 1 == Integer.parseInt(start_tv.getText().toString().substring(5, 7))
                    && i2 < Integer.parseInt(start_tv.getText().toString().substring(8))) {

                Toast.makeText(SalesActivity.this, "끝나는 날짜가 시작 날짜보다 작을 수는 없습니다", Toast.LENGTH_LONG).show();
            } else {
                if (i1 + 1 < 10 && i2 < 10) {
                    finish_tv.setText(i + ".0" + (i1 + 1) + ".0" + i2);

                } else if (i1 - 1 < 10) {
                    finish_tv.setText(i + ".0" + (i1 + 1) + "." + i2);

                } else if (i2 < 10) {
                    finish_tv.setText(i + "." + (i1 + 1) + ".0" + i2);

                } else {
                    finish_tv.setText(i + "." + (i1 + 1) + "." + i2);
                }
            }
            adapter.getFinishYear(String.valueOf(i));
            adapter.getFinishMonth(String.valueOf(i1 + 1));
            adapter.getFinishDay(String.valueOf(i2));
            adapter.notifyDataSetChanged();
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manageA:
                intent = new Intent(this, ManageActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;
            case R.id.orderA:
                intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                break;

            case R.id.settingA:
                intent = new Intent(this, SettingActivity.class);
                intent.putExtra("time", time);
                startActivity(intent);
                break;

            case R.id.sales_start_left_btn:
                String start_day = start_tv.getText().toString();
                String start_year = start_day.substring(0, 4);
                String start_month = start_day.substring(5, 7);
                String start_day_of_month = start_day.substring(8);
                set_calendar_term(start_year, start_month, start_day_of_month, start_tv, 0, 1);
                break;
            case R.id.sales_start_right_btn:
                String start_day_ = start_tv.getText().toString();
                String start_year_ = start_day_.substring(0, 4);
                String start_month_ = start_day_.substring(5, 7);
                String start_day_of_month_ = start_day_.substring(8);
                if (start_day_.equals(finish_tv.getText().toString())) {
                    Toast.makeText(this, "시작 날짜가 끝나는 날짜보다 클 수는 없습니다", Toast.LENGTH_LONG).show();
                } else {
                    set_calendar_term(start_year_, start_month_, start_day_of_month_, start_tv, 1, 1);
                }
                break;

            case R.id.sales_finish_left_btn:
                String finish_day = finish_tv.getText().toString();
                String finish_year = finish_day.substring(0, 4);
                String finish_month = finish_day.substring(5, 7);
                String finish_day_of_month = finish_day.substring(8);
                if (finish_day.equals(start_tv.getText().toString())) {
                    Toast.makeText(this, "끝나는 날짜가 시작 날짜보다 작을 수 없습니다.", Toast.LENGTH_LONG).show();
                } else {
                    set_calendar_term(finish_year, finish_month, finish_day_of_month, finish_tv, 0, 2);
                }
                break;
            case R.id.sales_finish_right_btn:
                String finish_day_ = finish_tv.getText().toString();
                String finish_year_ = finish_day_.substring(0, 4);
                String finish_month_ = finish_day_.substring(5, 7);
                String finish_day_of_month_ = finish_day_.substring(8);
                set_calendar_term(finish_year_, finish_month_, finish_day_of_month_, finish_tv, 1, 2);
                break;

            case R.id.sales_calendar_left_btn:
                day_list.clear();
                int prev_month = Integer.parseInt(month.getText().toString()) - 1;
                int prev_year = Integer.parseInt(year.getText().toString());
                if (prev_month < 1) {
                    prev_year -= 1;
                    year.setText(String.valueOf(prev_year));
                    prev_month = 12;
                }
                setCalendarDate(prev_year, prev_month);
                month.setText(String.valueOf(prev_month));
                adapter.getTitleYear(year.getText().toString());
                adapter.getTitleMonth(month.getText().toString());
                adapter.notifyDataSetChanged();
                break;
            case R.id.sales_calendar_right_btn:
                day_list.clear();
                int next_month = Integer.parseInt(month.getText().toString()) + 1;
                int next_year = Integer.parseInt(year.getText().toString());
                if (next_month > 12) {
                    next_year += 1;
                    year.setText(String.valueOf(next_year));
                    next_month = 1;
                }
                setCalendarDate(next_year, next_month);
                month.setText(String.valueOf(next_month));
                adapter.getTitleYear(year.getText().toString());
                adapter.getTitleMonth(month.getText().toString());
                adapter.notifyDataSetChanged();
                break;

            default:
                break;
        }
    }

    void set_calendar_term(String year, String month, String day, TextView set_tv, int flag, int flag2) {
        int year_ = Integer.parseInt(year);
        int month_ = Integer.parseInt(month);
        int day_ = 0;
        my_calendar.set(Calendar.MONTH, month_ - 1);
        if (flag == 1) {//right btn
            day_ = Integer.parseInt(day) + 1;
            if (day_ > my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                day_ = 1;
                month_ += 1;
            }
            if (month_ > 12) {
                year_ += 1;
                month_ = 1;
            }
            if (day_ < 10 && month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + ".0" + day_);
            } else if (day_ < 10) {
                set_tv.setText(year_ + "." + month_ + ".0" + day_);

            } else if (month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + "." + day_);
            } else {

                set_tv.setText(year_ + "." + month_ + "." + day_);
            }
        } else if (flag == 0) {//left btn
            day_ = Integer.parseInt(day) - 1;
            if (day_ < 1) {
                day_ = my_calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                month_ -= 1;
            }
            if (month_ < 1) {
                year_ -= 1;
                month_ = 12;
            }
            if (day_ < 10 && month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + ".0" + day_);
            } else if (day_ < 10) {
                set_tv.setText(year_ + "." + month_ + ".0" + day_);

            } else if (month_ < 10) {
                set_tv.setText(year_ + ".0" + month_ + "." + day_);
            } else {
                set_tv.setText(year_ + "." + month_ + "." + day_);
            }
        }
        if (flag2 == 1) {
            adapter.getStartYear(String.valueOf(year_));
            adapter.getStartMonth(String.valueOf(month_));
            adapter.getStartDay(String.valueOf(day_));
        } else if (flag2 == 2) {
            adapter.getFinishYear(String.valueOf(year_));
            adapter.getFinishMonth(String.valueOf(month_));
            adapter.getFinishDay(String.valueOf(day_));
        }
        adapter.notifyDataSetChanged();
    }


    public void sales_list_filter_Click(View v) {
        switch (v.getId()) {
            case R.id.sales_list_day_btn:

                break;
            case R.id.sales_list_month_btn:

                break;
            case R.id.sales_list_year_btn:

                break;
        }
    }
}
