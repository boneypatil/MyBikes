package com.digikrafi.mybikes.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.digikrafi.mybikes.base.BaseViewModel
import com.digikrafi.mybikes.model.BikeDashboardResponse
import com.digikrafi.mybikes.network.DashboardRepository
import com.digikrafi.mybikes.network.ErrorResult
import com.digikrafi.mybikes.network.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

/**
 * Created by rahul.p
 * Mocking  api test call
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class DashboardViewModelTest {

    lateinit var viewModel: DashboardViewModel
    lateinit var repo: DashboardRepository
    lateinit var application: Application



    @ExperimentalCoroutinesApi
    private val mainDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        repo = Mockito.mock(DashboardRepository::class.java)
        application = Mockito.mock(Application::class.java)

        viewModel = DashboardViewModel(
            repo = repo,
            application = application,
        )
        Dispatchers.setMain(mainDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainDispatcher.cancel()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchDashBoardContent success state success`() {
        val bodyResult = Mockito.mock(BikeDashboardResponse::class.java)

        runTest {
            Mockito.`when`(repo.getDashboardContentRepo()).thenReturn(
                Success(
                bodyResult
            )
            )
        }
        viewModel.fetchDashBoardContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Success)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `fetchLeaderboardInfo fail state error`() {
        runTest {
            Mockito.`when`(repo.getDashboardContentRepo())
                .thenReturn(ErrorResult(400, "", null))
        }
        viewModel.fetchDashBoardContent()
        assert(viewModel.state.value == BaseViewModel.BaseState.Error)
    }

}