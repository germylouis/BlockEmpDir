package com.example.employeedirectory.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirectory.data.repos.EmployeeRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EmployeeViewModel(private val employeeRepo: EmployeeRepoImpl?) : ViewModel() {
    private var _uiSuccessState = MutableStateFlow(LatestEmployeesUiState.Success(emptyList()))
    private var _uiErrorState = MutableStateFlow(LatestEmployeesUiState.Error(""))
    var uiState: StateFlow<LatestEmployeesUiState> = _uiSuccessState

    /**
     * Init block and Sealed class code from:
     * https://developer.android.com/kotlin/flow/stateflow-and-sharedflow
     */
    init {
        viewModelScope.launch {
            employeeRepo?.getEmployees()?.collect { latest ->
                when (latest) {
                    is LatestEmployeesUiState.Success -> {
                        _uiSuccessState.value = latest
                    }
                    is LatestEmployeesUiState.Loading -> {

                    }
                    else -> {
                        // _uiSuccessState.value =  MutableStateFlow(LatestEmployeesUiState.Error(""))
                        uiState = MutableStateFlow(latest)
                    }
                }

                Log.d(TAG, "germ: ${uiState.value}: ")
            }
        }
    }

    class Factory(app: Application, private val employeeRepo: EmployeeRepoImpl?) : ViewModelFactory(app) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmployeeViewModel(employeeRepo) as T
        }
    }


    companion object {
        val TAG: String = EmployeeViewModel::class.java.name
    }
}
