package com.example.employeedirectory.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.employeedirectory.R
import com.example.employeedirectory.data.models.Employee
import com.example.employeedirectory.databinding.EmployeeListViewBinding
import com.example.employeedirectory.ui.EmployeeProfileFragment
import com.example.employeedirectory.ui.loadImage

class EmployeeDirectoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var employees = emptyList<Employee>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            EmployeeListViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PhotosViewHolder).bind(employees[position])
    }

    override fun getItemCount(): Int = employees.size

    fun updateData(list: List<Employee>) {
        employees = list
        notifyDataSetChanged()
    }

    class PhotosViewHolder(
        private val employeeListViewBinding: EmployeeListViewBinding,
    ) : RecyclerView.ViewHolder(employeeListViewBinding.root) {
        fun bind(employee: Employee?) {
            with(employeeListViewBinding) {
                employee?.apply {
                    profileImage.loadImage(photo_url_small ?: "")
                    fldEmployeeName.text = full_name
                    fldEmployeeTeam.text = team
                }
                profileCard.setOnClickListener {
                    createEmployeeProfileFragment(employee)
                }
            }
        }

        private fun createEmployeeProfileFragment(employee: Employee?) {
            val fm = employeeListViewBinding.root.context as FragmentActivity
            val fragment = EmployeeProfileFragment.newInstance(employee)
            fm.supportFragmentManager.beginTransaction()
                .replace(R.id.emp_dir_act, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
