package fr.istic.mmm.tp4;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by soudou on 08/10/15.
 */
public class Main3Activity extends Activity
{


    private ListView maListViewPerso;
    private SimpleAdapter mListAdapter;
    private ArrayList<HashMap<String, String>> listItem;

    EditText inputSearch;
    Button rafraichir;
    Reciever reciever;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        rafraichir =(Button) findViewById(R.id.rafraichir);


        maListViewPerso = (ListView) findViewById(R.id.listviewperso);
        listItem = new ArrayList<HashMap<String, String>>();
        mListAdapter = new SimpleAdapter (this.getBaseContext(), listItem, R.layout.listviewitem,
                new String[] {"img", "name", "surname", "town", "birth"}, new int[] {R.id.img, R.id.name, R.id.surname, R.id.town, R.id.birth});



        displayContentProvider();
        // start intent
        IntentFilter intentFilter= new IntentFilter(Reciever.PROCESS_RESPONSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        reciever= new Reciever();
        registerReceiver(reciever, intentFilter);
        intent = new Intent(this, RefreshService.class);
        startService(intent);

        // refresh list
        rafraichir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayContentProvider();
            }
        });
    }


public  void onRestart(){
    super.onRestart();
    displayContentProvider();
}

    // add customer to  parse server

    public void addCustomer(View v) {
        Intent intent = new Intent(Main3Activity.this,MainActivity.class);
        startActivity(intent);
    }





    //add customers from Parse to ContentProvider

    private void displayContentProvider() {


        // fill dataBase from parse server

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Customer");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> customerList, ParseException e) {
                getContentResolver().delete(MyContentProvider.CONTENT_URI, null, null);

                if (e == null) {
                    for (ParseObject customer : customerList) {

                        ContentValues client = new ContentValues();
                        client.put(SharedIformation.Customers.Customer_NAME, customer.getString("name"));
                        client.put(SharedIformation.Customers.Customer_SURNAME, customer.getString("surname"));
                        client.put(SharedIformation.Customers.Customer_TOWN, customer.getString("town"));
                        client.put(SharedIformation.Customers.Customer_DATEOFBORTH, customer.getString("DateOfBirth"));
                        getContentResolver().insert(MyContentProvider.CONTENT_URI, client);
                    }
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });

       // display list of customer from local database

        HashMap<String, String> map;

        Uri uri = MyContentProvider.CONTENT_URI;
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        listItem.clear();
        mListAdapter.notifyDataSetChanged();
        if (c.moveToFirst()) {
            do {

                map = new HashMap<String, String>();
                map.put("id", c.getString(c.getColumnIndex(SharedIformation.Customers.Customer_ID)) );
                map.put("name", c.getString(c.getColumnIndex(SharedIformation.Customers.Customer_NAME)) );
                map.put("surname", c.getString(c.getColumnIndex(SharedIformation.Customers.Customer_SURNAME)));
                map.put("town", c.getString(c.getColumnIndex(SharedIformation.Customers.Customer_TOWN)));
                map.put("birth", c.getString(c.getColumnIndex(SharedIformation.Customers.Customer_DATEOFBORTH)));
                map.put("img", String.valueOf(R.mipmap.man3));

                listItem.add(map);

            } while (c.moveToNext());
        }
        maListViewPerso.setAdapter(mListAdapter);


    }

    // class reciever will execute displayContentProvider evrey 2 seconds
    public class Reciever extends BroadcastReceiver{

        public static final String PROCESS_RESPONSE="fr.istic.mmm.tp4.intent.action.PROCESS_RESPONSE";
        @Override
        public void onReceive(Context context, Intent intent) {
            displayContentProvider();
            Log.d("infoormation ", "-------success refresh --------");
        }
    }
}
