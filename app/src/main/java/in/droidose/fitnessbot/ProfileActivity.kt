package `in`.droidose.fitnessbot

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.View

class ProfileActivity : BaseActivity(), View.OnClickListener {
    private var btnSubmit: AppCompatButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnSubmit?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSubmit -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
            else -> {

            }
        }
    }
}
