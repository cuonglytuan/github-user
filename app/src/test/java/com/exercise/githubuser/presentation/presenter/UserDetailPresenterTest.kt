package com.exercise.githubuser.presentation.presenter

import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.domain.repository.UserDetailRepository
import com.exercise.githubuser.presentation.contract.UserDetailContract
import com.exercise.githubuser.utils.scheduler.BaseSchedulerProvider
import com.exercise.githubuser.utils.scheduler.ImmediateSchedulerProvider
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.modules.junit4.PowerMockRunner
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

@RunWith(PowerMockRunner::class)
class UserDetailPresenterTest {
    @Mock
    private lateinit var repository: UserDetailRepository
    @Mock
    private lateinit var view: UserDetailContract.View

    private lateinit var presenter: UserDetailPresenter
    private lateinit var schedulerProvider: BaseSchedulerProvider

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        schedulerProvider = ImmediateSchedulerProvider()
        presenter = UserDetailPresenter(view, repository, schedulerProvider)
    }

    @Test
    fun start() {
        presenter.start()
        verify(view).initView()
    }

    @Test
    fun getUserDetailSuccessTest() {

        val jsonInputStream = javaClass.getResourceAsStream("/userdetail.json")
        var jsonBody = ""
        jsonInputStream?.let {
             jsonBody = convertInputStreamToString(it)
        }

        val userDetail = UserDetail.parseUserDetail(jsonBody)

        stubbingGetUserDetail().thenReturn(Observable.just(userDetail))
        stubbingIsOnline().thenReturn(true)

        presenter.getUserDetail("defunkt")
        verify(view).showLoadingView()
        verify(view).onUserDetailLoaded(userDetail)
        verify(view).hideLoadingView()
    }

    @Test
    fun getUserDetailFailTest() {
        stubbingGetUserDetail().thenReturn(Observable.error(Exception()))
        stubbingIsOnline().thenReturn(true)

        presenter.getUserDetail("defunkt")
        verify(view).showLoadingView()
        verify(view).onUserDetailFail()
        verify(view).hideLoadingView()
    }

    @Test
    fun getUserDetailNoConnectionTest() {
        stubbingIsOnline().thenReturn(false)

        presenter.getUserDetail("defunkt")
        verify(view).showLoadingView()
        verify(view).onUserDetailFail()
        verify(view).hideLoadingView()
    }

    @Throws(IOException::class)
    private fun convertInputStreamToString(inputStream: InputStream): String {
        val reader = InputStreamReader(inputStream)
        val builder = StringBuilder()
        val buffer = CharArray(512)
        var read: Int
        do {
            read = reader.read(buffer)
            if (read <= 0) {
                break
            }
            builder.append(buffer, 0, read)

        } while (true)
        return builder.toString()
    }

    private fun stubbingGetUserDetail() = `when`(repository.getUserDetail("defunkt"))
    private fun stubbingIsOnline() = `when`(repository.isOnline())
}