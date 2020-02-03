package com.example.advancedwebview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener{

    private WebView webView;
    private AdvancedWebView mWebView;
    private WebSettings mWebSettings;
    Handler handler = new Handler();
    EditText editText;
    Button button;
    TextView textView;
    Context context;
    TextView mTextView;
    EditText mEditText;
    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mWebView = findViewById(R.id.webView3);
        editText = findViewById(R.id.edit_num);
        button = findViewById(R.id.send_button);
        textView = findViewById(R.id.web_text);
        context = this;
        mTextView = (TextView) findViewById(R.id.textview);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.edittext);

        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new AndroidBridge(),"android");
        mWebView.loadUrl("file:///android_asset/www/exam2.html");

        mButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mWebView.loadUrl("javascript:setMessage('" + mEditText.getText() + "')");
            }
        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mWebView.loadUrl("javascript:exam_script.plus_num("+editText.getText()+")");
//            }
//        });

//        mWebView = (AdvancedWebView) findViewById(R.id.webView);
//        //mWebView.setListener(this, this);
//        mWebView.getSettings().setJavaScriptEnabled(true);
//        mWebView.loadUrl("file:///android_asset/www/test.html");
//        mWebView.setWebChromeClient(new WebChromeClient()); //웹뷰에 크롬 사용 허용 //이 부분이 없으면 크롬에서 alert가 뜨지 않음

    }

    class WebBridge{
        @JavascriptInterface
        public void getNum(final int num){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context,"계산 결과는 "+ num +"입니다.",Toast.LENGTH_LONG).show();
                    textView.setText("Java :::: "+num);
                }
            });
        }
    }

    class AndroidBridge {
        @JavascriptInterface
        public void setMessage(final String arg) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "받은 메시지는 " + arg + "입니다.", Toast.LENGTH_LONG).show();
                    mTextView.setText("받은 메시지 : \n" + arg);
                }
            });
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //뒤로가기 버튼 이벤트
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) { //웹뷰에서 뒤로가기 버튼을 누르면 뒤로가짐
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mWebView.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mWebView.onActivityResult(requestCode,resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String s, Bitmap bitmap) {

    }

    @Override
    public void onPageFinished(String s) {

    }

    @Override
    public void onPageError(int i, String s, String s1) {

    }

    @Override
    public void onDownloadRequested(String s, String s1, String s2, String s3, long l) {

    }

    @Override
    public void onExternalPageRequest(String s) {

    }
}
