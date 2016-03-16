package com.h6ah4i.example.flexiblespaceheaderwithadvancedrecyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.recycler_view);

        RecyclerViewExpandableItemManager expMgr = new RecyclerViewExpandableItemManager(null);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(expMgr.createWrappedAdapter(new Adapter()));

        rv.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this, android.R.drawable.divider_horizontal_dark), false));

        expMgr.attachRecyclerView(rv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class GroupData {
        public long id;
        public String value;
        public List<ChildData> children = new ArrayList<>();

        public GroupData(long id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    static class ChildData {
        public long id;
        public String value;

        public ChildData(long id, String value) {
            this.id = id;
            this.value = value;
        }
    }

    static class GroupViewHolder extends AbstractExpandableItemViewHolder {
        public TextView text;
        public GroupViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    static class ChildViewHolder extends AbstractExpandableItemViewHolder {
        public TextView text;
        public ChildViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    static class Adapter extends AbstractExpandableItemAdapter<GroupViewHolder, ChildViewHolder> {
        private List<GroupData> mItems;

        Adapter() {
            setHasStableIds(true);

            mItems = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                GroupData g = new GroupData(i, "GROUP " + i);
                for (int j = 0; j < 5; j++) {
                    g.children.add(new ChildData(i, "child " + j));
                }
                mItems.add(g);
            }
        }

        @Override
        public int getGroupCount() {
            return mItems.size();
        }

        @Override
        public int getChildCount(int groupPosition) {
            return mItems.get(groupPosition).children.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return mItems.get(groupPosition).id;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return mItems.get(groupPosition).children.get(childPosition).id;
        }

        @Override
        public int getGroupItemViewType(int groupPosition) {
            return 0;
        }

        @Override
        public int getChildItemViewType(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public GroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            v.setBackgroundColor(0xffccffcc);
            return new GroupViewHolder(v);
        }

        @Override
        public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ChildViewHolder(v);
        }

        @Override
        public void onBindGroupViewHolder(GroupViewHolder holder, int groupPosition, int viewType) {
            GroupData d = mItems.get(groupPosition);
            holder.text.setText(d.value);
        }

        @Override
        public void onBindChildViewHolder(ChildViewHolder holder, int groupPosition, int childPosition, int viewType) {
            ChildData d = mItems.get(groupPosition).children.get(childPosition);
            holder.text.setText(d.value);
        }

        @Override
        public boolean onCheckCanExpandOrCollapseGroup(GroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
            return true;
        }
    }
}
