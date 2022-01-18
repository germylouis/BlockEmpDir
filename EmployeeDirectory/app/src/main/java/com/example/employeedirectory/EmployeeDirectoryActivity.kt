package com.example.employeedirectory

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.RetrofitInstance
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import com.example.employeedirectory.databinding.EmployeeDirectoryActivityBinding
import com.example.employeedirectory.ui.adapters.EmployeeDirectoryAdapter
import com.example.employeedirectory.viewmodels.EmployeeViewModel
import kotlinx.coroutines.launch

class EmployeeDirectoryActivity : AppCompatActivity() {
    private val employeeRepo: EmployeeRepoImpl
        get() = EmployeeRepoImpl(
            RetrofitInstance.getRetrofitInstance(Constants.EMPLOYEES_URL)?.create(
                EmployeeDataSource::class.java
            )
        )
    private val factory: ViewModelProvider.AndroidViewModelFactory
        get() = EmployeeViewModel.Factory(application, employeeRepo)
    private val viewModel: EmployeeViewModel by viewModels { factory }
    private var binding: EmployeeDirectoryActivityBinding? = null
    private var employeeAdapter: EmployeeDirectoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmployeeDirectoryActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        employeeAdapter = EmployeeDirectoryAdapter()

        /**
         * Code block from:
         * https://medium.com/androiddevelopers/repeatonlifecycle-api-design-story-8670d1a7d333
         * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
         */
        lifecycleScope.launch {
            // Suspend the coroutine until the lifecycle is DESTROYED.
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from locations when the lifecycle is STARTED
                // and stop collecting when the lifecycle is STOPPED
                viewModel.uiState.collect { state ->
                    // New employees, update the map
                    when (state) {
                        is EmployeeViewModel.LatestEmployeesUiState.Success -> {
                            Log.d(
                                TAG,
                                "germ: Size: ${state.employees?.size}."
                            )
                            binding?.employeesRv?.apply {
                                adapter = employeeAdapter
                                layoutManager = LinearLayoutManager(this@EmployeeDirectoryActivity)
                            }
                            state.employees?.let { employeeAdapter?.updateData(it) }
                            binding?.swipeRefresh?.setOnRefreshListener {
                                binding?.swipeRefresh?.isRefreshing = false
                                state.employees?.let {
                                    employeeAdapter?.updateData(
                                        it
                                    )
                                }
                            }
                        }
                        is EmployeeViewModel.LatestEmployeesUiState.Error -> Log.d(TAG, "germ: ${state.exception}.")
                    }
                }
            }
            // Note: at this point, the lifecycle is DESTROYED!
        }


    }

    companion object {
        val TAG: String = EmployeeDirectoryActivity::class.java.name
    }
}
