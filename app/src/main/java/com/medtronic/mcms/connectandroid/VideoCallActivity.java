package com.medtronic.mcms.connectandroid;

import android.app.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

//
import com.medtronic.mcms.connectandroid.models.NotificationModel;
import com.medtronic.mcms.connectandroid.services.FirebaseMessageService;
import com.vidyo.VidyoClient.Connector.Connector;
import com.vidyo.VidyoClient.Connector.ConnectorPkg;
import com.vidyo.VidyoClient.Device.LocalCamera;
import com.vidyo.VidyoClient.Device.RemoteCamera;
import com.vidyo.VidyoClient.Endpoint.LogRecord;
import com.vidyo.VidyoClient.Endpoint.Participant;
import com.vidyo.VidyoClient.NetworkInterface;

import java.util.ArrayList;

import java.util.List;

public class VideoCallActivity extends Activity implements
        Connector.IConnect,
        Connector.IRegisterParticipantEventListener {

    enum VIDYO_CONNECTOR_STATE {
        VC_CONNECTED,
        VC_DISCONNECTED,
        VC_DISCONNECTED_UNEXPECTED,
        VC_CONNECTION_FAILURE
    }

    private VIDYO_CONNECTOR_STATE mVidyoConnectorState = VIDYO_CONNECTOR_STATE.VC_DISCONNECTED;
    private boolean mVidyoConnectorConstructed = false;
    private boolean mVidyoClientInitialized = false;
    private Connector mVidyoConnector = null;
    private TextView mToolBarLayoutText;
    private ProgressBar mConnectionSpinner;
    private FrameLayout mVideoFrame;
    private FrameLayout mToggleToolbarFrame;
    private boolean mAllowReconnect = true;
    private boolean mEnableDebug = false;
    private String mReturnURL = null;
    private String mExperimentalOptions = null;
  //  private CallConfiguration cc;
    private TextView callerNameText;
    private TextView incomingTitleText;
    private TextView cancelActionText;
    private TextView clientNameText;
    private RelativeLayout callOverlayLayout;
    private Button acceptButton;
    private Button declineButton;
    private Ringtone ringtone;
    private RingtoneManager ringtoneManager;
    private static String TAG = "VideoCallActivity";
    private APIReceiver apiReceiver;
   // private ButtonBarLayout buttonBarLayout;
    private AudioManager audioManager = null;
    private NotificationModel notificationModel;
    private CountDownTimer countDownTimer;
    private CountDownTimer countDownTimer1;
    private MediaPlayer mMediaPlayer;
    public Vibrator vibrate;
    public VideoCallCancelReceiver videoCallCancelReceiver;
    LocalCamera mLastSelectedCamera = null;

    private final int PERMISSIONS_REQUEST_ALL = 1988;
    private static final String[] mPermissions = new String[]{
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

   // private ButtonBarReceiver buttonBarReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vibrate = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //  v.vibrate(60000);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_call);

        mToolBarLayoutText = findViewById(R.id.toolbar_layout_text);
        mToolBarLayoutText.setVisibility(View.GONE);
     //  buttonBarLayout = findViewById(R.id.button_bar_element);
        mVideoFrame = findViewById(R.id.videoFrame);
        mToggleToolbarFrame = findViewById(R.id.toggleToolbarFrame);
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//        mToolbarStatus = (TextView) findViewById(R.id.toolbarStatusText);
//        mClientVersion = (TextView) findViewById(R.id.toolbarStatusVersion);
        mConnectionSpinner = findViewById(R.id.connectionSpinner);

        callerNameText = findViewById(R.id.caller_name_text);
        clientNameText = findViewById(R.id.client_name_text);
        incomingTitleText = findViewById(R.id.incoming_title_text);
        cancelActionText = findViewById(R.id.cancel_action_text);
        callOverlayLayout = findViewById(R.id.call_overlay_layout);
        acceptButton = findViewById(R.id.accept_button);
        declineButton = findViewById(R.id.decline_button);
        acceptButton.setOnClickListener(new AcceptListener());
        declineButton.setOnClickListener(new DeclineListener());
        cancelActionText.setVisibility(View.GONE);

        // Suppress keyboard
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (videoCallCancelReceiver == null) {
            videoCallCancelReceiver = new VideoCallCancelReceiver();
        }
        IntentFilter intentFilter = new IntentFilter(FirebaseMessageService.CANCEL_VIDEO_CALL);
        getApplicationContext().registerReceiver(videoCallCancelReceiver, intentFilter);

        // Initialize the VidyoClient
         ConnectorPkg.setApplicationUIContext(this);
        mVidyoClientInitialized = ConnectorPkg.initialize();

        try {
            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(this, alert);
            final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            if (audioManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                // mMediaPlayer.start();
            }

          //  setStatusAndSend(Model.VIDEO_CALL_STATUS.CALLING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCountDownTimer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMediaPlayer.start();
        Log.e(TAG, "On Start called");
        Intent intent = getIntent();
//        mToggleConnectButton.setEnabled(true);
        if (intent != null) {
            try {
                notificationModel = (NotificationModel) intent.getSerializableExtra("notificationData");
             //   Model.getInstance().setVidyoMeetingId(notificationModel.getVidyoMeetingRoom());
                String callerName = notificationModel.getClinicianFirstName() + " " + notificationModel.getClinicianLastName();
                callerNameText.setText(callerName);
                clientNameText.setText(notificationModel.getClinicianProfession());
            } catch (ClassCastException cce) {
                Log.d(TAG, cce.getMessage());
            }
        }
        startVidyoConnector();
    }

    void startVidyoConnector() {
        ViewTreeObserver viewTreeObserver = mVideoFrame.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new GlobalLayoutListener());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterReceiver();
        Log.e(TAG, "On pause called");
        //  if (mVidyoConnector != null) mVidyoConnector.Disconnect();
        if (countDownTimer1 != null) {
            countDownTimer1.cancel();
        }
        if (countDownTimer != null)
            countDownTimer.cancel();
//      if(!(Model.getInstance().getCurrentCallStatus() == Model.VIDEO_CALL_STATUS.DECLINED)){
//        setStatusAndSend(Model.VIDEO_CALL_STATUS.DISCONNECTED);}
//        finishAndRemoveTask();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
        Log.e(TAG, "on Resume called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mVidyoConnector!=null) {
            mVidyoConnector.setMode(Connector.ConnectorMode.VIDYO_CONNECTORMODE_Foreground);
            mVidyoConnector.selectDefaultCamera();
            mVidyoConnector.selectDefaultMicrophone();
            mVidyoConnector.selectDefaultSpeaker();
        }
        Log.e(TAG, "on Restart called");
    }

    @Override
    protected void onStop() {
        if (mVidyoConnectorState != VIDYO_CONNECTOR_STATE.VC_CONNECTED)
        {
            mVidyoConnector.selectLocalCamera(null);
            mVidyoConnector.selectLocalMicrophone(null);
            mVidyoConnector.selectLocalSpeaker(null);
            mVidyoConnector.setMode(Connector.ConnectorMode.VIDYO_CONNECTORMODE_Background);
        }

//        if (mVidyoConnectorConstructed) {
//            mVidyoConnector.SetMode(VidyoConnector.VidyoConnectorMode.VIDYO_CONNECTORMODE_Background);
//        }
        Log.e(TAG, "on stop called");

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();

        }


        if (mVidyoConnector != null) {
        //    mVidyoConnector.Disconnect();
           // mVidyoConnector.Disable();
            mVidyoConnector = null;
            ConnectorPkg.setApplicationUIContext(null);
        }
        //close the video call in back or home or application buttons are pressed
//        if(mVidyoConnector != null ) mVidyoConnector.Disconnect();
//        finishAndRemoveTask();
        Log.e(TAG, "on Destroy called");
    }

    @Override
    public void onBackPressed() {
        Log.e(TAG, "on back pressed!");
//        super.onBackPressed();

        //  if(mVidyoConnector != null ) mVidyoConnector.Disconnect();
        // finishAndRemoveTask();
    }

    private void connectCall(NotificationModel cc) {

//        Log.e(TAG, "Data for starting the call Host: prod.vidyo.io, the token is " +  cc.getVidyoToken() +
//        " displayname: " + cc.getVidyoCallDisplayName() + "  resourceID: " + cc.getVidyoMeetingRoom());
        countDownTimer.cancel();
//        mMediaPlayer.stop();
//        mMediaPlayer.release();

        boolean status = mVidyoConnector.connect(
                "prod.vidyo.io",
                cc.getVidyoToken(),
                "Patient",
                cc.getVidyoMeetingRoom(),
                new VideoConnectListener());

        if (!status) {
            mConnectionSpinner.setVisibility(View.INVISIBLE);
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE, "Connection failed");
            //setStatusAndSend(Model.VIDEO_CALL_STATUS.ERROR_CONNECTION_FAILED);
        }
    }

    private void setCountDownTimer() {
        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {

                Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
                if (checkVibrationIsOn(getApplicationContext())) {
                    vibrate.vibrate(60000);
                    long[] pattern = {0, 100, 500, 100, 500, 100, 500, 100, 500, 100, 500};
                    vibrate.vibrate(pattern, -1);
                }
            }

            public void onFinish() {
                Log.d(TAG, "Done");
                mMediaPlayer.stop();
                if (checkVibrationIsOn(getApplicationContext())) {
                    vibrate.cancel();
                }
                createBannerNotification(VideoCallActivity.this, "Missed vidyo call", "Missed vidyo call");
                finishAndRemoveTask();
            }
        }.start();
    }

    private void setCountDownTimer1() {
        countDownTimer1 = new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Log.d(TAG, "Done");
                if (mVidyoConnector != null) mVidyoConnector.disconnect();
                finishAndRemoveTask();
                if (countDownTimer1 != null) {
                    countDownTimer1.cancel();
                }

//                if (mMediaPlayer!=null);
//                mMediaPlayer.stop();
            }
        }.start();
    }

    private void setStatusAndSend(String status) {
        Log.d(TAG, status);
//        Model model = Model.getInstance();
//        model.setCurrentCallStatus(status);
//        APIController apiController = APIController.getInstance(getApplicationContext());
//        apiController.postCallStatus();
    }

    public void createBannerNotification(Context context, String notificationText, String notificationTitle) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder notificationBuilder = new Notification.Builder(context);
        notificationBuilder.setContentTitle(notificationTitle);
