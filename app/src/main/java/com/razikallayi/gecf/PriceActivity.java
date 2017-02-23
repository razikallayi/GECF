package com.razikallayi.gecf;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.razikallayi.gecf.utils.FontUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PriceActivity extends AppCompatActivity {
    public LinkedHashMap<String, List<News>> mPriceNewsMap;
    public NewsAdapter mNewsAdapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

sendRequest();

    }

    private void sendRequest(){
        String url = "http://dailyapp.gecf.org/news/latest";
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getPriceNewsDataFromJson(response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

// Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
    }





    private void getPriceNewsDataFromJson(String PriceNewsOriginalJsonStr)
            throws JSONException {

        // Now we have a String representing the complete forecast in JSON Format.
        // Fortunately parsing is easy:  constructor takes the JSON string and converts it
        // into an Object hierarchy for us.

        // These are the names of the JSON objects that need to be extracted.
        final String TITLE = "title";
        final String CONTENT = "content";
        final String CATEGORY = "category";
        final String DATE = "date";
        final String NEWS = "news";
        final String NEWS_LIST = "news_listing";

        mPriceNewsMap = new LinkedHashMap<String, List<News>>();

        try {
            JSONObject gecfJson = new JSONObject(PriceNewsOriginalJsonStr);
            JSONArray newsArray = gecfJson.getJSONArray(NEWS_LIST);

            for(int i = 0; i < newsArray.length(); i++) {

                JSONObject newsPerDay = newsArray.getJSONObject(i);
                JSONArray newsPerDayArray = newsPerDay.getJSONArray(NEWS);

                for(int j = 0; j < newsPerDayArray.length(); j++) {
                    String date;
                    String title;
                    String content;
                    String category;

                    JSONObject eachNews = newsPerDayArray.getJSONObject(j);
//                    JSONArray eachNewsArray = eachNews.getJSONArray(NEWS);

                    News news = new News();
                    news.title = eachNews.getString(TITLE);
                    news.category = eachNews.getString(CATEGORY);
                    news.content = eachNews.getString(CONTENT);
                    news.date = eachNews.getString(DATE);

                    //Create a date key if not exist. Else add txn to the list
                    if (mPriceNewsMap.containsKey(news.date)) {
                        mPriceNewsMap.get(news.date).add(news);
                    } else {
                        mPriceNewsMap.put(news.date, new ArrayList<News>());
                        mPriceNewsMap.get(news.date).add(news);
                    }
                }
            }

            //Iterate through the linkedHashMap and set the Header
            List<ListItem> mItems = new ArrayList<>();
            for (String date : mPriceNewsMap.keySet()) {
                HeaderItem dateHeader = new HeaderItem();
                dateHeader.date = date;
                mItems.add(dateHeader);
                for (News news : mPriceNewsMap.get(date)) {
                    NewsItem item = new NewsItem();
                    item.news = news;
                    mItems.add(item);
                }
            }

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.priceNewsRecyclerView);
            mNewsAdapter = new NewsAdapter(getLayoutInflater(), mItems);
            recyclerView.setAdapter(mNewsAdapter);

        } catch (JSONException e) {
            Log.e("IZRA", e.getMessage(), e);
            e.printStackTrace();
        }
    }



    public abstract class ListItem {

        public static final int TYPE_HEADER = 1;
        public static final int TYPE_NEWS_ITEM = 2;

        abstract public int getType();
    }

    public class HeaderItem extends ListItem {
        private String date;

        @Override
        public int getType() {
            return TYPE_HEADER;
        }
    }

    public class NewsItem extends ListItem {
        private News news;

        // here getters and setters
        // for title and so on, built
        // using date

        @Override
        public int getType() {
            return TYPE_NEWS_ITEM;
        }
    }

    public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<ListItem> mItems;
        private LayoutInflater mLayoutInflater;

        public NewsAdapter(LayoutInflater mLayoutInflater, List<ListItem> mItems) {
            this.mLayoutInflater = mLayoutInflater;
            this.mItems = mItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ListItem.TYPE_HEADER) {
                View itemView = mLayoutInflater.inflate(R.layout.news_list_item_header, parent, false);
                return new HeaderViewHolder(itemView);
            } else {
                final View itemView = mLayoutInflater.inflate(R.layout.news_list_item, parent, false);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                return new NewsViewHolder(itemView);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int type = getItemViewType(position);
            if (type == ListItem.TYPE_HEADER) {
                HeaderItem header = (HeaderItem) mItems.get(position);
                HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
                viewHolder.dateTextView.setText(header.date);
            } else {
                NewsItem newsItem = (NewsItem) mItems.get(position);
                NewsViewHolder viewHolder = (NewsViewHolder) holder;
                viewHolder.title.setText(String.valueOf(newsItem.news.title));
                viewHolder.content.setText(String.valueOf(newsItem.news.content));
            }
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        @Override
        public int getItemViewType(int position) {
            return mItems.get(position).getType();
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            TextView dateTextView;

            public HeaderViewHolder(View itemView) {
                super(itemView);
                dateTextView = (TextView) itemView.findViewById(R.id.item_date);
            }
        }

        private class NewsViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView title;
            public final TextView content;

            public NewsViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                title = (TextView) itemView.findViewById(R.id.item_title);
                content = (TextView) itemView.findViewById(R.id.item_content);

                Typeface fontMuseoBold = FontUtils.getTypeface(getApplicationContext(), FontUtils.MUSEO_BOLD);
                Typeface fontMuseoLight = FontUtils.getTypeface(getApplicationContext(), FontUtils.MUSEO_LIGHT);
                title.setTypeface(fontMuseoBold);
                content.setTypeface(fontMuseoLight);
            }
        }
    }


}
