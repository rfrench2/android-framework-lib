package info.anodsplace.framework.content

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import info.anodsplace.applog.AppLog

class CreateDocument(private val mimeType: String) : ActivityResultContract<CreateDocument.Args, Uri?>() {
    private val inner = ActivityResultContracts.CreateDocument(mimeType)
    class Args(val title: String?, val initialUri: Uri? = null)
    
    @SuppressLint("MissingSuperCall")
    override fun createIntent(context: Context, input: Args): Intent {
        return Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            putExtra(Intent.EXTRA_TITLE, input.title)
            try {
                setDataAndType(input.initialUri, mimeType)
                if (input.initialUri != null) {
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, input.initialUri)
                }
            } catch (e: Exception) {
                AppLog.e(e)
            }
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return inner.parseResult(resultCode, intent)
    }
}