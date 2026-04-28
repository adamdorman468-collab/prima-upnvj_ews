package com.fik.upnvj.ews.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.upnvj.prima.R
import com.fik.upnvj.ews.data.api.RetrofitClient
import com.fik.upnvj.ews.data.repository.MainRepository
import com.upnvj.prima.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupListeners()
        observeUiState()
    }

    private fun setupViewModel() {
        val repository = MainRepository(RetrofitClient.apiService)
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(repository) as T
            }
        }
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupListeners() {
        binding.btnPredict.setOnClickListener {
            if (validateInput()) {
                val ips1 = binding.etIps1.text.toString().toDouble()
                val ips2 = binding.etIps2.text.toString().toDouble()
                val ips3 = binding.etIps3.text.toString().toDouble()
                val ips4 = binding.etIps4.text.toString().toDouble()
                val sks = binding.etSks.text.toString().toInt()
                val jk = if (binding.rbLaki.isChecked) 1 else 0

                viewModel.predict(ips1, ips2, ips3, ips4, sks, jk)
            }
        }
    }

    private fun validateInput(): Boolean {
        val ipsFields = listOf(binding.etIps1, binding.etIps2, binding.etIps3, binding.etIps4)
        
        for (field in ipsFields) {
            val value = field.text.toString()
            if (value.isEmpty()) {
                field.error = "Harus diisi"
                return false
            }
            val ips = value.toDoubleOrNull()
            if (ips == null || ips < 0.0 || ips > 4.0) {
                field.error = "Range IPS 0.0 - 4.0"
                return false
            }
        }

        if (binding.etSks.text.toString().isEmpty()) {
            binding.etSks.error = "Harus diisi"
            return false
        }

        if (binding.rgJk.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Pilih Jenis Kelamin", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun observeUiState() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnPredict.isEnabled = false
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnPredict.isEnabled = true
                    showResultDialog(state.data.prediction, state.data.confidence, state.data.message)
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnPredict.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
                is UiState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnPredict.isEnabled = true
                }
            }
        }
    }

    private fun showResultDialog(prediction: Int, confidence: Double, message: String) {
        val resultTitle = if (prediction == 1) "AMAN / TEPAT WAKTU" else "BAHAYA / POTENSI DO"
        val icon = if (prediction == 1) android.R.drawable.ic_dialog_info else android.R.drawable.ic_dialog_alert
        
        AlertDialog.Builder(this)
            .setTitle(resultTitle)
            .setMessage("Confidence: $confidence%\n\n$message")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setIcon(icon)
            .show()
    }
}
