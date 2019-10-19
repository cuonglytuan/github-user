package com.exercise.githubuser.presentation.presenter

import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.domain.repository.UserRepository
import com.exercise.githubuser.ext.convertInputStreamToString
import com.exercise.githubuser.presentation.contract.UserContract
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

@RunWith(PowerMockRunner::class)
class UserPresenterTest {
    @Mock
    private lateinit var repository: UserRepository
    @Mock
    private lateinit var view: UserContract.View

    private lateinit var presenter: UserPresenter
    private lateinit var schedulerProvider: BaseSchedulerProvider

    private lateinit var userList: List<User>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        schedulerProvider = ImmediateSchedulerProvider()
        presenter = UserPresenter(view, repository, schedulerProvider)

        val jsonInputStream = javaClass.getResourceAsStream("/user.json")
        var jsonBody = ""
        jsonInputStream?.let {
            jsonBody = String().convertInputStreamToString(it)
        }
        userList = User.parseUsers(jsonBody)
    }

    @Test
    fun start() {
        presenter.start()
        verify(view).initView()
    }

    @Test
    fun getUserSuccessFirstStartTest() {

        stubbingGetUsers().thenReturn(Observable.just(userList))
        stubbingIsOnline().thenReturn(true)

        presenter.getUser(0L)
        verify(view).showLoadingView()
        verify(view).onUserLoaded(userList)
        verify(view).hideLoadingView()
        Assert.assertTrue(presenter.canLoadMore)
        Assert.assertEquals(80, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserFailTest() {
        stubbingGetUsers().thenReturn(Observable.error(Exception()))
        stubbingIsOnline().thenReturn(true)

        presenter.getUser(0L)
        verify(view).showLoadingView()
        verify(view).onUserFail()
        verify(view).hideLoadingView()
        Assert.assertFalse(presenter.canLoadMore)
        Assert.assertEquals(0, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserEmptyTest() {
        stubbingGetUsers().thenReturn(Observable.just(ArrayList()))
        stubbingIsOnline().thenReturn(true)

        presenter.getUser(0L)
        verify(view).showLoadingView()
        verify(view).onUserEmpty()
        verify(view).hideLoadingView()
        Assert.assertFalse(presenter.canLoadMore)
        Assert.assertEquals(0, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserNoConnectionTest() {
        stubbingIsOnline().thenReturn(false)

        presenter.getUser(0L)
        verify(view).showLoadingView()
        verify(view).onUserFail()
        verify(view).hideLoadingView()
        Assert.assertTrue(presenter.canLoadMore)
        Assert.assertEquals(0, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserNextSuccessTest() {
        stubbingGetUsersNext().thenReturn(Observable.just(userList))
        stubbingIsOnline().thenReturn(true)

        presenter.isLoading = false
        presenter.since = 37
        presenter.canLoadMore = true
        presenter.getUserNext()
        verify(view).onUserLoaded(userList)
        Assert.assertTrue(presenter.canLoadMore)
        Assert.assertEquals(80, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserNextEndTest() {

        stubbingGetUsersEndNext().thenReturn(Observable.just(userList))
        stubbingIsOnline().thenReturn(true)

        presenter.isLoading = false
        presenter.since = 80
        presenter.canLoadMore = true
        presenter.getUserNext()
        Assert.assertFalse(presenter.canLoadMore)
        Assert.assertEquals(80, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserNextErrorTest() {

        stubbingGetUsersEndNext().thenReturn(Observable.error(Exception()))
        stubbingIsOnline().thenReturn(true)

        presenter.isLoading = false
        presenter.since = 80
        presenter.canLoadMore = true
        presenter.getUserNext()
        Assert.assertFalse(presenter.canLoadMore)
        Assert.assertEquals(80, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    @Test
    fun getUserNextNoConnectionTest() {
        stubbingIsOnline().thenReturn(false)

        presenter.isLoading = false
        presenter.since = 80
        presenter.canLoadMore = true
        presenter.getUserNext()
        Assert.assertTrue(presenter.canLoadMore)
        Assert.assertEquals(80, presenter.since)
        Assert.assertFalse(presenter.isLoading)
    }

    private fun stubbingGetUsers() = `when`(repository.getUsers(0L, 20))
    private fun stubbingIsOnline() = `when`(repository.isOnline())

    private fun stubbingGetUsersNext() = `when`(repository.getUsers(37, 20))
    private fun stubbingGetUsersEndNext() = `when`(repository.getUsers(80, 20))
}