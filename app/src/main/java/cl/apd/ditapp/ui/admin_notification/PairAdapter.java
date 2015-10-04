package cl.apd.ditapp.ui.admin_notification;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import cl.apd.ditapp.R;

/**
 * Created by Raimondz on 04-10-15.
 */
public class PairAdapter extends ArrayAdapter<Pair<String,String>> {

    private Context context;
    private int resource=R.layout.item_pair;

    public PairAdapter(Context context) {
        super(context, R.layout.item_pair);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater= LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_pair,parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_first = (TextView) convertView.findViewById(R.id.tv_pair_first);
            viewHolder.tv_second = (TextView) convertView.findViewById(R.id.tv_pair_second);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Pair<String,String> item=getItem(position);
        viewHolder.tv_first.setText(item.first);
        viewHolder.tv_second.setText(item.second);
        return convertView;
    }

    private class ViewHolder{
        private TextView tv_first;
        private TextView tv_second;
    }
}
