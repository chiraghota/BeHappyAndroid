package activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import vo.FeedVO;
import iamhappy.in.iamhappy.R;
import util.ConstantUtils;

/**
 * Created by Prateek on 29/08/15.
 */
public class TimelineActivity extends AppCompatActivity {

    private ListView feedListView;
    private List<FeedVO> feedsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        feedListView = (ListView) findViewById(R.id.timeline_list);
        feedsList = getIntent().getParcelableArrayListExtra(ConstantUtils.IKEY_FEED_LIST);
    }
}
