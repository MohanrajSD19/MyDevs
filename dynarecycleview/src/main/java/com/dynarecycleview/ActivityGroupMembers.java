package com.dynarecycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityGroupMembers extends AppCompatActivity implements RemoveClickListner{

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Button btnAddItem;
    ArrayList<RecyclerData> myList = new ArrayList<>();
   // EditText etTitle, etDescription;
    AutoCompleteTextView etTitle;
    String title = "",description = "";
    ImageView crossImage;
    ImageButton imgBtn_switch_views;
    public Bundle getBundle = null;
    String groupCount="";
    int mGroupCount=0;
    boolean isShowingGrid =false;
    TextView txtVw_groupcount;
    String[] mPeopels_1={"Kiruthika","Dinesh ","Mohan","Mellwyn","Subash","sasi","Priya"};
    String[] mPeopels={"Kiruthika Ayyasamy","DineshKumar Palanisamy ","Mohanraj Sambath","Mellwyn Joesph","Subash Sundarraj","Sasikumar Kannaiyan","Sathya Priya Perumal"};
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isViewWithCatalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);
        try {
            getBundle = this.getIntent().getExtras();
            groupCount = getBundle.getString("groupcount");
            mGroupCount= Integer.parseInt(groupCount);
        }catch (Exception e){
            e.printStackTrace();
        }

        txtVw_groupcount =(TextView) findViewById(R.id.txtVw_groupcount);
        imgBtn_switch_views = (ImageButton) findViewById(R.id.imgBtn_switch_views);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerAdapter = new RecyclerAdapter(myList,this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //------------------------



        //------------------------



        etTitle = (AutoCompleteTextView) findViewById(R.id.etTitle);
        //etDescription = (EditText) findViewById(R.id.etDescription);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        txtVw_groupcount.setText("Enter the Names of the "+mGroupCount+" Group members one at a time");
        ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1,mPeopels);

        etTitle.setAdapter(adapter);
        etTitle.setThreshold(1);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                //description = etDescription.getText().toString();
                if (title.matches("")) {
                    Toast.makeText(v.getContext(), "You did not enter a Title", Toast.LENGTH_SHORT).show();
                    return;
                }
                /*if (description.matches("")) {
                    Toast.makeText(v.getContext(), "You did not enter a description", Toast.LENGTH_SHORT).show();
                    return;
                }*/


               /*
                RecyclerData mLog = new RecyclerData();
                mLog.setTitle(title);
                mLog.setDescription(description);
                myList.add(mLog);
                mRecyclerAdapter.notifyData(myList);*/
               RecyclerData mLog = new RecyclerData();
                mLog.setTitle(title);
                /*if(myList.size()>=0 && myList.size()<2) {
                    myList.add(mLog);
                    mRecyclerAdapter.notifyData(myList);
                    etTitle.setText("");
                    etTitle.setFocusable(true);
                    etTitle.setCursorVisible(true);
                    //etDescription.setText("");
                }else{

                    etTitle.setText("You Reach Maximum");
                    etTitle.setFocusable(false);
                    etTitle.setCursorVisible(false);

                }*/

                if(myList.size()==mGroupCount) {
                    etTitle.setText("You Reach Maximum");
                    //etTitle.setFocusable(false);
                    //etTitle.setCursorVisible(false);
                }else if(myList.size()<mGroupCount){

                    myList.add(mLog);
                    mRecyclerAdapter.notifyData(myList);
                    etTitle.setText("");
                    etTitle.setFocusable(true);
                    etTitle.setCursorVisible(true);
                    //etDescription.setText("");

                }

            }
        });

        imgBtn_switch_views.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerLayout();
            }});
    }
    @Override
    public void OnRemoveClick(int index) {
        myList.remove(index);
        //mRecyclerAdapter.notifyData(myList);
        if(myList.size()>=0 && myList.size()<mGroupCount) {
            mRecyclerAdapter.notifyData(myList);
            etTitle.setText("");
            etTitle.setFocusable(true);
            etTitle.setCursorVisible(true);
            //etDescription.setText("");
        }else{
            etTitle.setText("You Reach Maximum");
            //etTitle.setFocusable(false);
            //etTitle.setCursorVisible(false);

        }
    }


    private void setRecyclerLayout(){

        isViewWithCatalog = !isViewWithCatalog;

        mRecyclerView.setLayoutManager(isViewWithCatalog ? new LinearLayoutManager(this) : new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        /*if(!isShowingGrid){
            isShowingGrid=true;
            mLinearLayoutManager = new LinearLayoutManager(this);
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }else{
            isShowingGrid=false;
            mGridLayoutManager = new GridLayoutManager(ActivityGroupMembers.this, 2);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
            mRecyclerView.setLayoutManager(mGridLayoutManager);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }*/

    }
}

