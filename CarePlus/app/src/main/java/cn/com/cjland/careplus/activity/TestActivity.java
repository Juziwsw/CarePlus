package cn.com.cjland.careplus.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.cjland.careplus.R;
import cn.com.cjland.careplus.adapter.TestBaseAdapter;

public class TestActivity extends Activity {
    private ListView listView;
    private TextView tv_des;
    Boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ListView lv=(ListView)findViewById(R.id.listview);
        List<String> data = new ArrayList<String>();
        for (int i=0;i<1;i++){
            data.add("极大降低娇滴滴大家反对反对反对方法打发打发打发打发打发反对方答复地方费打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发发打发打发打发打发费打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发打发");
        }
        TestBaseAdapter testBaseAdapter = new TestBaseAdapter(getBaseContext(),data,R.layout.activity_test_item);
        lv.setAdapter(testBaseAdapter);

//        tv_des = (TextView)findViewById(R.id.tv_des);//获取控件
//        //获取行数 目测是因为在TextView创建完毕之后才会Post这条消息
//        tv_des.post(new Runnable() {
//            @Override
//            public void run() {
//                Log.i("tv.getLineCount()", "" + tv_des.getLineCount());
//            }
//        });
//        //实现TextView的展开与收缩
//        tv_des.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("tv.getLineCount()", tv_des.getHeight() + "");
//                if (flag) {
//                    flag = false;
//                    tv_des.setEllipsize(null); // 展开
//                    tv_des.setSingleLine(flag);
//                    tv_des.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i("tv.getLineCount()", "" + tv_des.getLineCount());
//                        }
//                    });
//                } else {
//                    flag = true;
//                    tv_des.setLines(5);
//                    tv_des.setEllipsize(TextUtils.TruncateAt.END); // 收缩
//                    tv_des.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.i("tv.getLineCount()", "" + tv_des.getLineCount());
//                        }
//                    });
//                }
//            }
//        });

    }
}

