package com.example.testapp.feed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.testapp.R;
import com.example.testapp.db.DbHelper;
import com.example.testapp.listrepositories.RepositoryItem;
import com.example.testapp.listrepositories.RepositoryListAdapter;
import com.example.testapp.web.WebScreen;
import com.squareup.picasso.Picasso;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import flow.Flow;

public class DefaultFeedView extends RelativeLayout {
    private static final String TAG = DefaultFeedView.class.getSimpleName();
    private List<RepositoryItem> records;
    private String userName;
    private GHUser user;

    public DefaultFeedView(Context context, AttributeSet attrs) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        FeedScreen screen = Flow.getKey(this);
        if (screen == null) {
            return;
        }
        userName = screen.getUserName();
        getUserInfo();
        initUserInfo();
        initRepoList();
        doSomeActioAppropriateToButton();
    }

    private void populateRecords() {
        records = new ArrayList<>();
        try {

            for (GHRepository repo : user.getRepositories().values()) {
                RepositoryItem record = new RepositoryItem();
                record.setRepositoryTitle(repo.getName());
                record.setLanguage(repo.getLanguage());
                record.setForkCount(repo.getForks());
                record.setStarsCount(repo.listStargazers().asList().size());
                records.add(record);
            };
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void initUserInfo() {
        LinearLayout head_layout = (LinearLayout) findViewById(R.id.user_head_info);
        ImageView avatar = (ImageView) head_layout.findViewById(R.id.user_avatar);
        Picasso.with(avatar.getContext())
                .load(user.getAvatarUrl())
                .fit()
                .placeholder(R.drawable.default_avatar)
                .into(avatar);
        LinearLayout detail_block_info = (LinearLayout) head_layout.findViewById(R.id.user_detail_block);
        TextView userInfo = (TextView) detail_block_info.findViewById(R.id.user_info);
        TextView userDetInfo = (TextView) detail_block_info.findViewById(R.id.user_detail_info);

        try {
            userInfo.setText(user.getName() + " " + user.getCompany() + " " + user.getEmail());
            userDetInfo.setText("Folowers: " +
                    user.getFollowersCount() +
                    "\n" +
                    " Following: " +
                    user.getFollowingCount() +
                    " \n" + "Pablic gists: " +
                    user.getPublicGistCount() +
                    " \n" +
                    " Public repos: " +
                    user.getPublicRepoCount());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initRepoList() {
        populateRecords();
        RecyclerView repoRecycler = (RecyclerView) findViewById(R.id.repo_list);
        RepositoryListAdapter repoAdapter = new RepositoryListAdapter(records);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        repoRecycler.setAdapter(repoAdapter);
        repoRecycler.setLayoutManager(layoutManager);
        repoRecycler.setItemAnimator(itemAnimator);
    }

    private void getUserInfo() {
        GitHub github = null;
        try {
            GitHubBuilder gitHubBuilder = new GitHubBuilder();
            gitHubBuilder.withPassword(userName, null);
            github = gitHubBuilder.build();
            user = github.getUser(userName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doSomeActioAppropriateToButton() {
        LinearLayout btns = (LinearLayout) findViewById(R.id.btns);
        Button btnSave = (Button) btns.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                Log.d(TAG, "--- Insert in git_users_table: ---");
                try {

                    cv.put("name", user.getName());
                    cv.put("login", user.getLogin());
                    cv.put("email", user.getEmail());
                    cv.put("company", user.getCompany());
                    cv.put("repositories", user.getRepositories().values().toString());
                    cv.put("followers", user.getFollowersCount());
                    cv.put("following", user.getFollowingCount());

                    long rowID = db.insert("git_users_table", null, cv);
                    Log.d(TAG, "row inserted, ID = " + rowID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (db.isOpen()) {
                    dbHelper.close();
                }
            }
        });
        Button btnDb = (Button) btns.findViewById(R.id.btn_db);
        btnDb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d(TAG, "--- Rows in git_users_table: ---");
                Cursor c = db.query("git_users_table", null, null, null, null, null, null);

                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int nameColIndex = c.getColumnIndex("name");
                    int emailColIndex = c.getColumnIndex("email");

                    do {
                        Log.d(TAG,
                                "ID = " + c.getInt(idColIndex) +
                                        ", name = " + c.getString(nameColIndex) +
                                        ", email = " + c.getString(emailColIndex));
                    } while (c.moveToNext());
                } else
                    Log.d(TAG, "0 rows");
                if (db.isOpen()) {
                    dbHelper.close();
                }
                c.close();
            }
        });

        Button btnWeb = (Button) btns.findViewById(R.id.btn_web);
        btnWeb.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlow().set(new WebScreen(user.getHtmlUrl().toString()));
            }
        });
    }

    private Flow getFlow() {
        return Flow.get(getContext());
    }
}
