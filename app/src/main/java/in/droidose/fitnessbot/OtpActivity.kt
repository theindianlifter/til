package `in`.droidose.fitnessbot

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.View

class OtpActivity : BaseActivity(), View.OnClickListener {
    private var btnConfirm: AppCompatButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)
        btnConfirm = findViewById(R.id.btnConfirm)
        btnConfirm?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnConfirm -> {
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            else -> {

            }
        }
    }
}
