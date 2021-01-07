package my.e.wateryourplants.Notification;



import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import my.e.wateryourplants.MainActivity;
import my.e.wateryourplants.R;

public class AppNotification extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        /*
         if(remoteMessage.getData().size() > 0) {
             getMessageReceived(remoteMessage.getData().get("title"),
                     remoteMessage.getData().get("body"));
         }

         */

         if(remoteMessage.getNotification() != null) {
             getMessageReceived(remoteMessage.getNotification().getTitle(),
                     remoteMessage.getNotification().getBody());
         }
    }


    public void getMessageReceived(String title, String msg){
        Intent intent = new Intent(this, MainActivity.class);
        String channelId = "PlantWateringChanel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext(), channelId)
                .setSmallIcon(R.drawable.ic_baseline_notifications)
                .setContentTitle(title)
                .setContentText(msg)
                .setSound(uri)
                .setVibrate(new long[] {1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(101, builder.build());

    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("TOKEN", s);
    }
}
