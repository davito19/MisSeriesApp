package com.davito.misseriesapp.ui.misseries

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davito.misseriesapp.databinding.FragmentMySeriesBinding
import com.davito.misseriesapp.model.Serie


class MisSeriesFragment : Fragment() {

    private var _binding: FragmentMySeriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var seriesAdapter : SeriesAdapter
    private var seriesList: ArrayList<Serie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val misSeriesViewModel =
            ViewModelProvider(this)[MisSeriesViewModel::class.java]

        _binding = FragmentMySeriesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.newSereiesButton.setOnClickListener{
            findNavController().navigate(MisSeriesFragmentDirections.actionNavigationMySeriesToNewSerieFragment())
        }


        seriesAdapter = SeriesAdapter(
            seriesList,
            onItemClicked = {serie -> Log.d("nombre", serie.name!!) }
        )


        binding.serriesRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MisSeriesFragment.requireContext())
            adapter = seriesAdapter
            setHasFixedSize(false)
        }

        misSeriesViewModel.loadSeries()

        misSeriesViewModel.errorMsg.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(requireContext(), errorMsg, Toast.LENGTH_LONG)
                .show()
        }

        misSeriesViewModel.seriesList.observe(viewLifecycleOwner) {serielist ->
            seriesAdapter.appendItems(serielist)
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}