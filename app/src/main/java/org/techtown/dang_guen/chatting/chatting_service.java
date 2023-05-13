package org.techtown.dang_guen.chatting;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import org.techtown.dang_guen.dong_nae_life.dong_nae_life_ApiClient;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_ApiInterface;
import org.techtown.dang_guen.dong_nae_life.dong_nae_life_data;
import org.techtown.dang_guen.fragment_start;
import org.techtown.dong_nae_life.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static org.techtown.dang_guen.chatting.chatting_main.main_adapter;
import static org.techtown.dang_guen.chatting.chatting_room.chatting_room_number;
import static org.techtown.dang_guen.chatting.chatting_room.room_adapter;


public class chatting_service extends Service {
    public static final String TAG = "chatting_service";
    public static InetAddress serverAddr;
    public static Socket socket;
    String read;
    PrintWriter sendWriter;
    chatting_room_Handler chatting_room_Handler;
    chatting_room.chatting_room_Handler_user chatting_room_handler_user;
    static ArrayList<String> user_확인 = new ArrayList<>();
    // Channel에 대한 id 생성 : Channel을 구부하기 위한 ID 이다.
    private static final String CHANNEL_ID = "10001";
    // Channel을 생성 및 전달해 줄 수 있는 Manager 생성
    private NotificationManager mNotificationManager, OFF_manager;
    ServiceThread thread;
    String msg = null, nick = null, number = null;
    int room_number = 0;
    boolean 확인 = false;

    // 서비스로 동작하기위한 초기 시점
    @Override
    public void onCreate() {
        super.onCreate();
        chatting_room_Handler = new chatting_room_Handler();
        chatting_room_handler_user = new chatting_room.chatting_room_Handler_user();
    }


    public static Socket getSocket() {
        return socket;
    }


