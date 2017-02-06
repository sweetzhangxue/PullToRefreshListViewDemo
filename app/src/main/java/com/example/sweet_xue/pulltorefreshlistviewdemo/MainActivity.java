package com.example.sweet_xue.pulltorefreshlistviewdemo;

import java.util.ArrayList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sweet_xue.pulltorefreshlistviewdemo.PullToRefreshListView.PullToRefreshBase;
import com.example.sweet_xue.pulltorefreshlistviewdemo.PullToRefreshListView.PullToRefreshBase.Mode;
import com.example.sweet_xue.pulltorefreshlistviewdemo.PullToRefreshListView.PullToRefreshBase.OnRefreshListener2;
import com.example.sweet_xue.pulltorefreshlistviewdemo.PullToRefreshListView.PullToRefreshListView;
import com.example.sweet_xue.pulltorefreshlistviewdemo.adapter.CommonAdapter;
import com.example.sweet_xue.pulltorefreshlistviewdemo.adapter.CommonViewHolder;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> arrayList;

    private PullToRefreshListView pullToRefreshListView;

    private CommonAdapter<String> adapter;

    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<String>();

        for (int i = 0; i < 50; i++) {
            arrayList.add("item" + i);
        }

        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.listView_UnderwayOrder);

        pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "点击了item" + position, Toast.LENGTH_SHORT).show();
            }
        });


        pullToRefreshListView.setMode(Mode.BOTH);


        adapter = new CommonAdapter<String>(MainActivity.this, R.layout.layout, arrayList) {
            @Override
            public void convert(CommonViewHolder holder, String model, int position) {
                holder.<TextView>getView(R.id.tv).setText(model);
            }
        };

        pullToRefreshListView.setAdapter(adapter);

        pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage = 0;
                getList(currentPage * 10);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                currentPage++;
                getList(currentPage * 10);
            }
        });

    }

    private void getList(int offset) {


        if (currentPage == 0) {
            pullToRefreshListView.setMode(Mode.BOTH);
            arrayList.clear();
        }

        ArrayList<String> arrayList1 = new ArrayList<String>();
        for (int i = 0; i < 10 + offset; i++) {
            arrayList1.add("item" + i);
        }
        arrayList.addAll(arrayList1);
        adapter.notifyDataSetChanged();
        pullToRefreshListView.onRefreshComplete();

    }
}
