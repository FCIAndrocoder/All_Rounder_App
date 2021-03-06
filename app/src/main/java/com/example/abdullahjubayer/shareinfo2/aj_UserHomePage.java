package com.example.abdullahjubayer.shareinfo2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.abdullahjubayer.shareinfo2.Admin.Admin_Home_Avt;
import com.example.abdullahjubayer.shareinfo2.User.Home_User;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class aj_UserHomePage extends AppCompatActivity implements SearchView.OnQueryTextListener{

    FirebaseFirestore db;
    ArrayList<String>title=new ArrayList<>();
    ArrayList<String>image=new ArrayList<>();
    ListView listView;
    ProgressBar progressBar ;
    FirebaseAuth auth;
    SharedPreferences preferences;
    String user_email;
    aj_User_home_page_Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aj_user_home);
        db = FirebaseFirestore.getInstance();

        auth=FirebaseAuth.getInstance();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("User Home Page");
        listView=findViewById(R.id.list_view);

        preferences=this.getSharedPreferences("com.example.abdullahjubayer.shareinfo2",MODE_PRIVATE);
        user_email=preferences.getString("email",null);


        progressBar = (ProgressBar)findViewById(R.id.spin_kit_user_home_page);
        FadingCircle fadingCircle = new FadingCircle();
        progressBar.setIndeterminateDrawable(fadingCircle);

        loadData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aj_user_home, menu);
        MenuItem menuItem = menu.findItem(R.id.SerchMenuId);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.go_to_home) {

            Intent intent=new Intent(aj_UserHomePage.this,Home_User.class);
           /* intent.putExtra("title",title.get(position));
            intent.putExtra("image",image.get(position));
            intent.putExtra("Description",description.get(position));
            intent.putExtra("Company",company.get(position));
            startActivity(intent);*/
            finishActivity(0);
        }

        switch (id){
            case R.id.adminFragment:
                Intent intent=new Intent(getApplicationContext(), Admin_Home_Avt.class);
                startActivity(intent);
                break;
            case R.id.logout:
                preferences.edit().clear().apply();
                Intent i=new Intent(getApplicationContext(),aj_MainActivity.class);
                startActivity(i);
                finish();
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        DocumentReference user = db.collection("Admin_Account").document();
        user.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                        db.collection("Admin_Account").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                                title.clear();
                                image.clear();
                                for (DocumentSnapshot snapshot : documentSnapshots) {

                                    String compName = snapshot.get("Company").toString();
                                    String compImage = snapshot.get("Picture").toString();

                                    if (!compName.isEmpty() && !compImage.isEmpty()) {
                                        title.add(compName);
                                        image.add(compImage);
                                    } else {
                                        Toast.makeText(aj_UserHomePage.this, "Error in Home Page", Toast.LENGTH_LONG).show();
                                    }

                                }
                                if (user_email !=null){
                                    adapter = new aj_User_home_page_Adapter(getApplicationContext(),title,image,user_email);
                                    adapter.notifyDataSetChanged();
                                    listView.setAdapter(adapter);
                                    progressBar.setVisibility(View.INVISIBLE);
                                }else {
                                    Toast.makeText(aj_UserHomePage.this, "Error in Home Page", Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(aj_UserHomePage.this, "Home Page not found", Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent(aj_UserHomePage.this,Home_User.class);
                intent.putExtra("Company",title.get(position));
                intent.putExtra("Image",image.get(position));
                startActivity(intent);
                finishActivity(0);
            }
        });

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.getFilter().filter(s);
        return false;
    }
}
