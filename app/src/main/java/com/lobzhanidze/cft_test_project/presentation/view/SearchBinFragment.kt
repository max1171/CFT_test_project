package com.lobzhanidze.cft_test_project.presentation.view

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.lobzhanidze.cft_test_project.App
import com.lobzhanidze.cft_test_project.R
import com.lobzhanidze.cft_test_project.data.entity.BinModelDomain
import com.lobzhanidze.cft_test_project.data.entity.QueryBinHistory
import com.lobzhanidze.cft_test_project.domain.textChanges
import com.lobzhanidze.cft_test_project.presentation.view.recycler.RecyclerBinAdapter
import com.lobzhanidze.cft_test_project.presentation.viewModel.BinInformationViewModel
import com.lobzhanidze.cft_test_project.presentation.viewModel.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchBinFragment : Fragment() {

    companion object {
        val TAG = "SearchBinFragment"
    }

    lateinit var editBin: EditText
    lateinit var schemeData: TextView
    lateinit var brandData: TextView
    lateinit var lengthData: TextView
    lateinit var luhnData: TextView
    lateinit var typeData: TextView
    lateinit var prepaidData: TextView
    lateinit var countryData: TextView
    lateinit var bankNameData: TextView
    lateinit var bankUrlData: TextView
    lateinit var bankPhoneData: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    private var binModelDomainMap = BinModelDomain()

    lateinit var binAdapter: RecyclerBinAdapter

    private val historyList = mutableListOf<QueryBinHistory>()

    @Inject
    lateinit var factory: ViewModelFactory
    lateinit var viewModel: BinInformationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_bin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        App.instance.binComponent.inject(this)
        viewModel =
            ViewModelProvider(requireActivity(), factory).get(BinInformationViewModel::class.java)
        initElementPage()
        initAdapter()
        recyclerView.adapter = binAdapter
        initElementPage()
        initLoadData()
        initEditorActionListener()
        initClickCountry()
        viewModel.getHistoryQuery(requireContext())
    }

    private fun initEditorActionListener() {
        editBin
            .textChanges()
            .onEach { text ->
                if (text?.length!! < 4) {
                    addBinInformation(BinModelDomain())
                }
                if (text.length in 4..6) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }

            }
            .debounce(2000)
            .onEach { text ->
                if (text?.length!! >= 4 && text.length <= 6) {
                    viewModel.loadBinInformation(requireContext(), text.toString())
                }
            }
            .launchIn(lifecycleScope)
    }

    private fun initLoadData() {
        lifecycleScope.launchWhenStarted {
            viewModel.binInformation
                .collect { binInformation ->
                    addBinInformation(binInformation)
                    viewModel.getHistoryQuery(requireContext())
                    binModelDomainMap = binInformation
                }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.binHistoryQuery.collect { historyQuery ->
                historyList.clear()
                historyQuery.reverse()
                historyList.addAll(historyQuery)
                binAdapter.setItems(historyQuery)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resultStatus.collect { statusQuery ->
                if (!statusQuery) {
                    Toast.makeText(context, "БИН не существует", Toast.LENGTH_LONG).show()
                    progressBar.visibility = View.GONE
                    addBinInformation(BinModelDomain())
                }
            }
        }
    }

    private fun initAdapter() {
        binAdapter = RecyclerBinAdapter(
            LayoutInflater.from(requireContext()),
            object : RecyclerBinAdapter.OnClickListenerBin {
                override fun onBinClick(binHistory: QueryBinHistory, position: Int) {
                    editBin.clearComposingText()
                    editBin.setText(binHistory.binQuery)
                }
            })
    }

    private fun initClickCountry() {
        countryData.setOnClickListener { _ ->
            intentMap()
        }
    }

    private fun intentMap() {
        var latitude = binModelDomainMap.latitudeCountry
        var longitude = binModelDomainMap.longitudeCountry
        var locationName = binModelDomainMap.nameCountry
        val geoUri = "http://maps.google.com/maps?q=loc:$latitude,$longitude ($locationName)";
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        if (mapIntent.resolveActivity(requireContext().packageManager) != null) {
            requireContext().startActivity(mapIntent);
        }
    }

    private fun addBinInformation(binInformation: BinModelDomain) {
        schemeData.text = binInformation.scheme
        brandData.text = binInformation.brand
        lengthData.text = binInformation.lengthNumber.toString()
        luhnData.text = binInformation.luhnNumber
        typeData.text = binInformation.type
        prepaidData.text = binInformation.prepaid
        countryData.text = binInformation.nameCountry
        bankNameData.text = binInformation.nameBank
        bankUrlData.text = binInformation.urlBank
        bankPhoneData.text = binInformation.phoneBank

        progressBar.visibility = View.GONE
    }

    private fun initElementPage() {
        editBin = requireView().findViewById(R.id.edit_bin)
        schemeData = requireView().findViewById(R.id.scheme_data)
        brandData = requireView().findViewById(R.id.brand_data)
        lengthData = requireView().findViewById(R.id.length_data)
        luhnData = requireView().findViewById(R.id.luhn_data)
        typeData = requireView().findViewById(R.id.type_data)
        prepaidData = requireView().findViewById(R.id.prepaid_data)
        countryData = requireView().findViewById(R.id.country_data)
        bankNameData = requireView().findViewById(R.id.bank_name_data)
        bankUrlData = requireView().findViewById(R.id.bank_url_data)
        bankPhoneData = requireView().findViewById(R.id.bank_phone_data)
        recyclerView = requireActivity().findViewById(R.id.recycler_view)
        progressBar = requireActivity().findViewById(R.id.progress_Bar)

        countryData.paintFlags = countryData.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}

//@ExperimentalCoroutinesApi
//@CheckResult
//fun EditText.textChanges(): Flow<CharSequence?> {
//    return callbackFlow<CharSequence?> {
//        val listener = object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) = Unit
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
//                Unit
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                trySend(s)
//            }
//        }
//        addTextChangedListener(listener)
//        awaitClose { removeTextChangedListener(listener) }
//    }.onStart { emit(text) }
//}