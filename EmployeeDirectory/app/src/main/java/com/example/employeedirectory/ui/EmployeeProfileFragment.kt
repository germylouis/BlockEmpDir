package com.example.employeedirectory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.databinding.FragmentEmployeeProfileBinding

/**
 * A simple [Fragment] subclass.
 * Use the [EmployeeProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EmployeeProfileFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val employee = arguments?.get("employee") as? Employee
        binding.fldEmployeeName.text = employee?.full_name
        binding.fldEmployeeEmail.text = employee?.email_address
        binding.fldEmployeeTeam.text = employee?.team
        binding.fldBio.text = employee?.biography
    }

    companion object {
        @JvmStatic
        fun newInstance(employee: Employee?) =
            EmployeeProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("employee", employee)
                }
            }
    }
}