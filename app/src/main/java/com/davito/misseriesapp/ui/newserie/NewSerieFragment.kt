package com.davito.misseriesapp.ui.newserie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.davito.misseriesapp.R
import com.davito.misseriesapp.databinding.FragmentNewSerieBinding

class NewSerieFragment : Fragment() {

    private lateinit var viewModel: NewSerieViewModel
    private lateinit var newSerieBinding: FragmentNewSerieBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        newSerieBinding = FragmentNewSerieBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[NewSerieViewModel::class.java]
        val view = newSerieBinding.root

        with(newSerieBinding){
            button.setOnClickListener {
                viewModel.validateData(
                    nameEditText.text.toString(),
                    seasonEditText.text.toString(),
                    summryEditText.text.toString(),
                    scoreEditText.text.toString(),
                    checkBox.isChecked,
                    checkBox2.isChecked,
                    checkBox3.isChecked,
                    checkBox4.isChecked,
                    checkBox5.isChecked,
                    checkBox6.isChecked
                )
            }
        }

        viewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        viewModel.createSerieSucces.observe(viewLifecycleOwner) {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return view
    }
}