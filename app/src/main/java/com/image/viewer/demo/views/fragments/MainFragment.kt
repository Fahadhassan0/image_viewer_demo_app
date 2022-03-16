package com.image.viewer.demo.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.image.viewer.demo.R
import com.image.viewer.demo.base.BaseFragment
import com.image.viewer.demo.data.EdittextModel
import com.image.viewer.demo.data.ImageModel
import com.image.viewer.demo.databinding.FragmentMainBinding
import com.image.viewer.demo.listeners.OnDialogTextChangedListener
import com.image.viewer.demo.utilities.loadFull
import com.image.viewer.demo.utilities.showRenameDialog
import com.image.viewer.demo.utilities.showToast
import com.image.viewer.demo.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var imageModel: ImageModel? = null
    private var isTextAdded = false

    // This property is only valid between onCreateView and
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container!!, false)
        return binding.root
    }

    override fun clicks() {
        binding.apply {
            fabAddText.setOnClickListener {
                if (!isTextAdded) {
                    showDialog(R.string.label_add_text, R.string.label_add)
                } else
                    showDialog(R.string.label_edit_text, R.string.label_edit)
            }
        }
    }

    private fun showDialog(title: Int, yes: Int) {
        showRenameDialog(
            context,
            title,
            yes,
            object : OnDialogTextChangedListener {
                override fun onTextChanged(str: String?) {
                    if (!isTextAdded) {
                        mainViewModel.setTextAdded(true)
                        mainViewModel.addText(
                            createMarker(
                                imageModel?.translated_width?.div(2),
                                imageModel?.translated_height?.div(2),
                                str
                            )
                        )
                    } else {
                        binding.editorOverlay.editText(str)
                    }

                }
            }
        )

    }

    override fun initRef() {
        //attaching editor overlay with image view
        binding.apply {
            editorOverlay.attachToImage(ivDocument)
            loadFull(ivDocument, R.drawable.img_document, R.color.grey)
        }

        //calling get image size api
        attachViewModelObservers()
    }

    private fun createMarker(px: Int?, py: Int?, str: String?): EdittextModel {
        val editableText: EdittextModel = EdittextModel()
            .setLocation(px, py)
        editableText.setText(str)
        return editableText
    }

    private fun attachViewModelObservers() {

        mainViewModel.isTextAdded().observe(this) {
            isTextAdded = it
            if (it)
                binding.fabAddText.setImageResource(R.drawable.ic_edit)
            else
                binding.fabAddText.setImageResource(R.drawable.ic_add)
        }

        mainViewModel.getText().observe(this) {
            binding.editorOverlay.addText(
                it
            )
        }
        mainViewModel.getImageSize().observe(this, mObserver)
    }

    private val mObserver = Observer<ImageModel> {
        if (it != null) {
            imageModel = it
        } else
            context?.showToast(getString(R.string.message_some_error_occurred))
    }

    // onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}