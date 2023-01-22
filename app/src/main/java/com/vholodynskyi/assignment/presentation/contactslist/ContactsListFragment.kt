package com.vholodynskyi.assignment.presentation.contactslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import com.vholodynskyi.assignment.base.BaseFragment
import com.vholodynskyi.assignment.databinding.FragmentContactsListBinding
import com.vholodynskyi.assignment.presentation.contactslist.adapter.ContactAdapter
import com.vholodynskyi.assignment.common.ClickListener
import com.vholodynskyi.assignment.common.NetworkResult
import com.vholodynskyi.assignment.common.SwipeListener
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

open class ContactsListFragment : BaseFragment<FragmentContactsListBinding, ContactListViewModel>(),
    ClickListener, SwipeListener {

    private val contactAdapter by lazy { ContactAdapter(this, this) }

    private var idList = ArrayList<String>()
    private var isActive = false

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentContactsListBinding
        get() = FragmentContactsListBinding::inflate
    override val kClass: KClass<ContactListViewModel>
        get() = ContactListViewModel::class

    override val bindViews: FragmentContactsListBinding.() -> Unit = {

        integrationRcView()
        setRefreshLayout()

        lifecycleScope.launch {
            viewModel.contactResponse.collect { data ->
                contactAdapter.setData(data)
                idList.clear()
                data.forEach { item -> idList.add(item.id.toString()) }
            }
        }

        lifecycleScope.launch {
            viewModel.syncStatus.collect {
                when (it) {
                    is NetworkResult.Success -> {
                        if (isActive) binding.refreshLayout.finishRefreshing()
                    }
                    is NetworkResult.Error -> {
                        showToast(message = it.message!!)
                    }
                    is NetworkResult.Loading -> {}
                }
            }
        }

    }

    private fun integrationRcView() {
        binding.contactList.layoutManager = LinearLayoutManager(context)
        binding.contactList.adapter = contactAdapter
    }

    private fun setRefreshLayout() {
        binding.refreshLayout.setOnRefreshListener(object :
            LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                isActive = true
                viewModel.syncData()
            }
        })
    }

    override fun onClick(view: View, position: Int) {
        isActive = false
        findNavController().navigate(
            ContactsListFragmentDirections.actionContactListToDetails(
                idList[position]
            )
        )
    }

    override fun onSwipe(view: View, position: Int) {
    }
}