package com.busanit.ex10_movie2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieThread thread = new MovieThread();
                thread.start();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter = new MovieAdapter();
        recyclerView.setAdapter(adapter);
    }

    private class MovieThread extends Thread{
        @Override
        public void run() {
            ArrayList<Movie> movieList = new ArrayList<Movie>();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            String checkdate = df.format(cal.getTime());
            StringBuilder urlBuilder = new StringBuilder(
                    "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.xml");
            try {
                urlBuilder.append("?"+ URLEncoder.encode("key","UTF-8")+"=2425e7f093d9ae77591c3b891793f155");
                urlBuilder.append("&"+URLEncoder.encode("targetDt","UTF-8")+"="+checkdate);

                Document doc = Jsoup.connect(urlBuilder.toString()).parser(Parser.xmlParser()).get();
                Elements items = doc.select("dailyBoxOffice");
                for(int i=0; i<items.size();i++){
                    Element item = items.get(i);
                    Movie movie = new Movie();
                    movie.setRank(item.select("rank").text());
                    movie.setMovieNm(item.select("movieNm").text());
                    movie.setAudiAcc(item.select("audiAcc").text());
                    movie.setOpenDt(item.select("openDt").text());
                    movieList.add(movie);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setItems(movieList);
                        adapter.notifyDataSetChanged();
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}