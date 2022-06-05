package com.digikrafi.mybikes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.digikrafi.mybikes.base.BaseDashboardFragment
import com.digikrafi.mybikes.base.BaseViewModel
import com.digikrafi.mybikes.databinding.MainFragmentBinding
import com.digikrafi.mybikes.model.BikeDashboardResponse
import com.digikrafi.mybikes.network.ErrorResult
import com.digikrafi.mybikes.ui.adapter.BikeInfoAdapter
import com.digikrafi.mybikes.utils.enforceSingleScrollDirection
import com.digikrafi.mybikes.utils.show
import com.digikrafi.mybikes.utils.toast
import com.digikrafi.mybikes.viewmodel.DashboardViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * Created by rahul,p
 * Dashboard fragment that displays the list of bikes locations
 * and their related data
 */
class DashboardFragment : BaseDashboardFragment() {

    private val viewModel: DashboardViewModel by sharedViewModel()
    private lateinit var binding: MainFragmentBinding
    //private var dashboardData = mutableListOf<Content>()
    private val adapter by lazy {
        BikeInfoAdapter {
            onFeaturesSelected(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setObservers()
        viewModel.fetchDashBoardContent()
    }

    /**
     * Initializing Adapters
     *
     */
    private fun initAdapter() {
        val manager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        manager.initialPrefetchItemCount = 4
        binding.dashboardContentRV.layoutManager = manager
        binding.dashboardContentRV.adapter = this.adapter
        binding.dashboardContentRV.enforceSingleScrollDirection()
    }

    /**
     * Initializing Observers
     *
     */
    private fun setObservers() {
        viewModel.getDashboardContent().observe(viewLifecycleOwner, dashboardContentObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
        viewModel.state.observe(viewLifecycleOwner, loadingObserver)
    }

    /**
     * Errors handlers
     */
    private val errorObserver = Observer<ErrorResult<*>> {
        handleErrorInActivity(it)
    }

    private fun handleErrorInActivity(errorResult: ErrorResult<*>) {
        handleError(errorResult)
    }

    /**
     *  Observers Implementations
     *
     */
    private val loadingObserver = Observer<BaseViewModel.BaseState> { state ->
        when (state) {
            BaseViewModel.BaseState.Loading -> showLoader()
            BaseViewModel.BaseState.Success,
            BaseViewModel.BaseState.Error -> showLoader(false)
        }
    }

    /**
     * Loader hide and show
     *
     */
    private fun showLoader(show: Boolean = true) {
        binding.dashboardLoader show show
    }

    private val dashboardContentObserver = Observer<BikeDashboardResponse> {
        if (it.features.isEmpty()) {
            showErrorMessage()
            return@Observer
        }
      //  dashboardData = it.content.toMutableList()
        adapter.submitList(it.features)
    }

    /**
     * Showing error message
     *
     */
    private fun showErrorMessage() {
        context.toast("No data found,\n Please try again later ")
    }

//    private val carouselContentObserver = Observer<CarouselDataMapper> {
//        val content = dashboardData[it.pos]
//        content.images = it.images
//        dashboardData[it.pos] = content
//        adapter.notifyItemChanged(it.pos, content)
//    }

}