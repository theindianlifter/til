package `in`.droidose.fitnessbot

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class PhoneActivity : BaseActivity(), View.OnClickListener {
    private val REQUEST_CODE_SMS: Int = 100;
    private var btnSignup: AppCompatButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)
        btnSignup = findViewById(R.id.btnSignup)
        btnSignup?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSignup -> {
                val dialog: Dialog
                var tvContinue: TextView? = null
                var tvNotNow: TextView? = null
                dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_permission)
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                tvContinue = dialog.findViewById(R.id.tvContinue)
                tvNotNow = dialog.findViewById(R.id.tvNotNow)
                val clickListener =
                        View.OnClickListener { view ->
                            when (view.id) {
                                R.id.tvContinue -> {
                                    ActivityCompat.requestPermissions(this,
                                            arrayOf(Manifest.permission.READ_SMS), REQUEST_CODE_SMS)
                                    dialog.dismiss()
                                }
                                R.id.tvNotNow -> {
                                    dialog.dismiss()
                                    startActivity(Intent(this, OtpActivity::class.java));
                                }
                            }
                        }
                tvContinue.setOnClickListener(clickListener)
                tvNotNow.setOnClickListener(clickListener)
                dialog.setCancelable(false)
                dialog.show()
            }
            else -> {

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        startActivity(Intent(this, OtpActivity::class.java));
    }
}