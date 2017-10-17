package org.yanzi.fragment.adapter;

import java.util.List;

import org.yanzi.bean.MessageBean;
import org.yanzi.bean.MessageInfo;

import com.zed3.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageInfoAdapter extends BaseAdapter {
	private List<MessageInfo> mListMsgInfo = null;
	private Context mContext;
	private LayoutInflater mInflater;
	public MessageInfoAdapter(List<MessageInfo> listMsgInfo, Context context){
		mListMsgInfo = listMsgInfo;
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		return mListMsgInfo.size();
	}

	@Override
	public Object getItem(int position) {
		return mListMsgInfo.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = mInflater.inflate(R.layout.page_message_item_layout, null);
		
//		ImageView imageView = (ImageView) v.findViewById(R.id.img_msg_item);
//		imageView.setImageResource(mListMsgInfo.get(position).getPhotoDrawableId());
//		Log.i("-xiaomo-", mListMsgInfo.get(position).toString());
		TextView nameMsg = (TextView)v.findViewById(R.id.page_title_msg_item);
		nameMsg.setText(mListMsgInfo.get(position).getTitle());
		
		TextView idMsg = (TextView)v.findViewById(R.id.page_id_msg_item);
		idMsg.setText(String.valueOf(mListMsgInfo.get(position).getId()));

		TextView timeMsg = (TextView)v.findViewById(R.id.page_time_msg_item);
		timeMsg.setText(mListMsgInfo.get(position).getTime());
		
//		TextView timeMsg = (TextView)v.findViewById(R.id.time_msg_item);
//		timeMsg.setText(mListMsgBean.get(position).getMessageTime());

		return v;
	}

}
