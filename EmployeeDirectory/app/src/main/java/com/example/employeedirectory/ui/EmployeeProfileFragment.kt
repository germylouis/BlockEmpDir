package com.example.employeedirectory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.employeedirectory.constants.Constants
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.databinding.FragmentEmployeeProfileBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeProfileFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeProfileBinding
    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            arguments?.let {
                employee = it.getParcelable(Constants.EMPLOYEE)
            }
        }else{
            savedInstanceState.let {
                employee = it.getParcelable(Constants.EMPLOYEE)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fldEmployeeName.text = employee?.full_name
        binding.fldEmployeeEmail.text = employee?.email_address
        binding.fldEmployeeTeam.text = employee?.team
        binding.fldBio.text = employee?.biography
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        employee?.let {
            outState.putParcelable(Constants.EMPLOYEE, it)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.run {
            employee = getParcelable(Constants.EMPLOYEE)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(employee: Employee?) =
            EmployeeProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(Constants.EMPLOYEE, employee)
                }
            }
    }
}