    //다른 클래스에서 startService를 하게 되면 onStartCommand를 부르게 된다
    //그러면 서비스는 실행이 된다다
    //Intent intent-서비스가 실행이 되면 인텐트값은 1이라는 값을 받게 된다
    //int flags
    //int startId-서비스를 종료를 하기 위해서 존재하는것
    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //그래서 재시작을 하면 서비스가 실행이 되는구나
        // 인테트가 null이면,
        if (intent == null) {
            myServiceHandler handler = new myServiceHandler();
            Message message = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("server_send_msg", msg);
            bundle.putString("server_send_nick", nick);
            bundle.putString("server_send_number", number);
            bundle.putInt("server_send_room_number", room_number);
            message.setData(bundle);
            handler.sendMessage(message);
            thread = new ServiceThread(handler);
            return START_STICKY;
        } else {
            processCommand(intent);
        }
        // 서비스가 종료 되었을 때도 다시 자동으로 실행 함.
        return START_STICKY;
    }


    //processCommand 백그라운드에서 실행하기 위해서???
    private void processCommand(Intent intent) {
        // 이 안에서 Main에서 전달 받은 Extradata를 뽑아 볼 것이다.
        String ip = intent.getStringExtra("ip");
        int port = intent.getIntExtra("port", 0);

        // 서비스에서 액티비티 보내는 방법
        new Thread() {
            @SuppressLint("LongLogTag")
            public void run() {
                try {
                    //------------------------------------------------------------------------------------------
                    /*
                     * 상대방에게 받은 메세지값
                     * */
                    InetAddress serverAddr = InetAddress.getByName(ip);

                    socket = new Socket(serverAddr, port);

                    socket.setKeepAlive(true);

                    Socket socket = chatting_service.getSocket();

                    sendWriter = new PrintWriter(socket.getOutputStream());

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));


                    while (true) {
                        read = input.readLine();
                        String[] send_user_message = read.split(",");
                        String server_send_msg = send_user_message[0];
                        String server_send_시간 = send_user_message[1];
                        String server_send_현재유저_phone_number = send_user_message[2];
                        String server_send_your_phone_number = send_user_message[3];
                        String server_send_room_number = send_user_message[4];
                        String 현재페이지 = send_user_message[5];
                        String 현재유저_image = send_user_message[6];
                        String server_send_image = send_user_message[7];
                        String delete_postion = send_user_message[8];
                        String my_location = send_user_message[9];
                        String 읽음 = send_user_message[10];
                        String server_send_product_image = send_user_message[11];
                        String server_send_my_nick = send_user_message[12];
                        String 사진이도착했습니다 = send_user_message[13];

                        int server_send_room_number_int = Integer.parseInt(server_send_room_number);

                        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
                        String 현재유저_phone_number = preferences.getString("user_number", "");


//                        에이가 에이가 아닐떄->비가 들어가겠네?
                        if (!server_send_msg.equals("null")) {
                            NotificationSomethings(server_send_my_nick, server_send_msg, server_send_your_phone_number, server_send_room_number);
                        }

                        //a가 들어가게 되면 확인변수에 들어가게 된다
                        확인 = user_확인.contains(server_send_현재유저_phone_number);
                        //b가 없다면
                        if (!확인) {
                            //b가 들어왔을때
                            try{
                                user_확인.add(server_send_현재유저_phone_number);
                                if (현재유저_phone_number.equals(server_send_your_phone_number)) {
                                    Log.e("chatting_service", "a의 어댑터가 들어왔음");
                                    Message message = chatting_room_handler_user.obtainMessage();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("server_send_your_phone_number", server_send_your_phone_number);
                                    message.setData(bundle);
                                    chatting_room_handler_user.sendMessage(message);
                                }
                            }catch (ArrayIndexOutOfBoundsException e){

                            }
                        } else if (읽음.equals("나감")) {
                            try{
                                user_확인.remove(server_send_현재유저_phone_number);
                            }catch (ArrayIndexOutOfBoundsException e){

                            }
                        }

                        //같은 방일때
                        if (현재페이지.equals("chatting_room")) {
                            Message message = chatting_room_Handler.obtainMessage();
                            Bundle bundle = new Bundle();
                            bundle.putString("server_send_msg", server_send_msg);
                            bundle.putString("server_send_시간", server_send_시간);
                            bundle.putString("server_send_현재유저_phone_number", server_send_현재유저_phone_number);
                            bundle.putString("server_send_your_phone_number", server_send_your_phone_number);
                            bundle.putString("현재페이지", 현재페이지);

                            /**내가 언제 없앴지???*/
                            bundle.putString("server_send_room_number", server_send_room_number);
                            bundle.putString("현재유저_image", 현재유저_image);
                            bundle.putString("server_send_image", server_send_image);
                            bundle.putString("delete_position", delete_postion);
                            bundle.putString("my_location", my_location);
                            bundle.putString("server_send_읽음", 읽음);
                            bundle.putString("server_send_product_image", server_send_product_image);
                            bundle.putString("server_send_my_nick", server_send_my_nick);
                            bundle.putString("사진이도착했습니다", 사진이도착했습니다);
                            message.setData(bundle);
                            chatting_room_Handler.sendMessage(message);
                        }
                        //무조건 b의 입장만 생각을 함
                        //a의 위치와 b의 현재위치가 다를때
                    }
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
        }.

                start();
    }


    //같은 채팅방에 있을때
    class chatting_room_Handler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            /*-------------------------------------------------------------------------------------------------------------*/
            SharedPreferences preferences = getSharedPreferences("현재유저", 0);
            String 현재유저_phone_number = preferences.getString("user_number", "");
            String 현재유저_image = preferences.getString("user_image", "");
            /*-------------------------------------------------------------------------------------------------------------*/

            //상대방의 현재위치를 알기 위해서
            //getSystemService-앱의 현재 어떤 서비스를 사용하고 있는지 확인
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

            //getRunningTasks()- //왜 List에 있을까?-최대 100개 까지의 시스템 서비스를 가질수 있다
            //1은 하나의 서비스만 사용하기 때문에
            //ActivityManager.RunningTaskInfo- 리스트에 getRunningTasks얻은 서비스들을 저장할수 있다
            List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);

            //topActivity-앱의 최상단 액티비티 클래스명값 가져오기
            //info-리스트에 저장되어있는 값
            //ComponentName-현재 액티비티의 패키지명,클래스명을 얻을수 있다
            ComponentName componentName = info.get(0).topActivity;

            //substring(1)-문자열에서 특정문자를 기준으로 자르는 메소드
            //String str1 = "1학년5반 손흥민";
            //substring(0, str1.indexOf(" ")); 맨 뒤에서 부터 " "공간이 있는곳까지 자름
            //결과는 1학년5반 나오게 된다
            //getShortClassName()-화면의 최상단에 보이기 위해서
            String your_location = componentName.getShortClassName().substring(1);

            chatting_room_data chat = new chatting_room_data();
            chatting_main_data main = new chatting_main_data();
            Bundle bundle = msg.getData();
            String server_send_msg = bundle.getString("server_send_msg");
            String server_send_시간 = bundle.getString("server_send_시간");
            String server_send_현재유저_phone_number = bundle.getString("server_send_현재유저_phone_number");
            String server_send_your_phone_number = bundle.getString("server_send_your_phone_number");
            String server_send_room_number = bundle.getString("server_send_room_number");
            String 현재페이지 = bundle.getString("현재페이지");
            String server_send_현재유저_image = bundle.getString("현재유저_image");
            String server_send_image = bundle.getString("server_send_image");
            String server_delete_position = bundle.getString("delete_position");
            String my_location = bundle.getString("my_location");
            String 읽음 = bundle.getString("server_send_읽음");
            String server_send_product_image = bundle.getString("server_send_product_image");
            String server_send_my_nick = bundle.getString("server_send_my_nick");
            String 사진이도착했습니다 = bundle.getString("사진이도착했습니다");
            System.out.println(server_send_room_number + "server_send_room_number비어있음");
            int server_send_room_number_int = Integer.parseInt(server_send_room_number);
            int server_delete_position_int = Integer.parseInt(server_delete_position);
            String room_number = String.valueOf(chatting_room_number);
            /**
             * 2월 16일 목요일
             * 1.채팅방이 아닌 다른 액티비티에 있을때 채팅 알림
             * 1)노티피케이션매니저 만들기-노티피케이션의 객체를 만듬
             * 2)앱의 채널 만들기-티비의 채널이 있듯이 앱에도 채널이 있어야 상대방에게 정보를 보낼수가 있다
             * 3)알림창 나오게 하기 문자를 보낸 상대방의 닉네임값,메세지값 설정하기
             * */
            try {
                if (!현재유저_phone_number.equals(server_send_현재유저_phone_number) && server_send_room_number.equals(room_number) && (사진이도착했습니다.equals("사진없음"))) {
                    chat.setChatting_room_msg(server_send_msg);
                    chat.setChatting_room_time(server_send_시간);
                    chat.setMy_number(server_send_현재유저_phone_number);
                    chat.setRoom_number(server_send_room_number_int);
                    chat.setYour_profile_image(현재유저_image);
                    chat.setChatting_room_read_check(null);
                    chat.setViewType(ViewType.LEFT_CHAT);
                    chatting_room.room_adapter.addChat(chat);
                } else if (!현재유저_phone_number.equals(server_send_현재유저_phone_number) && server_send_room_number.equals(room_number) && 사진이도착했습니다.equals("사진이 도착했습니다")) {
                    chat.setSend_select_image(server_send_image);
                    chat.setChatting_room_time(server_send_시간);
                    chat.setMy_number(server_send_현재유저_phone_number);
                    chat.setRoom_number(server_send_room_number_int);
                    chat.setChatting_room_read_check(null);
                    chat.setYour_profile_image(현재유저_image);
                    chat.setViewType(ViewType.LEFT_CHAT);
                    chatting_room.room_adapter.addChat(chat);
                } else if (server_delete_position_int >= 0) {
                    chat.setChatting_room_msg(server_send_msg);
                    chat.setChatting_room_time(server_send_시간);
                    chat.setMy_number(server_send_현재유저_phone_number);
                    chat.setRoom_number(server_send_room_number_int);
                    chat.setYour_profile_image(현재유저_image);
                    chat.setDelete_position(server_delete_position_int);
                    chat.setViewType(ViewType.LEFT_CHAT);
                    chatting_room.room_adapter.delete_Chat(server_delete_position_int);
                } else if (!server_send_msg.equals("null") && your_location.equals("fragment_start") && 현재유저_phone_number.equals(server_send_your_phone_number) && main_adapter != null) {
                    main.setChatting_main_product_image(server_send_product_image);
                    main.setChatting_main_your_profile_image(현재유저_image);
                    main.setChatting_main_my_nick(server_send_my_nick);
                    main.setChatting_main_idx(server_send_room_number_int);
                    main.setChatting_main_time(server_send_시간);
                    main.setChatting_main_last_msg(server_send_msg);
                    main.setChatting_main_my_number(server_send_현재유저_phone_number);
                    main.setChatting_main_your_number(server_send_your_phone_number);
                    main.setChatting_main_room_check(server_send_현재유저_phone_number + server_send_your_phone_number);
                    main_adapter.reset_chatting_main(server_send_현재유저_phone_number + server_send_your_phone_number, main);
                }
            } catch (NullPointerException e) {

            }

        }
    }


    //노티피케이션
    @SuppressLint("LongLogTag")
    public void NotificationSomethings(String server_send_my_nick, String server_send_msg, String your, String server_send_room_number) {
        System.out.println("들어가?1");
        //현재 사용하고 있는 액티비티를 String화를 하는것
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> info = manager.getRunningTasks(1);
        ComponentName componentName = info.get(0).topActivity;
        String topActivityName = componentName.getShortClassName().substring(1);

        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        /*-------------------------------------------------------------------------------------------------------------*/

        SharedPreferences pref = getSharedPreferences(server_send_room_number + 현재유저_phone_number, MODE_PRIVATE);
        String noti_on_off_shared = pref.getString("알림상태", "null");
        System.out.println(현재유저_phone_number + noti_on_off_shared + "noti_on_off_shared");


        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        /*notify*/


        //앱을 켜진상황
        if (!topActivityName.equals("NexusLauncherActivity") && !topActivityName.equals("chatting.chatting_room") && 현재유저_phone_number.equals(your) && !noti_on_off_shared.equals("off")) {
            System.out.println("들어가?2");
            /*NotificationCompat*/
            /*Builder*/
            /* NotificationCompat.Builder를 사용하려면 채널이 있어야 된다*/
//        Compat-소형의,작은
//        Builder-쌓다
            //notification manager 생성
            /* 화면 상단에 보여주기 위해서 객체를 만듬*/
            /* getSystemService()-서비스를 가져온다*/
            /*NOTIFICATION_SERVICE-알림을 이용한다*/
            int change_int = Integer.parseInt(server_send_room_number);
            System.out.println(change_int + "change_int");
            Intent notificationIntent = new Intent(getApplicationContext(), chatting_room.class);
            notificationIntent.putExtra("server_send_room_number", change_int);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
            /*버전확인*/
            if (android.os.Build.VERSION.SDK_INT
                    >= android.os.Build.VERSION_CODES.O) {
                //Channel 정의 생성자( construct 이용 )

                /*앱의 채널(정보를 얻기위해 길을 알려줌)을 만듬*/
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
                //Channel에 대한 기본 설정
                /*불빛표시 여부*/
                notificationChannel.enableLights(true);
                /*진동여부*/
                notificationChannel.enableVibration(true);
                /*채널 설명 문자열*/
                notificationChannel.setDescription("Notification from Mascot");
                /*Manager을 이용하여 Channel 생성*/
                mNotificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    /*밑에 3가지가 없으면 안된다*/
                    //타이틀은 니넥임
                    .setContentTitle(server_send_my_nick)
                    //문자가 왔습니다
                    .setContentText(server_send_msg)
                    //사각형 아이콘
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시
                    .setAutoCancel(true); //notification을 탭 했을경우 notification을 없앤다.
            mNotificationManager.notify(1234, notifyBuilder.build());
        }

        //앱을 끈 상황
        else if (topActivityName.equals("NexusLauncherActivity") && !server_send_msg.equals("메세지값 없음") && 현재유저_phone_number.equals(your) && !noti_on_off_shared.equals("off")) {
            int change_int = Integer.parseInt(server_send_room_number);
            System.out.println(change_int + "change_int");
            Intent notificationIntent = new Intent(getApplicationContext(), chatting_room.class);
            notificationIntent.putExtra("server_send_room_number", change_int);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
            /*버전확인*/
            if (android.os.Build.VERSION.SDK_INT
                    >= android.os.Build.VERSION_CODES.O) {
                //Channel 정의 생성자( construct 이용 )

                /*앱의 채널(정보를 얻기위해 길을 알려줌)을 만듬*/
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
                //Channel에 대한 기본 설정
                /*불빛표시 여부*/
                notificationChannel.enableLights(true);
                /*진동여부*/
                notificationChannel.enableVibration(true);
                /*채널 설명 문자열*/
                notificationChannel.setDescription("Notification from Mascot");
                /*Manager을 이용하여 Channel 생성*/
                mNotificationManager.createNotificationChannel(notificationChannel);
            }

            NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                    /*밑에 3가지가 없으면 안된다*/
                    //타이틀은 니넥임
                    .setContentTitle(server_send_my_nick)
                    //문자가 왔습니다
                    .setContentText(server_send_msg)
                    //사각형 아이콘
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시
                    .setAutoCancel(true); //notification을 탭 했을경우 notification을 없앤다.
            mNotificationManager.notify(1234, notifyBuilder.build());
        }
        //조건문 만들어서 포그라운드가 여기에 올수있게 만들기
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
    }


    //거의 사용하지 않는다고 한다
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class myServiceHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            //현재 사용하고 있는 액티비티를 String화를 하는것
            Bundle bundle = msg.getData();
            String server_send_msg = bundle.getString("server_send_msg");
            String server_send_nick = bundle.getString("server_send_nick");
            String server_send_number = bundle.getString("server_send_number");
            int server_send_room_number = bundle.getInt("server_send_room_number");


            /*-------------------------------------------------------------------------------------------------------------*/
            SharedPreferences preferences = getSharedPreferences("현재유저", 0);
            String 현재유저_phone_number = preferences.getString("user_number", "");
            /*-------------------------------------------------------------------------------------------------------------*/
            /*notify*/
            if (현재유저_phone_number.equals(server_send_number)) {
                System.out.println("들어가?3");

                /*NotificationCompat*/
                /*Builder*/
                /* NotificationCompat.Builder를 사용하려면 채널이 있어야 된다*/
//        Compat-소형의,작은
//        Builder-쌓다
                //notification manager 생성
                /* 화면 상단에 보여주기 위해서 객체를 만듬*/
                /* getSystemService()-서비스를 가져온다*/
                /*NOTIFICATION_SERVICE-알림을 이용한다*/
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent notificationIntent = new Intent(getApplicationContext(), chatting_room.class);
                notificationIntent.putExtra("server_send_room_number", server_send_room_number);
                notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                // 기기(device)의 SDK 버전 확인 ( SDK 26 버전 이상인지 - VERSION_CODES.O = 26)
                /*버전확인*/
                if (android.os.Build.VERSION.SDK_INT
                        >= android.os.Build.VERSION_CODES.O) {
                    //Channel 정의 생성자( construct 이용 )

                    /*앱의 채널(정보를 얻기위해 길을 알려줌)을 만듬*/
                    NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Test Notification", NotificationManager.IMPORTANCE_HIGH);
                    //Channel에 대한 기본 설정
                    /*불빛표시 여부*/
                    notificationChannel.enableLights(true);
                    /*진동여부*/
                    notificationChannel.enableVibration(true);
                    /*채널 설명 문자열*/
                    notificationChannel.setDescription("Notification from Mascot");
                    /*Manager을 이용하여 Channel 생성*/
                    mNotificationManager.createNotificationChannel(notificationChannel);
                }

                NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        /*밑에 3가지가 없으면 안된다*/
                        //타이틀은 니넥임
                        .setContentTitle(server_send_nick)
                        //문자가 왔습니다
                        .setContentText(server_send_msg)
                        //사각형 아이콘
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent) // 사용자가 노티피케이션을 탭시
                        .setAutoCancel(true); //notification을 탭 했을경우 notification을 없앤다.
                mNotificationManager.notify(1234, notifyBuilder.build());
            }

        }

    }

    //앱 강제종료할때 알수있는 코드
    //-----------------------------------------------------------------------------------------------------------------------------
    /*
    onTaskRemoved
           역할?앱을 강제로 종료함을 알수있는 코드
           책임?앱을 종료 시키고 알수있게 해야함
           협력?stopSelf()와 메인액티비티에 있는 startService를 이용해서 종료했음을 알려주는것을 도와준다
           위치?
           매개변수?
           */


    @SuppressLint({"NewApi", "WrongConstant"})
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        /*-------------------------------------------------------------------------------------------------------------*/
        SharedPreferences preferences = getSharedPreferences("현재유저", 0);
        String 현재유저_phone_number = preferences.getString("user_number", "");
        /*-------------------------------------------------------------------------------------------------------------*/

        //------------------------------------------------------------------------------------------
        /*나의 메세지값을 서버에 보내는곳*/
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Socket socket = chatting_service.getSocket();
                    sendWriter = new PrintWriter(socket.getOutputStream());
                    //메세지,시간,쉐어드 폰넘버,너의 폰넘버,채팅룸 넘버,현재페이지,룸넘버,선택한 이미지,현재유저 이미지,사진이 도착했습니다
                    sendWriter.print("메세지값 없음" + ",");
                    sendWriter.print("시간값 없음" + ",");
                    sendWriter.print("나의 번호값을 보냈음" + ",");
                    sendWriter.print(현재유저_phone_number + ",");
                    sendWriter.print("-1" + ",");
                    //앱을 종료시킴
                    sendWriter.print("NexusLauncherActivity" + ",");
                    sendWriter.print("유저이미지 없음" + ",");
                    sendWriter.print("이미지값 없음" + ",");
                    sendWriter.print("아이템번호값 없음" + ",");
                    sendWriter.print("나의위치값 없음" + ",");
                    sendWriter.print("읽음값 없음" + ",");
                    sendWriter.print("상품이미지값 없음" + ",");
                    sendWriter.print("나의닉네임값 없음" + ",");
                    sendWriter.println("사진이 도착했습니다값 없음");
                    sendWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        /**환경설정에서 소리 설정하는곳에서 소리를 켜고 메세지 popup창을 띄울것인지 아닌지 확인해야 된다*/
        //포그라운드 시작하는 부분
        //-----------------------------------------------------------------------------------------------------------------------------
     /*
OFF_manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
역할?NotificationManager는 노티의 시스템을 현재 클래스에서 실행할수있도록(관리) 하는 코드
왜 매니저라고 표현할까?연예인을 생각해보자 데뷔를 시키려면 음악만드는 관리자 생활 관리자 등등 해줘야 무대를 설수가 있다


책임?노티피케이션을 실행할수 있도록 해야한다
협력? OFF_manager.createNotificationChannel(notificationChannel); 채널을 만들때 사용한다
위치?x
*/
        OFF_manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);



            /*
  if (android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O) {
역할? android.os.Build.VERSION.SDK_INT 현재 안드로이드 버전 확인하는 코드,  android.os.Build.VERSION_CODES.O는 안드로이드 26버전을 의미

책임? 26이상이여 만 채널을 만들수가 있다

협력? OFF_manager.createNotificationChannel(notificationChannel);을 만들수가 있다

위치? x
*/
        if (android.os.Build.VERSION.SDK_INT
                >= android.os.Build.VERSION_CODES.O) {



               /*
NotificationChannel notificationChannel = new NotificationChannel("off", "off Notification", NotificationManager.IMPORTANCE_DEFAULT);
역할? 노티의 채널을 만들어 준다 조건은 채널 id, 채널명, 중요순서 정도
//채널을 만들어 주는 이유-옛날 소규모 앱은 알림이 많이 없었는데 앱이 커지면서 여러가지 알림을 받기위해서

책임?노티의 채널을 만들어 줘야 한다

협력? notificationChannel.setDescription("Notification from Mascot");

위치?
*/
            NotificationChannel notificationChannel = new NotificationChannel("off", "off Notification", NotificationManager.IMPORTANCE_DEFAULT);


             /*
OFF_manager.createNotificationChannel(notificationChannel);
역할?노티 채널을 만들어 준다
책임?노티 채널을 만들어 줘야한다
협력?
OFF_manager.createNotificationChannel(notificationChannel); 노티를 실행해줄 객체를 만들고
NotificationChannel notificationChannel = new NotificationChannel("off", "off Notification", NotificationManager.IMPORTANCE_DEFAULT); 채널 객체 생성한다음
매니저에 채널을 생성을 해준다

위치?
*/
            OFF_manager.createNotificationChannel(notificationChannel);
        }

            /*
NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, "off");
역할?만든 채널명을 이용해서 제목 내용 이미지등 설정 쌓을수 있다
//왜 빌더라고 표현할까? 밑에 있는 코드 처럼 쌓고 있다고 볼수있다
                .setContentTitle(server_send_nick)
                .setContentText(server_send_msg)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

책임?노티에 위에 나온것처럼 설정을 할수가 있다
협력?    startForeground(1, notifyBuilder.build());로 포그라운드 실행을 할수가 있다
*/
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, "off");



        /*
startForeground(1, notifyBuilder.build());
역할?앱이 꺼진 상태에서 실행을 할수있게 해준다
왜 포그라운드일까? 앱이 꺼져있으니 안보이니까 중요한 위치라고 표현함

책임?앱이 꺼져도 실행할수 있게 해야된다
협력? NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, "off"); 내가 쌓아 올려서 만든것을 잘 보이게 해준다는 의미
위치?
*/
        startForeground(1, notifyBuilder.build());
    }
}
//-----------------------------------------------------------------------------------------------------------------------------


