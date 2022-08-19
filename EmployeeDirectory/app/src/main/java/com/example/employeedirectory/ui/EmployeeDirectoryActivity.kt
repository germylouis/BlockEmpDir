package com.example.employeedirectory.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.datasources.EmployeeDataSource
import com.example.employeedirectory.data.datasources.RetrofitInstance
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import com.example.employeedirectory.databinding.EmployeeDirectoryActivityBinding
import com.example.employeedirectory.viewmodels.EmployeeViewModel
import kotlinx.coroutines.launch

class EmployeeDirectoryActivity : AppCompatActivity() {
    private val employeeRepo: EmployeeRepoImpl?
        get() = RetrofitInstance.getRetrofitInstance(Constants.BASE_EMPLOYEES_URL)?.create(
            EmployeeDataSource::class.java
        )?.let {
            EmployeeRepoImpl(
                it, Constants.EMPLOYEES_JSON_TYPE
            )
        }
    private val factory: ViewModelProvider.AndroidViewModelFactory
        get() = EmployeeViewModel.Factory(application, employeeRepo)
    private val viewModel: EmployeeViewModel by viewModels { factory }
    private var binding: EmployeeDirectoryActivityBinding? = null

    private var employees: ArrayList<Employee>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EmployeeDirectoryActivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var employees: ArrayList<Employee>? = null
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
                if (savedInstanceState == null) {
                    viewModel.getEmployees().collect {
                        employees = it as? ArrayList<Employee>
                    }
                } else {
                    savedInstanceState.run {
                        employees = getParcelableArrayList(Constants.EMPLOYEES_LIST)
                    }
                }

                // New employees, update the map
                Log.d(
                    TAG,
                    "germ: LatestEmployeesUiState.Success. $employees"
                )
                val fragment: HomeFragment =
                    HomeFragment.newInstance(employees)
                val fragmentManager = supportFragmentManager
                binding?.run {
                    fragmentManager.beginTransaction().replace(root.id, fragment)
                        .commit()
                }
                this@EmployeeDirectoryActivity.employees = employees as ArrayList<Employee>

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        employees?.let {
            outState.putParcelableArrayList(Constants.EMPLOYEES_LIST, it)
        }
    }

    companion object {
        val TAG: String = EmployeeDirectoryActivity::class.java.name
    }
}
