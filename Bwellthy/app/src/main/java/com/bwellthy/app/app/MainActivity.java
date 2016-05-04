package com.bwellthy.app.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bwellthy.app.R;
import com.bwellthy.app.adapters.VocabularyAdapter;
import com.bwellthy.app.api.ApiFactory;
import com.bwellthy.app.api.ApiRequest;
import com.bwellthy.app.api.response.Vocabulary;
import com.bwellthy.app.storage.AppDB;
import com.bwellthy.app.utils.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private AppDB appDB;
    private RecyclerView vocabularyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        appDB = AppDB.getInstance(MainActivity.this);
        getVocabularyData();

    }

    private void initUI() {
        vocabularyList = (RecyclerView) findViewById(R.id.vocabulary_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        vocabularyList.setLayoutManager(mLayoutManager);
        vocabularyList.setItemAnimator(new DefaultItemAnimator());
    }

    private void postUI() {
        vocabularyList.setAdapter(new VocabularyAdapter(appDB.getAllWordsIgnoreRatio(MainActivity.this), MainActivity.this));
    }

    private void getVocabularyData() {
        ApiRequest getAllWords = ApiFactory.createService(ApiRequest.class);
        Call<Vocabulary> vocabulary = getAllWords.getVocabulary();
        Utility.showProgressDialog(MainActivity.this);
        vocabulary.enqueue(new Callback<Vocabulary>() {
            @Override
            public void onResponse(Call<Vocabulary> call, Response<Vocabulary> response) {
                Log.e("onResponse", "" + response.body().getWords().size());
                if (response.isSuccess()) {
                    appDB.bulkInsert(MainActivity.this, response.body().getWords());
                    Log.e("onResponse", "" + appDB.getAllWords(MainActivity.this).size());
                    Log.e("getAllWordsIgnoreRatio", "" + appDB.getAllWordsIgnoreRatio(MainActivity.this).size());
                    postUI();
                }
                Utility.dismissProgessDialog();
            }

            @Override
            public void onFailure(Call<Vocabulary> call, Throwable t) {
                Log.e("onFailure", "Error");
                Utility.dismissProgessDialog();
            }
        });
    }
}
