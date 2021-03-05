package abstractask.example.media;


import abstractask.example.media.androidtest.CalculatorActivity;
import abstractask.example.media.ch01.CameraIntent;
import abstractask.example.media.ch01.SizedCameraIntent;
import abstractask.example.media.androidtest.loghistory.LogHistoryMainActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    enum Chapter{
        CH01(0)
        ,TEST(1)
        ;
        int value;
        Chapter(int value){
            this.value = value;
        }
        public int getValue()
        {
            return this.value;
        }

        public static Chapter valueOf(int value){
            switch (value){
                case 0 : return CH01;
                case 1 : return TEST;
            }
            return CH01;
        }
    }
    static final int SETTING_ACTIVITY = 1;
    class Example {
        Example(Class<?> acls, String aDesc) {
            cls = acls;
            Name = cls.getSimpleName();

            // _ 앞쪽의 접두는 제목에서 제외한다.
            if (mOmitChapter) {
                int underbar = Name.indexOf('_');
                if (underbar != -1) {
                    Name = Name.substring(underbar + 1);
                }
            }
            Desc = aDesc;
        }
        Class<?> cls;
        String Name;
        String Desc;
    }

    public ArrayList<Example> arExample = new ArrayList<Example>();
    // 요청한 장의 예제들을 배열에 채운다.
    void FillExample(Chapter chapter) {
        arExample.clear();

        switch(chapter) {
            case CH01: // 1장.
                arExample.add(new Example(CameraIntent.class, "카메라앱 이용"));
                arExample.add(new Example(SizedCameraIntent.class, "카메라앱 이용 크기 변환"));
                break;
            case TEST: // 테스트
                arExample.add(new Example(LogHistoryMainActivity.class, "로그 히스토리 샘플"));
                arExample.add(new Example(CalculatorActivity.class, "계산 샘플"));
                break;
        }
    }
    String[] arChapter = {
            "카메라",
            "테스트 유닛",
    };

    ArrayAdapter<CharSequence> mAdapter;
    ListView mExamList;
    Spinner mSpinner;
    boolean mInitSelection = true;
    int mFontSize;
    int mBackType;
    boolean mDescSide;
    boolean mOmitChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExamList = (ListView)findViewById(R.id.examlist);
        mSpinner = (Spinner)findViewById(R.id.spinnerchapter);
        mSpinner.setPrompt("장을 선택하세요.");

        mAdapter = new ArrayAdapter<CharSequence>(this,
                android.R.layout.simple_spinner_item, arChapter);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 최초 전개시에도 Selected가 호출되는데 이때는 프레퍼런스에서 최후 장을 찾아 로드한다.
                // 이후부터는 사용자가 선택한 장을 로드한다.
                if (mInitSelection) {
                    mInitSelection = false;
                    SharedPreferences pref = getSharedPreferences("AndExam", 0);
                    int lastchapter = pref.getInt("LastChapter", 0);
                    mSpinner.setSelection(lastchapter);
                    ChangeChapter(lastchapter);
                } else {
                    // 장을 변경할 때마다 프레퍼런스에 기록한다.
                    ChangeChapter(position);
                    SharedPreferences pref = getSharedPreferences("AndExam", 0);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt("LastChapter", position);
                    edit.commit();
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ReadOption();

        // 자동실행 옵션의 디폴트는 false로 설정한다. 한 예제만 반복적으로 테스트할 때 이 옵션을
        // 사용하되 예외 처리가 어려우므로 왠만하면 사용하지 않는 것이 좋다.
        boolean bRunLast = false;
        if (bRunLast) {
            SharedPreferences pref = getSharedPreferences("AndExam", 0);
            int pkg = pref.getInt("LastChapter", 0);
            int pos = pref.getInt("LastPosition", 0);
            ChangeChapter(pkg);
            Intent intent = new Intent(this, arExample.get(pos).cls);
            startActivity(intent);
        }
    }

    public void ReadOption() {
        SharedPreferences pref = getSharedPreferences("AndExam", 0);
        mFontSize = pref.getInt("mFontSize", 1);
        mBackType = pref.getInt("mBackType", 0);
        mDescSide = pref.getBoolean("mDescSide", false);
        mOmitChapter = pref.getBoolean("mOmitChapter", false);;

        if (mBackType != 0) {
            mExamList.setBackgroundColor(0xff101010);
            mExamList.setDivider(new ColorDrawable(0xff606060));
            mExamList.setDividerHeight(1);
        } else {
            mExamList.setBackgroundColor(0xffe0e0e0);
            mExamList.setDivider(new ColorDrawable(0xff404040));
            mExamList.setDividerHeight(1);
        }
    }

    public void ChangeChapter(int chapter) {
        FillExample(Chapter.valueOf(chapter));
        ExamListAdapter Adapter = new ExamListAdapter(this);
        mExamList.setAdapter(Adapter);

        final Context ctx = this;
        mExamList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences pref = getSharedPreferences("AndExam", 0);
                SharedPreferences.Editor edit = pref.edit();
                edit.putInt("LastPosition", position);
                edit.commit();
                Intent intent = new Intent(ctx, arExample.get(position).cls);
                startActivity(intent);
            }
        });
    }

    public void mOnClick(View v) {
        SharedPreferences pref = getSharedPreferences("AndExam", 0);
        int lastchapter = pref.getInt("LastChapter", 0);
        switch (v.getId()) {
            case R.id.btnprev:
                if (lastchapter != 0) {
                    lastchapter--;
                    mSpinner.setSelection(lastchapter);
                }
                break;
            case R.id.btnnext:
                if (lastchapter != arChapter.length -1) {
                    lastchapter++;
                    mSpinner.setSelection(lastchapter);
                }
                break;
        }
    }


    //어댑터 클래스
    class ExamListAdapter extends BaseAdapter {
        Context maincon;
        LayoutInflater Inflater;

        public ExamListAdapter(Context context) {
            maincon = context;
            Inflater = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return arExample.size();
        }

        public String getItem(int position) {
            return arExample.get(position).Name;
        }

        public long getItemId(int position) {
            return position;
        }

        // 각 항목의 뷰 생성
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = Inflater.inflate(R.layout.andexamlist, parent, false);
            }

            LinearLayout examlayout = (LinearLayout)convertView.findViewById(R.id.examlayout);
            TextView txt1 = (TextView)convertView.findViewById(R.id.text1);
            TextView txt2 = (TextView)convertView.findViewById(R.id.text2);

            if (mDescSide) {
                examlayout.setOrientation(LinearLayout.HORIZONTAL);
            }

            if (mBackType != 0) {
                examlayout.setBackgroundResource(R.drawable.exambackdark);
                txt1.setTextColor(Color.WHITE);
                txt2.setTextColor(Color.LTGRAY);
            }

            switch (mFontSize) {
                case 0:
                    txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                    txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 9);
                    break;
                case 1:
                    txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
                    break;
                case 2:
                    txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                    txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    break;
            }

            txt1.setText(arExample.get(position).Name);
            txt2.setText(arExample.get(position).Desc);

            return convertView;
        }
    }

}