package com.mta.studyenglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mta.studyenglish.R;
import com.mta.studyenglish.model.ExerciseItem;

/**
 *
 */

public class ExerciseDetailActivity extends AppCompatActivity {

    private ExerciseItem exerciseItem;
    private TextView tvQuestion;
    private RadioButton rbAnswerA;
    private RadioButton rbAnswerB;
    private RadioButton rbAnswerC;
    private ImageView ivCheck;
    private Button btnCheck;
    private RadioGroup rgbAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        Bundle b = getIntent().getExtras();
        exerciseItem = (ExerciseItem) b.getSerializable("exercise_item");

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle("Câu hỏi " + exerciseItem.getId());

        initViews();
    }

    private void initViews() {
        tvQuestion = findViewById(R.id.tv_question);
        rbAnswerA = findViewById(R.id.rb_answer_a);
        rbAnswerB = findViewById(R.id.rb_answer_b);
        rbAnswerC = findViewById(R.id.rb_answer_c);
        ivCheck = findViewById(R.id.iv_check);
        btnCheck = findViewById(R.id.btn_check);
        rgbAnswer = findViewById(R.id.rbg_answer);

        tvQuestion.setText(exerciseItem.getQuestion());
        rbAnswerA.setText(exerciseItem.getAnswerA());
        rbAnswerB.setText(exerciseItem.getAnswerB());
        rbAnswerC.setText(exerciseItem.getAnswerC());

        rgbAnswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_answer_a:
                        if (exerciseItem.getTrueAnswer().equals("A"))
                            exerciseItem.setPass(ExerciseItem.PASSED);
                        else exerciseItem.setPass(ExerciseItem.NOT_PASSED);
                        break;
                    case R.id.rb_answer_b:
                        if (exerciseItem.getTrueAnswer().equals("B")) 
                            exerciseItem.setPass(ExerciseItem.PASSED);
                        else exerciseItem.setPass(ExerciseItem.NOT_PASSED);
                        break;
                    case R.id.rb_answer_c:
                        if (exerciseItem.getTrueAnswer().equals("C"))
                            exerciseItem.setPass(ExerciseItem.PASSED);
                        else exerciseItem.setPass(ExerciseItem.NOT_PASSED);
                        break;
                }
                ivCheck.setVisibility(View.INVISIBLE);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pass = exerciseItem.getPass();
                if (pass == ExerciseItem.PASSED || pass == ExerciseItem.NOT_PASSED) {
                    if (exerciseItem.getPass() == ExerciseItem.PASSED) {
                        ivCheck.setImageResource(R.drawable.ic_unchecked);
                    } else {
                        ivCheck.setImageResource(R.drawable.ic_checked);
                    }
                    ivCheck.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("exercise_item", exerciseItem);
                    intent.putExtras(b);
                    setResult(RESULT_OK, intent);
                } else {
                    ivCheck.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
