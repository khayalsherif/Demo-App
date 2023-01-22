package com.vholodynskyi.assignment.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.vholodynskyi.assignment.base.BaseFragment
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

open class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate
    override val kClass: KClass<DetailsViewModel>
        get() = DetailsViewModel::class

    private val args by navArgs<DetailsFragmentArgs>()

    override val bindViews: FragmentDetailsBinding.() -> Unit = {
        lifecycleScope.launch {
            viewModel.getContactItemById(args.id!!).collect {
                println(it)
            }
        }
    }
}