package com.exercise.githubuser.presentation.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exercise.githubuser.R
import com.exercise.githubuser.data.entity.UserDetail
import com.exercise.githubuser.presentation.contract.UserDetailContract
import com.exercise.githubuser.presentation.ui.activity.MainActivity
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.user_detail_fragment.*
import kotlinx.android.synthetic.main.user_detail_info.*
import javax.inject.Inject

class UserDetailFragment : Fragment(), UserDetailContract.View {

    @Inject
    lateinit var mPresenter: UserDetailContract.Presenter

    private lateinit var login: String

    init {
        retainInstance = false
    }

    companion object {
        const val KEY_USER_LOGIN = "key_user_login"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            if (it.containsKey(KEY_USER_LOGIN)) {
                login = it.getString(KEY_USER_LOGIN, "")
            }
        }
        return inflater.inflate(R.layout.user_detail_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.start()
        mPresenter.getUserDetail(login)
    }

    override fun initView() {
        if (activity == null || !isAdded) {
            return
        }

        val parentActivity = activity as MainActivity
        parentActivity.supportActionBar?.hide()

        imageButtonClose.setOnClickListener {
            activity?.onBackPressed()
        }

        imageButtonCloseFail.setOnClickListener {
            activity?.onBackPressed()
        }

        buttonDetailRetry.setOnClickListener {
            mPresenter.getUserDetail(login)
        }
    }

    override fun setPresenter(presenter: UserDetailContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showLoadingView() {
        relativeLayoutProgressUserDetail.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        relativeLayoutProgressUserDetail.visibility = View.GONE
    }

    override fun onUserDetailLoaded(userDetail: UserDetail) {
        relativeLayoutDetailError.visibility = View.GONE
        textViewName.text = userDetail.name
        textViewDetailLogin.text = userDetail.login
        textViewDetailBio.text = userDetail.bio
        textViewDetailLocation.text = userDetail.location
        textViewDetailLink.text = userDetail.blog
        when (userDetail.siteAdmin) {
            true -> textViewDetailBadge.visibility = View.VISIBLE
            else -> textViewDetailBadge.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(userDetail.avatarUrl)) {
            Picasso.get().load(userDetail.avatarUrl).into(imageViewUserDetail)
        }
    }

    override fun onUserDetailFail() {
        relativeLayoutDetailError.visibility = View.VISIBLE
    }

}