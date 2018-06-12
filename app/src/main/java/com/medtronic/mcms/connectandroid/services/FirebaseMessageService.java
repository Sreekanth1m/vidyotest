package com.medtronic.mcms.connectandroid.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import com.medtronic.mcms.connectandroid.VideoCallActivity;
import com.medtronic.mcms.connectandroid.models.NotificationModel;
import com.medtronic.mcms.connectandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;

import static android.content.ContentValues.TAG;


public class FirebaseMessageService extends FirebaseMessagingService {

    public static String CANCEL_VIDEO_CALL = "cancel_video_call";
    private NotificationModel notificationModel;
    //public VideoCallStatusReceiver videoCallStatusReceiver;
    String notificationStatus;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static class NOTIFICATION_TYPES {
        public static final String VIDYO = "vidyo";
        public static final String HEALTH_CHECK = "healthCheck";
    }

    Gson gson = new Gson();

    @Override
    public void onDeletedMessages() {

    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.d(TAG, "Push received");
        Log.d(TAG, message.getData().toString());
        JSONObject messageData = null;
        String notificationType = null;

        String remoteMessage = message.getData().get("message");

        try {

            JSONObject messageJSON = new JSONObject(remoteMessage);
            notificationType = messageJSON.getString("notificationType");

            switch (notificationType) {
                case (NOTIFICATION_TYPES.VIDYO):
                    notificationStatus = messageJSON.getString("notificationStatus");
                    handleVidyoPushNotification(remoteMessage);
                    break;
                case (NOTIFICATION_TYPES.HEALTH_CHECK):
                    Log.e(TAG, "Notification type = " + messageJSON.getString("notificationType"));
                    handleHealthCheckPushNotification(messageJSON);
                    break;
                default:
                    Log.e(TAG, "Unhandled push notification type");
            }
        } catch (JSONException je) {
            Log.d(TAG, je.getMessage());
        }

    }

    public void handleVidyoPushNotification(String remoteMessage) throws JSONException {
        notificationModel = NotificationModel.fromJSON(remoteMessage);
     //   Model.getInstance().setVidyoMeetingId(notificationModel.getVidyoMeetingRoom());
        if (notificationStatus.equals("Initiated")) {
          //  IntentFilter intentFilter = new IntentFilter(APIController.API_CONTROLLER_EVENT);
//            if (videoCallStatusReceiver == null)
//            {
//                videoCallStatusReceiver = new VideoCallStatusReceiver();
//            }
         //   getApplicationContext().registerReceiver(videoCallStatusReceiver, intentFilter);

//            APIController apiController = APIController.getInstance(getApplicationContext());
//            apiController.getCallStatus();
        Intent i = new Intent(getApplicationContext(), VideoCallActivity.class);
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        Bundle extras = new Bundle();
        extras.putSerializable("notificationData", notificationModel);
        i.putExtras(extras);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        sendBroadcast(closeDialog);

        getApplicationContext().startActivity(i);
        }else{
            Intent intent = new Intent();
            intent.setAction(CANCEL_VIDEO_CALL);
            sendBroadcast(intent);
        }
            //send a cancel broad cast
    }

    public void handleHealthCheckPushNotification(JSONObject messageData) throws JSONException {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setContentTitle(messageData.getString("notificationTitle"));
//        notificationBuilder.setLargeIcon();
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentText(messageData.getString("notificationText"));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        notificationBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
        notificationBuilder.setCategory(Notification.CATEGORY_CALL);
//        notificationBuilder.setPublicVersion("Add notification shown in non secure context here")
        //Logic to start app when notification is clicked
//        Intent notificationIntent = new Intent(this, LoginActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.notify(0, notificationBuilder.build());

        //Increment badgeCount
//        SharedPrefsController sharedPrefsController = new SharedPrefsController();
//        int healthCheckBadgeNumber = sharedPrefsController.getHealthCheckBadgeNumber(this);
//        int missedCallBadgeNumber = sharedPrefsController.getMissedCallBadgeNumber(this);
//        int badgeNumber = missedCallBadgeNumber + healthCheckBadgeNumber;
//        sharedPrefsController.setHealthCheckBadgeNumber(this, healthCheckBadgeNumber + 1);
//        ShortcutBadger.applyCount(this, badgeNumber + 1);
    }

    @Override
    public void onMessageSent(String messageID) {

    }

    @Override
    public void onSendError(String msgId, Exception exception) {


    }

//    private class VideoCallStatusReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            getApplicationContext().unregisterReceiver(videoCallStatusReceiver);
//            String callType = intent.getStringExtra(APIController.CALL_TYPE_KEY);
//            if (callType.equals(APIQuery.CALLS.CALL_STATUS)) {
//                String currentCallStatus = intent.getStringExtra("status");
//                if (currentCallStatus != Model.VIDEO_CALL_STATUS.CANCELLED)
//                {
//                    Intent i = new Intent(getApplicationContext(), VideoCallActivity.class);
//                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//                    Bundle extras = new Bundle();
//                    extras.putSerializable("notificationData", notificationModel);
//                    i.putExtras(extras);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                    Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//                    sendBroadcast(closeDialog);
//
//                    getApplicationContext().startActivity(i);
//                }
//            }
//        }
//
//    }
}
