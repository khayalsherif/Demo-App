package com.vholodynskyi.assignment.presentation.details

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vholodynskyi.assignment.base.BaseFragment
import com.vholodynskyi.assignment.databinding.FragmentDetailsBinding
import kotlin.reflect.KClass

open class DetailsFragment : BaseFragment<FragmentDetailsBinding, DetailsViewModel>() {

    override val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailsBinding
        get() = FragmentDetailsBinding::inflate
    override val kClass: KClass<DetailsViewModel>
        get() = DetailsViewModel::class

    override val bindViews: FragmentDetailsBinding.() -> Unit = {

    }
}