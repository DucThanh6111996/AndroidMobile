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
import android.widget.Toast;

import com.mta.studyenglish.R;
import com.mta.studyenglish.app.AppController;
import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.model.GrammarItem;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class GrammarAdapter extends BaseAdapter {

    private List<GrammarItem> grammarList;
    private Context mContext;
    private LayoutInflater lf;

    public GrammarAdapter(Context mContext) {
        this.mContext = mContext;
        lf = LayoutInflater.from(this.mContext);
        this.grammarList = new ArrayList();
    }

    public void setData(List<GrammarItem> grammarItemList) {
        this.grammarList = grammarItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return grammarList.size();
    }

    @Override
    public GrammarItem getItem(int position) {
        return grammarList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return grammarList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lf.inflate(R.layout.item_grammar_in_list, null);
        }

        Animation myAni = AnimationUtils.loadAnimation(mContext, R.anim.anim_show_item_listview);
        convertView.startAnimation(myAni);

        TextView tvIndex = convertView.findViewById(R.id.tv_grammar_index);
        TextView tvName = convertView.findViewById(R.id.tv_grammar_item_name);
        ImageView ivMark = convertView.findViewById(R.id.iv_is_mark_grammar);

        final GrammarItem item = grammarList.get(position);
        tvIndex.setText(item.getId() + "");
        tvName.setText(item.getName());
        setMarkBook(item, ivMark);

        ivMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getMark() == GrammarItem.NOT_MARKED) {
                    item.setMark(GrammarItem.MARKED);
                    Toast.makeText(mContext, "Đã đánh dấu", Toast.LENGTH_SHORT).show();
                } else {
                    item.setMark(GrammarItem.NOT_MARKED);
                }
                setMarkBook(item, (ImageView) v);
                MySqliteHelper sqliteHelper = AppController.getSqlitHelper();
                long resultUpdate = sqliteHelper.updateMarkInGrammarList(item.getId(), item.getMark());
            }
        });

        return convertView;
    }

    private void setMarkBook(GrammarItem item, ImageView ivMark) {
        if (item.getMark() == GrammarItem.NOT_MARKED) {
            ivMark.setImageResource(R.drawable.ic_star_dark);
        } else {
            ivMark.setImageResource(R.drawable.ic_star_yellow);
        }
    }
}
