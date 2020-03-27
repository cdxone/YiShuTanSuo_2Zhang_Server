package com.example.yishutansuo_2zhang_server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();
    private static class MessageHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    //接收到信息
                    Log.e(TAG,"接收到的数据：" + msg.getData().get("msg"));
                    //服务器端向客户单发送消息，获取客户端的Messenger。
                    Messenger client = msg.replyTo;
                    //创建消息
                    Message relpyMessage = Message.obtain(null,2);
                    Bundle bundle = new Bundle(); bundle.putString("reply","嗯，你的消息我已经收到，稍后会回复你。");
                    //然后发送消息
                    relpyMessage.setData(bundle);
                    try {
                        client.send(relpyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger mMessenger = new Messenger(new MessageHandler());

    public MessengerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return mMessenger.getBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }
}
