package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import vo.FeedVO;
import iamhappy.in.iamhappy.R;
import server.RequestManager;
import util.AppUtil;

/**
 * Created by Prateek on 29/08/15.
 */

public class FeedlistAdapter extends BaseAdapter {

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 300;

    private Context mContext;
    private List<FeedVO> feedList;

    @Override
    public int getCount() {
        if(feedList != null){
            return feedList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return feedList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            convertView = inflater.inflate(R.layout.layout_cell_feed, viewGroup, false);
            holder = new ViewHolder();
            holder.feedImage = (ImageView) convertView.findViewById(R.id.feed_image);
            holder.feedLikes = (TextView) convertView.findViewById(R.id.feed_likes);
            holder.feedComments = (TextView) convertView.findViewById(R.id.feed_comments);
            holder.imageListener = new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(response.getBitmap() != null){
                        holder.feedImage.setImageBitmap(response.getBitmap());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            };
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        FeedVO feed = feedList.get(position);
        if(AppUtil.getNullCheck(feed.image_url)){
            RequestManager.getInstance().getImageLoader().get(feed.image_url,
                    holder.imageListener,IMAGE_WIDTH, IMAGE_HEIGHT);
        }
        if(AppUtil.getNullCheck(feed.likes)){
            holder.feedLikes.setText(mContext.getString(R.string.feed_likes));
        }
        if(feed.comments != null){
            holder.feedComments.setText(mContext.getString(R.string.feed_comments));
        }
        return null;
    }

    private static class ViewHolder{
        ImageView feedImage;
        TextView feedLikes;
        TextView feedComments;
        ImageLoader.ImageListener imageListener;
    }
}
