package com.blogspot.softlabsja.iiucstudentapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.Cookies;
import com.blogspot.softlabsja.iiucstudentapp.LoginAndCookies.UpdateSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class PushD {
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private final Activity activity;

    public PushD(Activity activity) {
        this.activity = activity;
    }

    public void save() {
        Thread thread = new Thread(new Runnable() {
            @SuppressLint("CommitPrefEdits")
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect("https://www.iiuc.ac.bd/student/course-status")
                            .cookies(Cookies.getCookies(activity))
                            .timeout(20000)
                            .get();
                    String text = doc.html();
                    if (text.contains("id=\"tab-1")) {
                        String semester = doc.select("table > tbody").get(0).select("tr").get(2).select("td").get(1).select("b").text();
                        try {
                            Document doc2 = Jsoup.connect("https://www.iiuc.ac.bd/admin/profile")
                                    .cookies(Cookies.getCookies(activity))
                                    .timeout(20000)
                                    .get();
                            String text2 = doc2.html();
                            if (text2.contains("id=\"tab-1\"")) {
                                Elements t = doc2.select("table > tbody");
                                String name = t.get(1).select("tr").get(1).select("td").get(1).text();
                                String gender = t.get(1).select("tr").get(8).select("td").get(1).text();
                                if(gender.equalsIgnoreCase("male")){
                                    SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                    String id = prefs.getString("id", "");
                                    String img = prefs.getString("img","");
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/male");
                                    helperClassFirebase helper = new helperClassFirebase(id, name, semester, gender,img);
                                    reference.child(id).setValue(helper);
                                }else{
                                    SharedPreferences prefs = activity.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                                    String id = prefs.getString("id", "");
                                    String img = prefs.getString("img","");
                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user/female");
                                    helperClassFirebase helper = new helperClassFirebase(id, name, semester, gender,img);
                                    reference.child(id).setValue(helper);
                                }

                            } else if (text.contains("user_id")) {
                                UpdateSession updateSession = new UpdateSession();
                                updateSession.Update(activity);
                                save();
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(activity, "Update Session First", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            } else {
//                                activity.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(activity, "Not Pound First", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (text.contains("user_id")) {
                        UpdateSession updateSession = new UpdateSession();
                        updateSession.Update(activity);
                        save();
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity, "Update Session Last", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } else {
//                        activity.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(activity, "Not Pound Last", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
