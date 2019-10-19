package com.exercise.githubuser.presentation.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exercise.githubuser.R
import com.exercise.githubuser.data.entity.User
import com.exercise.githubuser.presentation.contract.UserContract
import com.exercise.githubuser.presentation.ui.activity.MainActivity
import com.exercise.githubuser.presentation.ui.adapter.UserAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.user_fragment.*
import javax.inject.Inject
import kotlin.math.roundToInt

class UserFragment : Fragment(), UserContract.View, UserAdapter.UserInfoListener {
    override fun onUserClick(user: User) {
        val parentActivity = activity as MainActivity
        parentActivity.changeFragment(
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(UserDetailFragment.KEY_USER_LOGIN, user.login)
                }},
            parentActivity.supportFragmentManager,
            false
        )
    }

    override fun showLoadingView() {
        relativeLayoutProgressBarReputation?.visibility = View.VISIBLE
    }

    override fun hideLoadingView() {
        relativeLayoutProgressBarReputation?.visibility = View.GONE
    }

    override fun onUserLoaded(user: List<User>) {
        userAdapter.update(user)
    }

    @Inject
    lateinit var mPresenter: UserContract.Presenter

    private lateinit var userAdapter: UserAdapter

    init {
        retainInstance = false
    }

    companion object {
        fun newInstance() = UserFragment()
        const val OFFSET_LOAD_MORE = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.start()
        mPresenter.getUser(0L)
    }

    override fun initView() {
        if (activity == null || !isAdded) {
            return
        }

        val parentActivity = activity as MainActivity
        parentActivity.supportActionBar?.show()

        activity?.let {
            val linearLayoutManager = LinearLayoutManager(it)
            recyclerViewUser.layoutManager = linearLayoutManager
            userAdapter =
                UserAdapter(it, ArrayList(), this)
            recyclerViewUser.setHasFixedSize(true)
            recyclerViewUser.adapter = userAdapter

            if (recyclerViewUser.itemDecorationCount == 0) {
                val bottomSpacing = resources.getDimension(R.dimen.bottom_loading_size).roundToInt()
                recyclerViewUser.addItemDecoration(BottomSpacesItemDecoration(bottomSpacing))
            }

            recyclerViewUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val totalItemCount = linearLayoutManager.itemCount
                    val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (dy > 0 && totalItemCount <= (lastVisibleItem + OFFSET_LOAD_MORE)) {
                        mPresenter.getUserNext()
                    }
                }
            })
        }
    }

    override fun setPresenter(presenter: UserContract.Presenter) {
        this.mPresenter = presenter
    }

    inner class BottomSpacesItemDecoration internal constructor(private val space: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            if (parent.getChildAdapterPosition(view) == state.itemCount - 1) {
                outRect.bottom = space
            }
        }
    }
}