package fr.istic.mmm.tp4;

import android.app.Application;
import android.content.ContentValues;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by ayari on 10/11/15.
 */
public class ParseIntialize extends Application {


    final String APPLICATION_ID = "xpYNrl8H0xnjXG0A04wr92PIEeQcgpyIRsymmgFy";
    final String CLIENT_KEY = "X3AuD4R9P9etPZpS8Vl1IWGsqcg0SgcBRwHk7gee";

   //Initialise parse.com before any activity
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
     }
}