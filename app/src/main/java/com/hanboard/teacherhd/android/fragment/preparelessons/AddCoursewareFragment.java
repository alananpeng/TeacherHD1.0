package com.hanboard.teacherhd.android.fragment.preparelessons;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.adapter.ResAddGdAdapter;
import com.hanboard.teacherhd.android.entity.LoadRes;
import com.hanboard.teacherhd.common.base.BaseFragment;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目名称：TeacherHD1.0
 * 类描述：
 * 创建人：peng.an@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/8/10 0010 14:15
 */
public class AddCoursewareFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    @BindView(R.id.add_lessons_courseware_gridview)
    GridView addLessonsCoursewareGridview;
    public static final int FILE_CODE = 200;
    private List<LoadRes> loadResList = new ArrayList<>();
    private ResAddGdAdapter mAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_add_lessons_courseware, container, false);
    }

    @Override
    protected void initData() {
        LoadRes loadRes = new LoadRes();
        loadRes.format="ADD";
        loadRes.name = "添加课件";
        loadResList.add(loadRes);
        mAdapter = new ResAddGdAdapter(context,R.layout.add_res_load_item,loadResList);
        addLessonsCoursewareGridview.setAdapter(mAdapter);
        addLessonsCoursewareGridview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LoadRes loadRes = (LoadRes) parent.getAdapter().getItem(position);
        if (loadRes.format.equals("ADD"))
        {
            Intent i = new Intent(context, FilePickerActivity.class);
            i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, true);
            i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
            i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);
            i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());
            startActivityForResult(i, FILE_CODE);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    ClipData clip = data.getClipData();

                    if (clip != null) {
                        for (int i = 0; i < clip.getItemCount(); i++) {
                            Uri uri = clip.getItemAt(i).getUri();
                            //文件路径
                            String path = getRealFilePath(uri);
                            //文件格式
                            String format = path.substring(path.indexOf(".")+1, path.length());
                            File tempFile = new File(path.trim());
                            if(format.equals("ppt")||format.equals("pptx")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="PPT";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }else if(format.equals("doc")||format.equals("docx")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="WORD";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }else if(format.equals("xlsx")||format.equals("xls")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="EXCEL";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }else if(format.equals("mp3")||format.equals("wav")||format.equals("wma")||format.equals("ape")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="AUDIO";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }else if(format.equals("mkv")||format.equals("mp4")||format.equals("avi")||format.equals("rm")||format.equals("rmvb")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="VIDEO";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }else if(format.equals("pdf")){
                                LoadRes loadRes = new LoadRes();
                                loadRes.format="PDF";
                                loadRes.name = tempFile.getName();
                                loadRes.path = path;
                                loadResList.add(loadRes);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    ArrayList<String> paths = data.getStringArrayListExtra
                            (FilePickerActivity.EXTRA_PATHS);
                    if (paths != null) {
                        for (String path: paths) {
                            Uri uri = Uri.parse(path);
                        }
                    }
                }

            } else {
                Uri uri = data.getData();
            }
        }
    }
    /**
     * @param uri
     * @return the file path or null
     */
    private String getRealFilePath(final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = getActivity().getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

}