//        notificationBuilder.setLargeIcon();
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentText(notificationText);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setPriority(Notification.PRIORITY_HIGH);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Refresh the video size after it is painted
        ViewTreeObserver viewTreeObserver = mVideoFrame.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    mVideoFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    RefreshUI();
                }
            });
        }
    }

    // Refresh the UI
    private void RefreshUI() {
        // Refresh the rendering of the video
        mVidyoConnector.showViewAt(mVideoFrame, 0, 0, mVideoFrame.getWidth(), mVideoFrame.getHeight());
    }

    // The state of the VidyoConnector connection changed, reconfigure the UI.
    // If connected, dismiss the controls layout
    private void ConnectorStateUpdated(VIDYO_CONNECTOR_STATE state, final String statusText) {

        mVidyoConnectorState = state;

        // Execute this code on the main thread since it is updating the UI layout
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Update the toggle connect button to either start call or end call image
//                mToggleConnectButton.setChecked(mVidyoConnectorState == VIDYO_CONNECTOR_STATE.VC_CONNECTED);
          //      buttonBarLayout.selectConnect(mVidyoConnectorState == VIDYO_CONNECTOR_STATE.VC_CONNECTED);
                // Set the status text in the toolbar
//                mToolbarStatus.setText(statusText);

                if (mVidyoConnectorState == VIDYO_CONNECTOR_STATE.VC_CONNECTED) {
                    // Enable the toggle toolbar control
                    mToggleToolbarFrame.setVisibility(View.VISIBLE);
                } else {
                    // VidyoConnector is disconnected

                    // Disable the toggle toolbar control
                    mToggleToolbarFrame.setVisibility(View.GONE);

                    // If a return URL was provided as an input parameter, then return to that application
                    if (mReturnURL != null) {
                        // Provide a callstate of either 0 or 1, depending on whether the call was successful
                        Intent returnApp = getPackageManager().getLaunchIntentForPackage(mReturnURL);
                        returnApp.putExtra("callstate", (mVidyoConnectorState == VIDYO_CONNECTOR_STATE.VC_DISCONNECTED) ? 1 : 0);
                        startActivity(returnApp);
                    }

                    // If the allow-reconnect flag is set to false and a normal (non-failure) disconnect occurred,
                    // then disable the toggle connect button, in order to prevent reconnection.
                    if (!mAllowReconnect && (mVidyoConnectorState == VIDYO_CONNECTOR_STATE.VC_DISCONNECTED)) {
                      //  buttonBarLayout.selectConnect(false);
                    }
                }
                // Hide the spinner animation
                mConnectionSpinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    //     Toggle the microphone privacy
    public void MicrophonePrivacyButtonPressed(View v) {
        mVidyoConnector.setMicrophonePrivacy(((ToggleButton) v).isChecked());
    }

    // Toggle the camera privacy
    public void CameraPrivacyButtonPressed(View v) {
        mVidyoConnector.setCameraPrivacy(((ToggleButton) v).isChecked());
    }

    // Handle the camera swap button being pressed. Cycle the camera.
    public void CameraSwapButtonPressed(View v) {
        mVidyoConnector.cycleCamera();
    }

    // Handle successful connection.
    public void OnSuccess() {
        ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_CONNECTED, "Connected");
        mConnectionSpinner.setVisibility(View.INVISIBLE);
    }

    // Handle attempted connection failure.
    public void OnFailure(Connector.ConnectorFailReason reason) {
        mConnectionSpinner.setVisibility(View.INVISIBLE);
        // Update UI to reflect connection failed
        ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE, "Connection failed");
    }

    // Handle an existing session being disconnected.
    public void OnDisconnected(Connector.ConnectorDisconnectReason reason) {
        if (reason == Connector.ConnectorDisconnectReason.VIDYO_CONNECTORDISCONNECTREASON_Disconnected) {
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_DISCONNECTED, "Disconnected");
            //finishAndRemoveTask();
        } else {
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_DISCONNECTED_UNEXPECTED, "Unexpected disconnection");
            //finishAndRemoveTask();
        }
    }

    private class AcceptListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            countDownTimer.cancel();
            if (checkVibrationIsOn(getApplicationContext())) {
                vibrate.cancel();
            }
            if (Build.VERSION.SDK_INT > 22) {
                List<String> permissionsNeeded = new ArrayList<>();
                for (String permission : mPermissions) {
                    // Check if the permission has already been granted.
                    if (ContextCompat.checkSelfPermission(VideoCallActivity.this, permission) != PackageManager.PERMISSION_GRANTED)
                        permissionsNeeded.add(permission);
                }
                if (permissionsNeeded.size() > 0) {
                    // Request any permissions which have not been granted. The result will be called back in onRequestPermissionsResult.
                    ActivityCompat.requestPermissions(VideoCallActivity.this, permissionsNeeded.toArray(new String[0]), PERMISSIONS_REQUEST_ALL);
                } else {
                    if (mVidyoConnectorConstructed) {
                        connectCall(notificationModel);
                        callOverlayLayout.setVisibility(View.INVISIBLE);
                        mMediaPlayer.stop();
                        mConnectionSpinner.setVisibility(View.VISIBLE);
                      //  setStatusAndSend(Model.VIDEO_CALL_STATUS.CONNECTING);
                    }

                }
            } else {
                if (mVidyoConnectorConstructed) {
                    connectCall(notificationModel);
                    callOverlayLayout.setVisibility(View.INVISIBLE);
                    mMediaPlayer.stop();
                    mConnectionSpinner.setVisibility(View.VISIBLE);
                   // setStatusAndSend(Model.VIDEO_CALL_STATUS.CONNECTING);
                }
            }
        }
    }

    private class DeclineListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            countDownTimer.cancel();
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_DISCONNECTED, "Disconnected");

        //    setStatusAndSend(Model.VIDEO_CALL_STATUS.DECLINED);
         //   mVidyoConnector.Disable();
            // Connector.Uninitialize();
            mMediaPlayer.stop();
            vibrate.cancel();

            ConnectorPkg.uninitialize();
            Log.e(TAG, "unitilialized the connector");
            finishAndRemoveTask();
        }
    }

    public void logLargeString(String str) {
        if (str.length() > 3000) {
            Log.i(TAG, str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.i(TAG, str); // continuation
        }
    }

    private class GlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            mVideoFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            // If the vidyo connector was not previously successfully constructed then construct it

            if (!mVidyoConnectorConstructed) {

                if (mVidyoClientInitialized) {

                    mVidyoConnector = new Connector(mVideoFrame,
                            Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Tiles,
                            16,
                            "info@VidyoClient info@VidyoConnector warning",
                            "",
                            0);

                    if (mVidyoConnector != null) {
                        mVidyoConnectorConstructed = true;

                        // If enableDebug is configured then enable debugging
                        if (mEnableDebug) {
                            mVidyoConnector.enableDebug(7776, "warning info@VidyoClient info@VidyoConnector");
                        }

                        // Set experimental options if any exist
                        if (mExperimentalOptions != null) {
                            ConnectorPkg.setExperimentalOptions(mExperimentalOptions);
                        }

                        RefreshUI();

//                        // Register for local camera events
//                        if (!mVidyoConnector.RegisterLocalCameraEventListener(new LocalCameraEventListener())) {
//                            Log.d(TAG,"registerLocalCameraEventListener failed");
//                        }

                        if (!mVidyoConnector.registerNetworkInterfaceEventListener(new RegistrationListener())) {
                            Log.d(TAG, "VidyoConnector RegisterNetworkInterfaceEventListener failed");
                        }

                        if(!mVidyoConnector.registerParticipantEventListener(new ParticipantListener()))
                        {
                            Log.d(TAG, "VidyoConnector RegisterParticipantEventListener failed");
                        }

//                        if (!mVidyoConnector.RegisterRemoteCameraEventListener(new RemoteCameraEventListener()))
//                        {}
                        // Register for log callbacks
                        if (!mVidyoConnector.registerLogEventListener(new LogEventListener(), "info@VidyoClient info@VidyoConnector warning")) {
                            Log.d(TAG, "VidyoConnector RegisterLogEventListener failed");
                        }
                    }
                }
            }
        }
    }

    private class LogEventListener implements Connector.IRegisterLogEventListener {
        @Override
        public void onLog(LogRecord logRecord) {
            Log.d(TAG, logRecord.toString());
        }
    }

    private class VideoConnectListener implements Connector.IConnect {

        @Override
        public void onSuccess() {
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_CONNECTED, "Connected");
          //  setStatusAndSend(Model.VIDEO_CALL_STATUS.CONNECTED);
        }

        @Override
        public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
            ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_CONNECTION_FAILURE, "Connection failed");
            //setStatusAndSend(Model.VIDEO_CALL_STATUS.ERROR_CONNECTION_FAILED);
        }

        @Override
        public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {
            if (connectorDisconnectReason == Connector.ConnectorDisconnectReason.VIDYO_CONNECTORDISCONNECTREASON_Disconnected) {
                ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_DISCONNECTED, "Disconnected");
              //  setStatusAndSend(Model.VIDEO_CALL_STATUS.DISCONNECTED);
                Log.e(TAG, "Disconnecting on disconnect");
                finishAndRemoveTask();
            } else {
                ConnectorStateUpdated(VIDYO_CONNECTOR_STATE.VC_DISCONNECTED_UNEXPECTED, "Unexpected disconnection");
               // setStatusAndSend(Model.VIDEO_CALL_STATUS.ERROR_UNEXPECTED_DISCONNECTION);
                finishAndRemoveTask();
            }
        }
    }

    private class RegistrationListener implements Connector.IRegisterNetworkInterfaceEventListener {

        @Override
        public void onNetworkInterfaceAdded(NetworkInterface networkInterface) {

        }

        @Override
        public void onNetworkInterfaceRemoved(NetworkInterface networkInterface) {

        }

        @Override
        public void onNetworkInterfaceSelected(NetworkInterface networkInterface, NetworkInterface.NetworkInterfaceTransportType networkInterfaceTransportType) {

        }

        @Override
        public void onNetworkInterfaceStateUpdated(NetworkInterface networkInterface, NetworkInterface.NetworkInterfaceState networkInterfaceState) {

        }
    }

    private class ParticipantListener implements Connector.IRegisterParticipantEventListener {
        @Override
        public void onParticipantJoined(Participant participant) {

        }

        @Override
        public void onParticipantLeft(Participant participant) {
            Log.d(TAG,"OnpartipantLeft");
            mVidyoConnector.disconnect();
            finishAndRemoveTask();
        }

        @Override
        public void onDynamicParticipantChanged(ArrayList<Participant> arrayList, ArrayList<RemoteCamera> arrayList1) {

        }

        @Override
        public void onLoudestParticipantChanged(Participant participant, boolean b) {

        }
    }

    private void registerReceiver() {
   //     IntentFilter buttonBarIntentFilter = new IntentFilter(ButtonBarLayout.BUTTON_BAR_ACTION);
//        if (buttonBarReceiver == null) {
//            buttonBarReceiver = new ButtonBarReceiver();
//        }
     //   registerReceiver(buttonBarReceiver, buttonBarIntentFilter);
    }

    private void unRegisterReceiver() {
        if (apiReceiver != null) {
            getApplicationContext().unregisterReceiver(apiReceiver);
            apiReceiver = null;
        }

//        if (buttonBarReceiver != null) {
//            unregisterReceiver(buttonBarReceiver);
//            buttonBarReceiver = null;
//        }
    }

    private class APIReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            String callType = intent.getStringExtra(APIController.CALL_TYPE_KEY);
