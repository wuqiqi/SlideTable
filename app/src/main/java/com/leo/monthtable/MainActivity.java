package com.leo.monthtable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leo.monthtable.bean.TestTableContentBean;
import com.leo.monthtable.bean.TestTableFirstColumn;
import com.leo.monthtable.bean.TestTableFirstRow;
import com.leo.monthtable.utils.DateUtil;
import com.leo.tablelibrary.SlideTableView;
import com.leo.tablelibrary.bean.TableContentBean;
import com.leo.tablelibrary.bean.TableFirstColumnBean;
import com.leo.tablelibrary.bean.TableFirstRowBean;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private TextView tvSelDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //默认加载今天的日期
        tvSelDate = (TextView) findViewById(R.id.tv_sel_date);
        tvSelDate.setText(DateUtil.getYearAndMonth());
        configureTableView(0);
    }

    private void configureTableView(int col) {
        //获取月份的天数
        int currentMonthDays = DateUtil.getMonthDays(tvSelDate.getText().toString());
        Log.e("Leo", "日数：" + currentMonthDays);
        //行数
        int rows = 100;

        List<TableFirstRowBean> dateData = new ArrayList<>();
        for (int i = 0; i < currentMonthDays; i++) {
            TestTableFirstRow testTableFirstRow = new TestTableFirstRow();
            int day = i + 1;
            testTableFirstRow.date = tvSelDate.getText().toString() + "-" + day;
            dateData.add(testTableFirstRow);
        }

        List<TableFirstColumnBean> staffData = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            TestTableFirstColumn tableFirstColumn = new TestTableFirstColumn();
            tableFirstColumn.name = "A" + i;
            staffData.add(tableFirstColumn);
        }

        List<List<TableContentBean>> contentData = new ArrayList<>();
        List<TableContentBean> list = new ArrayList<>();
        for (int i = 0; i < currentMonthDays; i++) {
            TestTableContentBean testTableContentBean = new TestTableContentBean();
            testTableContentBean.content = "U";
            list.add(testTableContentBean);
        }

        for (int i = 0; i < rows; i++) {
            contentData.add(list);
        }

        SlideTableView slideTableView = (SlideTableView) findViewById(R.id.cTableView);
        slideTableView.setTableAdapter(new TestTableAdapter(this, dateData, staffData, contentData));
        //滑动到指定位置
        slideTableView.slideToSomeColAndRow(col, col);
        //定义第一个表格
        slideTableView.addFirstView(LayoutInflater.from(this).inflate(R.layout.item_first_view, null, false));
    }


    //点击切换日期
    public void changeDate(View view) {
        new CDatePickerDialog(tvSelDate.getText().toString(), false).showDatePickDlg(this, new CDatePickerDialog.OnCDateSelectListener() {
            @Override
            public void onDateSel(int year, int month, int dayOfMonth) {
                tvSelDate.setText(year + "-" + month);
                configureTableView(month);
            }
        }, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {

            }
        });

    }
}
