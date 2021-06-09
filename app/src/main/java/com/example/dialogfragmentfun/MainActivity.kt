package com.example.dialogfragmentfun

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model: SimpleViewModel by viewModels()
        model.showDialog.observe(this, { showDialog ->
            Log.d(LOG_TAG, "observing showDialog -> $showDialog")
            showDialog ?: return@observe
            if (showDialog) {
                NonCancellableDialogFragment.show(supportFragmentManager)
                NonCancellableDialogFragment.show(supportFragmentManager)
            } else {
                NonCancellableDialogFragment.dismiss(supportFragmentManager)
            }
        })

        findViewById<View>(R.id.doWork).setOnClickListener { _ ->
            model.doWork()
        }

        if (savedInstanceState != null && model.showDialog.value == null) {
            NonCancellableDialogFragment.dismiss(supportFragmentManager)
        }
    }
}

/**
 * A basic indeterminate progress dialog that can't be cancelled via hardware back key or touching outside the dialog
 */
class NonCancellableDialogFragment : DialogFragment() {

    companion object {

        private const val TAG = "NonCancellableDialogFragment"

        fun show(fragmentManager: FragmentManager) {
            NonCancellableDialogFragment().showNow(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            Log.d(LOG_TAG, "NonCancellableDialogFragment.dismiss()")
            (fragmentManager.findFragmentByTag(TAG) as? NonCancellableDialogFragment)?.dismiss()
        }
    }

    init {
        Log.d(LOG_TAG, "NonCancellableDialogFragment init")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_Dialog)
        Log.d(LOG_TAG, "onCreate ${this::class.java.simpleName}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return inflater.inflate(R.layout.noncancellable_dialog_fragment, container, false)
    }
}

class SimpleViewModel : ViewModel() {

    private val _showDialog: MutableLiveData<Boolean?> = MutableLiveData(null)
    val showDialog: LiveData<Boolean?> = _showDialog

    private val handler = Handler(Looper.getMainLooper())

    init {
        Log.d(LOG_TAG, "SimpleViewModel init")
    }

    /**
     * Pretends to be doing something that requires the dialog to be shown for 10 seconds
     */
    fun doWork() {
        _showDialog.postValue(true)
        handler.postDelayed({
            _showDialog.postValue(false)
        }, 10000)
    }
}