package com.hanboard.teacherhd.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hanboard.teacherhd.R;
import com.hanboard.teacherhd.android.entity.tree.Node;
import com.hanboard.teacherhd.lib.common.utils.ToastUtils;
public class TreeAdapter<T> extends TreeListViewAdapter<T>
{
	public TreeAdapter(ListView mTree, Context context, List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		super(mTree, context, datas, defaultExpandLevel);


	}
	@Override
	public View getConvertView(Node node , int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.prepare_lessons_list_item, parent, false);
				viewHolder = new ViewHolder();
				viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
				viewHolder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
				convertView.setTag(viewHolder);
			} else {/**360U2727817184**/
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if (node.getIcon() == -1) {
				viewHolder.icon.setVisibility(View.INVISIBLE);
			} else {
				viewHolder.icon.setVisibility(View.VISIBLE);
				viewHolder.icon.setImageResource(node.getIcon());
			}
			viewHolder.label.setText(node.getName());
		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
	}
}
