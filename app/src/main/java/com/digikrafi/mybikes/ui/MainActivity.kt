package com.digikrafi.mybikes.ui

import android.content.Intent
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.digikrafi.mybikes.R
import com.digikrafi.mybikes.base.BaseDashboardFragment
import com.digikrafi.mybikes.model.Features
/**
 * Created by rahul.p
 * Main launcher activity that is the base for multiple fragment architecture
 * and their related operations
 */


class MainActivity : AppCompatActivity(), BaseDashboardFragment.DashboardListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        selectScreen(MyFashionDashboardScreen.DashboardScreen)
    }

    /**
     * Selected screen navigation
     */
    private fun selectScreen(
        dashboardScreen: MyFashionDashboardScreen,
        bundleToSend: Bundle? = null
    ) {
        val fragment: BaseDashboardFragment = when (dashboardScreen) {
            MyFashionDashboardScreen.DashboardScreen -> DashboardFragment()
        }
        fragment.setListener(this)
        transactFragment(fragment, R.id.container, arguments = bundleToSend)
    }

    /**
     * Different type of screens that we will be supporting as fragment
     *
     */
    private enum class MyFashionDashboardScreen {
        DashboardScreen
    }

    /**
     * Commit to transaction on fragment passed
     *
     */
    private fun transactFragment(
        frag: Fragment,
        @IdRes fragmentContainer: Int,
        arguments: Bundle? = null,
        retain: Boolean = true
    ) {
        val transaction = supportFragmentManager.beginTransaction()
            .apply {
                if (retain) addToBackStack(null)
                replace(fragmentContainer, frag, frag.tag)
            }
        arguments?.let {
            frag.arguments = it
        }

        transaction
            .commit()
    }

    /**
     * Callback that is triggered from the Dashboard Fragment
     * Also sending the selected  Features type
     */
    override fun onFeaturesSelected(features: Features) {
        launchMapView(features)
    }

    /**
     * Launches the MapView and plots the location over map
     *
     */
    private fun launchMapView(feature: Features) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra(getString(R.string.extra_intent_feature), feature)
        startActivity(intent)
    }


    /**
     * Back pressed handler
     */
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else
            this.finish()
    }
}