package com.nyan.johan.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static String base_url = "http://api.androidhive.info/contacts/";

    RecyclerView rvContacts;
    ArrayList<Contacts> contactList;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lookup the recyclerview in activity layout
        rvContacts = (RecyclerView) findViewById(R.id.recyclerSenpai);

        setupRecyclerView();

        //ENABLE THIS AND DISABLE loadJsonFromUrl() load to load Json from Local
//        loadJsonFromLocal();

        //ENABLE THIS AND DISABLE loadJsonFromLocal() to load Json from URL / Server
        loadJsonFromUrl();

    }

    private void loadJsonFromLocal() {

        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            //We get json in here from the request,
            //so now we need to Parse the json to model.
            Log.v("v", "raw data we get  :" + "\n" + json);

            // Getting JSON Array contacts
            JSONArray contacts = json.getJSONArray("contacts");

            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                //get all item in contacts
                String id = c.getString("id");
                String name = c.getString("name");
                String email = c.getString("email");
                String address = c.getString("address");
                String gender = c.getString("gender");

                //bind the string to model Class
                Contacts contactss = new Contacts();
                contactss.setId(id);
                contactss.setName(name);
                contactss.setEmail(email);
                contactss.setAddress(address);
                contactss.setGender(gender);

                // adding contact to contact list
                contactList.add(contactss);
                Log.v("v", "contactList size " + contactList.size());
            }

            //to change UI, must do it in UI thread.
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //after complete add all item in contactList , notify the adapter
                    // to refresh
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void loadJsonFromUrl() {

        // Initialized http client
        OkHttpClient client = new OkHttpClient();

        //Setup the request
        Request request = new Request.Builder()
                .url(base_url)
                .build();

        //Set a callback to request so we will know when it complete or failed.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("e","whyyyy D:");

                //If request go here
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //In Async method
                //we use Async method to parse Json in other thread, so it will not interfered with
                // UI thread.

                //Do network operation in UI thread will lead to AnR (Application Not Response)

                if (!response.isSuccessful()){
                    Log.e("e","whyyyy D:");
                    //If failed go here
                    throw new IOException("Unexcepted code " + response);
                } else {
                    Log.v("v","yay successss ");
                    //If request success goes here

                    try {
                        String responseData = response.body().string();

                        JSONObject json = new JSONObject(responseData);
                        //We get json in here from the request,
                        //so now we need to Parse the json to model.
                        Log.v("v","raw data we get  :"  + "\n" + responseData);

                        // Getting JSON Array contacts
                        JSONArray contacts = json.getJSONArray("contacts");

                        // looping through All Contacts
                        for (int i = 0; i < contacts.length(); i++) {
                            JSONObject c = contacts.getJSONObject(i);

                            //get all item in contacts
                            String id = c.getString("id");
                            String name = c.getString("name");
                            String email = c.getString("email");
                            String address = c.getString("address");
                            String gender = c.getString("gender");

                            //bind the string to model Class
                            Contacts contactss = new Contacts();
                            contactss.setId(id);
                            contactss.setName(name);
                            contactss.setEmail(email);
                            contactss.setAddress(address);
                            contactss.setGender(gender);

                            // adding contact to contact list
                            contactList.add(contactss);
                            Log.v("v","contactList size " + contactList.size());
                        }

                        //to change UI, must do it in UI thread.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //after complete add all item in contactList , notify the adapter
                                // to refresh
                                adapter.notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        //When parsing goes wrong , error here
                    }

                }

            }
        });

    }

    private void setupRecyclerView() {
        //initialized ArrayList
        contactList = new ArrayList<>();
        //setup our adapter, pass list and context of this activity to adapter
        adapter = new ContactAdapter(MainActivity.this, contactList);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        //add line at list item
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
        rvContacts.addItemDecoration(itemDecoration);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager
                (MainActivity.this));
        // That's all!

        //We can also enable optimizations if the items are static and will not change for significantly smoother scrolling:
        rvContacts.setHasFixedSize(true);

    }

    public String loadJSONFromAsset() {
        //Load json in teh asset files
        String json = null;
        try {
            InputStream is = getAssets().open("pure.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}
