package com.cocojeong.coronawidget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    DataResult data=new DataResult();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        crawlingThread t = new crawlingThread(data);
        t.start();
        try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }

        TextView tv_dom = findViewById(R.id.tv_dom);
        TextView tv_over = findViewById(R.id.tv_over);

        tv_dom.setText(tv_dom.getText().toString()+data.domestic);
        tv_over.setText(tv_over.getText().toString()+data.overseas);
    }
}

class DataResult{
    public int domestic;
    public int overseas;
}

class crawlingThread extends Thread {

    DataResult data;

    public crawlingThread(DataResult data) { this.data=data; }
    @Override
    public void run() {
        super.run();
        try {
            Document doc = Jsoup.connect("http://ncov.mohw.go.kr/").get();
            Elements elements = doc.select("div.datalist ul li span.data");
            data.domestic = Integer.parseInt(elements.get(0).text());
            data.overseas = Integer.parseInt(elements.get(1).text());
//            Log.d("로그e사이즈",""+elements.size());
//            for(Element e : elements) Log.d("로그",e.text());

        } catch (IOException e) {
            Log.d("로그애러",""+e.toString());
            e.printStackTrace();
        }

    }
}