package com.shang.localchatgpt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shang.localchatgpt.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

/**
 * ipconfig 查看IP
 * android:usesCleartextTraffic="true" 要打開
 * onFailure:CLEARTEXT communication to 192.168.1.106 not permitted by network security policy null
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mClient by lazy {
        OkHttpClient.Builder()
            .pingInterval(10, TimeUnit.SECONDS)
            .build()
    }

    private val mWebSocketRequest by lazy {
        Request.Builder()
            .url("ws://192.168.1.106:8888")
            .build()
    }
    private val mMessageAdapter by lazy {
        MessageAdapter()
    }
    private val mViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val webSocket = mClient.newWebSocket(mWebSocketRequest,mViewModel.chatGPTWebsocketListener)


        mBinding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        mBinding.ivArrow.setOnClickListener {
            mViewModel.sendQuestion(mBinding.etInput.text.toString())
            webSocket.send(mBinding.etInput.text.toString())
        }

        mBinding.rvMessage.layoutManager = LinearLayoutManager(this)
        mBinding.rvMessage.adapter = mMessageAdapter


        lifecycleScope.launch {
            mViewModel.messageFlow.collect{
                mMessageAdapter.submitList(it)
            }
        }
    }
}

