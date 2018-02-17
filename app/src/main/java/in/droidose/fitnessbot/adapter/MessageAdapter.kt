package `in`.droidose.fitnessbot.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.shawnlin.numberpicker.NumberPicker

import java.util.ArrayList

import `in`.droidose.fitnessbot.AppConstants
import `in`.droidose.fitnessbot.MainActivity
import `in`.droidose.fitnessbot.R
import `in`.droidose.fitnessbot.model.ChatMessage

/**
 * Created by rajatdhamija
 * 19/01/18.
 */

class MessageAdapter(chatMessageList: ArrayList<ChatMessage>, mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AppConstants {
    private var chatMessageList = ArrayList<ChatMessage>()
    private val mainActivity: MainActivity

    init {
        this.chatMessageList = chatMessageList
        mainActivity = mContext as MainActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            AppConstants.SELF_MESSAGE -> {
                val selfView = LayoutInflater.from(parent.context).inflate(R.layout.item_message_right, parent, false)
                return SelfViewHolder(selfView)
            }
            AppConstants.OTHER_MESSAGE -> {
                val otherView = LayoutInflater.from(parent.context).inflate(R.layout.item_message_left, parent, false)
                return OtherViewHolder(otherView)
            }
            AppConstants.GENDER_MESSAGE -> {
                val selectGender = LayoutInflater.from(parent.context).inflate(R.layout.item_select_gender, parent, false)
                return GenderViewHolder(selectGender)
            }
            AppConstants.AGE_MESSAGE -> {
                val selectAge = LayoutInflater.from(parent.context).inflate(R.layout.item_age, parent, false)
                return AgeViewHolder(selectAge)
            }
            AppConstants.HEIGHT_MESSAGE -> {
                val selectHeight = LayoutInflater.from(parent.context).inflate(R.layout.item_height, parent, false)
                return HeightViewHolder(selectHeight)
            }
            AppConstants.BOGY_TYPE_MESSAGE -> {
                val selectBodyType = LayoutInflater.from(parent.context).inflate(R.layout.item_body_type, parent, false)
                return BodyTypeViewHolder(selectBodyType)
            }
            else -> return null
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, pos: Int) {
        val position = holder.adapterPosition
        val itemType = getItemViewType(position)
        val chatMessageItem = chatMessageList[position]
        when (itemType) {
            AppConstants.SELF_MESSAGE -> {
                val selfViewHolder = holder as SelfViewHolder
                if (position > 0 && getItemViewType(position - 1) == AppConstants.SELF_MESSAGE
                        && getItemViewType(position) == AppConstants.SELF_MESSAGE) {
                    selfViewHolder.tvMessage.setBackgroundResource(R.drawable.chat_bg_right_normal)
                } else {
                    selfViewHolder.tvMessage.setBackgroundResource(R.drawable.chat_bg_right)
                }
                selfViewHolder.tvMessage.text = chatMessageItem.message
                selfViewHolder.tvTime.text = chatMessageItem.time
                selfViewHolder.tvMessage.setPadding(20, 20, 40, 20)
            }
            AppConstants.OTHER_MESSAGE -> {
                val otherViewHolder = holder as OtherViewHolder
                val params = LinearLayoutCompat.LayoutParams(
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT
                )
                if (position > 0 && getItemViewType(position - 1) == AppConstants.OTHER_MESSAGE
                        && getItemViewType(position) == AppConstants.OTHER_MESSAGE) {
                    otherViewHolder.tvMessage.setBackgroundResource(R.drawable.chat_bg_left_normal)
                    params.setMargins(30, 3, 60, 0)
                    otherViewHolder.tvMessage.setPadding(20, 20, 20, 20)
                } else {
                    otherViewHolder.tvMessage.setBackgroundResource(R.drawable.chat_bg_left)
                    params.setMargins(12, 3, 60, 0)
                    otherViewHolder.tvMessage.setPadding(40, 20, 40, 20)
                }
                otherViewHolder.tvMessage.layoutParams = params
                if (!TextUtils.isEmpty(chatMessageItem.time)) {
                    otherViewHolder.tvTime.text = chatMessageItem.time
                } else {
                    otherViewHolder.tvTime.visibility = View.GONE
                }
                otherViewHolder.tvMessage.text = chatMessageItem.message
                val toastBg = otherViewHolder.tvMessage.background
                val drawable = tintDrawable(toastBg, ColorStateList.valueOf(mainActivity.resources.getColor(R.color.otherMessageBg)))
                otherViewHolder.tvMessage.background = drawable
                val drawable2 = otherViewHolder.tvMessage.background as NinePatchDrawable
                drawable2.setColorFilter(mainActivity.resources.getColor(R.color.otherMessageBg), PorterDuff.Mode.MULTIPLY)
            }
            AppConstants.GENDER_MESSAGE -> {
                val genderViewHolder = holder as GenderViewHolder
                genderViewHolder.tvMale.setOnClickListener {
                    genderViewHolder.tvMale.setOnClickListener(null)
                    genderViewHolder.tvFemale.setOnClickListener(null)
                    mainActivity.addMessage("I am a Male")
                }
                genderViewHolder.tvFemale.setOnClickListener {
                    genderViewHolder.tvMale.setOnClickListener(null)
                    genderViewHolder.tvFemale.setOnClickListener(null)
                    mainActivity.addMessage("I am a Female")
                }

                if (!TextUtils.isEmpty(chatMessageItem.time)) {
                    genderViewHolder.tvTime.text = chatMessageItem.time
                } else {
                    genderViewHolder.tvTime.visibility = View.GONE
                }
            }
            AppConstants.AGE_MESSAGE -> {
                val ageViewHolder = holder as AgeViewHolder
                ageViewHolder.tvContinue.setOnClickListener {
                    mainActivity.addMessage("I am " + ageViewHolder.npAge.value + " years young")
                    ageViewHolder.tvContinue.setOnClickListener(null)
                }
                if (!TextUtils.isEmpty(chatMessageItem.time)) {
                    ageViewHolder.tvTime.text = chatMessageItem.time
                } else {
                    ageViewHolder.tvTime.visibility = View.GONE
                }
            }
            AppConstants.HEIGHT_MESSAGE -> {
                val heightViewHolder = holder as HeightViewHolder
                heightViewHolder.tvContinue.setOnClickListener {
                    mainActivity.addMessage("I am " + heightViewHolder.npFeet.value + "\' " + heightViewHolder.npInches.value + "\"" + " tall")
                    heightViewHolder.tvContinue.setOnClickListener(null)
                }

                if (!TextUtils.isEmpty(chatMessageItem.time)) {
                    heightViewHolder.tvTime.text = chatMessageItem.time
                } else {
                    heightViewHolder.tvTime.visibility = View.GONE
                }
            }
            AppConstants.BOGY_TYPE_MESSAGE -> {
                val bodyTypeViewHolder = holder as BodyTypeViewHolder
                bodyTypeViewHolder.tvEctomorph.setOnClickListener {
                    bodyTypeViewHolder.tvEctomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvMesomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvEndomorph.setOnClickListener(null)
                    mainActivity.addMessage("I am an Ectomorph")
                }
                bodyTypeViewHolder.tvMesomorph.setOnClickListener {
                    bodyTypeViewHolder.tvEctomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvMesomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvEndomorph.setOnClickListener(null)
                    mainActivity.addMessage("I am a Mesomorph")
                }
                bodyTypeViewHolder.tvEndomorph.setOnClickListener {
                    bodyTypeViewHolder.tvEctomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvMesomorph.setOnClickListener(null)
                    bodyTypeViewHolder.tvEndomorph.setOnClickListener(null)
                    mainActivity.addMessage("I am an Endomorph")
                }
                if (!TextUtils.isEmpty(chatMessageItem.time)) {
                    bodyTypeViewHolder.tvTime.text = chatMessageItem.time
                } else {
                    bodyTypeViewHolder.tvTime.visibility = View.GONE
                }
            }
            else -> {
            }
        }


    }

    private fun tintDrawable(drawable: Drawable, colors: ColorStateList): Drawable {
        val wrappedDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTintList(wrappedDrawable, colors)
        return wrappedDrawable
    }

    override fun getItemViewType(position: Int): Int {
        return chatMessageList[position].messageType
    }

    override fun getItemCount(): Int {
        return chatMessageList.size
    }


    internal inner class SelfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: AppCompatTextView
        val tvTime: AppCompatTextView

        init {
            tvMessage = itemView.findViewById(R.id.tvMessage)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    internal inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: AppCompatTextView
        val tvTime: AppCompatTextView

        init {
            tvMessage = itemView.findViewById(R.id.tvMessage)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    internal inner class GenderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMale: TextView
        val tvFemale: TextView
        val tvTime: TextView

        init {
            tvMale = itemView.findViewById(R.id.tvMale)
            tvFemale = itemView.findViewById(R.id.tvFemale)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    internal inner class AgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val npAge: NumberPicker
        val tvContinue: TextView
        val tvTime: TextView

        init {
            tvContinue = itemView.findViewById(R.id.tvContinue)
            npAge = itemView.findViewById(R.id.npAge)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    internal inner class HeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val npFeet: NumberPicker
        val npInches: NumberPicker
        val tvContinue: TextView
        val tvTime: TextView

        init {
            tvContinue = itemView.findViewById(R.id.tvContinue)
            npFeet = itemView.findViewById(R.id.npFeet)
            npInches = itemView.findViewById(R.id.npInches)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }

    internal inner class BodyTypeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvEctomorph: TextView
        val tvMesomorph: TextView
        val tvEndomorph: TextView
        val tvTime: TextView

        init {
            tvEctomorph = itemView.findViewById(R.id.tvEctomorph)
            tvMesomorph = itemView.findViewById(R.id.tvMesomorph)
            tvEndomorph = itemView.findViewById(R.id.tvEndomorph)
            tvTime = itemView.findViewById(R.id.tvTime)
        }
    }
}
