package com.example.employeedirectory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.databinding.FragmentHomeBinding
import com.example.employeedirectory.ui.adapters.EmployeeDirectoryAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var employees: ArrayList<Employee>? = emptyList<Employee>() as? ArrayList<Employee>
    private lateinit var binding: FragmentHomeBinding
    private var employeeAdapter: EmployeeDirectoryAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            employees = it.getParcelableArrayList("employees_list")
        }
        employeeAdapter = EmployeeDirectoryAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // New employees, update the map

        binding.progressBar.visibility = View.GONE
        binding.employeesRv.apply {
            adapter = employeeAdapter
            layoutManager = LinearLayoutManager(context)
        }
        employees?.let { employeeAdapter?.updateData(it) }
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            employees?.let {
                employeeAdapter?.updateData(
                    it
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(employees: List<Employee>?) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    if (!employees.isNullOrEmpty()) {
                        putParcelableArrayList("employees_list", employees as java.util.ArrayList)
                    }
                }
            }
    }
}