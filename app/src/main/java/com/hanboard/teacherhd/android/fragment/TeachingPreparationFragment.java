package com.hanboard.teacherhd.android.fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.activity.SelectTextBookActivity;
import com.hanboard.teacherhd.android.adapter.TextBookAllChapterAdapter;
import com.hanboard.teacherhd.android.adapter.TreeListViewAdapter;
import com.hanboard.teacherhd.android.entity.Chapter;
import com.hanboard.teacherhd.android.entity.Elements;
import com.hanboard.teacherhd.android.entity.params.GetLessons;
import com.hanboard.teacherhd.android.entity.tree.Node;
import com.hanboard.teacherhd.android.model.ISelectTextBookModel;
import com.hanboard.teacherhd.android.model.impl.SelectTextBookModelImpl;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.hanboard.teacherhd.common.callback.IDataCallback;
import com.hanboard.teacherhd.common.view.LoadingDialog;
import com.hanboard.teacherhd.lib.common.utils.SharedPreferencesUtils;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 项目名称：TeacherHD
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/3 0003 11:32
 */
public class TeachingPreparationFragment extends BaseFragment implements IDataCallback<Elements<Chapter>>,TreeListViewAdapter.OnTreeNodeClickListener {
    @BindView(R.id.list_textbook)
    ListView mListTextbook;
    private ISelectTextBookModel iSelectTextBookModel;
    public static final int REQUSET = 1;
    private TextBookAllChapterAdapter<Chapter> mAdapter;
    private List<Chapter> listChapter = new ArrayList<>();
    private LoadingDialog loading;
    private Timer timer;
    private  int recLen=Integer.MAX_VALUE;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_teaching_preparation, container, false);
    }
    @Override
    protected void initData(){
        iSelectTextBookModel = new SelectTextBookModelImpl();
        loading = new LoadingDialog(context,"玩命加载中...");
        String pageNum = "1";
        String accountId = (String) SharedPreferencesUtils.getParam(context,"id","");
        iSelectTextBookModel.getTextbookChapter(pageNum,accountId,this);
    }
    @OnClick({R.id.rel_add_textbook})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_add_textbook:
                startActivityForResult(new Intent(context, SelectTextBookActivity.class),REQUSET);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUSET) {
            String pageNum = "1";
            String accountId = (String) SharedPreferencesUtils.getParam(context,"id","");
            iSelectTextBookModel.getTextbookChapter(pageNum,accountId,this);
        }
    }
    @Override
    public void onSuccess(Elements<Chapter> data){
        try {
            mAdapter = new TextBookAllChapterAdapter<Chapter>(mListTextbook,context,data.elements, 0);
            mListTextbook.setAdapter(mAdapter);
            mAdapter.setOnTreeNodeClickListener(this);
            loading.dismiss();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onError(String msg, int code) {
        loading.dismiss();
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClick(Node node, int position) {
        if(node.getLevel()==0){
            SharedPreferencesUtils.setParam(context,"textBookId",node.getCid());
            SharedPreferencesUtils.setParam(context,"subject",node.getName());
        }
        if(node.getLevel()!=0){
            GetLessons getLessons = new GetLessons();
            getLessons.chapterId = node.getCid();
            SharedPreferencesUtils.setParam(context,"chapterId",node.getCid());
            getLessons.textBookId = (String) SharedPreferencesUtils.getParam(context,"textBookId","");
            getLessons.subject = (String) SharedPreferencesUtils.getParam(context,"subject","");
            EventBus.getDefault().post(getLessons);
        }
    }
}

