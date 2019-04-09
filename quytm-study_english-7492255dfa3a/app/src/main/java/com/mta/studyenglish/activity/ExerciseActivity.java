package com.mta.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.mta.studyenglish.R;
import com.mta.studyenglish.adapter.ExerciseAdapter;
import com.mta.studyenglish.app.AppController;
import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.model.ExerciseItem;
import com.mta.studyenglish.model.GrammarItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ExerciseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView lvExerciseList;
    private ExerciseAdapter adapter;

    private List<ExerciseItem> exerciseList;
    private GrammarItem grammarItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        initViews();
    }

    private void initViews() {
        lvExerciseList = findViewById(R.id.lv_exercise_list);
        adapter = new ExerciseAdapter(this);
        lvExerciseList.setAdapter(adapter);
        lvExerciseList.setOnItemClickListener(this);

        initData();
    }

    private void initData() {
        Bundle b = getIntent().getExtras();
        grammarItem = (GrammarItem) b.getSerializable("grammar_item");

        if (grammarItem == null) return;

        MySqliteHelper sqliteHelper = AppController.getSqlitHelper();
        exerciseList = sqliteHelper.findAllExerciseByGrammarId(grammarItem.getId());
        adapter.setData(exerciseList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ExerciseDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("exercise_item", exerciseList.get(position));
        intent.putExtras(b);
        startActivityForResult(intent, EXERCISE_DETAIL_CODE);
    }

    private static final int EXERCISE_DETAIL_CODE = 111;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EXERCISE_DETAIL_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle b = data.getExtras();
                ExerciseItem item = (ExerciseItem) b.getSerializable("exercise_item");
                for (int i = 0; i < exerciseList.size(); i++) {
                    if (item.getId() == exerciseList.get(i).getId()) {
                        exerciseList.set(i, item);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
