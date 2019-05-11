package com.example.semana1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewActivity extends AppCompatActivity {

    private static final String TAC="NewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setupUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAC", "Estoy en Resume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAC", "Estoy en Start");
    }

    @Override
    protected void onPause() {
        Log.d("TAC", "Estoy en Pause");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.d("TAC", "Estoy en Destroy");
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Log.d("TAC", "Estoy en Stop");
        super.onStop();
    }

    public boolean hasInternetAccess()
    {
        ConnectivityManager  cm = (ConnectivityManager)
        getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void downloadContent(String url) throws IOException
    {
        String response = "";
        URL urlToFetch = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) urlToFetch.openConnection();
        InputStream stream = urlConnection.getInputStream();
        response = readStream(stream);
        urlConnection.disconnect();
        EditText urlRerult = (EditText) findViewById(R.id.edit_UrlCont);
        urlRerult.setText(response);
    }

    public String readStream(InputStream stream) {
        Reader reader = new InputStreamReader(stream);
        BufferedReader buffer = new BufferedReader(reader);
        String response = "";
        String chunkJustRead = "";
        try
        {
            while   ((chunkJustRead = buffer.readLine()) != null)
            {
                response += chunkJustRead;
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return response;
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String>
    {
        private ProgressDialog mProgress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(NewActivity.this);
            mProgress.setMessage("Downloading website content");
            mProgress.show();
        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();
            Request request = null;
            try {
                request = new Request.Builder().url(url[0]).build();
            }
            catch (Exception e)
            {
                Log.i(TAC,"Error in the URL");
                return "Download Failed";
            }
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful())
                {
                    return response.body().string();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
            return "Download Failed";
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
            EditText urlRerult = (EditText) findViewById(R.id.edit_UrlCont);
            urlRerult.setText(result);
            mProgress.dismiss();
        }


    }


    private void setupUI()
    {
        Button obtienUrl = (Button) findViewById(R.id.button_CargaUrl);
        obtienUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui va el codigo
                if (hasInternetAccess())
                {
                    EditText strUrl = (EditText) findViewById(R.id.edit_Url);
                    String urlfi = strUrl.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Hay Internet",Toast.LENGTH_SHORT).show();
                    try {
                        downloadContent(urlfi);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Hay Internet",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button obtienUrlAsync = (Button) findViewById(R.id.button_CargaUrlAsync);
        obtienUrlAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //aqui va el codigo
                if (hasInternetAccess())
                {
                    EditText strUrl = (EditText) findViewById(R.id.edit_Url);
                    String urlfi = strUrl.getText().toString();
                    //Toast.makeText(getApplicationContext(),"Hay Internet",Toast.LENGTH_SHORT).show();
                    try {
                        DownloadWebPageTask task = new DownloadWebPageTask();
                        task.execute(new String[] {urlfi});

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Hay Internet",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
