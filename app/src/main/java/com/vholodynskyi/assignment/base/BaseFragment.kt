package com.vholodynskyi.assignment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.reflect.KClass

abstract class BaseFragment<Binding : ViewBinding, ViewModel : BaseViewModel> : Fragment() {
    protected abstract val bindingCallBack: (LayoutInflater, ViewGroup?, Boolean) -> Binding

    lateinit var binding: Binding

    protected abstract val kClass: KClass<ViewModel>
    val viewModel: ViewModel by lazy { getViewModel(kClass) { parametersOf(arguments) } }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = bindingCallBack.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews.invoke(binding)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.commonEffect.observe(viewLifecycleOwner) {
            when (it) {
                is NoInternet -> showToast("Has not connection.")
                is BackEndError -> showToast(message = "")
                is UnknownError -> showToast(message = "")
                is MessageError -> showToast(message = "")
                else -> showToast(message = "")
            }
        }
    }

    protected open val bindViews: Binding.() -> Unit = {}

    protected fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    protected fun popBackStack() {
        findNavController().popBackStack()
    }
}