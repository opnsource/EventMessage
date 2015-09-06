package com.event.message.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

import com.event.message.core.EventHandlerMessageBuilder;
import com.event.message.core.EventPostBuilder;
import com.event.message.core.EventPublisher;

public class MainActivity extends Activity {

    private Test test1 = new Test();

    private Test test2 = new Test();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventPublisher.addSupportMethod("helloTest");
        EventPublisher.register(test1);
        EventPublisher.register(test2);
        EventPublisher.addEventProcess(new TestProcess());
        EventPublisher.post(EventPostBuilder.createAsyPost().params("aaaa"));
        EventPublisher.post(EventPostBuilder.createAsyPost().delay(4000).params("bbbbb"));
        EventPublisher.post(EventPostBuilder.createMainPost().params("cccc"));
        EventPublisher.post(EventPostBuilder.createBackgroundPost().params("dddd"));
        EventPublisher.post(EventPostBuilder.createPost().params("fffff"));
        EventPublisher.post(EventPostBuilder.createBuilder("helloTest").delay(4000).params("test custom process"));
        EventPublisher.sendMessage(new EventHandlerMessageBuilder().what(300));
        EventPublisher.post(EventPostBuilder.createAsyPost().params("hhhhhh").target(test1));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class Test {
        public void onPostEvent(String txt) {
            System.out.println("txt:" + txt + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
        }

        public String onAsyEvent(String txt) {
            System.out.println("txt:" + txt + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
            return "hello ";
        }

        public void onBackgroundEvent(String txt) {
            System.out.println("txt:" + txt + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
        }

        public void onMainEvent(String txt) {
            System.out.println("txt:" + txt + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
        }

        public void handlerMessage(Message msg) {
            System.out.println("txt:" + msg.what + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
        }

        public void helloTest(String txt) {
            System.out.println("helloText txt:" + txt + " Looper:" + Looper.myLooper() + " " + Thread.currentThread().getName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventPublisher.removeSupportMethod("helloTest");
        EventPublisher.unregister(test1);
        EventPublisher.unregister(test2);
    }
}