//            if (callType.equals(APIQuery.CALLS.CALL_STATUS)) {
//                Log.e("Video", "sent up status");
//            }
        }
    }

    private class VideoCallCancelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getApplicationContext().unregisterReceiver(videoCallCancelReceiver);
            if (callerNameText.isShown()) {
                cancelActionText.setVisibility(View.VISIBLE);
            }
//            if (buttonBarLayout.isShown()) {
//                mToolBarLayoutText.setText(R.string.cancel_action_text_placeholder);
//                mToolBarLayoutText.setVisibility(View.VISIBLE);
//                mToolBarLayoutText.setTextColor(getResources().getColor(R.color.connect_error));
//            }

            setCountDownTimer1();
        }
    }

//    private class ButtonBarReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String buttonClicked = intent.getStringExtra(ButtonBarLayout.BUTTON_KEY);
//            if (buttonClicked != null) {
//
//                boolean buttonSelected = intent.getBooleanExtra(ButtonBarLayout.BUTTON_EXTRA_KEY, false);
//
//                if (buttonClicked.equals(ButtonBarLayout.CONNECT_BUTTON)) {
//                    Log.d(TAG, "onRecieve(): CONNECT_BUTTON");
//                    mVidyoConnector.disconnect();
//                }
//
//                if (buttonClicked.equals(ButtonBarLayout.MICROPHONE_BUTTON)) {
//                    Log.d(TAG, "onRecieve(): MICROPHONE_BUTTON");
//                    mVidyoConnector.setMicrophonePrivacy(!buttonSelected);
//                }
//
//                if (buttonClicked.equals(ButtonBarLayout.VIDEO_BUTTON)) {
//                    Log.d(TAG, "onRecieve(): VIDEO_BUTTON");
//                    mVidyoConnector.setCameraPrivacy(!buttonSelected);
//                    mVidyoConnector.setMicrophonePrivacy(!buttonSelected);
//                }
//
//                if (buttonClicked.equals(ButtonBarLayout.VOLUME_BUTTON)) {
//                    Log.d(TAG, "onRecieve(): VOLUME_BUTTON");
//                }
//
//                Log.d(TAG, "buttonSelected = " + buttonSelected);
//            }
//
//            // Get Volume Value
//            int volumeValue = intent.getIntExtra(ButtonBarLayout.VALUE_ON_ACTION_UP_KEY, -1);
//            if (volumeValue > -1) {
//                // adjust volume
//                AudioManager audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
//                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeValue, 0);
//
//                Log.d(TAG, "onRecieve(): VALUE_ON_ACTION_UP_KEY = " + volumeValue);
//            }
//        }
//    }

