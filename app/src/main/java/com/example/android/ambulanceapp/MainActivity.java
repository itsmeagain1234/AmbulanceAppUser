package com.example.android.ambulanceapp;



        import android.app.Activity;
        import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.EditText;
        import android.widget.Toast;
        import android.util.Log;
        import android.content.SharedPreferences.Editor;

        import java.util.Vector;

public class MainActivity extends Activity {
    EditText ET_USER_NAME,ET_USER_PASS;
    String login_name,login_pass;
    String u = BackgroundTask.user;

    //AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSharedPreferences("user_session", Context.MODE_PRIVATE).getString("username", "n") != "n" && getSharedPreferences("user_session", Context.MODE_PRIVATE).getString("userid", "n") != "n") {
            Intent intent = new Intent(MainActivity.this, CurrentLocationActivity.class);
            startActivity(intent);
            finish();
        }

        ET_USER_NAME=(EditText)findViewById(R.id.Username);
        ET_USER_PASS=(EditText)findViewById(R.id.Password);
        //alertDialog.setTitle("Debugging");


    }
    public void userReg(View view)
    {

        startActivity(new Intent(this, Register.class));
    }

    public void userLogin(View view) {
        login_name = ET_USER_NAME.getText().toString();
        login_pass = ET_USER_PASS.getText().toString();
        String method = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, login_name, login_pass);
        try {
            String username = (String) BackgroundTask.u.get(0);
            String userid = (String) BackgroundTask.u.get(1);
            if(username.equals("Fails")) {
                Toast.makeText(MainActivity.this, "Fucked!", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(MainActivity.this, CurrentLocationActivity.class);
                intent.putExtra("username", username);
                intent.putExtra("userid", userid);
                SharedPreferences sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE);
                Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("userid", userid);
                editor.commit();
                startActivity(intent);
                finish();
            }

        } catch(NullPointerException e) {
            //
        }


    }







}