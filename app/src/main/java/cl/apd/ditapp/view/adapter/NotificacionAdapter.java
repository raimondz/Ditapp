package cl.apd.ditapp.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import java.text.SimpleDateFormat;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Notificacion;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

public class NotificacionAdapter extends RealmBaseAdapter<Notificacion> implements ListAdapter {

    private class NotificationHolder {
        TextView title;
        TextView content;
        IconTextView date;
    }

    public NotificacionAdapter(Context context,
                               RealmResults<Notificacion> realmResults,
                               boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NotificationHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_notification,
                    parent, false);
            viewHolder = new NotificationHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.titleText);
            viewHolder.content = (TextView) convertView.findViewById(R.id.contentText);
            viewHolder.date = (IconTextView) convertView.findViewById(R.id.dateText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NotificationHolder) convertView.getTag();
        }

        Notificacion item = realmResults.get(position);
        viewHolder.title.setText(item.getTitulo());
        viewHolder.content.setText(item.getDescripcion());
        viewHolder.date.setText("{fa-clock-o} " + new SimpleDateFormat("dd-MM-yyyy HH:mm").format(item.getFecha()));
        return convertView;
    }

    public RealmResults<Notificacion> getRealmResults() {
        return realmResults;
    }
}