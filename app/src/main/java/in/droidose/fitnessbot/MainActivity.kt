package `in`.droidose.fitnessbot

import `in`.droidose.fitnessbot.adapter.MessageAdapter
import `in`.droidose.fitnessbot.interfaces.MessageInterface
import `in`.droidose.fitnessbot.model.ChatMessage
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity(), View.OnClickListener, MessageInterface, AppConstants {
    private val messageList = ArrayList<ChatMessage>()
    private var messageAdapter: MessageAdapter? = null
    private var rvMessages: RecyclerView? = null
    private val defaultMessages = ArrayList<String>()
    private var etMessage: AppCompatEditText? = null
    private var ivSend: AppCompatImageView? = null
    private var currentMessageIndex = 0
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dateFormat: DateFormat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etMessage = findViewById(R.id.etMessage)
        ivSend = findViewById(R.id.ivSend)
        layoutManager = LinearLayoutManager(this)
        ivSend!!.setOnClickListener(this)
        defaultMessages.clear()
        defaultMessages.add("Hi welcome to fitness bot")
        defaultMessages.add("What is you good name ?")
        defaultMessages.add("Hello, ")
        defaultMessages.add("Let me ask you few questions so that we can get a fitness plan ready for you.")
        defaultMessages.add("")
        defaultMessages.add("")
        defaultMessages.add("")
        defaultMessages.add("")
        defaultMessages.add("Thank you. We are now good to go..")
        rvMessages = findViewById(R.id.rvChat)
        rvMessages!!.layoutManager = layoutManager
        messageAdapter = MessageAdapter(messageList, this)
        rvMessages!!.adapter = messageAdapter
        addMessage(currentMessageIndex)
        Handler().postDelayed({ addMessage(currentMessageIndex) }, 1000)
        etMessage!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {
                if (editable.length > 0) {
                    ivSend!!.setImageResource(R.drawable.send_button)
                } else {
                    ivSend!!.setImageResource(R.drawable.ic_like_thumb_up)
                }
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    private fun addMessage(messageIndex: Int) {
        Log.e("Index", messageIndex.toString() + "")
        dateFormat = SimpleDateFormat("hh:mm a")
        currentMessageIndex += 1
        Handler().postDelayed({
            when (messageIndex) {
                0 -> messageList.add(ChatMessage(defaultMessages[messageIndex], "", AppConstants.OTHER_MESSAGE))
                2 -> {
                    messageList.add(ChatMessage(defaultMessages[messageIndex] + messageList[2].message!!, "", AppConstants.OTHER_MESSAGE))
                    addMessage(currentMessageIndex)
                }
                3 -> {
                    messageList.add(ChatMessage(defaultMessages[messageIndex], "", AppConstants.OTHER_MESSAGE))
                    addMessage(currentMessageIndex)
                }
                4 -> messageList.add(ChatMessage("", dateFormat!!.format(Date()), AppConstants.GENDER_MESSAGE))
                5 -> messageList.add(ChatMessage("", dateFormat!!.format(Date()), AppConstants.AGE_MESSAGE))
                6 -> messageList.add(ChatMessage("", dateFormat!!.format(Date()), AppConstants.HEIGHT_MESSAGE))
                7 -> messageList.add(ChatMessage("", dateFormat!!.format(Date()), AppConstants.BOGY_TYPE_MESSAGE))
                else -> messageList.add(ChatMessage(defaultMessages[messageIndex], dateFormat!!.format(Date()), AppConstants.OTHER_MESSAGE))
            }
            updateRecycler()
        }, 1000)
    }

    private fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            etMessage!!.isFocusable = false
            etMessage!!.isFocusableInTouchMode = false
            etMessage!!.isClickable = false
        }
    }

    private fun updateRecycler() {
        messageAdapter!!.notifyItemInserted(messageList.size - 1)
        layoutManager!!.scrollToPosition(messageList.size - 1)

    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(view: View) {
        dateFormat = SimpleDateFormat("hh:mm a")
        when (view.id) {
            R.id.ivSend -> {
                hideKeyBoard()
                ivSend!!.setOnClickListener(null)
                messageList.add(ChatMessage(etMessage!!.text.toString(), dateFormat!!.format(Date()), AppConstants.SELF_MESSAGE))
                etMessage!!.setText("")
                updateRecycler()
                addMessage(currentMessageIndex)
            }
            else -> {
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun addMessage(message: String) {
        dateFormat = SimpleDateFormat("hh:mm a")
        messageList.add(ChatMessage(message, dateFormat!!.format(Date()), AppConstants.SELF_MESSAGE))
        addMessage(currentMessageIndex)
        updateRecycler()
    }

    override fun removeMessage(messagePosition: Int) {}
}
