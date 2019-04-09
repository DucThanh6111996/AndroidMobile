package com.mta.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mta.studyenglish.R;
import com.mta.studyenglish.app.AppController;
import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.model.GrammarDetailItem;
import com.mta.studyenglish.model.GrammarItem;

/**
 *
 */

public class GrammarDetailActivity extends AppCompatActivity {

    private GrammarItem grammarItem;
    private GrammarDetailItem grammarDetailItem;

    private TextView tvTitle;
    private TextView tvDetails;
    private Button btnExercise;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar_detail);

        initToolBar();

        initViews();

    }

    private void initToolBar() {
        Bundle b = this.getIntent().getExtras();
        grammarItem = (GrammarItem) (b != null ? b.getSerializable("item_grammar") : null);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initViews() {
        tvTitle = findViewById(R.id.tv_grammar_title);
        tvDetails = findViewById(R.id.tv_grammar_details);
        btnExercise = findViewById(R.id.btn_exercise);

        tvTitle.setText(grammarItem.getName());
        MySqliteHelper sqliteHelper = AppController.getSqlitHelper();
        grammarDetailItem = sqliteHelper.findGrammarDetailByListId(grammarItem.getId());
        if (grammarDetailItem != null) {
            tvDetails.setText(grammarDetailItem.getDescription());
        } else {
            tvDetails.setText("Not have data");
        }

        btnExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("grammar_item", grammarItem);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
