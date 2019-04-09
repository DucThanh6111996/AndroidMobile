package com.mta.studyenglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mta.studyenglish.R;
import com.mta.studyenglish.model.ExerciseItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ExerciseAdapter extends BaseAdapter {

    private List<ExerciseItem> exerciseList;
    private Context mContext;
    private LayoutInflater lf;

    public ExerciseAdapter(Context mContext) {
        this.mContext = mContext;
        lf = LayoutInflater.from(this.mContext);
        this.exerciseList = new ArrayList();
    }

    public void setData(List<ExerciseItem> exerciseList) {
        this.exerciseList = exerciseList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return exerciseList.size();
    }

    @Override
    public ExerciseItem getItem(int position) {
        return exerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.item_exercise_in_list, null);
        }

        Animation myAni = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_item_listview);
        convertView.startAnimation(myAni);

        TextView tvName = convertView.findViewById(R.id.tv_exercise_item_name);
        ImageView ivChecked = convertView.findViewById(R.id.iv_is_mark_grammar);

        final ExerciseItem item = exerciseList.get(position);
        tvName.setText("Câu hỏi " + item.getId());
        int pass = item.getPass();

        if (pass == ExerciseItem.PASSED || pass == ExerciseItem.NOT_PASSED) {
            ivChecked.setVisibility(View.VISIBLE);
            if (pass == ExerciseItem.NOT_PASSED) {
                ivChecked.setImageResource(R.drawable.ic_unchecked);
            } else {
                ivChecked.setImageResource(R.drawable.ic_checked);
            }
        } else {
            ivChecked.setVisibility(View.GONE);
        }

        return convertView;
    }

}
