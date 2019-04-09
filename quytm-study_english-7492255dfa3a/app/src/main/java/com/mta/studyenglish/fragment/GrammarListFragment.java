package com.mta.studyenglish.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.mta.studyenglish.R;
import com.mta.studyenglish.activity.MainActivity;
import com.mta.studyenglish.adapter.GrammarAdapter;
import com.mta.studyenglish.app.AppController;
import com.mta.studyenglish.helper.MySqliteHelper;
import com.mta.studyenglish.model.GrammarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * đơn thuần là dùng để search các bản ghi trong DB cho các case "Show tất cả" hoặc "show các bài đã đc đánh dấu"
 * vì việc truy xuất DB tốn thời gian nên a k chạy nó trên main thread (vì nó gây giật lag)
 * tạo thread mới , để lấy dữ liệu, lấy xong thành công thì gửi 1 cái message cho Handler
 * thằng Handler lúc đó mới update UI
 */

public class GrammarListFragment extends Fragment implements AdapterView.OnItemClickListener {
    public static final int FILTER_ALL_GRAMMAR = 0;
    public static final int FILTER_MARKED_GRAMMAR = 1;


    private GrammarAdapter adapter;
    private List<GrammarItem> grammarList;

    private View rootView;
    private Context mContext;
    private ListView lvGrammarList;
    private ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_grammar_list, null);
        mContext = getActivity();
        grammarList = new ArrayList<>();

        initViews();

        return rootView;
    }

    private void initViews() {
        adapter = new GrammarAdapter(mContext);
        lvGrammarList = rootView.findViewById(R.id.lv_grammar_list);
        lvGrammarList.setAdapter(adapter);
        lvGrammarList.setOnItemClickListener(this);

        filter(FILTER_ALL_GRAMMAR);
    }

    public void filter(final int filter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MySqliteHelper sqliteHelper = AppController.getSqlitHelper();
                if (filter == FILTER_ALL_GRAMMAR) {
                    grammarList = sqliteHelper.findAllGrammar();
                } else if (filter == FILTER_MARKED_GRAMMAR) {
                    grammarList = sqliteHelper.findAllMarkedGrammar();
                }

                Message msg = new Message();
                msg.setTarget(mHandler);
                msg.sendToTarget();
            }
        }).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity mainActivity = (MainActivity) mContext;
        mainActivity.goToGrammarDetail(grammarList.get(position));
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressBar = rootView.findViewById(R.id.pb_loading_grammar);
            progressBar.setVisibility(View.GONE);
            adapter.setData(grammarList);
        }
    };
}
