package info.anodsplace.framework.app

import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.color.DynamicColors
import info.anodsplace.framework.R
import kotlinx.coroutines.launch

/**
 * @author Alex Gavrishev
 * @date 2015-06-20
 */
abstract class ToolbarActivity : AppCompatActivity(), CustomThemeActivity {

    override val themeRes = 0
    override val themeColors = CustomThemeColors.none

    abstract val layoutView: View

    @get:IdRes
    open val detailsLayoutId = 0

    @get:IdRes
    open val hingeLayoutId = 0

    private lateinit var duoDevice: HingeDevice

    private var hinge: View? = null
    private var details: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val app = ApplicationContext(this)
        AppCompatDelegate.setDefaultNightMode(app.appCompatNightMode)
        if (this.themeRes > 0) {
            this.setTheme(this.themeRes)
        }
        DynamicColors.applyToActivityIfAvailable(this)
        if (themeColors.available) {
            WindowCustomTheme.apply(themeColors, window, this)
        }
        duoDevice = HingeDevice.create(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutView)
        setupToolbar()
        lifecycleScope.launch {
            duoDevice.layout.collect {
                updateWideLayout(it)
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        duoDevice.attachedToWindow = true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        duoDevice.attachedToWindow = false
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val wideLayout = duoDevice.layout.value
        updateWideLayout(wideLayout)
    }

    protected open fun updateWideLayout(wideLayout: HingeDeviceLayout) {
        if (hingeLayoutId != 0) {
            if (this.hinge == null) {
                this.hinge = findViewById(hingeLayoutId)
            }
            this.hinge!!.isVisible = wideLayout.isWideLayout && wideLayout.hinge.width() > 0
            this.hinge!!.layoutParams.width = wideLayout.hinge.width()
        }
        if (detailsLayoutId != 0) {
            if (details == null) {
                details = findViewById(detailsLayoutId)
            }
            details!!.isVisible = wideLayout.isWideLayout
        }
    }

    private fun setupToolbar() {
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        if (toolbar != null) {
            //set the Toolbar as ActionBar
            setSupportActionBar(toolbar)

            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        if (!themeColors.statusBarColor.isLight) {
            for (i in 0 until menu.size()) {
                menu.getItem(i).iconTintList = ColorStateList.valueOf(Color.WHITE)
                menu.getItem(i).iconTintMode = PorterDuff.Mode.SRC_IN
            }
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected val intentExtras: Bundle
        get() = intent?.extras ?: Bundle()

}