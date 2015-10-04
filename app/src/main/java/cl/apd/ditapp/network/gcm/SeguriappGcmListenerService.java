/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cl.apd.ditapp.network.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.Date;

import cl.apd.ditapp.R;
import cl.apd.ditapp.model.Notificacion;
import cl.apd.ditapp.ui.main.MainActivity;
import cl.apd.ditapp.util.Constants;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SeguriappGcmListenerService extends GcmListenerService {

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        //String message = data.getString("message");
        Log.d(Constants.TAG, "From: " + from);
        //Log.d(Constants.TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {
            // message received from some topic.
        } else {
            // normal downstream message.
        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
        sendNotification(data);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param data GCM message received.
     */
    private void sendNotification(final Bundle data) {

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(this).build();
        Realm realm = Realm.getInstance(realmConfig);
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Notificacion notificacion = realm.createObject(Notificacion.class);
                notificacion.setTitulo(data.getString(Constants.NOTIFICACION_TITULO));
                notificacion.setDescripcion(data.getString(Constants.NOTIFICACION_DESCRIPCION));
                notificacion.setFecha(new Date());
                /*
                notification.setType(data.getInt(Constant.MESSAGE_TYPE));
                notification.setPriority(data.getInt(Constant.MESSAGE_PRIORITY));
                notification.setTitle(data.getString(Constant.MESSAGE_TITLE));
                notification.setContent(data.getString(Constant.MESSAGE_CONTENT));
                notification.setVoice(data.getString(Constant.MESSAGE_VOICE));
                notification.setDate(new Date());
                notification.setRead(false);
                */
            }
        });

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(data.getString(Constants.NOTIFICACION_TITULO))
                .setContentText("un mensaje")
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}