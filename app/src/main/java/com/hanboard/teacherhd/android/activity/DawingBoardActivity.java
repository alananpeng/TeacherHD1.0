package com.hanboard.teacherhd.android.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.common.view.DawingBoard;

/**
 * 项目名称：TeacherHD
 * 类描述：电子白板
 * 创建人：kun.ren@hanboard.com
 * 作者单位：四川汉博德信息技术有限公司
 * 创建时间：2016/7/29
 **/
public class DawingBoardActivity extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout frameLayout;
    private Button btn_undo;
    private Button btn_redo;
    private Button btn_save;
    private Button btn_recover;
    private DawingBoard dawingBoard;
    private Button btn_paintcolor;
    private Button btn_paintstyle;
    private Button btn_paintsize;
    private SeekBar sb_size;

    private Button btn_curve;
    private Button btn_line;
    private Button btn_square;
    private Button btn_circle;
    private Button btn_oval;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dawingboard_text);
        ininView();
        initDate();
        initListener();
    }

    private void initListener() {
        btn_undo.setOnClickListener(this);
        btn_redo.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_recover.setOnClickListener(this);
        btn_paintstyle.setOnClickListener(this);
        btn_paintsize.setOnClickListener(this);
        btn_paintcolor.setOnClickListener(this);
        sb_size.setOnSeekBarChangeListener(new MySeekChangeListener());

        btn_curve.setOnClickListener(this);
        btn_line.setOnClickListener(this);
        btn_square.setOnClickListener(this);
        btn_circle.setOnClickListener(this);
        btn_oval.setOnClickListener(this);
    }

    class MySeekChangeListener implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            dawingBoard.selectPaintSize(seekBar.getProgress());
            //Toast.makeText(MainActivity.this, "当前画笔尺寸为" + seekBar.getProgress(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            dawingBoard.selectPaintSize(seekBar.getProgress());
            //Toast.makeText(MainActivity.this, "当前画笔尺寸为" + seekBar.getProgress(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Toast.makeText(DawingBoardActivity.this, "当前画笔尺寸为" + seekBar.getProgress(),Toast.LENGTH_SHORT).show();
        }
    }

    private void initDate() {
        //控制涂鸦板大小
        //dawingBoard = (DawingBoard) findViewById(R.id.view);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        int screenWidth = defaultDisplay.getWidth();
        int screenHeight = defaultDisplay.getHeight();
        dawingBoard = new DawingBoard(this, screenWidth, screenHeight);
        frameLayout.addView(dawingBoard);
        dawingBoard.requestFocus();
        dawingBoard.selectPaintSize(sb_size.getProgress());
    }

    private void ininView() {
        frameLayout = (FrameLayout) findViewById(R.id.fl_boardcontainer);
        btn_undo = (Button) findViewById(R.id.btn_undo);
        btn_redo = (Button) findViewById(R.id.btn_redo);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_recover = (Button) findViewById(R.id.btn_recover);
        btn_paintcolor = (Button) findViewById(R.id.btn_paintcolor);
        btn_paintsize = (Button) findViewById(R.id.btn_paintsize);
        btn_paintstyle = (Button) findViewById(R.id.btn_paintstyle);
        sb_size = (SeekBar) findViewById(R.id.sb_size);

        btn_curve = (Button) findViewById(R.id.btn_curve);
        btn_line = (Button) findViewById(R.id.btn_line);
        btn_square = (Button) findViewById(R.id.btn_square);
        btn_circle = (Button) findViewById(R.id.btn_circle);
        btn_oval = (Button) findViewById(R.id.btn_oval);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_undo:
                dawingBoard.undo();
                break;
            case R.id.btn_redo:
                dawingBoard.redo();
                break;
            case R.id.btn_recover:
                dawingBoard.recover();
                break;
            case R.id.btn_save:
                dawingBoard.saveToSDCard();
                break;
            case R.id.btn_paintcolor:
                sb_size.setVisibility(View.GONE);
                showPaintColorDialog(v);
                break;
            case R.id.btn_paintsize:
                sb_size.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_paintstyle:
                sb_size.setVisibility(View.GONE);
                showMoreDialog(v);
                break;
            case R.id.btn_curve:
                dawingBoard.setShape(DawingBoard.SHAPE_CURVE);
                break;
            case R.id.btn_line:
                dawingBoard.setShape(DawingBoard.SHAPE_LINE);
                break;
            case R.id.btn_square:
                dawingBoard.setShape(DawingBoard.SHAPE_SQUARE);
                break;
            case R.id.btn_circle:
                dawingBoard.setShape(DawingBoard.SHAPE_CIRCLE);
                break;
            case R.id.btn_oval:
                dawingBoard.setShape(DawingBoard.SHAPE_OVAL);
                break;

        }
    }

    private int select_paint_color_index = 0;
    private int select_paint_style_index = 0;
    //private int select_paint_size_index = 0;

    private void showPaintColorDialog(View parent) {
        AlertDialog.Builder alertDialgoBuilder = new AlertDialog.Builder(this);
        alertDialgoBuilder.setTitle("选择画笔颜色：");
        alertDialgoBuilder.setSingleChoiceItems(R.array.paintcolor, select_paint_color_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select_paint_color_index = which;
                dawingBoard.selectPaintColor(which);
                dialog.dismiss();
            }
        });
        alertDialgoBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });
        alertDialgoBuilder.create().show();
    }


    //弹出选择画笔或橡皮擦对话框
    public void showMoreDialog(View parent){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("选择画笔或橡皮擦：");
        alertDialogBuilder.setSingleChoiceItems(R.array.paintstyle, select_paint_style_index, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                select_paint_style_index = which;
                dawingBoard.selectPaintStyle(which);
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }

}