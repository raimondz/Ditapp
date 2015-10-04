package cl.apd.ditapp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Sucursal;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class SucursalAdapter extends RealmBaseAdapter<Sucursal> implements ListAdapter {

    private class NotificationHolder {
        TextView title;
    }

    public SucursalAdapter(Context context,
                               RealmResults<Sucursal> realmResults,
                               boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_sucursal,
                    parent, false);
            viewHolder = new NotificationHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.direccionText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NotificationHolder) convertView.getTag();
        }

        Sucursal item = realmResults.get(position);
        viewHolder.title.setText(item.getDireccion());
        //viewHolder.content.setText(item.getContent());
        //viewHolder.date.setText("{fa-clock-o} " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(item.getDate()));
        return convertView;
    }

    public RealmResults<Sucursal> getRealmResults() {
        return realmResults;
    }
}