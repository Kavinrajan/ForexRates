package com.kavin.forex.currencies.view

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.kavin.forex.R
import com.kavin.forex.currencies.data.model.room.entities.Currencies
import com.kavin.forex.currencies.view.adapters.CurrenciesAdapter
import com.kavin.forex.currencies.common.ScreenState
import com.kavin.forex.currencies.common.gone
import com.kavin.forex.currencies.common.isAppFirstLaunchPerf
import com.kavin.forex.currencies.common.visible
import com.kavin.forex.currencies.data.model.CurrencyRates
import com.kavin.forex.currencies.view.adapters.decoration.GridSpacerDecoration
import com.kavin.forex.currencies.view.dialogs.Dialogs
import com.kavin.forex.currencies.view.viewmodel.CurrenciesViewModel
import com.kavin.forex.databinding.FragmentForexRatesBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment to show list of currencies and exchange rates
 */
@AndroidEntryPoint
class ForexFragment : Fragment() {

    private val currenciesViewModel: CurrenciesViewModel by viewModels()

    // view binding
    private lateinit var binding: FragmentForexRatesBinding

    // adapter to show currency values
    private val adapter: CurrenciesAdapter = CurrenciesAdapter()

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentForexRatesBinding.inflate(inflater)
        binding.circularProgress.gone()

        listenToCurrenciesData()
        showEmptyLabel()
        if (isAppFirstLaunchPerf(sharedPref)) {
            currenciesViewModel.fetchRemoteCurrencies()
        } else {
            currenciesViewModel.fetchLocalCurrencies()
        }

        (binding.currenciesSpinner.editText as? AutoCompleteTextView)?.setOnItemClickListener { dropdown, _, position, _ ->
            val baseCurrency = dropdown.adapter.getItem(position) as Currencies
            currenciesViewModel.changeSelectedCurrency(baseCurrency)
            binding.forexRecycler.smoothScrollToPosition(0)
        }
        binding.baseAmount.editText?.doAfterTextChanged { text ->
            if(!text.isNullOrEmpty()) {
                currenciesViewModel.changeAmount(text.toString().toDoubleOrNull())
            } else {
                showEmptyLabel()
            }

        }
        binding.baseAmount.editText?.setText(currenciesViewModel.baseAmount?.toString())
        binding.currenciesSpinner.editText?.setText(currenciesViewModel.baseCurrency?.toString())

        forexGridRecycler()

        return binding.root
    }

    private fun listenToCurrenciesData() {
        // to collect currencies
        currenciesViewModel.currencies.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> showLoadingCurrencies()
                is ScreenState.Success -> forexCurrencySpinner(it.uiData)
                is ScreenState.Error -> showErrorRetry()
            }
        }
        // to collect each currency exchange rates
        // observing currency values, to show by recycler view
        currenciesViewModel.currenciesRates.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> showLoadingValues()
                is ScreenState.Success -> { showData(it.uiData) }
                is ScreenState.Error -> { showError(it.message) }
            }
        }
    }
    private fun forexCurrencySpinner(data: List<Currencies>) {
        binding.currenciesSpinner.visible()
        binding.progressCurrencies.gone()
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_single_choice, data)
        (binding.currenciesSpinner.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }
    private fun forexGridRecycler() {
        binding.forexRecycler.setHasFixedSize(true)
        val view_columns = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 8 else 5
        binding.forexRecycler.layoutManager = GridLayoutManager(activity, view_columns)
        binding.forexRecycler.addItemDecoration(GridSpacerDecoration(view_columns, resources.getDimensionPixelSize(R.dimen.rv_adapter_item_space), false))
        binding.forexRecycler.adapter = adapter
    }
    private fun showLoadingCurrencies() {
        binding.currenciesSpinner.gone()
        binding.progressCurrencies.visible()
    }
    private fun showLoadingValues() {
        binding.emptyListLabel.gone()
        binding.forexRecycler.gone()
        binding.circularProgress.visible()
    }
    private fun showEmptyLabel() {
        binding.emptyListLabel.visible()
        binding.forexRecycler.gone()
        binding.circularProgress.gone()
    }
    private fun showErrorRetry() {
        Dialogs.showErrorDialogWithRetry(requireContext(),
            { currenciesViewModel.fetchLocalCurrencies() }, { requireActivity().finish() })

        binding.currenciesSpinner.gone()
        binding.progressCurrencies.gone()

        binding.forexRecycler.gone()
        binding.circularProgress.gone()
    }
    private fun showError(errMsg: String?) {
        Dialogs.showErrorDialog(requireContext(), errMsg)
        binding.currenciesSpinner.gone()
        binding.progressCurrencies.gone()

        binding.forexRecycler.gone()
        binding.circularProgress.gone()
    }
    private fun showData(currenciesRates: List<CurrencyRates>?) {
        binding.circularProgress.gone()
        if (currenciesRates != null) {
            binding.emptyListLabel.gone()
            binding.forexRecycler.visible()
            adapter.setData(currenciesRates)
        } else {
            binding.emptyListLabel.visible()
            binding.forexRecycler.gone()
        }
    }

}
