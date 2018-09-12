package com.sun.demo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    List<Data> list;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycle);

        Bundle args = getIntent().getExtras();
        list = (List<Data>) args.get("datas");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        list.clear();
        recyclerView.setAdapter(new TableAdapter(MainActivity.this, list, ((App) getApplication()).getmTablename()));
    }

    class TableAdapter extends RecyclerView.Adapter {
        private MainActivity context;
        private List<Data> data;
        private String tableName;

//    private Date selectDate;

        public TableAdapter(MainActivity context, List<Data> data, String tableName) {
            this.context = context;
            this.data = data;
            this.tableName = tableName;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycle_item, viewGroup, false);
            return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            ViewHolder holder = (ViewHolder) viewHolder;
            final Data d = data.get(i);
            holder.setDeviceId(d.getDeviceId());
            holder.setTemperature(d.getLatestTemperature());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog = new DatePickerDialog(context, 0,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                                mYear = year;
//                                mMonth = month;
//                                mDay = dayOfMonth;
//                                mEdt.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
//                                selectDate = ;
//                                Log.e("cheng",year+" "+month+ " "+dayOfMonth+1);
                                    if (datePickerDialog != null) {
                                        datePickerDialog.cancel();

                                        new MyAsyncTask(d.getDeviceId(), new Date(year - 1900, month, dayOfMonth)).execute();
                                    }

                                }
                            },
                            d.getLatestTime().getYear(), d.getLatestTime().getMonth(), d.getLatestTime().getDay());
                    //设置起始日期和结束日期
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    datePicker.setMinDate(d.getEarliestTime().getTime());
                    datePicker.setMaxDate(d.getLatestTime().getTime());
                    datePickerDialog.show();
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView deviceIdTV;
            private TextView temperatureTV;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                deviceIdTV = itemView.findViewById(R.id.device_id);
                temperatureTV = itemView.findViewById(R.id.temperature);
            }

            void setDeviceId(String deviceId) {
                deviceIdTV.setText(deviceId);
            }

            void setTemperature(String temperature) {
                temperatureTV.setText(temperature);
            }
        }

        class MyAsyncTask extends AsyncTask<Void, Void, Boolean> {
            private String id;
            private Date date;
            private List<DataEveryDay> datas2;
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            Calendar cal = Calendar.getInstance();


            public MyAsyncTask(String id, Date date) {
                super();
                this.id = id;
                this.date = date;

            }

            @Override
            protected Boolean doInBackground(Void... strings) {
                try {
                    cal.setTime(date);
                    cal.add(Calendar.DATE, 1);
                    Date date2 = cal.getTime();

                    Class.forName("com.mysql.jdbc.Driver");
                    java.sql.Connection cn = DriverManager.getConnection("jdbc:mysql://47.100.21.245:3306/test", "root", "sunyurong");
                    String sql = "select datatime," + id + " from " + tableName + " where datatime>='" + mFormat.format(date) + "' AND datatime<'" + mFormat.format(date2) + "'";
                    Log.i("cheng", sql);
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    datas2 = new ArrayList<>();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    while (rs.next()) {
//                        datas2.add(new DataEveryDay(rs.getFloat(id), new Date(rs.getDate("datatime").getTime()).getTime()));
//                        Log.e("cheng", rs.getFloat(id) + " ooooo  " + new Date(rs.getDate("datatime").getTime()).getTime()+"  "+sdf.format(new Date(rs.getDate("datatime").getTime()).getTime()));
//                        datas2.add(new DataEveryDay(rs.getFloat(id), rs.getLong("datatime")));

                        datas2.add(new DataEveryDay(rs.getFloat(id),(rs.getTimestamp("datatime")).getTime()));
                        Log.e("cheng", rs.getFloat(id) + " ooooo  " + (rs.getTimestamp("datatime")).getTime()+"  "+sdf.format(rs.getTimestamp("datatime").getTime()));
                    }
                    cn.close();
                    st.close();
                    rs.close();
                    if (datas2.size() > 0) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    Log.e("cheng", "e111");
                    return false;
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.e("cheng", "e222");
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (aBoolean) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LineChartTime.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("datas2", (ArrayList<? extends Parcelable>) datas2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "无数据。。。。", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}