//    public void displayCancelAlert() {
//        final Handler handler = new Handler();
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        builder.setMessage("    Clinician Cancelled the meeting, Video will close soon!  ");
//        final AlertDialog dialog = builder.create();
//        dialog.show();
//        handler.postDelayed(new Runnable() {
//            public void run() {
//                dialog.dismiss();
//            }
//        }, 10000);
//    }

    public static boolean checkVibrationIsOn(Context context) {
        boolean status = false;
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if (am.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
            status = true;
        } else if (1 == Settings.System.getInt(context.getContentResolver(), "vibrate_when_ringing", 0)) //vibrate on
            status = true;
        return status;
    }


    // Callback containing the result of the permissions request. If permissions were not previously,
    // obtained, wait until this is received until calling startVidyoConnector where Connector is constructed.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i("RequestPermissionsRst", ": number of requested permissions = " + permissions.length);

        // If the expected request code is received, start VidyoConnector
        if (requestCode == PERMISSIONS_REQUEST_ALL) {
            for (int i = 0; i < permissions.length; ++i)
                Log.i("permission: ", "  " + permissions[i] + " " + grantResults[i]);
            if (mVidyoConnectorConstructed) {
                connectCall(notificationModel);
                callOverlayLayout.setVisibility(View.INVISIBLE);
                mMediaPlayer.stop();
                mConnectionSpinner.setVisibility(View.VISIBLE);
               // setStatusAndSend(Model.VIDEO_CALL_STATUS.CONNECTING);
            }
        } else {
            Log.i("", "Unexpected permission requested. ");
        }
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {

    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {

    }

    @Override
    public void onParticipantJoined(Participant participant) {

    }

    @Override
    public void onParticipantLeft(Participant participant) {
        Log.i("Test", "TestLeft");
        mVidyoConnector.disconnect();
        finishAndRemoveTask();
    }

    @Override
    public void onDynamicParticipantChanged(ArrayList<Participant> arrayList, ArrayList<RemoteCamera> arrayList1) {

    }

    @Override
    public void onLoudestParticipantChanged(Participant participant, boolean b) {

    }

}

